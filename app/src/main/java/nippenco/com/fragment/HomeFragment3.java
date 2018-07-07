package nippenco.com.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aigestudio.wheelpicker.WheelPicker;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.LineChartView;
import nippenco.com.Common;
import nippenco.com.MainActivity;
import nippenco.com.R;
import nippenco.com.api_model.login.Device;
import nippenco.com.api_model.login.Login;

import static nippenco.com.Constant.host;
import static nippenco.com.Constant.login;

/**
 * Created by aishwarydhare on 03/02/18.
 */

public class HomeFragment3 extends Fragment implements WheelPicker.OnItemSelectedListener{

    private Context activity_context, frag_context;
    private TextView meter_name_tv;
    private TextView notiff_count_tv;
    private ImageView notiff_iv;
    private String TAG = "NPN_LOG";
    private ArrayList<String> picker_values;
    private WheelPicker wheel_picker;
    private Device login_datum_device;
    private FrameLayout line_chart_fl;
    static String selected_feed_parameter = "";
    private DetailedLineDataChartFragment feed_fragment;
    private boolean is_fetching_data = false;
    private JsonObjectRequest update_data_request;
    private Runnable data_updation_runnable;
    private Handler data_updation_handler;
    private TextView last_update_tv, goto_feed_tv;
    static TextView current_value_tv, nodata_tv;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity_context = context;
        frag_context = getContext();
        Log.d(TAG, "onAttach: ");
    }


    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)activity_context).selected_frag_id = 1;
        try {
            if(data_updation_handler != null){
                if(data_updation_runnable != null){
                    if (!is_fetching_data) {
                        data_updation_handler.postDelayed(data_updation_runnable, 5000);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_home_3, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initLayoutVars(view);

        nodata_tv.setVisibility(View.GONE);
        line_chart_fl.setVisibility(View.GONE);

        picker_values = new ArrayList<>();

        View.OnClickListener notiff_click_listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)activity_context).set_fragment(5);
            }
        };

        notiff_iv.setOnClickListener(notiff_click_listener);

        update_UI();

        notiff_count_tv.setText(""+Common.getInstance().login_datum.getData().getUnreadAlerts());
        notiff_count_tv.setVisibility(View.GONE);

        wheel_picker.setOnItemSelectedListener(this);
        wheel_picker.setCurved(true);
        wheel_picker.setIndicator(true);
        wheel_picker.setIndicatorColor(getActivity().getResources().getColor(R.color.white_background));
        wheel_picker.setSelectedItemTextColor(getActivity().getResources().getColor(R.color.white_background));
        wheel_picker.setItemTextColor(getActivity().getResources().getColor(R.color.grey_text));

        view.findViewById(R.id.meter_select_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMailboxSelectDialog();
            }
        });

        goto_feed_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.getInstance().device_history_for = picker_values.get(wheel_picker.getCurrentItemPosition());
                Common.getInstance().device_history_for_pos = wheel_picker.getCurrentItemPosition();
                ((MainActivity)getActivity()).set_fragment(2);
            }
        });
    }


    private void initLayoutVars(View v) {
        meter_name_tv = v.findViewById(R.id.meter_name_tv);
        notiff_count_tv = v.findViewById(R.id.notiff_count_tv);
        notiff_iv = v.findViewById(R.id.notiff_iv);
        wheel_picker = v.findViewById(R.id.wheel_picker);
        line_chart_fl = v.findViewById(R.id.line_chart_fl);
        nodata_tv = v.findViewById(R.id.nodata_tv);
        last_update_tv = v.findViewById(R.id.last_update_tv);
        current_value_tv = v.findViewById(R.id.current_value_tv);
        goto_feed_tv = v.findViewById(R.id.goto_feed_tv);
    }


    void update_UI() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        try {
            int temp_device_pos = sharedPreferences.getInt("last_meter", -1);
            if(temp_device_pos != -1){
                if(Common.getInstance().login_datum.getData().getDevices().getDevices().get(temp_device_pos) != null){
                    Common.getInstance().selected_login_device = temp_device_pos;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        meter_name_tv.setText(Common.getInstance().login_datum.getData().getDevices().getDevices().get(Common.getInstance().selected_login_device).getName());

        login_datum_device = Common.getInstance().login_datum.getData().getDevices().getDevices().get(Common.getInstance().selected_login_device);

        if (login_datum_device.getLatestData().size() != picker_values.size()) {
            picker_values.clear();
            for (int i = 0; i < login_datum_device.getLatestData().size(); i++) {
                picker_values.add(login_datum_device.getLatestData().get(i).getName());
            }

            wheel_picker.setData(picker_values);
        }

        if (selected_feed_parameter.equalsIgnoreCase("")) {
            wheel_picker.setSelectedItemPosition(0);
            selected_feed_parameter = (String) picker_values.get(0);
        }

        if(Common.getInstance().device_history_for_pos != -1){
            wheel_picker.setSelectedItemPosition(Common.getInstance().device_history_for_pos);
            selected_feed_parameter = (String) picker_values.get(Common.getInstance().device_history_for_pos);

        }

//        if (feed_fragment != null) {
//            getActivity().getSupportFragmentManager().beginTransaction().detach(feed_fragment).attach(feed_fragment).commit();
//        } else {
            feed_fragment = new DetailedLineDataChartFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(line_chart_fl.getId(), feed_fragment).commit();
//        }
        line_chart_fl.setVisibility(View.VISIBLE);

        if(data_updation_runnable == null){
            data_updation_runnable = new Runnable() {
                @Override
                public void run() {
                    if (!is_fetching_data) {
                        Log.i(TAG, "run: requesting data");
                        update_data();
                    }
                }
            };
            data_updation_handler = new Handler();
            data_updation_handler.postDelayed(data_updation_runnable, 5000);
        }

        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd  hh:mm:ss");
        last_update_tv.setText(dateFormatter.format(Calendar.getInstance().getTime()));
    }


    void update_graph(){
        getActivity().getSupportFragmentManager().beginTransaction().detach(feed_fragment).attach(feed_fragment).commit();
        line_chart_fl.setVisibility(View.VISIBLE);
    }


    void update_data(){

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        String url = host + login;
        String user_name = sharedPreferences.getString("user_name", "");
        String user_pass = sharedPreferences.getString("user_pass", "");

        Map<String, String> payload = new HashMap<>();
        payload.put("username", user_name);
        payload.put("password", user_pass);
        JSONObject jsonPayload = new JSONObject(payload);

        update_data_request = new JsonObjectRequest(url, jsonPayload,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        is_fetching_data = false;
                        if(response.optInt("ResponseCode") != 200){
                            Log.e(TAG, "onResponse: Invalid Response");
                            data_updation_handler.postDelayed(data_updation_runnable, 5000);
                            return;
                        }
                        try{
                            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                            Common.getInstance().login_datum = gson.fromJson(response.toString(), Login.class);
                            Common.getInstance().alerts_arr = Common.getInstance().login_datum.getAllNormalizedAlerts();
                            data_updation_handler.postDelayed(data_updation_runnable, 5000);
                            is_fetching_data = false;
                            update_UI();
                            Log.i(TAG, "run: data updated");
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // if error received
                        error.printStackTrace();
                        Log.e(TAG, "onResponse: Invalid Fetched");
                        data_updation_handler.postDelayed(data_updation_runnable, 5000);
                    }
                });

        update_data_request.setRetryPolicy(new DefaultRetryPolicy( 15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        is_fetching_data = true;
        ((MainActivity)getActivity()).requestQueue.add(update_data_request);

    }


    void openMailboxSelectDialog(){
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(frag_context);
        builderSingle.setTitle("Select Meter");

        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(frag_context, android.R.layout.select_dialog_item);
        for (int i = 0; i < Common.getInstance().login_datum.getData().getDevices().getDevices().size(); i++) {
            arrayAdapter.add(Common.getInstance().login_datum.getData().getDevices().getDevices().get(i).getName());
        }

        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which != Common.getInstance().selected_login_device) {
                    String strName = arrayAdapter.getItem(which);
                    Log.d(TAG, "onClick: "+ strName);
                    Common.getInstance().selected_login_device = which;
                    SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    sharedPreferences.edit().putInt("last_meter", Common.getInstance().selected_login_device).apply();
                    update_UI();
                }
                dialog.dismiss();
            }
        });
        builderSingle.show();
    }

    @Override
    public void onItemSelected(WheelPicker picker, Object data, int position) {
        Log.d(TAG, "onItemSelected: " + "" + picker_values.get(position));
        selected_feed_parameter = picker_values.get(position);
        update_graph();
    }


    public static class DetailedLineDataChartFragment extends Fragment {

        Context activity_context, frag_context;
        private String TAG = "NPN_LOG";
        private LineChartView chart;
        private LineChartData data;
        private int numberOfLines = 1;
        private boolean hasAxes = false;
        private boolean hasAxesNames = true;
        private boolean hasLines = true;
        private boolean hasPoints = true;
        private ValueShape shape = ValueShape.CIRCLE;
        private boolean isFilled = true;
        private boolean hasLabels = false;
        private boolean isCubic = false;
        private boolean hasLabelForSelected = false;
        private boolean pointsHaveDifferentColor;
        private boolean hasGradientToTransparent = true;

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            this.activity_context = context;
            frag_context = getContext();
            Log.d(TAG, "onAttach: ");
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);
            return inflater.inflate(R.layout.fragment_detailed_feed_line_chart, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            chart = view.findViewById(R.id.chart);
            chart.setOnValueTouchListener(new LineValueTouchListener());
            chart.setOverScrollMode(View.OVER_SCROLL_ALWAYS);

            generateData();
        }

        private void generateData() {
            List<Line> lines = new ArrayList<Line>();
            int numberOfPoints = 0;
            List<String> dbl_arr = new ArrayList<>();
//            ArrayList<String> str_arr = new ArrayList<>();

            Device device = Common.getInstance().login_datum.getData().getDevices().getDevices().get(Common.getInstance().selected_login_device);

            for (int i = 0; i < device.getLatestData().size(); i++) {
                if(device.getLatestData().get(i).getName().equalsIgnoreCase(selected_feed_parameter)) {
                    current_value_tv.setText(""+ device.getLatestData().get(i).getValue());
                    for (int j = 0; j < device.getLatestData().get(i).getPastValues().size(); j++) {
                        dbl_arr.add("" + device.getLatestData().get(i).getPastValues().get(j));
                    }
                    dbl_arr.add("" + device.getLatestData().get(i).getValue());
                    break;
                }
            }

            boolean is_at_least_two_nonzero = true;
            int tmp = 0;
            for (int i = 0; i < dbl_arr.size(); i++) {
                if(!dbl_arr.get(i).equalsIgnoreCase(""+0.0)){
                    tmp += 1;
                    if (tmp >= 2) {
                        is_at_least_two_nonzero = false;
                        break;
                    }
                }
            }

            boolean is_all_data_same = true;
            double tmp2 = -1.0;
            for (int i = 0; i < dbl_arr.size(); i++) {
                if (tmp2 == -1.0) {
                    tmp2 = Double.parseDouble(dbl_arr.get(i));
                } else {
                    if(Double.parseDouble(dbl_arr.get(i)) != tmp2){
                        is_all_data_same = false;
                        break;
                    }
                }
            }

            if(is_all_data_same){
                nodata_tv.setText("Data is consistently same | no graph");
            } else {
                nodata_tv.setText("No Recent Data Found");
            }

            if(is_at_least_two_nonzero || is_all_data_same){
                chart.setVisibility(View.GONE);
                nodata_tv.setVisibility(View.VISIBLE);
            } else {
                nodata_tv.setVisibility(View.GONE);
                chart.setVisibility(View.VISIBLE);
            }

            numberOfPoints = dbl_arr.size();

            for (int i = 0; i < numberOfLines; ++i) {

                List<PointValue> values = new ArrayList<PointValue>();
                for (int j = 0; j < numberOfPoints; ++j) {
                    float value;
                    value = Float.parseFloat("" + dbl_arr.get(j));
                    values.add(new PointValue(j, value));
                }

                Line line = new Line(values);
                line.setColor(ChartUtils.COLORS[i]);
                line.setShape(shape);
                line.setCubic(isCubic);
                line.setFilled(isFilled);
                line.setHasLabels(hasLabels);
                line.setHasLabelsOnlyForSelected(hasLabelForSelected);
                line.setHasLines(hasLines);
                line.setHasPoints(hasPoints);
                line.setHasGradientToTransparent(hasGradientToTransparent);
                if (pointsHaveDifferentColor) {
                    line.setPointColor(ChartUtils.COLORS[(i + 1) % ChartUtils.COLORS.length]);
                }
                lines.add(line);
            }

            data = new LineChartData(lines);

            if (hasAxes) {
                Axis axisX = new Axis();
                Axis axisY = new Axis().setHasLines(true);
                if (hasAxesNames) {
                    axisX.setName("Recent Data");
                }
                data.setAxisXBottom(axisX);
                data.setAxisYLeft(axisY);
            } else {
                data.setAxisXBottom(null);
                data.setAxisYLeft(null);
            }

            data.setBaseValue(Float.NEGATIVE_INFINITY);
            chart.setLineChartData(data);

        }

        private class LineValueTouchListener implements LineChartOnValueSelectListener {
            @Override
            public void onValueSelected(int lineIndex, int pointIndex, PointValue value) {
                Toast.makeText(activity_context, "value: "+ value.getY(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onValueDeselected() {

            }
        }
    }


    @Override
    public void onPause() {
        try {
            if (update_data_request != null) {
                update_data_request.cancel();
            }
            data_updation_handler.removeCallbacks(data_updation_runnable);
            is_fetching_data = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onPause();
    }




}

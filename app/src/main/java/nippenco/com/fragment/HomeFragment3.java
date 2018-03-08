package nippenco.com.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.ArrayList;
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
import nippenco.com.OnboardingActivity;
import nippenco.com.R;
import nippenco.com.api_model.login.Device;
import nippenco.com.api_model.login.Login;
import nippenco.com.utils.HorizontalPicker;

import static nippenco.com.Constant.host;
import static nippenco.com.Constant.login;

/**
 * Created by aishwarydhare on 03/02/18.
 */

public class HomeFragment3 extends Fragment implements HorizontalPicker.OnItemSelected, HorizontalPicker.OnItemClicked {

    Context activity_context, frag_context;
    private TextView meter_name_tv;
    private TextView notiff_count_tv;
    private ImageView notiff_iv;
    private String TAG = "NPN_LOG";
    String[] picker_values;
    private HorizontalPicker horizontal_picker;
    private Device login_datum_device;
    FrameLayout line_chart_fl;
    private View nodata_tv;
    static String selected_feed_parameter = "";
    private DetailedLineDataChartFragment feed_fragment;
    private boolean is_fetching_data;
    JsonObjectRequest update_data_request;
    private Runnable data_updation_runnable;
    private Handler data_updation_handler;

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
        Log.d(TAG, "onResume: ");
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

        View.OnClickListener notiff_click_listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)activity_context).set_fragment(5);
            }
        };

        notiff_iv.setOnClickListener(notiff_click_listener);

        update_UI();

        notiff_count_tv.setText(""+Common.getInstance().login_datum.getData().getUnreadAlerts());

        horizontal_picker.setOnItemClickedListener(this);
        horizontal_picker.setOnItemSelectedListener(this);

        view.findViewById(R.id.meter_select_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMailboxSelectDialog();
            }
        });
    }


    private void initLayoutVars(View v) {
        meter_name_tv = v.findViewById(R.id.meter_name_tv);
        notiff_count_tv = v.findViewById(R.id.notiff_count_tv);
        notiff_iv = v.findViewById(R.id.notiff_iv);
        horizontal_picker = v.findViewById(R.id.picker);
        line_chart_fl = v.findViewById(R.id.line_chart_fl);
        nodata_tv = v.findViewById(R.id.nodata_tv);
    }


    void update_UI() {
        meter_name_tv.setText(Common.getInstance().login_datum.getData().getDevices().getDevices().get(Common.getInstance().selected_login_device).getName());

        login_datum_device = Common.getInstance().login_datum.getData().getDevices().getDevices().get(Common.getInstance().selected_login_device);

        picker_values = new String[login_datum_device.getLatestData().size()];
        for (int i = 0; i < login_datum_device.getLatestData().size(); i++) {
            picker_values[i] = login_datum_device.getLatestData().get(i).getName();
        }

        horizontal_picker.setValues(picker_values);

        if (selected_feed_parameter.equalsIgnoreCase("")) {
            horizontal_picker.setSelectedItem(0);
            selected_feed_parameter = picker_values[0];
        }

        if (feed_fragment != null) {
            getActivity().getSupportFragmentManager().beginTransaction().detach(feed_fragment).attach(feed_fragment).commit();
        } else {
            feed_fragment = new DetailedLineDataChartFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(line_chart_fl.getId(), feed_fragment).commit();
        }
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
                            update_UI();
                            is_fetching_data = false;
                            Log.i(TAG, "req: data updated");
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
                    update_UI();
                }
                dialog.dismiss();
            }
        });
        builderSingle.show();
    }


    @Override
    public void onItemClicked(int index) {
        Log.d(TAG, "onItemClicked: " + ""+ picker_values[index]);
        selected_feed_parameter = picker_values[index];
        update_graph();
    }

    @Override
    public void onItemSelected(int index) {
        Log.d(TAG, "onItemSelected: " + ""+ picker_values[index]);
        selected_feed_parameter = picker_values[index];
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
        private TextView nodata_tv;

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
            nodata_tv = view.findViewById(R.id.nodata_tv);
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
                    for (int j = 0; j < device.getLatestData().get(i).getPastValues().size(); j++) {
                        dbl_arr.add("" + device.getLatestData().get(i).getPastValues().get(j));
                    }
                    dbl_arr.add("" + device.getLatestData().get(i).getValue());
                    break;
                }
            }

            if(dbl_arr.size() < 1){
                nodata_tv.setVisibility(View.VISIBLE);
                chart.setVisibility(View.GONE);
                return;
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
//                Toast.makeText(activity_context, "value: "+ value, Toast.LENGTH_SHORT).show();
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
                is_fetching_data = false;
            }
            data_updation_handler.removeCallbacks(data_updation_runnable);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onPause();
    }


}

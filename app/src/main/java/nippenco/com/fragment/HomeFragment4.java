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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.aigestudio.wheelpicker.WheelPicker;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.firebase.iid.FirebaseInstanceId;
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

public class HomeFragment4 extends Fragment implements WheelPicker.OnItemSelectedListener{

    private Context activity_context, frag_context;
    private TextView meter_name_tv;
    private TextView notiff_count_tv;
    private ImageView notiff_iv;
    private String TAG = "NPN_LOG";
    private ArrayList<String> picker_values;
    private WheelPicker wheel_picker;
    private Device login_datum_device;
    private FrameLayout line_chart_fl, chart_data_fl;
    static String selected_feed_parameter = "";
    private LineChartFragment feed_fragment;
    private ChartDataFragment chartDataFragment;
    private boolean is_fetching_data = false;
    private JsonObjectRequest update_data_request;
    private Runnable data_updation_runnable;
    private Handler data_updation_handler;
    private TextView last_update_tv;
    private Button goto_feed_btn, toggle_fragment_btn;
    static TextView current_value_tv, nodata_tv;

    boolean is_chart_data_visible;

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


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_home_4, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initLayoutVars(view);

        is_chart_data_visible = true;

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
        wheel_picker.setIndicatorColor(getActivity().getResources().getColor(R.color.colorPrimary));
        wheel_picker.setSelectedItemTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
        wheel_picker.setItemTextSize(50);
        wheel_picker.setItemTextColor(getActivity().getResources().getColor(R.color.blue_grey_text));

        view.findViewById(R.id.meter_select_ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMailboxSelectDialog();
            }
        });

        goto_feed_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.getInstance().device_history_for = picker_values.get(wheel_picker.getCurrentItemPosition());
                Common.getInstance().device_history_for_pos = wheel_picker.getCurrentItemPosition();
                ((MainActivity)getActivity()).set_fragment(2);
            }
        });

        toggle_fragment_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                is_chart_data_visible = !is_chart_data_visible;
                if(is_chart_data_visible){
                    chart_data_fl.setVisibility(View.VISIBLE);
                    line_chart_fl.setVisibility(View.GONE);
                    toggle_fragment_btn.setText("Show Graph");
                } else {
                    chart_data_fl.setVisibility(View.GONE);
                    line_chart_fl.setVisibility(View.VISIBLE);
                    toggle_fragment_btn.setText("Show Data");
                }
            }
        });
    }


    private void initLayoutVars(View v) {
        meter_name_tv = v.findViewById(R.id.meter_name_tv);
        notiff_count_tv = v.findViewById(R.id.notiff_count_tv);
        notiff_iv = v.findViewById(R.id.notiff_iv);
        wheel_picker = v.findViewById(R.id.wheel_picker);
        line_chart_fl = v.findViewById(R.id.line_chart_fl);
        chart_data_fl = v.findViewById(R.id.chart_data_fl);
        nodata_tv = v.findViewById(R.id.nodata_tv);
        last_update_tv = v.findViewById(R.id.last_update_tv);
        current_value_tv = v.findViewById(R.id.current_value_tv);
        goto_feed_btn = v.findViewById(R.id.goto_feed_btn);
        toggle_fragment_btn = v.findViewById(R.id.toggle_chart_btn);
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

        chart_data_fl.setVisibility(View.GONE);
        line_chart_fl.setVisibility(View.VISIBLE);
        toggle_fragment_btn.setText("Show Data");
        if(is_chart_data_visible){
            line_chart_fl.setVisibility(View.GONE);
            chart_data_fl.setVisibility(View.VISIBLE);
            toggle_fragment_btn.setText("Show Graph");
        }

        feed_fragment = new LineChartFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(line_chart_fl.getId(), feed_fragment).commit();

        chartDataFragment = new ChartDataFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(chart_data_fl.getId(), chartDataFragment).commit();

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
        getActivity().getSupportFragmentManager().beginTransaction().detach(chartDataFragment).attach(chartDataFragment).commit();
    }


    void update_data(){

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        String url = host + login;
        String user_name = sharedPreferences.getString("user_name", "");
        String user_pass = sharedPreferences.getString("user_pass", "");
        String fcm_token = FirebaseInstanceId.getInstance().getToken();

        Map<String, String> payload = new HashMap<>();
        payload.put("username", user_name);
        payload.put("password", user_pass);
        payload.put("fcm_token", fcm_token);
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
        Common.getInstance().device_history_for = picker_values.get(wheel_picker.getCurrentItemPosition());
        Common.getInstance().device_history_for_pos = wheel_picker.getCurrentItemPosition();
        update_graph();
    }


    public static class LineChartFragment extends Fragment implements OnChartValueSelectedListener {
        Context activity_context, frag_context;
        private String TAG = "NPN_LOG";
        private LineChart chart;

        List<Entry> entries = new ArrayList<>();

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
            return inflater.inflate(R.layout.fragment_home_chart, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            chart = view.findViewById(R.id.chart);

            entries = new ArrayList<>();
            chart.setOnChartValueSelectedListener(this);

            generateData();
        }

        private void generateData() {

            entries.clear();

            Device device = Common.getInstance().login_datum.getData().getDevices().getDevices().get(Common.getInstance().selected_login_device);

            for (int i = 0; i < device.getLatestData().size(); i++) {
                if(device.getLatestData().get(i).getName().equalsIgnoreCase(selected_feed_parameter)) {
                    current_value_tv.setText(""+ device.getLatestData().get(i).getValue());
                    for (int j = device.getLatestData().get(i).getPastValues().size()-1; j >= 0; j--) {
                        int temp_pos2 = entries.size()+1;
                        entries.add( new Entry( Float.parseFloat(""+temp_pos2) , Float.parseFloat(""+device.getLatestData().get(i).getPastValues().get(j)) ) );
                    }
                    int temp_pos = entries.size()+1;
                    entries.add( new Entry( Float.parseFloat(""+temp_pos) , Float.parseFloat(""+device.getLatestData().get(i).getValue()) ) );
                    break;
                }
            }

            LineDataSet lineDataSet = new LineDataSet(entries, "Last 5 data stats");
            lineDataSet.setDrawFilled(true);
            lineDataSet.setDrawCircles(true);
            lineDataSet.setDrawValues(true);
            LineData lineData = new LineData(lineDataSet);
            chart.setData(lineData);
            chart.invalidate();
//            chart.animateY(1000, Easing.EasingOption.Linear);
        }

        @Override
        public void onValueSelected(Entry e, Highlight h) {
            String str = "" + e.getY();
            Toast.makeText(activity_context, str, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected() {

        }
    }


    public static class ChartDataFragment extends Fragment {
        private Context activity_context, frag_context;
        private String TAG = "NPN_LOG";
        private RecyclerView data_rv;
        private HomeDataAdapter homeDataAdapter;
        List<String> y_axis_values;
        List<String> x_axis_values;

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
            return inflater.inflate(R.layout.fragment_home_data, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            data_rv = view.findViewById(R.id.data_rv);

            generateData();
        }


        private void generateData() {

            y_axis_values = new ArrayList<>();
            x_axis_values = new ArrayList<>();

            Device device = Common.getInstance().login_datum.getData().getDevices().getDevices().get(Common.getInstance().selected_login_device);

            for (int i = 0; i < device.getLatestData().size(); i++) {
                if(device.getLatestData().get(i).getName().equalsIgnoreCase(selected_feed_parameter)) {

                    for (int j = device.getTimestamp().getPastValues().size()-1; j >= 0; j--) {
                        x_axis_values.add(device.getTimestamp().getPastValues().get(j));
                    }
                    x_axis_values.add(device.getTimestamp().getValue());

                    current_value_tv.setText(""+ device.getLatestData().get(i).getValue());

                    for (int j = device.getLatestData().get(i).getPastValues().size()-1; j >= 0; j--) {
                        y_axis_values.add( "" + device.getLatestData().get(i).getPastValues().get(j));
                    }
                    y_axis_values.add( "" + device.getLatestData().get(i).getValue());
                    break;
                }
            }

            data_rv.setOverScrollMode(View.OVER_SCROLL_NEVER);

            data_rv.setVisibility(View.GONE);
            nodata_tv.setVisibility(View.VISIBLE);
            if(x_axis_values.size() > 0 && y_axis_values.size() > 0){
                nodata_tv.setVisibility(View.GONE);
                data_rv.setVisibility(View.VISIBLE);
                homeDataAdapter = new HomeDataAdapter();
                data_rv.setLayoutManager(new LinearLayoutManager(activity_context));
                data_rv.setAdapter(homeDataAdapter);
            }

        }


        class HomeDataAdapter extends RecyclerView.Adapter<HomeDataAdapter.ViewHolder>{

            @NonNull
            @Override
            public HomeDataAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater inflater = LayoutInflater.from(activity_context);
                View view = inflater.inflate(R.layout.item_home_dashboard_stat, parent, false);
                return new ViewHolder(view);
            }

            @Override
            public void onBindViewHolder(@NonNull HomeDataAdapter.ViewHolder holder, int position) {
                holder.feed_left_tv.setText(x_axis_values.get(position));
                holder.feed_right_tv.setText(y_axis_values.get(position));
            }

            @Override
            public int getItemCount() {
                return x_axis_values.size();
            }

            class ViewHolder extends RecyclerView.ViewHolder{

                TextView feed_left_tv, feed_right_tv;

                ViewHolder(View itemView) {
                    super(itemView);
                    feed_left_tv = itemView.findViewById(R.id.state_title_tv);
                    feed_right_tv = itemView.findViewById(R.id.state_value_tv);
                }
            }
        }

    }


}

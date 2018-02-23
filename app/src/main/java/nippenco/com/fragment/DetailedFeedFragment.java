package nippenco.com.fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

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

import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import nippenco.com.Common;
import nippenco.com.MainActivity;
import nippenco.com.R;
import nippenco.com.api_model.get_device_history.GetDeviceHistory;

import static nippenco.com.Constant.get_device_history;
import static nippenco.com.Constant.host;
import static nippenco.com.Constant.login;

/**
 * Created by aishwarydhare on 20/02/18.
 */

public class DetailedFeedFragment extends Fragment {



    Context activity_context, frag_context;
    private TextView meter_name_tv;
    private FrameLayout chart_container_fl;
    Button select_from_btn, select_to_btn;
    ProgressBar pb;
    private String TAG = "NPN_LOG";
    Calendar from_date, to_date;
    String date_from_str, date_to_str;

    Calendar date;
    static GetDeviceHistory device_history_datum;

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
        ((MainActivity)activity_context).selected_frag_id = 2;
        Log.d(TAG, "onResume: ");
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_detailed_feed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initLayoutVars(view);

        chart_container_fl.setVisibility(View.GONE);
        pb.setVisibility(View.GONE);

        select_from_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimePicker(true);
            }
        });

        select_to_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDateTimePicker(false);
            }
        });

        meter_name_tv.setText(Common.getInstance().login_datum.getData().getDevices().getDevices()
                .get(Common.getInstance().selected_login_device).getName());
    }


    private void showDateTimePicker(final boolean is_from_date_selecting){
        final Calendar currentDate = Calendar.getInstance();
        date = Calendar.getInstance();
        new DatePickerDialog(activity_context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.set(year, monthOfYear, dayOfMonth);
                new TimePickerDialog(activity_context, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        date.set(Calendar.MINUTE, minute);
                        Log.v(TAG, "The choosen one " + date.getTime());
                        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                        if(is_from_date_selecting){
                            from_date = date;
                            date_from_str = dateFormatter.format(from_date.getTime());
                            select_from_btn.setText(date_from_str);
                        } else {
                            to_date = date;
                            date_to_str = dateFormatter.format(to_date.getTime());
                            select_to_btn.setText(date_to_str);
                        }
                        if (from_date != null && to_date != null) {
                            pb.setVisibility(View.VISIBLE);
                            hit_api_to_get_device_history_data();
                        }
                    }
                }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
            }
        }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();
    }

    private void initLayoutVars(View v) {
        meter_name_tv = v.findViewById(R.id.meter_name_tv);
        select_from_btn = v.findViewById(R.id.select_from_btn);
        select_to_btn = v.findViewById(R.id.select_to_btn);
        chart_container_fl = v.findViewById(R.id.chart_container_fl);
        pb = v.findViewById(R.id.pb);
    }


    void update_UI() {
        getActivity().getSupportFragmentManager().beginTransaction().replace(chart_container_fl.getId(), new DetailedDataChartFragment()).commit();
        pb.setVisibility(View.GONE);
        chart_container_fl.setVisibility(View.VISIBLE);
    }


    void hit_api_to_get_device_history_data(){

        String url = host + get_device_history;

        Map<String, Object> payload = new HashMap<>();
        payload.put("user_id", Common.getInstance().login_datum.getData().getUserInfo().getId());
        payload.put("from_date", date_from_str);
        payload.put("to_date", date_to_str);
        payload.put("device_id", Common.getInstance().login_datum.getData().getDevices().getDevices().get(Common.getInstance().selected_login_device).getId());
        Log.d(TAG, "hit_api_to_get_device_history_data: "+ payload.toString());
        JSONObject jsonPayload = new JSONObject(payload);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, jsonPayload,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(response.optInt("ResponseCode") != 200){
                            return;
                        }
                        try{
                            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                            device_history_datum = gson.fromJson(response.toString(), GetDeviceHistory.class);
                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(activity_context, "data fetch error", Toast.LENGTH_SHORT).show();
                        }
                        if (device_history_datum.getData().getDeviceData().getTimestamps().size() > 0) {
                            update_UI();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // if error received
                        error.printStackTrace();
                        Toast.makeText(activity_context, "Invalid Data Fetch Failed, Sign In Again", Toast.LENGTH_SHORT).show();
                    }
                });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy( 60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ((MainActivity)activity_context).requestQueue.add(jsonObjectRequest);

    }


    private void hiddenKeyboard(View focus) {
        InputMethodManager keyboard = (InputMethodManager) activity_context.getSystemService(Context.INPUT_METHOD_SERVICE);
        keyboard.hideSoftInputFromWindow(focus.getWindowToken(), 0);
    }



    public static class DetailedDataChartFragment extends Fragment {

        Context activity_context, frag_context;
        private String TAG = "NPN_LOG";
        private ColumnChartView chart;
        private ColumnChartData data;
        private boolean hasAxes = true;
        private boolean hasAxesNames = true;
        private boolean hasLabels = true;
        private boolean hasLabelForSelected = false;

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
        }


        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);
            return inflater.inflate(R.layout.fragment_detailed_feed_column_chart, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            chart = view.findViewById(R.id.chart);
            chart.setOnValueTouchListener(new ValueTouchListener());
            chart.setOverScrollMode(View.OVER_SCROLL_ALWAYS);

            generateDefaultData();
        }

        private void generateDefaultData() {
            int numSubcolumns = 1;
            int numColumns = device_history_datum.getData().getDeviceData().getTimestamps().size();
            // Column can have many subcolumns, here by default I use 1 subcolumn in each of 25 columns.
            List<Column> columns = new ArrayList<Column>();
            List<SubcolumnValue> values;
            for (int i = 0; i < numColumns; ++i) {

                values = new ArrayList<SubcolumnValue>();
                for (int j = 0; j < numSubcolumns; ++j) {
                    float value = Float.parseFloat(""+device_history_datum.getData().getDeviceData().getAmpsI1().get(i));
                    values.add(new SubcolumnValue(value, ChartUtils.pickColor()));
                }

                Column column = new Column(values);
                column.setHasLabels(hasLabels);
                column.setHasLabelsOnlyForSelected(hasLabelForSelected);
                columns.add(column);
            }

            data = new ColumnChartData(columns);

            if (hasAxes) {
                Axis axisX = new Axis();
                Axis axisY = new Axis().setHasLines(true);
                if (hasAxesNames) {
                    axisX.setName("Timestamp");
                    axisY.setName("Values");
                }
                data.setAxisXBottom(axisX);
                data.setAxisYLeft(axisY);
            } else {
                data.setAxisXBottom(null);
                data.setAxisYLeft(null);
            }

            chart.setColumnChartData(data);

        }

        private class ValueTouchListener implements ColumnChartOnValueSelectListener {
            @Override
            public void onValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {

            }

            @Override
            public void onValueDeselected() {

            }
        }
    }

}

package nippenco.com.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
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
import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lecho.lib.hellocharts.listener.ColumnChartOnValueSelectListener;
import lecho.lib.hellocharts.listener.LineChartOnValueSelectListener;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.Column;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.SubcolumnValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.ColumnChartView;
import lecho.lib.hellocharts.view.LineChartView;
import nippenco.com.Common;
import nippenco.com.MainActivity;
import nippenco.com.R;
import nippenco.com.api_model.get_device_history.GetDeviceHistory;

import static nippenco.com.Constant.get_device_history;
import static nippenco.com.Constant.host;

/**
 * Created by aishwarydhare on 20/02/18.
 */

public class DetailedFeedFragment extends Fragment {

    Context activity_context, frag_context;
    private TextView meter_name_tv;
    private FrameLayout chart_container_fl;
    Button select_from_btn, select_to_btn, select_feed_type_btn, select_basis_btn, submit_btn, graph_type_btn;
    ProgressBar pb;
    private String TAG = "NPN_LOG";
    Calendar from_date, to_date;
    static String date_from_str, date_to_str, feed_type_str, basis_str, graph_type_str;
    static int feed_type_pos;

    TextView edit_date_tv, feed_type_tv, basis_tv, from_date_tv, to_date_tv;
    LinearLayout date_select_ll, date_show_ll;

    Calendar date;
    static GetDeviceHistory device_history_datum;
    String device_history_for = "";
    JsonObjectRequest jsonObjectRequest;

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

        from_date = null;
        to_date = null;
        basis_str = null;
        date_from_str = null;
        date_to_str = null;
        graph_type_str = null;

        if(Common.getInstance().device_history_for_pos == -1) {
            if (Common.getInstance().device_history_for != null) {
                if (!Common.getInstance().device_history_for.equalsIgnoreCase("")) {
                    for (int i = 0; i < Common.getInstance().login_datum.getData().getDevices().getDevices().size(); i++) {
                        if (Common.getInstance().login_datum.getData().getDevices().getDevices().get(i).getName().equalsIgnoreCase(Common.getInstance().device_history_for)) {
                            feed_type_str = Common.getInstance().device_history_for;
                            feed_type_pos = i;
                            select_feed_type_btn.setText(Common.getInstance().device_history_for);
                        }
                    }
                }
            }
        } else {
            feed_type_str = Common.getInstance().device_history_for;
            feed_type_pos = Common.getInstance().device_history_for_pos;
            select_feed_type_btn.setText(Common.getInstance().device_history_for);
        }

        chart_container_fl.setVisibility(View.GONE);
        pb.setVisibility(View.GONE);

        date_show_ll.setVisibility(View.GONE);
        date_select_ll.setVisibility(View.VISIBLE);

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

        select_feed_type_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel_active_requests();
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(frag_context);
                builderSingle.setTitle("Select Feed Type");

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(frag_context, android.R.layout.select_dialog_item);
                for (int i = 0; i < Common.getInstance().login_datum.getData().getDevices().getDevices().get(Common.getInstance().selected_login_device).getLatestData().size(); i++) {
                    arrayAdapter.add(Common.getInstance().login_datum.getData().getDevices().getDevices().get(Common.getInstance().selected_login_device).getLatestData().get(i).getName());
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
                        feed_type_str = arrayAdapter.getItem(which);
                        feed_type_pos = which;
                        select_feed_type_btn.setText(""+feed_type_str);
                        dialog.dismiss();
                    }
                });
                builderSingle.show();
            }
        });

        select_basis_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel_active_requests();
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(frag_context);
                builderSingle.setTitle("Select Basis");

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(frag_context, android.R.layout.select_dialog_item);
                arrayAdapter.add("Averaged by Days");
                arrayAdapter.add("Averaged by Hours");
                arrayAdapter.add("Averaged by Minutes");

                builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        basis_str = arrayAdapter.getItem(which);
                        assert basis_str != null;
                        if(basis_str.equalsIgnoreCase("Averaged by Days")){
                            basis_str = "Daily";
                            select_to_btn.setEnabled(true);
                            select_from_btn.setText("-- Select From Date --");
                            select_to_btn.setText("-- Select To Date --");
                        } else if(basis_str.equalsIgnoreCase("Averaged by Hours")){
                            basis_str = "Hourly";
                            select_to_btn.setEnabled(false);
                            select_to_btn.setText("-- Not Required --");
                        } else if(basis_str.equalsIgnoreCase("Averaged by Minutes")){
                            basis_str = "Minute";
                            select_from_btn.setText("-- Select From Hour --");
                            select_to_btn.setEnabled(false);
                            select_to_btn.setText("-- Not Required --");
                        }
                        select_basis_btn.setText(""+basis_str);
                        dialog.dismiss();
                    }
                });
                builderSingle.show();
            }
        });

        graph_type_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel_active_requests();
                AlertDialog.Builder builderSingle = new AlertDialog.Builder(frag_context);
                builderSingle.setTitle("Select Graph Type");

                final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(frag_context, android.R.layout.select_dialog_item);
                arrayAdapter.add("Line Graph");
                arrayAdapter.add("Bar Graph");

                builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        graph_type_str = arrayAdapter.getItem(which);
                        graph_type_btn.setText(""+graph_type_str);
                        dialog.dismiss();
                    }
                });
                builderSingle.show();
            }
        });

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel_active_requests();
                if(from_date == null){
                    Toast.makeText(activity_context, "Please select From-Date to continue", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(basis_str == null){
                    Toast.makeText(activity_context, "Please select Basis to continue", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(feed_type_str == null){
                    Toast.makeText(activity_context, "Please select Feed Type to continue", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(graph_type_str == null){
                    Toast.makeText(activity_context, "Please select Graph Type to continue", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(basis_str.equalsIgnoreCase("Daily")){
                    if (date_to_str == null) {
                        Toast.makeText(activity_context, "Please select To-Date to continue", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                pb.setVisibility(View.VISIBLE);
                hit_api_to_get_device_history_data();
            }
        });

        edit_date_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update_UI(false);
            }
        });

        meter_name_tv.setText(Common.getInstance().login_datum.getData().getDevices().getDevices()
                .get(Common.getInstance().selected_login_device).getName());
    }


    private void showDateTimePicker(final boolean is_from_date_selecting){
        cancel_active_requests();

        if (basis_str == null){
            Toast.makeText(activity_context, "Select Basis to continue", Toast.LENGTH_SHORT).show();
            return;
        } else if(basis_str.equalsIgnoreCase("")){
            Toast.makeText(activity_context, "Select Basis to continue", Toast.LENGTH_SHORT).show();
            return;
        }

        if (basis_str.equalsIgnoreCase("Daily") && !is_from_date_selecting) {
            if(date_from_str == null) {
                Toast.makeText(activity_context, "Select From Date to continue", Toast.LENGTH_SHORT).show();
                return;
            } else if(date_from_str.equalsIgnoreCase("")){
                Toast.makeText(activity_context, "Select From Date to continue", Toast.LENGTH_SHORT).show();
                return;
            }
        }

        final Calendar currentDate = Calendar.getInstance();
        date = Calendar.getInstance();

        Calendar default_date = currentDate;
        if(is_from_date_selecting){
            if(from_date != null){
                default_date = from_date;
            }
        } else {
            if(to_date != null){
                default_date = to_date;
            }
        }
        final TimePickerDialog timePickerDialog = new TimePickerDialog(activity_context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                date.set(Calendar.MINUTE, minute);
                Log.v(TAG, "The choosen one " + date.getTime());
                DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
                if (basis_str.equalsIgnoreCase("Minute")) {
                    dateFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                }
                if(is_from_date_selecting){
                    from_date = date;
                    date_from_str = dateFormatter.format(from_date.getTime());
                    select_from_btn.setText(date_from_str);
                } else {
                    to_date = date;
                    date_to_str = dateFormatter.format(to_date.getTime());
                    select_to_btn.setText(date_to_str);
                }
            }
        }, default_date.get(Calendar.HOUR_OF_DAY), default_date.get(Calendar.MINUTE), false);

        DatePickerDialog datePickerDialog = new DatePickerDialog(activity_context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                date.set(year, monthOfYear, dayOfMonth);
                if (basis_str.equalsIgnoreCase("Minute")) {
                    timePickerDialog.show();
                } else {
                    date.set(Calendar.HOUR_OF_DAY, 24);
                    date.set(Calendar.MINUTE, 0);
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
                }
            }
        }, default_date.get(Calendar.YEAR), default_date.get(Calendar.MONTH), default_date.get(Calendar.DATE));

        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        datePickerDialog.show();

//        if(basis_str.equalsIgnoreCase("Daily")){
//            datePickerDialog.show();
//        } else if(basis_str.equalsIgnoreCase("Hourly")){
//            datePickerDialog.show();
//        } else if(basis_str.equalsIgnoreCase("Minute")){
//            timePickerDialog.show();
//        }
    }


    private void initLayoutVars(View v) {
        meter_name_tv = v.findViewById(R.id.meter_name_tv);
        select_from_btn = v.findViewById(R.id.select_from_btn);
        select_to_btn = v.findViewById(R.id.select_to_btn);
        select_basis_btn = v.findViewById(R.id.basis_btn);
        select_feed_type_btn = v.findViewById(R.id.select_feed_type_btn);
        graph_type_btn = v.findViewById(R.id.graph_type_btn);
        submit_btn = v.findViewById(R.id.submit_btn);
        chart_container_fl = v.findViewById(R.id.chart_container_fl);

        date_select_ll = v.findViewById(R.id.date_select_ll);
        date_show_ll = v.findViewById(R.id.date_show_ll);
        basis_tv = v.findViewById(R.id.basis_tv);
        edit_date_tv = v.findViewById(R.id.edit_date_tv);
        feed_type_tv = v.findViewById(R.id.feed_type_tv);
        from_date_tv = v.findViewById(R.id.from_date_tv);
        to_date_tv = v.findViewById(R.id.to_date_tv);

        pb = v.findViewById(R.id.pb);
    }


    void cancel_active_requests(){
        if (jsonObjectRequest != null) {
            jsonObjectRequest.cancel();
        }
        pb.setVisibility(View.GONE);
    }


    void update_UI(boolean is_graph_visible) {
        if (is_graph_visible) {
            if (graph_type_str.equalsIgnoreCase("Bar Graph")) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(chart_container_fl.getId(), new DetailedColumnDataChartFragment()).commit();
            } else if(graph_type_str.equalsIgnoreCase("Line Graph")){
                getActivity().getSupportFragmentManager().beginTransaction().replace(chart_container_fl.getId(), new DetailedLineDataChartFragment()).commit();
            }
            from_date_tv.setText(device_history_datum.getData().getTimestamps().getFromDate());
            to_date_tv.setText(device_history_datum.getData().getTimestamps().getToDate());
            basis_tv.setText(basis_str);
            feed_type_tv.setText(feed_type_str);
            date_select_ll.setVisibility(View.GONE);
            date_show_ll.setVisibility(View.VISIBLE);
            pb.setVisibility(View.GONE);
            chart_container_fl.setVisibility(View.VISIBLE);
        } else {
            date_show_ll.setVisibility(View.GONE);
            pb.setVisibility(View.GONE);
            date_select_ll.setVisibility(View.VISIBLE);
            chart_container_fl.setVisibility(View.GONE);
        }
    }


    void hit_api_to_get_device_history_data(){

        String url = host + get_device_history;

        Map<String, Object> payload = new HashMap<>();
        payload.put("user_id", Common.getInstance().login_datum.getData().getUserInfo().getId());
        payload.put("basis", basis_str);
        payload.put("from_date", date_from_str);

        if(date_to_str == null){
            payload.put("to_date", date_from_str);
        } else {
            payload.put("to_date", date_to_str);
        }

        payload.put("device_id", Common.getInstance().login_datum.getData().getDevices().getDevices().get(Common.getInstance().selected_login_device).getId());
        Log.d(TAG, "hit_api_to_get_device_history_data: "+ payload.toString());
        JSONObject jsonPayload = new JSONObject(payload);

        jsonObjectRequest = new JsonObjectRequest(url, jsonPayload,
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
                        if (device_history_datum.getData().getDeviceData().get(0).getValues().size() > 0) {
                            update_UI(true);
                        } else {
                            Toast.makeText(activity_context, "No Data Found", Toast.LENGTH_SHORT).show();
                            update_UI(false);
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



    public static class DetailedColumnDataChartFragment extends Fragment {

        Context activity_context, frag_context;
        private String TAG = "NPN_LOG";
        private ColumnChartView chart;
        private ColumnChartData data;
        private boolean hasAxes = true;
        private boolean hasAxesNames = true;
        private boolean hasLabels = false;
        private boolean hasLabelForSelected = false;
        private TextView nodata_tv;

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
            nodata_tv = view.findViewById(R.id.nodata_tv);
            chart.setOnValueTouchListener(new ColumnValueTouchListener());
            chart.setOverScrollMode(View.OVER_SCROLL_ALWAYS);

            generateDefaultData();
        }

        private void generateDefaultData() {
            int numColumns = 0;
            int numSubcolumns = 1;
            List<String> dbl_arr = new ArrayList<>();
            ArrayList<String> str_arr = new ArrayList<>();
            // Column can have many subcolumns, here by default I use 1 subcolumn in each of 25 columns.
            List<Column> columns = new ArrayList<Column>();
            List<SubcolumnValue> values;

            for (int i = 0; i < device_history_datum.getData().getDeviceData().size(); i++) {
                String short_name = Common.getInstance().login_datum.getData().getDevices().getDevices().get(Common.getInstance().selected_login_device).getLatestData().get(feed_type_pos).getShortName();
                if(device_history_datum.getData().getDeviceData().get(i).getName().equalsIgnoreCase("timestamps")) {
                    str_arr = (ArrayList<String>) device_history_datum.getData().getDeviceData().get(i).getValues();
                }
                if(device_history_datum.getData().getDeviceData().get(i).getName().equalsIgnoreCase(short_name)){
                    dbl_arr = device_history_datum.getData().getDeviceData().get(i).getValues();
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
                nodata_tv.setText("No Data Found");
            }

            if(is_at_least_two_nonzero || is_all_data_same){
                nodata_tv.setVisibility(View.GONE);
                chart.setVisibility(View.VISIBLE);
            } else {
                chart.setVisibility(View.GONE);
                nodata_tv.setVisibility(View.VISIBLE);
            }

            numColumns = dbl_arr.size();

            for (int i = 0; i < numColumns; ++i) {

                values = new ArrayList<SubcolumnValue>();
                for (int j = 0; j < numSubcolumns; ++j) {
                    float value = 0;
                    value = Float.parseFloat(""+dbl_arr.get(i));
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
                    if (basis_str.equalsIgnoreCase("Daily")) {
                        axisX.setName("Days");
                    } else if(basis_str.equalsIgnoreCase("Hourly")) {
                        axisX.setName("Hours");
                    } else if(basis_str.equalsIgnoreCase("Minute")) {
                        axisX.setName("Minutes");
                    }
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

        private class ColumnValueTouchListener implements ColumnChartOnValueSelectListener {
            @Override
            public void onValueSelected(int columnIndex, int subcolumnIndex, SubcolumnValue value) {
//                Toast.makeText(activity_context, "value: "+ value, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onValueDeselected() {

            }
        }
    }


    public static class DetailedLineDataChartFragment extends Fragment {

        Context activity_context, frag_context;
        private String TAG = "NPN_LOG";
        private LineChartView chart;
        private LineChartData data;
        private int numberOfLines = 1;
        private boolean hasAxes = true;
        private boolean hasAxesNames = true;
        private boolean hasLines = true;
        private boolean hasPoints = true;
        private ValueShape shape = ValueShape.CIRCLE;
        private boolean isFilled = true;
        private boolean hasLabels = false;
        private boolean isCubic = false;
        private boolean hasLabelForSelected = false;
        private boolean pointsHaveDifferentColor;
        private boolean hasGradientToTransparent = false;
        private TextView nodata_tv;

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
            ArrayList<String> str_arr = new ArrayList<>();

            for (int i = 0; i < device_history_datum.getData().getDeviceData().size(); i++) {
                String short_name = Common.getInstance().login_datum.getData().getDevices().getDevices().get(Common.getInstance().selected_login_device).getLatestData().get(feed_type_pos).getShortName();
                if(device_history_datum.getData().getDeviceData().get(i).getName().equalsIgnoreCase("timestamps")) {
                    str_arr = (ArrayList<String>) device_history_datum.getData().getDeviceData().get(i).getValues();
                }
                if(device_history_datum.getData().getDeviceData().get(i).getName().equalsIgnoreCase(short_name)){
                    dbl_arr = device_history_datum.getData().getDeviceData().get(i).getValues();
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
                nodata_tv.setText("No Data Found");
            }

            if(is_at_least_two_nonzero || is_all_data_same){
                nodata_tv.setVisibility(View.GONE);
                chart.setVisibility(View.VISIBLE);
            } else {
                chart.setVisibility(View.GONE);
                nodata_tv.setVisibility(View.VISIBLE);
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
                    if (basis_str.equalsIgnoreCase("Daily")) {
                        axisX.setName("Days");
                    } else if(basis_str.equalsIgnoreCase("Hourly")) {
                        axisX.setName("Hours");
                    } else if(basis_str.equalsIgnoreCase("Minute")) {
                        axisX.setName("Minutes");
                    }
                    axisY.setName("Values");
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
    public void onDestroy() {
        super.onDestroy();
        if(jsonObjectRequest != null){
            jsonObjectRequest.cancel();
        }
    }
}

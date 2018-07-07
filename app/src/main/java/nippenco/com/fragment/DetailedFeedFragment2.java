package nippenco.com.fragment;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import nippenco.com.Common;
import nippenco.com.MainActivity;
import nippenco.com.R;
import nippenco.com.adapter.DetailFeedRVAdapter;
import nippenco.com.api_model.get_device_history.GetDeviceHistory;
import nippenco.com.api_model.login.Device;

import static nippenco.com.Constant.get_device_history;
import static nippenco.com.Constant.host;

/**
 * Created by aishwarydhare on 20/02/18.
 */

public class DetailedFeedFragment2 extends Fragment {

    Context activity_context, frag_context;
    private TextView meter_name_tv;
    private FrameLayout chart_container_fl, stat_data_container_fl;
    Button select_from_btn, select_to_btn, select_feed_type_btn, select_basis_btn, submit_btn, graph_type_btn;
    ProgressBar pb;
    private String TAG = "NPN_LOG";
    Calendar from_date, to_date;
    static String date_from_str, date_to_str, feed_type_str, basis_str, graph_type_str;
    static int feed_type_pos;

    Button edit_date_tv, show_data_tv;
    TextView feed_type_tv, basis_tv, from_date_tv, to_date_tv;
    LinearLayout date_select_ll, date_show_ll;

    Calendar date;
    static GetDeviceHistory device_history_datum;
    JsonObjectRequest jsonObjectRequest;
    boolean is_stat_data_visible;

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
    public void onDestroy() {
        super.onDestroy();
        if(jsonObjectRequest != null){
            jsonObjectRequest.cancel();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_detailed_feed_two, container, false);
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

        is_stat_data_visible = false;
        stat_data_container_fl.setVisibility(View.GONE);
        chart_container_fl.setVisibility(View.VISIBLE);
        if(is_stat_data_visible){
            chart_container_fl.setVisibility(View.GONE);
            stat_data_container_fl.setVisibility(View.VISIBLE);
        }

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

        show_data_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                is_stat_data_visible = !is_stat_data_visible;
                stat_data_container_fl.setVisibility(View.GONE);
                chart_container_fl.setVisibility(View.VISIBLE);
                show_data_tv.setText("Show Data");
                if(is_stat_data_visible){
                    chart_container_fl.setVisibility(View.GONE);
                    stat_data_container_fl.setVisibility(View.VISIBLE);
                    show_data_tv.setText("Show Graph");
                }
                Fragment chartDataFragment = new ChartDataFragment();
                getFragmentManager().beginTransaction().replace(stat_data_container_fl.getId(), chartDataFragment).commit();
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

        if(is_from_date_selecting  && basis_str.equalsIgnoreCase("Daily")){
            default_date.add(Calendar.DAY_OF_MONTH, -1);
        }

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
                DateFormat dateFormatter_disp = dateFormatter;
                if (basis_str.equalsIgnoreCase("Minute")) {
                    dateFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                    dateFormatter_disp = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
                }
                if(is_from_date_selecting){
                    from_date = date;
                    date_from_str = dateFormatter.format(from_date.getTime());
                    select_from_btn.setText(dateFormatter_disp.format(from_date.getTime()));
                } else {
                    to_date = date;
                    date_to_str = dateFormatter.format(to_date.getTime());
                    select_to_btn.setText(dateFormatter_disp.format(to_date.getTime()));
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
                    date.set(Calendar.HOUR_OF_DAY, 12);
                    date.set(Calendar.MINUTE, 0);
                    Log.v(TAG, "The choosen one " + date.getTime());
                    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                    DateFormat dateFormatter_disp = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
                    if(is_from_date_selecting){
                        from_date = date;
                        date_from_str = dateFormatter.format(from_date.getTime());
                        select_from_btn.setText(dateFormatter_disp.format(from_date.getTime()));
                    } else {
                        to_date = date;
                        date_to_str = dateFormatter.format(to_date.getTime());
                        select_to_btn.setText(dateFormatter_disp.format(to_date.getTime()));
                    }
                }
            }
        }, default_date.get(Calendar.YEAR), default_date.get(Calendar.MONTH), default_date.get(Calendar.DATE));

        datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
        if(is_from_date_selecting && basis_str.equalsIgnoreCase("Daily")){
            Calendar temp_date = Calendar.getInstance();
            temp_date.add(Calendar.DAY_OF_MONTH, -1);
            datePickerDialog.getDatePicker().setMaxDate( temp_date.getTimeInMillis() );
        }
        datePickerDialog.show();
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
        stat_data_container_fl = v.findViewById(R.id.stat_data_container_fl);

        date_select_ll = v.findViewById(R.id.date_select_ll);
        date_show_ll = v.findViewById(R.id.date_show_ll);
        basis_tv = v.findViewById(R.id.basis_tv);
        edit_date_tv = v.findViewById(R.id.edit_date_tv);
        show_data_tv = v.findViewById(R.id.show_data_tv);
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
//            date_show_ll.setVisibility(View.GONE);
//            pb.setVisibility(View.GONE);
//            date_select_ll.setVisibility(View.VISIBLE);
//            chart_container_fl.setVisibility(View.GONE);

            is_stat_data_visible = !is_stat_data_visible;
            stat_data_container_fl.setVisibility(View.GONE);
            chart_container_fl.setVisibility(View.VISIBLE);
            show_data_tv.setText("Show Data");

            Fragment chartDataFragment = new ChartDataFragment();
            getFragmentManager().beginTransaction().replace(stat_data_container_fl.getId(), chartDataFragment).commit();

            getActivity().getSupportFragmentManager().beginTransaction().replace(chart_container_fl.getId(), new DetailedLineDataChartFragment()).commit();
            from_date_tv.setText(device_history_datum.getData().getTimestamps().getFromDate());
            to_date_tv.setText(device_history_datum.getData().getTimestamps().getToDate());
            basis_tv.setText(basis_str);
            feed_type_tv.setText(feed_type_str);

            date_select_ll.setVisibility(View.VISIBLE);
            date_show_ll.setVisibility(View.GONE);
            pb.setVisibility(View.GONE);
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


    public static class DetailedColumnDataChartFragment extends Fragment implements OnChartValueSelectedListener {
        Context activity_context, frag_context;
        private String TAG = "NPN_LOG";
        private BarChart chart;
        private TextView nodata_tv;

        List<BarEntry> entries = new ArrayList<>();
        List<IBarDataSet> barDataSetArrayList = new ArrayList<>();
        List<String> str_arr = new ArrayList<>();
        List<String> str_val_arr = new ArrayList<>();

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
            return inflater.inflate(R.layout.fragment_detailed_feed_column_chart_2, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            chart = view.findViewById(R.id.chart);
            nodata_tv = view.findViewById(R.id.nodata_tv);

            nodata_tv.setVisibility(View.GONE);
            chart.setVisibility(View.VISIBLE);
            chart.setOnChartValueSelectedListener(this);

            generateData();
        }

        private void generateData() {
            entries.clear();
            barDataSetArrayList.clear();
            str_arr.clear();
            str_val_arr.clear();

            Device device = Common.getInstance().login_datum.getData().getDevices().getDevices().get(Common.getInstance().selected_login_device);
            String short_name = device.getLatestData().get(feed_type_pos).getShortName();

            for (int i = 0; i < device_history_datum.getData().getDeviceData().size(); i++) {
                if(device_history_datum.getData().getDeviceData().get(i).getName().equalsIgnoreCase("timestamps")) {
                    str_arr.addAll(device_history_datum.getData().getDeviceData().get(i).getValues());
                }
                if(device_history_datum.getData().getDeviceData().get(i).getName().equalsIgnoreCase(short_name)){
                    str_val_arr.addAll(device_history_datum.getData().getDeviceData().get(i).getValues());
                }
            }

            if(str_arr.size() == 0 || str_val_arr.size() == 0){
                nodata_tv.setText("No Data Found for selected feed type, between selected range");
                chart.setVisibility(View.GONE);
                nodata_tv.setVisibility(View.VISIBLE);
                return;
            }

            for (int i = 0; i < str_arr.size(); i++) {
                entries.add( new BarEntry( Float.parseFloat(""+i) , Float.parseFloat(str_val_arr.get(i))) );
                BarDataSet barDataSet = new BarDataSet(entries, null);
                barDataSet.setDrawValues(true);
                barDataSetArrayList.add(barDataSet);
            }

            BarData barData = new BarData(barDataSetArrayList);
            chart.setData(barData);
            Description desc = new Description();
            desc.setText("");
            chart.setDescription(desc);
            chart.getLegend().setEnabled(false);
            chart.invalidate();
        }

        @Override
        public void onValueSelected(Entry e, Highlight h) {
            String str = "";
            try {
                str = "DateTime:" + str_arr.get((int)e.getX()) + "  ,  Value:" + e.getY();
            } catch (Exception e1) {
                e1.printStackTrace();
                str = "" + (int)e.getX() + " : " + e.getY();
            }
            Toast.makeText(activity_context, str, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected() {

        }
    }


    public static class DetailedLineDataChartFragment extends Fragment implements OnChartValueSelectedListener{
        Context activity_context, frag_context;
        private String TAG = "NPN_LOG";
        private LineChart chart;
        private TextView nodata_tv;

        List<Entry> entries = new ArrayList<>();
        List<String> str_arr = new ArrayList<>();
        List<String> str_val_arr = new ArrayList<>();

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
            return inflater.inflate(R.layout.fragment_detailed_feed_line_chart_2, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            chart = view.findViewById(R.id.chart);
            nodata_tv = view.findViewById(R.id.nodata_tv);

            nodata_tv.setVisibility(View.GONE);
            chart.setVisibility(View.VISIBLE);
            chart.setOnChartValueSelectedListener(this);

            generateData();
        }

        private void generateData() {

            entries.clear();
            str_arr.clear();
            str_val_arr.clear();

            Device device = Common.getInstance().login_datum.getData().getDevices().getDevices().get(Common.getInstance().selected_login_device);
            String short_name = device.getLatestData().get(feed_type_pos).getShortName();
            String long_name = device.getLatestData().get(feed_type_pos).getName();

            for (int i = 0; i < device_history_datum.getData().getDeviceData().size(); i++) {
                if(device_history_datum.getData().getDeviceData().get(i).getName().equalsIgnoreCase("timestamps")) {
                    str_arr.addAll(device_history_datum.getData().getDeviceData().get(i).getValues());
                }
                if(device_history_datum.getData().getDeviceData().get(i).getName().equalsIgnoreCase(short_name)){
                    str_val_arr.addAll(device_history_datum.getData().getDeviceData().get(i).getValues());
                }
            }

            if(str_arr.size() == 0 || str_val_arr.size() == 0){
                nodata_tv.setText("No Data Found for selected feed type, between selected range");
                chart.setVisibility(View.GONE);
                nodata_tv.setVisibility(View.VISIBLE);
                return;
            }

            for (int i = 0; i < str_arr.size(); i++) {
                entries.add( new Entry( Float.parseFloat(""+i) , Float.parseFloat(str_val_arr.get(i))) );
            }

            LineDataSet lineDataSet = new LineDataSet(entries, long_name);
            lineDataSet.setDrawFilled(true);
            lineDataSet.setDrawCircles(true);
            lineDataSet.setDrawValues(true);
            LineData lineData = new LineData(lineDataSet);
            chart.setData(lineData);
            Description desc = new Description();
            desc.setText("");
            chart.setDescription(desc);
            chart.invalidate();
        }

        @Override
        public void onValueSelected(Entry e, Highlight h) {
            String str = "";
            try {
                str = "DateTime:" + str_arr.get((int)e.getX()) + "  ,  Value:" + e.getY();
            } catch (Exception e1) {
                e1.printStackTrace();
                str = "" + (int)e.getX() + " : " + e.getY();
            }
            Toast.makeText(activity_context, str, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected() {

        }
    }


    public static class ChartDataFragment extends Fragment {
        private Context activity_context;
        private String TAG = "NPN_LOG";
        private RecyclerView data_rv;
        private TextView nodata_in_rv_tv;
        private TabLayout tabLayout;
        private DetailFeedRVAdapter detailFeedRVAdapter;

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            this.activity_context = context;
            Log.d(TAG, "onAttach: ");
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            super.onCreateView(inflater, container, savedInstanceState);
            return inflater.inflate(R.layout.fragment_chart_detail_data, container, false);
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            data_rv = view.findViewById(R.id.data_rv);
            tabLayout = view.findViewById(R.id.tabLayout);
            nodata_in_rv_tv = view.findViewById(R.id.nodata_tv);

            tabLayout.setTabTextColors(getResources().getColor(R.color.grey_text2), getResources().getColor(R.color.emirates_blue));
            tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

            for (int i = 0; i < device_history_datum.getData().getDeviceData().size(); i++) {
                TabLayout.Tab tab = tabLayout.newTab();
                tabLayout.addTab(tab);
            }
            for (int i = 0; i < device_history_datum.getData().getDeviceData().size(); i++) {
                String tab_name = "";
                tab_name = device_history_datum.getData().getDeviceData().get(i).getName();
                tabLayout.getTabAt(i).setText(tab_name);
            }


            data_rv.setLayoutManager(new LinearLayoutManager(activity_context));
            detailFeedRVAdapter = new DetailFeedRVAdapter(activity_context, device_history_datum.getData().getDeviceData(), 0);
            data_rv.setAdapter(detailFeedRVAdapter);

            nodata_in_rv_tv.setVisibility(View.VISIBLE);
            data_rv.setVisibility(View.GONE);
            if(detailFeedRVAdapter.getItemCount() > 0){
                nodata_in_rv_tv.setVisibility(View.GONE);
                data_rv.setVisibility(View.VISIBLE);
            }

            tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    int pos = tab.getPosition();
                    detailFeedRVAdapter = new DetailFeedRVAdapter(activity_context, device_history_datum.getData().getDeviceData(), pos);
                    data_rv.setAdapter(detailFeedRVAdapter);
                    nodata_in_rv_tv.setVisibility(View.VISIBLE);
                    data_rv.setVisibility(View.GONE);
                    if(detailFeedRVAdapter.getItemCount() > 0){
                        nodata_in_rv_tv.setVisibility(View.GONE);
                        data_rv.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                    // do nothing
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {
                    // do nothing
                }
            });

        }
    }

}

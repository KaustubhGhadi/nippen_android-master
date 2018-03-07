package nippenco.com.fragment;

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
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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
import nippenco.com.utils.HorizontalPicker;

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

        getActivity().getSupportFragmentManager().beginTransaction().replace(line_chart_fl.getId(), new DetailedLineDataChartFragment()).commit();
        line_chart_fl.setVisibility(View.VISIBLE);
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
    }

    @Override
    public void onItemSelected(int index) {
        Log.d(TAG, "onItemSelected: " + ""+ picker_values[index]);
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
        private boolean hasPoints = false;
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

            for (int i = 0; i < 5; i++) {
                str_arr.add("" + i + " minutes");
                dbl_arr.add(""+i*4);
            }

            if(dbl_arr.size() != str_arr.size()){
                nodata_tv.setVisibility(View.VISIBLE);
                chart.setVisibility(View.GONE);
                return;
            } else if(dbl_arr.size() < 1){
                nodata_tv.setVisibility(View.VISIBLE);
                chart.setVisibility(View.GONE);
                return;
            }

            numberOfPoints = str_arr.size();

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
//                line.setHasGradientToTransparent(hasGradientToTransparent);
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
}

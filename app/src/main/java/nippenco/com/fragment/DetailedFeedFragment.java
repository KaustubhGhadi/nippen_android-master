package nippenco.com.fragment;

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

import java.util.Calendar;

import nippenco.com.Common;
import nippenco.com.MainActivity;
import nippenco.com.R;

/**
 * Created by aishwarydhare on 20/02/18.
 */

public class DetailedFeedFragment extends Fragment {



    Context activity_context, frag_context;
    private TextView meter_name_tv;
    private FrameLayout chart_container_fl;
    Button select_from_btn, select_to_btn;
    ProgressBar pb;
    public int year,month,day;
    private String TAG = "NPN_LOG";

    Calendar date;

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
        return inflater.inflate(R.layout.fragment_detailed_feed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initLayoutVars(view);

        chart_container_fl.setVisibility(View.VISIBLE);
        pb.setVisibility(View.VISIBLE);

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

        meter_name_tv.setText(Common.getInstance().login_datum.getData().getDevices().getDevices().get(Common.getInstance().selected_login_device).getName());

    }


    private void showDateTimePicker(boolean is_from_date_selecting){
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
        meter_name_tv.setText(Common.getInstance().login_datum.getData().getDevices().getDevices().get(Common.getInstance().selected_login_device).getName());
    }


    private void hiddenKeyboard(View focus) {
        InputMethodManager keyboard = (InputMethodManager) activity_context.getSystemService(Context.INPUT_METHOD_SERVICE);
        keyboard.hideSoftInputFromWindow(focus.getWindowToken(), 0);
    }

}

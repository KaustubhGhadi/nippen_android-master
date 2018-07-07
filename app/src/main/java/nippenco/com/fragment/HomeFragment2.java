package nippenco.com.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import nippenco.com.Common;
import nippenco.com.MainActivity;
import nippenco.com.R;
import nippenco.com.adapter.MeterStatsAdapter;
import nippenco.com.api_model.login.Device;

/**
 * Created by aishwarydhare on 03/02/18.
 */

public class HomeFragment2 extends Fragment/* implements OnWheelScrollListener */{

    Context activity_context, frag_context;
    private TextView meter_name_tv;
    private TextView notiff_count_tv;
    private ImageView notiff_iv;
    private String TAG = "NPN_LOG";
    String[] wheel_values;
//    private AbstractWheel abstractWheel;
    private Device login_datum_device;

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
        return inflater.inflate(R.layout.fragment_home_2, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initLayoutVars(view);

        View.OnClickListener notiff_click_listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity)activity_context).set_fragment(5);
            }
        };

        notiff_iv.setOnClickListener(notiff_click_listener);

        update_UI();

        notiff_count_tv.setText(""+Common.getInstance().login_datum.getData().getUnreadAlerts());

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
//        abstractWheel = v.findViewById(R.id.HorizontalView);
    }


    void update_UI() {
        meter_name_tv.setText(Common.getInstance().login_datum.getData().getDevices().getDevices().get(Common.getInstance().selected_login_device).getName());

        login_datum_device = Common.getInstance().login_datum.getData().getDevices().getDevices().get(Common.getInstance().selected_login_device);

        wheel_values = new String[login_datum_device.getLatestData().size()];
        for (int i = 0; i < login_datum_device.getLatestData().size(); i++) {
            wheel_values[i] = login_datum_device.getLatestData().get(i).getName();
        }

//        ArrayWheelAdapter<String> ampmAdapter = new ArrayWheelAdapter<String>(getContext(), wheel_values);
//        ampmAdapter.setItemResource(R.layout.horizontal_wheel_text_centered);
//        ampmAdapter.setItemTextResource(R.id.text_of_horizontal_wheel);
//        abstractWheel.setViewAdapter(ampmAdapter);
//        abstractWheel.addScrollingListener(this);
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


//
//    @Override
//    public void onScrollingStarted(AbstractWheel abstractWheel) {
//
//    }
//
//    @Override
//    public void onScrollingFinished(AbstractWheel abstractWheel) {
//        Log.d(TAG, "onScrollingFinished: " + ""+wheel_values[abstractWheel.getCurrentItem()]);
//    }


}

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import nippenco.com.Common;
import nippenco.com.Constant;
import nippenco.com.MainActivity;
import nippenco.com.R;
import nippenco.com.api_model.alarm_creation_data.AlarmCreationData;
import nippenco.com.api_model.get_all_alarms.Datum;

import static nippenco.com.Constant.create_alarm;
import static nippenco.com.Constant.host;
import static nippenco.com.Constant.update_alarm;

/**
 * Created by aishwarydhare on 26/02/18.
 */

public class AlertManagementFragment extends Fragment {

    private Context activity_context, frag_context;
    private String TAG = "NPN_LOG";
    private TextView meter_name_tv;
    private LinearLayout alarm_update_ll;
    private ProgressBar pb;
    private EditText value_et, alarm_name_et;
    private Button parameter_btn, condition_btn, submit_btn, device_btn;
    private Datum alarm_to_edit;
    private boolean is_creating_new;
    private JsonObjectRequest jsonObjectRequest;
    private AlarmCreationData alarm_creation_data;
    private String condition_str, parameter_str, device_str;
    private int condition_pos, parameter_pos, device_pos;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity_context = context;
        frag_context = getContext();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)activity_context).selected_frag_id = 3;
        Log.d(TAG, "onResume: ");
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_alert_manage, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initLayoutVars(view);

        if(Common.getInstance().alarm_to_edit != null){
            is_creating_new = false;
            alarm_to_edit = Common.getInstance().alarm_to_edit;
            alarm_name_et.setText(alarm_to_edit.getName());
            value_et.setText(""+alarm_to_edit.getValue());
            condition_btn.setText(alarm_to_edit.getCondition().getName() + ": " + alarm_to_edit.getCondition().getCondition());
            parameter_btn.setText(alarm_to_edit.getParameter().getName());
            device_btn.setText(alarm_to_edit.getDevice().getName());
            meter_name_tv.setText("Modify Alert");
        } else {
            is_creating_new = true;
            meter_name_tv.setText("Create New Alert");
        }


        hit_api_to_get_alarm_creation_data();

        alarm_update_ll.setVisibility(View.GONE);
        pb.setVisibility(View.VISIBLE);


    }


    private void hit_api_to_get_alarm_creation_data() {
        String url = host + Constant.alarm_creation_data;

        Map<String, String> payload = new HashMap<>();
        payload.put("user_id", ""+Common.getInstance().login_datum.getData().getUserInfo().getId());
        payload.put("offset", ""+0);
        JSONObject jsonPayload = new JSONObject(payload);

        jsonObjectRequest = new JsonObjectRequest(url, jsonPayload,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(response.optInt("ResponseCode") != 200){
                            Toast.makeText(activity_context, "Invalid Data!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        AlarmCreationData temp_datum = null;
                        try{
                            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                            temp_datum = gson.fromJson(response.toString(), AlarmCreationData.class);
                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(activity_context, "Invalid Data!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(temp_datum.getData().getAlarmParameters().size() > 0){
                            alarm_creation_data = temp_datum;
                            update_ui(true);
                        } else {
                            Toast.makeText(activity_context, "No Data Found", Toast.LENGTH_SHORT).show();
                            pb.setVisibility(View.GONE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // if error received
                        error.printStackTrace();
                        Toast.makeText(activity_context, "Invalid Data!", Toast.LENGTH_SHORT).show();
                        pb.setVisibility(View.GONE);
                    }
                });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy( 15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ((MainActivity)activity_context).requestQueue.add(jsonObjectRequest);
    }


    private void update_ui(boolean is_fields_visible) {
        if (is_fields_visible) {

            condition_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cancel_active_requests();
                    AlertDialog.Builder builderSingle = new AlertDialog.Builder(frag_context);
                    builderSingle.setTitle("Select Condition");

                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(frag_context, android.R.layout.select_dialog_item);
                    for (int i = 0; i < alarm_creation_data.getData().getConditions().size(); i++) {
                        arrayAdapter.add(alarm_creation_data.getData().getConditions().get(i).getName() + ": " + alarm_creation_data.getData().getConditions().get(i).getCondition());
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
                            condition_str = arrayAdapter.getItem(which);
                            condition_pos = which;
                            condition_btn.setText(""+condition_str);
                            dialog.dismiss();
                        }
                    });
                    builderSingle.show();
                }
            });

            parameter_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cancel_active_requests();
                    AlertDialog.Builder builderSingle = new AlertDialog.Builder(frag_context);
                    builderSingle.setTitle("Select Parameter");

                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(frag_context, android.R.layout.select_dialog_item);
                    for (int i = 0; i < alarm_creation_data.getData().getAlarmParameters().size(); i++) {
                        arrayAdapter.add(alarm_creation_data.getData().getAlarmParameters().get(i).getName());
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
                            parameter_str = arrayAdapter.getItem(which);
                            parameter_pos = which;
                            parameter_btn.setText(""+parameter_str);
                            dialog.dismiss();
                        }
                    });
                    builderSingle.show();
                }
            });

            device_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    cancel_active_requests();
                    AlertDialog.Builder builderSingle = new AlertDialog.Builder(frag_context);
                    builderSingle.setTitle("Select Device");

                    final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(frag_context, android.R.layout.select_dialog_item);
                    for (int i = 0; i < alarm_creation_data.getData().getDevices().size(); i++) {
                        arrayAdapter.add(alarm_creation_data.getData().getDevices().get(i).getName());
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
                            device_str = arrayAdapter.getItem(which);
                            device_pos = which;
                            device_btn.setText(""+device_str);
                            dialog.dismiss();
                        }
                    });
                    builderSingle.show();
                }
            });

            submit_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(condition_str == null){
                        Toast.makeText(activity_context, "Select Condition to continue", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(parameter_str == null){
                        Toast.makeText(activity_context, "Select Parameter to continue", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(device_str == null){
                        Toast.makeText(activity_context, "Select Device to continue", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(alarm_name_et.getText().toString().equalsIgnoreCase("")){
                        Toast.makeText(activity_context, "Enter Alarm Name to continue", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(value_et.getText().toString().equalsIgnoreCase("")){
                        Toast.makeText(activity_context, "Select Value to continue", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    pb.setVisibility(View.VISIBLE);
                    hit_api_to_modify_alarm();
                }
            });

            pb.setVisibility(View.GONE);
            alarm_update_ll.setVisibility(View.VISIBLE);
        }
    }


    private void hit_api_to_modify_alarm() {

        String url = host + update_alarm;
        if(is_creating_new){
            url = host + create_alarm;
        }

        Map<String, String> payload = new HashMap<>();
        payload.put("user_id", ""+Common.getInstance().login_datum.getData().getUserInfo().getId());
        payload.put("alarm_name", ""+alarm_name_et.getText().toString());
        payload.put("device_id", ""+alarm_creation_data.getData().getDevices().get(device_pos).getId());
        payload.put("parameter_id", ""+alarm_creation_data.getData().getAlarmParameters().get(parameter_pos).getId());
        payload.put("condition", ""+alarm_creation_data.getData().getConditions().get(condition_pos).getCondition());
        payload.put("value", ""+value_et.getText().toString());

        if (!is_creating_new) {
            payload.put("alarm_id", ""+alarm_to_edit.getId());
        }

        JSONObject jsonPayload = new JSONObject(payload);

        jsonObjectRequest = new JsonObjectRequest(url, jsonPayload,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(response.optInt("ResponseCode") != 200){
                            Toast.makeText(activity_context, "Invalid Data!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(activity_context, "Alarm Saved", Toast.LENGTH_SHORT).show();
                            pb.setVisibility(View.GONE);
                            ((MainActivity)activity_context).onBackPressed();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // if error received
                        error.printStackTrace();
                        Toast.makeText(activity_context, "Invalid Data!", Toast.LENGTH_SHORT).show();
                        pb.setVisibility(View.GONE);
                    }
                });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy( 15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ((MainActivity)activity_context).requestQueue.add(jsonObjectRequest);
    }


    private void cancel_active_requests() {
        if(jsonObjectRequest != null){
            jsonObjectRequest.cancel();
        }
        pb.setVisibility(View.GONE);
    }


    private void initLayoutVars(View view) {
        meter_name_tv = view.findViewById(R.id.meter_name_tv);
        alarm_name_et = view.findViewById(R.id.alarm_name_et);
        value_et = view.findViewById(R.id.value_et);
        parameter_btn = view.findViewById(R.id.parameter_btn);
        condition_btn = view.findViewById(R.id.condition_btn);
        submit_btn = view.findViewById(R.id.submit_btn);
        device_btn = view.findViewById(R.id.device_btn);
        alarm_update_ll = view.findViewById(R.id.alarm_update_ll);
        pb = view.findViewById(R.id.pb);
    }


    @Override
    public void onDestroy() {
        if (jsonObjectRequest != null) {
            jsonObjectRequest.cancel();
        }
        super.onDestroy();
    }

}

package nippenco.com.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import nippenco.com.MainActivity;
import nippenco.com.R;
import nippenco.com.adapter.AlertsListAdapter;
import nippenco.com.api_model.get_all_alarms.GetAllAlarms;

import static nippenco.com.Constant.get_all_alarms;
import static nippenco.com.Constant.host;

/**
 * Created by aishwarydhare on 24/02/18.
 */

public class ViewAlertsFragment extends Fragment {

    private Context activity_context, frag_context;
    private String TAG = "NPN_LOG";
    RecyclerView alarms_rv;
    TextView meter_name_tv;
    private AlertsListAdapter alertsListAdapter;
    private GetAllAlarms all_alarms_datum;
    private ProgressBar pb;


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
        return inflater.inflate(R.layout.fragment_alert_view, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initLayoutVars(view);

        meter_name_tv.setText("Manage Alert");

        alarms_rv.setVisibility(View.GONE);
        pb.setVisibility(View.VISIBLE);

        hit_api_to_get_all_alarms();
    }

    private void initLayoutVars(View view) {
        alarms_rv = view.findViewById(R.id.notiff_rv);
        meter_name_tv = view.findViewById(R.id.meter_name_tv);
        pb = view.findViewById(R.id.pb);
    }

    void hit_api_to_get_all_alarms(){
        String url = host + get_all_alarms;

        Map<String, String> payload = new HashMap<>();
        payload.put("user_id", ""+Common.getInstance().login_datum.getData().getUserInfo().getId());
        payload.put("offset", ""+0);
        JSONObject jsonPayload = new JSONObject(payload);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, jsonPayload,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(response.optInt("ResponseCode") != 200){
                            Toast.makeText(activity_context, "Invalid Data!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        GetAllAlarms temp_datum = null;
                        try{
                            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                            temp_datum = gson.fromJson(response.toString(), GetAllAlarms.class);
                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(activity_context, "Invalid Data!", Toast.LENGTH_SHORT).show();
                        }
                        if(temp_datum.getData().size() > 0){
                            all_alarms_datum = temp_datum;
                            alertsListAdapter = new AlertsListAdapter(activity_context, all_alarms_datum);
                            alarms_rv.setLayoutManager( new LinearLayoutManager(frag_context));
                            alarms_rv.setAdapter(alertsListAdapter);
                            alarms_rv.setVisibility(View.VISIBLE);
                        } else {
                            Toast.makeText(activity_context, "No Data Found", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // if error received
                        error.printStackTrace();
                        Toast.makeText(activity_context, "Invalid Data!", Toast.LENGTH_SHORT).show();
                    }
                });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy( 15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ((MainActivity)activity_context).requestQueue.add(jsonObjectRequest);
    }

}

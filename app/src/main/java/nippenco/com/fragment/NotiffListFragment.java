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
import nippenco.com.adapter.NotiffListAdapter;
import nippenco.com.api_model.get_all_alerts.GetAllAlerts;

import static nippenco.com.Constant.get_all_alerts;
import static nippenco.com.Constant.host;

/**
 * Created by aishwarydhare on 24/02/18.
 */

public class NotiffListFragment extends Fragment {

    private Context activity_context, frag_context;
    private String TAG = "NPN_LOG";
    RecyclerView notiff_rv;
    NotiffListAdapter notiffListAdapter;
    TextView meter_name_tv;
    private LinearLayoutManager rv_linearLayoutManager;
    private boolean is_fetching_data = false;
    private boolean is_limit_reached = false;
    private Map<String, String> alert_already_available;
    JsonObjectRequest jsonObjectRequest;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.activity_context = context;
        frag_context = getContext();
    }

    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)activity_context).selected_frag_id = 5;
        Log.d(TAG, "onResume: ");
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_notiff_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initLayoutVars(view);

        meter_name_tv.setText("All Alert");
        alert_already_available = new HashMap<>();

        for (int i = 0; i < Common.getInstance().alerts_arr.size(); i++) {
            alert_already_available.put(""+Common.getInstance().alerts_arr.get(i).getId(), "true");
        }

        notiffListAdapter = new NotiffListAdapter(frag_context, Common.getInstance().alerts_arr);
        rv_linearLayoutManager =  new LinearLayoutManager(frag_context);
        notiff_rv.setLayoutManager(rv_linearLayoutManager);
        notiff_rv.setAdapter(notiffListAdapter);
        notiff_rv.setVisibility(View.VISIBLE);

        notiff_rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int visibleItemCount = rv_linearLayoutManager.getChildCount();
                int totalItemCount = rv_linearLayoutManager.getItemCount();
                int firstVisibleItemPosition = rv_linearLayoutManager.findFirstVisibleItemPosition();

                if (!is_fetching_data && !is_limit_reached) {
                    if(firstVisibleItemPosition+visibleItemCount > totalItemCount-4){
                        is_fetching_data = true;
                        hit_api_to_get_alerts_data(25, Common.getInstance().alerts_arr.size());
                    }
                }
            }
        });
    }

    private void hit_api_to_get_alerts_data(int limit, int offset) {

        String url = host + get_all_alerts;

        Map<String, String> payload = new HashMap<>();
        payload.put("user_id", ""+ Common.getInstance().login_datum.getData().getUserInfo().getId());
        payload.put("limit", ""+ limit);
        payload.put("offset", "" + offset);
        JSONObject jsonPayload = new JSONObject(payload);

        jsonObjectRequest = new JsonObjectRequest(url, jsonPayload,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(response.optInt("ResponseCode") != 200){
                            return;
                        }
                        GetAllAlerts temp_datum;
                        try{
                            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                            temp_datum = gson.fromJson(response.toString(), GetAllAlerts.class);
                            for (int i = 0; i < temp_datum.getData().size(); i++) {
                                if (!alert_already_available.containsKey(""+temp_datum.getData().get(i).getId()) && temp_datum.getData().get(i).getId() != -1) {
                                    Common.getInstance().alerts_arr.add(temp_datum.getNormalizedAlert(temp_datum.getData().get(i)));
                                    alert_already_available.put(""+temp_datum.getData().get(i).getId(), "true");
                                }
                            }
                            if(temp_datum.getData().size() == 0){
                                is_limit_reached = true;
                            } else {
                                notiffListAdapter.notifyDataSetChanged();
                            }
                            is_fetching_data = false;
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
                        Toast.makeText(activity_context, "Invalid Data!", Toast.LENGTH_SHORT).show();
                    }
                });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy( 15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ((MainActivity)activity_context).requestQueue.add(jsonObjectRequest);
    }

    private void initLayoutVars(View view) {
        notiff_rv = view.findViewById(R.id.notiff_rv);
        meter_name_tv = view.findViewById(R.id.meter_name_tv);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (jsonObjectRequest != null) {
            jsonObjectRequest.cancel();
        }
    }
}

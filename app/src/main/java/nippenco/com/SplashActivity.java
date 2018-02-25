package nippenco.com;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nippenco.com.api_model.login.Login;
import nippenco.com.api_model.login.RecentAlert;

import static nippenco.com.Constant.host;
import static nippenco.com.Constant.login;

/**
 * Created by aishwarydhare on 18/02/18.
 */

public class SplashActivity extends Activity {

    Activity activity;
    RequestQueue requestQueue;
    private String TAG = "NPN_LOG";
    Bundle bundle;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        activity = this;
        requestQueue = Volley.newRequestQueue(this);
        FirebaseApp.initializeApp(this);

        bundle = getIntent().getExtras();

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        if(sharedPreferences.getBoolean("isLoggedIn", false)){
            fetch_user_data(sharedPreferences.getString("user_name", ""), sharedPreferences.getString("user_pass", ""), FirebaseInstanceId.getInstance().getToken());
        } else {
            startActivity(new Intent(activity, OnboardingActivity.class));
            finish();
        }
    }

    void fetch_user_data(final String user_name, final String user_pass, final String fcm_token){
        if(!user_name.equalsIgnoreCase("") && !user_pass.equalsIgnoreCase("")){

            findViewById(R.id.pb).setVisibility(View.VISIBLE);

            String url = host + login;

            Map<String, String> payload = new HashMap<>();
            payload.put("username", user_name);
            payload.put("password", user_pass);
            JSONObject jsonPayload = new JSONObject(payload);

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, jsonPayload,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            if(response.optInt("ResponseCode") != 200){
                                Toast.makeText(activity, "Invalid Data!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                            try{
                                Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                                Common.getInstance().login_datum = gson.fromJson(response.toString(), Login.class);
                                Common.getInstance().alerts_arr = Common.getInstance().login_datum.getAllNormalizedAlerts();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            findViewById(R.id.pb).setVisibility(View.GONE);
                            Intent intent = new Intent(activity, MainActivity.class);
                            if (bundle != null) {
                                if(!bundle.getString("notification","").equalsIgnoreCase("")) {
                                    String jsonStr = bundle.getString("notification");
                                    intent.putExtra("notification", jsonStr);
                                }
                            }
                            startActivity(intent);
                            finish();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // if error received
                            error.printStackTrace();
                            Toast.makeText(activity, "Invalid Data Fetch Failed, Sign In Again", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(activity, OnboardingActivity.class));
                            finish();
                        }
                    });

            jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy( 15000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(jsonObjectRequest);
        }
    }

}

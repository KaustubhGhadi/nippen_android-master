package nippenco.com;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import nippenco.com.api_model.login.Login;
import nippenco.com.api_model.login.RecentAlert;

import static nippenco.com.Constant.host;
import static nippenco.com.Constant.login;

/**
 * Created by aishwarydhare on 30/01/18.
 */

public class SignUpActivity extends AppCompatActivity {

    Activity activity;
    RequestQueue requestQueue;
    private String TAG = "NPN_LOG";
    private String fcm_token = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        activity = this;
        requestQueue = Volley.newRequestQueue(this);

        findViewById(R.id.sign_in_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText email_et = findViewById(R.id.email_et);
                EditText password_et = findViewById(R.id.password_et);
                verify_credentials(email_et.getText().toString(), password_et.getText().toString());
            }
        });

    }


    void verify_credentials(final String email, final String password){
        if(email.equalsIgnoreCase("")){
            Toast.makeText(activity, "enter email id", Toast.LENGTH_SHORT).show();
            return;
        }

        if(password.equalsIgnoreCase("")){
            Toast.makeText(activity, "enter password", Toast.LENGTH_SHORT).show();
            return;
        }

        toggle_pb_visibility(true);

        String url = host + login;

        Map<String, String> payload = new HashMap<>();
        payload.put("username", email);
        payload.put("password", password);
        payload.put("fcm_token", fcm_token);
        JSONObject jsonPayload = new JSONObject(payload);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, jsonPayload,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.optInt("ResponseCode") != 200){
                                return;
                            }
                            try{
                                Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                                Common.getInstance().login_datum = gson.fromJson(response.toString(), Login.class);
                                Common.getInstance().alerts_arr = Common.getInstance().login_datum.getAllNormalizedAlerts();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                            SharedPreferences.Editor sharedPrefEditor = PreferenceManager.getDefaultSharedPreferences(activity).edit();
                            sharedPrefEditor.putBoolean("isLoggedIn", true);
                            sharedPrefEditor.putString("user_name", email);
                            sharedPrefEditor.putString("user_pass", password);
                            sharedPrefEditor.apply();
                            toggle_pb_visibility(false);
                            startActivity(new Intent(activity, MainActivity.class));
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(activity, "Invalid Data", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError e) {
                        // if error received
                        e.printStackTrace();
                        Toast.makeText(activity, "Invalid Data Fetch Failed, Sign In Again", Toast.LENGTH_SHORT).show();
                    }
                });

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy( 15000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonObjectRequest);
    }


    void toggle_pb_visibility(boolean is_visible){
        if(is_visible){
            findViewById(R.id.forgot_tv).setVisibility(View.GONE);
            findViewById(R.id.sign_in_btn).setVisibility(View.GONE);
            findViewById(R.id.google_sign_in_btn).setVisibility(View.GONE);
            findViewById(R.id.pb).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.pb).setVisibility(View.GONE);
            findViewById(R.id.forgot_tv).setVisibility(View.VISIBLE);
            findViewById(R.id.sign_in_btn).setVisibility(View.VISIBLE);
            findViewById(R.id.google_sign_in_btn).setVisibility(View.VISIBLE);
        }
    }


}

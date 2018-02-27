package nippenco.com;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import nippenco.com.fragment.AlertManagementFragment;
import nippenco.com.fragment.ViewAlertsFragment;
import nippenco.com.fragment.DetailedFeedFragment;
import nippenco.com.fragment.HomeFragment;
import nippenco.com.fragment.NotiffListFragment;
import nippenco.com.fragment.SettingsFragment;

/**
 * Created by aishwarydhare on 18/02/18.
 */

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    FrameLayout fragment_fl;
    RelativeLayout mainView;
    private boolean isDrawerOpen;
    ImageView drawer_icon, back_icon, back_icon_white;
    Activity activity;
    Bundle item_list_frag_bundle;

    public int selected_frag_id = 0;
    private boolean wantToExit = false;
    public View.OnClickListener nav_drawer_items_listener;

    private final String TAG = "NPN_LOG";

    FirebaseDatabase database;
    DatabaseReference iot_data_ref;

    public static boolean isAlive = false;
    public RequestQueue requestQueue;


    @Override
    protected void onPause() {
        super.onPause();
        isAlive = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isAlive = true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "token: "+ FirebaseInstanceId.getInstance().getToken());

        activity = this;
        Common.getInstance().selected_login_device = 0;
        requestQueue = Volley.newRequestQueue(this);

        /*handleDataFromServer();
        setUpFireBaseDatabaseReferences();*/

        mainView = findViewById(R.id.main_view);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        fragment_fl = findViewById(R.id.fragment_fl);

        ActionBarDrawerToggle mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {
            public void onDrawerClosed(View view) {
                isDrawerOpen = false;
                supportInvalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                isDrawerOpen = true;
                supportInvalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                mainView.setTranslationX(slideOffset * drawerView.getWidth());
                mDrawerLayout.bringChildToFront(drawerView);
                mDrawerLayout.requestLayout();
            }
        };

        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerLayout.setScrimColor(getResources().getColor(android.R.color.transparent));

        drawer_icon = findViewById(R.id.drawer_icon);
        View.OnClickListener drawer_listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isDrawerOpen){
                    mDrawerLayout.openDrawer(GravityCompat.START);
                } else {
                    mDrawerLayout.closeDrawers();
                }
            }
        };
        drawer_icon.setOnClickListener(drawer_listener);

        back_icon = findViewById(R.id.back_icon);
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer_icon.setVisibility(View.VISIBLE);
                back_icon.setVisibility(View.GONE);
                back_icon_white.setVisibility(View.GONE);
                onBackPressed();
            }
        });

        back_icon_white = findViewById(R.id.back_icon_white);
        back_icon_white.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawer_icon.setVisibility(View.VISIBLE);
                back_icon.setVisibility(View.GONE);
                back_icon_white.setVisibility(View.GONE);
                onBackPressed();
            }
        });

        nav_drawer_items_listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                on_nav_drawer_item_selected(view);
            }
        };

        findViewById(R.id.home_ll).setOnClickListener(nav_drawer_items_listener);
        findViewById(R.id.detailed_ll).setOnClickListener(nav_drawer_items_listener);
        findViewById(R.id.alerts_ll).setOnClickListener(nav_drawer_items_listener);
        findViewById(R.id.settings_ll).setOnClickListener(nav_drawer_items_listener);
//        findViewById(R.id.signout_ll).setOnClickListener(nav_drawer_items_listener);

        set_fragment(1);

        Bundle notification_bundle = getIntent().getExtras();
        if (notification_bundle != null && !notification_bundle.getString("notification", "").equalsIgnoreCase("")) {
            try {
                JSONObject jsonObject = new JSONObject(notification_bundle.getString("notification", ""));
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(activity, R.style.Theme_AppCompat_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(activity);
                }
                builder.setTitle(jsonObject.getString("title"))
                        .setMessage(jsonObject.getString("body"))
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(activity, "Oops, notiff lapsed", Toast.LENGTH_SHORT).show();
            }
        }
    }


    @SuppressLint("ResourceAsColor")
    public void set_fragment(int id){
        if(id == selected_frag_id){
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
            Log.d("tmp", "set_fragment: already active");
            return;
        }

        Fragment fragment = null;
        String frag_str = "";

        switch (id){
            case 1:
                fragment = new HomeFragment();
                frag_str = "HomeFragment";
                break;

            case 2:
                fragment = new DetailedFeedFragment();
                frag_str = "DetailedFeedFragment";
                break;

            case 3:
                fragment = new ViewAlertsFragment();
                frag_str = "ViewAlertsFragment";
                break;

            case 4:
                fragment = new SettingsFragment();
                frag_str = "SettingsFragment";
                break;

            case 5:
                fragment = new NotiffListFragment();
                frag_str = "NotiffListFragment";
                break;

            case 6:
                fragment = new AlertManagementFragment();
                frag_str = "AlertManagementFragment";
                break;

            default:
                fragment = new HomeFragment();
                frag_str = "HomeFragment";
                break;
        }

        if(frag_str.equalsIgnoreCase("DetailedFeedFragment") || frag_str.equalsIgnoreCase("ItemsListFragment")){
            drawer_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_drawer_blue));
        } else {
            drawer_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_drawer));
        }

        if(frag_str.equalsIgnoreCase("NotiffListFragment") || frag_str.equalsIgnoreCase("AlertManagementFragment")){
            drawer_icon.setVisibility(View.GONE);
            back_icon_white.setVisibility(View.GONE);
            back_icon.setVisibility(View.VISIBLE);
        } else {
            drawer_icon.setVisibility(View.VISIBLE);
            back_icon.setVisibility(View.GONE);
            back_icon_white.setVisibility(View.GONE);
        }

        if(getSupportFragmentManager().getBackStackEntryCount() == 0 && !frag_str.equalsIgnoreCase("HomeFragment")){
            getSupportFragmentManager().beginTransaction().addToBackStack(frag_str).replace(R.id.fragment_fl, fragment, frag_str).commit();

        } else if(getSupportFragmentManager().getBackStackEntryCount() == 1 && frag_str.equalsIgnoreCase("AlertManagementFragment")) {
            getSupportFragmentManager().beginTransaction().addToBackStack(frag_str).replace(R.id.fragment_fl, fragment, frag_str).commit();

        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_fl, fragment, frag_str).commit();

        }

        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void on_nav_drawer_item_selected(View view){
        switch (view.getId()){
            case R.id.home_ll:
                set_fragment(1);
                break;

            case R.id.detailed_ll:
                set_fragment(2);
                break;

            case R.id.alerts_ll:
                set_fragment(3);
                break;

            case R.id.settings_ll:
                set_fragment(4);
                break;

            case R.id.signout_ll:
                sign_out_user();
                break;

            default:
                // do nothing
                break;
        }
    }


    public void sign_out_user(){
        startActivity(new Intent(activity, OnboardingActivity.class));
        finish();
    }


    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }

        if(selected_frag_id == 1){
            if (wantToExit) {
                try {
                    super.onBackPressed();
                    this.finish();
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                wantToExit = true;
            }

            Toast.makeText(this, "Click again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    wantToExit = false;
                }
            }, 2000);
        } else {
            if(true){
                drawer_icon.setVisibility(View.VISIBLE);
                back_icon.setVisibility(View.GONE);
                back_icon_white.setVisibility(View.GONE);
            }
            super.onBackPressed();
        }

        if(selected_frag_id == 1){
            drawer_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_drawer));
        }
    }

    /*class MySnackBarListener implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            AlertDialog.Builder builder;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                builder = new AlertDialog.Builder(activity, R.style.Theme_AppCompat_Dialog_Alert);
            } else {
                builder = new AlertDialog.Builder(activity);
            }
            builder.setTitle(cloudMessage.title)
                    .setMessage(cloudMessage.body)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }*/





}

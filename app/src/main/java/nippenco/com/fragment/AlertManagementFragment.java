package nippenco.com.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import nippenco.com.MainActivity;
import nippenco.com.R;
import nippenco.com.adapter.AlertsListAdapter;
import nippenco.com.api_model.get_all_alarms.GetAllAlarms;

/**
 * Created by aishwarydhare on 26/02/18.
 */

public class AlertManagementFragment extends Fragment {

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
        return inflater.inflate(R.layout.fragment_alert_manage, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initLayoutVars(view);

        meter_name_tv.setText("Manage Alert");

        alarms_rv.setVisibility(View.GONE);
        pb.setVisibility(View.VISIBLE);

    }

    private void initLayoutVars(View view) {
        alarms_rv = view.findViewById(R.id.notiff_rv);
        meter_name_tv = view.findViewById(R.id.meter_name_tv);
        pb = view.findViewById(R.id.pb);
    }

}

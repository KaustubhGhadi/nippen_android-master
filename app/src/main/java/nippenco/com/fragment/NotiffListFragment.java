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
import java.util.ArrayList;
import nippenco.com.Common;
import nippenco.com.MainActivity;
import nippenco.com.R;
import nippenco.com.adapter.NotiffListAdapter;
import nippenco.com.api_model.login.RecentAlert;

/**
 * Created by aishwarydhare on 24/02/18.
 */

public class NotiffListFragment extends Fragment {

    private Context activity_context, frag_context;
    private String TAG = "NPN_LOG";
    RecyclerView notiff_rv;
    NotiffListAdapter notiffListAdapter;
    TextView meter_name_tv;


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

        meter_name_tv.setText("All Alerts");

        notiffListAdapter = new NotiffListAdapter(frag_context, (ArrayList<RecentAlert>) Common.getInstance().login_datum.getData().getRecentAlerts());
        notiff_rv.setLayoutManager( new LinearLayoutManager(frag_context));
        notiff_rv.setAdapter(notiffListAdapter);
        notiff_rv.setVisibility(View.VISIBLE);
    }

    private void initLayoutVars(View view) {
        notiff_rv = view.findViewById(R.id.notiff_rv);
        meter_name_tv = view.findViewById(R.id.meter_name_tv);
    }

}

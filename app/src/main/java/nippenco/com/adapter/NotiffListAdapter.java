package nippenco.com.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import nippenco.com.Common;
import nippenco.com.R;
import nippenco.com.api_model.Alert;
import nippenco.com.api_model.login.RecentAlert;

/**
 * Created by aishwarydhare on 24/02/18.
 */

public class NotiffListAdapter extends RecyclerView.Adapter<NotiffListAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList<Alert> alerts;

    public NotiffListAdapter(Context activity_context, ArrayList<Alert> para_alerts) {
        this.mContext = activity_context;
        this.alerts = para_alerts;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view;
        if(viewType == 0){
            view = inflater.inflate(R.layout.item_notiff, parent, false);
        } else {
            view = inflater.inflate(R.layout.item_loader, parent, false);
        }
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position != alerts.size()) {
            Alert recentAlert = alerts.get(position);
            holder.title_tv.setText("" + recentAlert.getDeviceName());
            holder.alarm_tv.setText("" + recentAlert.getAlarmName());
            holder.msg_tv.setText("" + recentAlert.getDescription());
            holder.condition_tv.setText("" + recentAlert.getConditionName() + " " + recentAlert.getConditionValue());
            holder.value_tv.setText("" + recentAlert.getFeedValue());
//            holder.title_tv.setText("" + recentAlert.getDeviceName());
        }
    }

    @Override
    public int getItemCount() {
        return alerts.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == alerts.size()){
            return 1;
        } else {
            return 0;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView title_tv, alarm_tv, date_time_tv, value_tv, condition_tv, msg_tv;

        ViewHolder(View itemView) {
            super(itemView);
            title_tv = itemView.findViewById(R.id.title_tv);
            alarm_tv = itemView.findViewById(R.id.alarm_tv);
            date_time_tv = itemView.findViewById(R.id.date_time_tv);
            condition_tv = itemView.findViewById(R.id.condition_tv);
            value_tv = itemView.findViewById(R.id.value_tv);
            msg_tv = itemView.findViewById(R.id.msg_tv);
        }
    }
}

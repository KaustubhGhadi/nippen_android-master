package nippenco.com.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import nippenco.com.Common;
import nippenco.com.MainActivity;
import nippenco.com.R;
import nippenco.com.api_model.get_all_alarms.Datum;
import nippenco.com.api_model.get_all_alarms.GetAllAlarms;

/**
 * Created by aishwarydhare on 26/02/18.
 */

public class AlertsListAdapter extends RecyclerView.Adapter<AlertsListAdapter.ViewHolder>{

    private Context mContext;
    private GetAllAlarms allAlarms;

    public AlertsListAdapter(Context para_context, GetAllAlarms para_allAlarms) {
        this.mContext = para_context;
        this.allAlarms = para_allAlarms;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_alarm, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final int final_pos = position;
        final Datum alarm = allAlarms.getData().get(position);
        holder.title_tv.setText(alarm.getName());
        holder.subtitle_tv.setText(alarm.getDevice().getName());
        holder.para_name_tv.setText(alarm.getParameter().getName());
        holder.para_sub_tv.setText("[" + alarm.getParameter().getKey() + "]");
        holder.para_type_tv.setText(alarm.getParameter().getDataType());
        holder.condition_tv.setText(alarm.getCondition().getName());
        holder.value_tv.setText("" + alarm.getValue());

        holder.edit_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.getInstance().alarm_to_edit = alarm;
                Common.getInstance().alarm_to_edit_pos = final_pos;
                ((MainActivity)mContext).set_fragment(6);
            }
        });
    }

    @Override
    public int getItemCount() {
        return allAlarms.getData().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView title_tv, edit_tv, subtitle_tv, para_name_tv, para_sub_tv, para_type_tv, condition_tv, value_tv;

        ViewHolder(View itemView) {
            super(itemView);
            title_tv = itemView.findViewById(R.id.title_tv);
            edit_tv = itemView.findViewById(R.id.edit_tv);
            subtitle_tv = itemView.findViewById(R.id.subtitle_tv);
            para_name_tv = itemView.findViewById(R.id.para_name_tv);
            para_sub_tv = itemView.findViewById(R.id.para_sub_tv);
            para_type_tv = itemView.findViewById(R.id.para_type_tv);
            condition_tv = itemView.findViewById(R.id.condition_tv);
            value_tv = itemView.findViewById(R.id.value_tv);
        }
    }
}

package nippenco.com.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import nippenco.com.Common;
import nippenco.com.MainActivity;
import nippenco.com.R;
import nippenco.com.api_model.login.Device;

/**
 * Created by aishwarydhare on 18/02/18.
 */

public class MeterStatsAdapter extends RecyclerView.Adapter<MeterStatsAdapter.ViewHolder>{

    private ArrayList<String> temp = new ArrayList<>();
    private Context mContext;
    private Device login_datum_device;

    public MeterStatsAdapter(Context frag_context, ArrayList<String> strings) {
        this.mContext = frag_context;
        this.temp = strings;
        login_datum_device = Common.getInstance().login_datum.getData().getDevices().getDevices().get(Common.getInstance().selected_login_device);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_dashboard_stat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.state_title_tv.setText(login_datum_device.getLatestData().get(position).getName());
        holder.state_value_tv.setText(""+login_datum_device.getLatestData().get(position).getValue());

        holder.root_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.getInstance().device_history_for = holder.state_title_tv.getText().toString();
                Common.getInstance().device_history_for_pos = position;
                ((MainActivity)mContext).set_fragment(2);
            }
        });
    }

    @Override
    public int getItemCount() {
        return login_datum_device.getLatestData().size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView state_title_tv, state_value_tv;
        CardView root_cv;
        ViewHolder(View itemView) {
            super(itemView);
            state_title_tv = itemView.findViewById(R.id.state_title_tv);
            state_value_tv = itemView.findViewById(R.id.state_value_tv);
            root_cv = itemView.findViewById(R.id.root_cv);
        }
    }
}

package nippenco.com.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nippenco.com.R;
import nippenco.com.api_model.get_device_history.DeviceDatum;

/**
 * Created by aishwarydhare on 10/03/18.
 */

public class DetailFeedRVAdapter extends RecyclerView.Adapter<DetailFeedRVAdapter.ViewHolder>{

    private Context activity_context;
    private List<DeviceDatum> deviceDatum;
    private int data_pos;
    private ArrayList<String> date_str_arr = new ArrayList<>();

    public DetailFeedRVAdapter(Context activity_context, List<DeviceDatum> para_deviceData, int data_pos) {
        this.activity_context = activity_context;
        this.deviceDatum = para_deviceData;
        this.data_pos = data_pos;

        for (int i = 0; i < deviceDatum.size(); i++) {
            if(deviceDatum.get(i).getName().equalsIgnoreCase("timestamps")){
                date_str_arr.addAll(deviceDatum.get(i).getValues());
            }
        }
    }

    @NonNull
    @Override
    public DetailFeedRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(activity_context);
        View view = inflater.inflate(R.layout.item_detail_feed, parent, false);
        return new DetailFeedRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DetailFeedRVAdapter.ViewHolder holder, int position) {
        try {
            holder.feed_left_tv.setText(date_str_arr.get(position));
        } catch (Exception e) {
            e.printStackTrace();
            holder.feed_left_tv.setText("Value: ");
        }
        holder.feed_right_tv.setText(deviceDatum.get(data_pos).getValues().get(position));
    }

    @Override
    public int getItemCount() {
        try {
            return deviceDatum.get(data_pos).getValues().size();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView feed_left_tv, feed_right_tv;

        ViewHolder(View itemView) {
            super(itemView);
            feed_left_tv = itemView.findViewById(R.id.feed_left_tv);
            feed_right_tv = itemView.findViewById(R.id.feed_right_tv);
        }
    }
}
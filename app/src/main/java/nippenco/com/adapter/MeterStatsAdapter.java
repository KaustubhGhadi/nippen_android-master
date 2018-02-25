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

    public MeterStatsAdapter(Context frag_context, ArrayList<String> strings) {
        this.mContext = frag_context;
        this.temp = strings;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.item_dashboard_stat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Device login_datum_device = Common.getInstance().login_datum.getData().getDevices().getDevices().get(Common.getInstance().selected_login_device);
        switch (position){
            case 0 :
                holder.state_title_tv.setText("Comm Interval");
                holder.state_value_tv.setText(""+login_datum_device.getCommInterval());
                break;
            case 1 :
                holder.state_title_tv.setText("CT Multiplier");
                holder.state_value_tv.setText("" + login_datum_device.getLatestData().getCTMultiplier());
                break;
            case 2 :
                holder.state_title_tv.setText("PT Multiplier");
                holder.state_value_tv.setText("" + login_datum_device.getLatestData().getPTMultiplier());
                break;
            case 3 :
                holder.state_title_tv.setText("Active Power kW-l1");
                holder.state_value_tv.setText("" + login_datum_device.getLatestData().getActivePowerKWL1());
                break;
            case 4 :
                holder.state_title_tv.setText("Active Power kW-l2");
                holder.state_value_tv.setText("" + login_datum_device.getLatestData().getActivePowerKWL2());
                break;
            case 5 :
                holder.state_title_tv.setText("Active Power kW-l3");
                holder.state_value_tv.setText("" + login_datum_device.getLatestData().getActivePowerKWL3());
                break;
            case 6 :
                holder.state_title_tv.setText("Apparent Energy kVAh");
                holder.state_value_tv.setText("" + login_datum_device.getLatestData().getApparentEnergyKVAh());
                break;
            case 7 :
                holder.state_title_tv.setText("Power Factor PF1");
                holder.state_value_tv.setText("" + login_datum_device.getLatestData().getPowerFactorPF1());
                break;
            case 8 :
                holder.state_title_tv.setText("Power Factor PF2");
                holder.state_value_tv.setText("" + login_datum_device.getLatestData().getPowerFactorPF2());
                break;
            case 9 :
                holder.state_title_tv.setText("Power Factor PF3");
                holder.state_value_tv.setText("" + login_datum_device.getLatestData().getPowerFactorPF3());
                break;
            case 10 :
                holder.state_title_tv.setText("Reactive Power kVAr1");
                holder.state_value_tv.setText("" + login_datum_device.getLatestData().getReActivePowerKVAr1());
                break;
            case 11 :
                holder.state_title_tv.setText("Reactive Power kVAr2");
                holder.state_value_tv.setText("" + login_datum_device.getLatestData().getReActivePowerKVAr2());
                break;
            case 13 :
                holder.state_title_tv.setText("Reactive Power kVAr3");
                holder.state_value_tv.setText("" + login_datum_device.getLatestData().getReActivePowerKVAr3());
                break;
            case 14 :
                holder.state_title_tv.setText("Apparent Power kVA1");
                holder.state_value_tv.setText("" + login_datum_device.getLatestData().getApparentPowerKVA1());
                break;
            case 15 :
                holder.state_title_tv.setText("Apparent Power kVA2");
                holder.state_value_tv.setText("" + login_datum_device.getLatestData().getApparentPowerKVA2());
                break;
            case 16 :
                holder.state_title_tv.setText("Apparent Power kVA3");
                holder.state_value_tv.setText("" + login_datum_device.getLatestData().getApparentPowerKVA3());
                break;
            case 17 :
                holder.state_title_tv.setText("Volts VLN-L1L2");
                holder.state_value_tv.setText("" + login_datum_device.getLatestData().getVoltsVLNL1L2());
                break;
            case 18 :
                holder.state_title_tv.setText("Volts VLN-L2L3");
                holder.state_value_tv.setText("" + login_datum_device.getLatestData().getVoltsVLNL2L3());
                break;
            case 19 :
                holder.state_title_tv.setText("Volts VLN-L3L1");
                holder.state_value_tv.setText("" + login_datum_device.getLatestData().getVoltsVLNL3L1());
                break;
            case 20 :
                holder.state_title_tv.setText("Amps I1");
                holder.state_value_tv.setText("" + login_datum_device.getLatestData().getAmpsI1());
                break;
            case 21 :
                holder.state_title_tv.setText("Amps I2");
                holder.state_value_tv.setText("" + login_datum_device.getLatestData().getAmpsI2());
                break;
            case 22 :
                holder.state_title_tv.setText("Amps I3");
                holder.state_value_tv.setText("" + login_datum_device.getLatestData().getAmpsI3());
                break;
            case 23 :
                holder.state_title_tv.setText("Frequency Hz");
                holder.state_value_tv.setText("" + login_datum_device.getLatestData().getFrequencyHz());
                break;
            case 24 :
                holder.state_title_tv.setText("Run Hour");
                holder.state_value_tv.setText("" + login_datum_device.getLatestData().getRunHour());
                break;
            case 25 :
                holder.state_title_tv.setText("%THD Amps-I1");
                holder.state_value_tv.setText("" + login_datum_device.getLatestData().getTHDAmpsI1());
                break;
            case 26 :
                holder.state_title_tv.setText("%THD Amps-I2");
                holder.state_value_tv.setText("" + login_datum_device.getLatestData().getTHDAmpsI2());
                break;
            case 27 :
                holder.state_title_tv.setText("Communication Status");
                holder.state_value_tv.setText("" + login_datum_device.getLatestData().getCommunicationStatus());
                break;
            case 28 :
                holder.state_title_tv.setText("Last Updated at");
                holder.state_value_tv.setText("N/A");
                break;
        }

        holder.root_cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.getInstance().device_history_for = holder.state_title_tv.getText().toString();
                ((MainActivity)mContext).set_fragment(2);
            }
        });


    }

    @Override
    public int getItemCount() {
        return 29;
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


package nippenco.com.api_model.alarm_creation_data;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("devices")
    @Expose
    private List<Device> devices = null;
    @SerializedName("conditions")
    @Expose
    private List<Condition> conditions = null;
    @SerializedName("alarm_parameters")
    @Expose
    private List<AlarmParameter> alarmParameters = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param conditions
     * @param alarmParameters
     * @param devices
     */
    public Data(List<Device> devices, List<Condition> conditions, List<AlarmParameter> alarmParameters) {
        super();
        this.devices = devices;
        this.conditions = conditions;
        this.alarmParameters = alarmParameters;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    public List<AlarmParameter> getAlarmParameters() {
        return alarmParameters;
    }

    public void setAlarmParameters(List<AlarmParameter> alarmParameters) {
        this.alarmParameters = alarmParameters;
    }

}

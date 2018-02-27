
package nippenco.com.api_model.get_device_history;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("timestamps")
    @Expose
    private Timestamps timestamps;
    @SerializedName("device_data")
    @Expose
    private List<DeviceDatum> deviceData = null;
    @SerializedName("device")
    @Expose
    private Device device;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param deviceData
     * @param timestamps
     * @param device
     */
    public Data(Timestamps timestamps, List<DeviceDatum> deviceData, Device device) {
        super();
        this.timestamps = timestamps;
        this.deviceData = deviceData;
        this.device = device;
    }

    public Timestamps getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(Timestamps timestamps) {
        this.timestamps = timestamps;
    }

    public List<DeviceDatum> getDeviceData() {
        return deviceData;
    }

    public void setDeviceData(List<DeviceDatum> deviceData) {
        this.deviceData = deviceData;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

}

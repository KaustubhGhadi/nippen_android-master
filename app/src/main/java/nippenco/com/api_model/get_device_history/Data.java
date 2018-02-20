
package nippenco.com.api_model.get_device_history;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("timestamps")
    @Expose
    private Timestamps timestamps;
    @SerializedName("device")
    @Expose
    private Device device;
    @SerializedName("device_data")
    @Expose
    private DeviceData deviceData;

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
    public Data(Timestamps timestamps, Device device, DeviceData deviceData) {
        super();
        this.timestamps = timestamps;
        this.device = device;
        this.deviceData = deviceData;
    }

    public Timestamps getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(Timestamps timestamps) {
        this.timestamps = timestamps;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public DeviceData getDeviceData() {
        return deviceData;
    }

    public void setDeviceData(DeviceData deviceData) {
        this.deviceData = deviceData;
    }

}

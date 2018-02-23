
package nippenco.com.api_model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Device {

    @SerializedName("comm_interval")
    @Expose
    private double commInterval;
    @SerializedName("latest_data")
    @Expose
    private LatestData latestData;
    @SerializedName("id")
    @Expose
    private double id;
    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("ct_gain")
    @Expose
    private double ctGain;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("vt_gain")
    @Expose
    private double vtGain;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Device() {
    }

    /**
     * 
     * @param id
     * @param vtGain
     * @param latestData
     * @param name
     * @param ctGain
     * @param deviceId
     * @param commInterval
     */
    public Device(double commInterval, LatestData latestData, double id, String deviceId, double ctGain, String name, double vtGain) {
        super();
        this.commInterval = commInterval;
        this.latestData = latestData;
        this.id = id;
        this.deviceId = deviceId;
        this.ctGain = ctGain;
        this.name = name;
        this.vtGain = vtGain;
    }

    public double getCommInterval() {
        return commInterval;
    }

    public void setCommInterval(double commInterval) {
        this.commInterval = commInterval;
    }

    public LatestData getLatestData() {
        return latestData;
    }

    public void setLatestData(LatestData latestData) {
        this.latestData = latestData;
    }

    public double getId() {
        return id;
    }

    public void setId(double id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public double getCtGain() {
        return ctGain;
    }

    public void setCtGain(double ctGain) {
        this.ctGain = ctGain;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getVtGain() {
        return vtGain;
    }

    public void setVtGain(double vtGain) {
        this.vtGain = vtGain;
    }

}

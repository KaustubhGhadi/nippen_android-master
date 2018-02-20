
package nippenco.com.api_model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Device {

    @SerializedName("comm_interval")
    @Expose
    private Integer commInterval;
    @SerializedName("latest_data")
    @Expose
    private LatestData latestData;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("ct_gain")
    @Expose
    private Integer ctGain;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("vt_gain")
    @Expose
    private Integer vtGain;

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
    public Device(Integer commInterval, LatestData latestData, Integer id, String deviceId, Integer ctGain, String name, Integer vtGain) {
        super();
        this.commInterval = commInterval;
        this.latestData = latestData;
        this.id = id;
        this.deviceId = deviceId;
        this.ctGain = ctGain;
        this.name = name;
        this.vtGain = vtGain;
    }

    public Integer getCommInterval() {
        return commInterval;
    }

    public void setCommInterval(Integer commInterval) {
        this.commInterval = commInterval;
    }

    public LatestData getLatestData() {
        return latestData;
    }

    public void setLatestData(LatestData latestData) {
        this.latestData = latestData;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getCtGain() {
        return ctGain;
    }

    public void setCtGain(Integer ctGain) {
        this.ctGain = ctGain;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getVtGain() {
        return vtGain;
    }

    public void setVtGain(Integer vtGain) {
        this.vtGain = vtGain;
    }

}

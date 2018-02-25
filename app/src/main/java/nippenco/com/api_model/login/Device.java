
package nippenco.com.api_model.login;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Device {

    @SerializedName("ct_gain")
    @Expose
    private Integer ctGain;
    @SerializedName("vt_gain")
    @Expose
    private Integer vtGain;
    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("latest_data")
    @Expose
    private List<LatestDatum> latestData = null;
    @SerializedName("comm_interval")
    @Expose
    private Integer commInterval;

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
    public Device(Integer ctGain, Integer vtGain, String deviceId, String name, Integer id, List<LatestDatum> latestData, Integer commInterval) {
        super();
        this.ctGain = ctGain;
        this.vtGain = vtGain;
        this.deviceId = deviceId;
        this.name = name;
        this.id = id;
        this.latestData = latestData;
        this.commInterval = commInterval;
    }

    public Integer getCtGain() {
        return ctGain;
    }

    public void setCtGain(Integer ctGain) {
        this.ctGain = ctGain;
    }

    public Integer getVtGain() {
        return vtGain;
    }

    public void setVtGain(Integer vtGain) {
        this.vtGain = vtGain;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<LatestDatum> getLatestData() {
        return latestData;
    }

    public void setLatestData(List<LatestDatum> latestData) {
        this.latestData = latestData;
    }

    public Integer getCommInterval() {
        return commInterval;
    }

    public void setCommInterval(Integer commInterval) {
        this.commInterval = commInterval;
    }

}


package nippenco.com.api_model.login;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Device {

    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("timestamp")
    @Expose
    private Timestamp timestamp;
    @SerializedName("latest_data")
    @Expose
    private List<LatestDatum> latestData = null;
    @SerializedName("total_phase")
    @Expose
    private Integer totalPhase;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("ct_gain")
    @Expose
    private Integer ctGain;
    @SerializedName("comm_interval")
    @Expose
    private Integer commInterval;
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
     * @param timestamp
     * @param vtGain
     * @param totalPhase
     * @param latestData
     * @param name
     * @param ctGain
     * @param deviceId
     * @param commInterval
     */
    public Device(String deviceId, Timestamp timestamp, List<LatestDatum> latestData, Integer totalPhase, Integer id, Integer ctGain, Integer commInterval, String name, Integer vtGain) {
        super();
        this.deviceId = deviceId;
        this.timestamp = timestamp;
        this.latestData = latestData;
        this.totalPhase = totalPhase;
        this.id = id;
        this.ctGain = ctGain;
        this.commInterval = commInterval;
        this.name = name;
        this.vtGain = vtGain;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public List<LatestDatum> getLatestData() {
        return latestData;
    }

    public void setLatestData(List<LatestDatum> latestData) {
        this.latestData = latestData;
    }

    public Integer getTotalPhase() {
        return totalPhase;
    }

    public void setTotalPhase(Integer totalPhase) {
        this.totalPhase = totalPhase;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCtGain() {
        return ctGain;
    }

    public void setCtGain(Integer ctGain) {
        this.ctGain = ctGain;
    }

    public Integer getCommInterval() {
        return commInterval;
    }

    public void setCommInterval(Integer commInterval) {
        this.commInterval = commInterval;
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

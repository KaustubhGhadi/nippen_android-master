
package nippenco.com.api_model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecentAlert {

    @SerializedName("alarm_name")
    @Expose
    private String alarmName;
    @SerializedName("feed_value")
    @Expose
    private Double feedValue;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("condition_value")
    @Expose
    private Double conditionValue;
    @SerializedName("device_id")
    @Expose
    private Integer deviceId;
    @SerializedName("device_name")
    @Expose
    private String deviceName;
    @SerializedName("condition_name")
    @Expose
    private String conditionName;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("description")
    @Expose
    private String description;

    /**
     * No args constructor for use in serialization
     * 
     */
    public RecentAlert() {
    }

    /**
     * 
     * @param id
     * @param conditionValue
     * @param deviceName
     * @param description
     * @param createdAt
     * @param feedValue
     * @param conditionName
     * @param alarmName
     * @param deviceId
     */
    public RecentAlert(String alarmName, Double feedValue, String createdAt, Double conditionValue, Integer deviceId, String deviceName, String conditionName, Integer id, String description) {
        super();
        this.alarmName = alarmName;
        this.feedValue = feedValue;
        this.createdAt = createdAt;
        this.conditionValue = conditionValue;
        this.deviceId = deviceId;
        this.deviceName = deviceName;
        this.conditionName = conditionName;
        this.id = id;
        this.description = description;
    }

    public String getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    public Double getFeedValue() {
        return feedValue;
    }

    public void setFeedValue(Double feedValue) {
        this.feedValue = feedValue;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Double getConditionValue() {
        return conditionValue;
    }

    public void setConditionValue(Double conditionValue) {
        this.conditionValue = conditionValue;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getConditionName() {
        return conditionName;
    }

    public void setConditionName(String conditionName) {
        this.conditionName = conditionName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

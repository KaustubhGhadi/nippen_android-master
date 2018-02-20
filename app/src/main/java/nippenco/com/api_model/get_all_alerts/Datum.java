
package nippenco.com.api_model.get_all_alerts;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("device_name")
    @Expose
    private String deviceName;
    @SerializedName("alarm_name")
    @Expose
    private String alarmName;
    @SerializedName("condition_value")
    @Expose
    private Integer conditionValue;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("condition_name")
    @Expose
    private String conditionName;
    @SerializedName("device_id")
    @Expose
    private Integer deviceId;
    @SerializedName("feed_value")
    @Expose
    private Double feedValue;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Datum() {
    }

    /**
     * 
     * @param conditionValue
     * @param deviceName
     * @param description
     * @param feedValue
     * @param conditionName
     * @param alarmName
     * @param deviceId
     */
    public Datum(String deviceName, String alarmName, Integer conditionValue, String description, String conditionName, Integer deviceId, Double feedValue) {
        super();
        this.deviceName = deviceName;
        this.alarmName = alarmName;
        this.conditionValue = conditionValue;
        this.description = description;
        this.conditionName = conditionName;
        this.deviceId = deviceId;
        this.feedValue = feedValue;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    public Integer getConditionValue() {
        return conditionValue;
    }

    public void setConditionValue(Integer conditionValue) {
        this.conditionValue = conditionValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getConditionName() {
        return conditionName;
    }

    public void setConditionName(String conditionName) {
        this.conditionName = conditionName;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public Double getFeedValue() {
        return feedValue;
    }

    public void setFeedValue(Double feedValue) {
        this.feedValue = feedValue;
    }

}

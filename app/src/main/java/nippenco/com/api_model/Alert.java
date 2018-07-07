package nippenco.com.api_model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by aishwarydhare on 25/02/18.
 */

public class Alert {

        @SerializedName("condition_value")
        @Expose
        private Double conditionValue;
        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("device_id")
        @Expose
        private Integer deviceId;
        @SerializedName("condition_name")
        @Expose
        private String conditionName;
        @SerializedName("device_name")
        @Expose
        private String deviceName;
        @SerializedName("feed_value")
        @Expose
        private Double feedValue;
        @SerializedName("alarm_name")
        @Expose
        private String alarmName;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("created_at")
        @Expose
        private String createdAt;

        /**
         * No args constructor for use in serialization
         *
         */
    public Alert() {
    }

        /**
         *
         * @param id
         * @param conditionValue
         * @param deviceName
         * @param createdAt
         * @param description
         * @param feedValue
         * @param alarmName
         * @param conditionName
         * @param deviceId
         */
    public Alert(Double conditionValue, Integer id, Integer deviceId, String conditionName, String deviceName, Double feedValue, String alarmName, String description, String createdAt) {
        super();
        this.conditionValue = conditionValue;
        this.id = id;
        this.deviceId = deviceId;
        this.conditionName = conditionName;
        this.deviceName = deviceName;
        this.feedValue = feedValue;
        this.alarmName = alarmName;
        this.description = description;
        this.createdAt = createdAt;
    }

    public Double getConditionValue() {
        return conditionValue;
    }

    public void setConditionValue(Double conditionValue) {
        this.conditionValue = conditionValue;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public String getConditionName() {
        return conditionName;
    }

    public void setConditionName(String conditionName) {
        this.conditionName = conditionName;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public Double getFeedValue() {
        return feedValue;
    }

    public void setFeedValue(Double feedValue) {
        this.feedValue = feedValue;
    }

    public String getAlarmName() {
        return alarmName;
    }

    public void setAlarmName(String alarmName) {
        this.alarmName = alarmName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}

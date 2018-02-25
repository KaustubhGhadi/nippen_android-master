
package nippenco.com.api_model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import nippenco.com.api_model.Alert;

public class Login {

    @SerializedName("Data")
    @Expose
    private Data data;
    @SerializedName("ResponseCode")
    @Expose
    private Integer responseCode;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Login() {
    }

    /**
     * 
     * @param responseCode
     * @param data
     */
    public Login(Data data, Integer responseCode) {
        super();
        this.data = data;
        this.responseCode = responseCode;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public ArrayList<Alert> getAllNormalizedAlerts(){
        ArrayList<Alert> normal_alert_arr = new ArrayList<>();
        for (int i = 0; i < getData().getRecentAlerts().size(); i++) {
            RecentAlert ra = getData().getRecentAlerts().get(i);
            normal_alert_arr.add(new Alert(ra.getConditionValue(), ra.getId(), ra.getDeviceId(), ra.getConditionName(), ra.getDeviceName(), ra.getFeedValue(), ra.getAlarmName(), ra.getDescription(), ra.getCreatedAt()));
        }
        return normal_alert_arr;
    }

    public Alert getNormalizedAlert(RecentAlert ra){
        return new Alert(ra.getConditionValue(), ra.getId(), ra.getDeviceId(), ra.getConditionName(), ra.getDeviceName(), ra.getFeedValue(), ra.getAlarmName(), ra.getDescription(), ra.getCreatedAt());
    }


}

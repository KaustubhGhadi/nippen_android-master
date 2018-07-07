
package nippenco.com.api_model.get_all_alerts;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import nippenco.com.api_model.Alert;
import nippenco.com.api_model.login.RecentAlert;

public class GetAllAlerts {

    @SerializedName("ResponseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("Data")
    @Expose
    private List<Datum> data = null;

    /**
     * No args constructor for use in serialization
     *
     */
    public GetAllAlerts() {
    }

    /**
     *
     * @param responseCode
     * @param data
     */
    public GetAllAlerts(Integer responseCode, List<Datum> data) {
        super();
        this.responseCode = responseCode;
        this.data = data;
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public ArrayList<Alert> getAllNormalizedAlerts(){
        ArrayList<Alert> normal_alert_arr = new ArrayList<>();
        for (int i = 0; i < getData().size(); i++) {
            Datum ra = getData().get(i);
            normal_alert_arr.add(new Alert(ra.getConditionValue(), ra.getId(), ra.getDeviceId(), ra.getConditionName(), ra.getDeviceName(), ra.getFeedValue(), ra.getAlarmName(), ra.getDescription(), ra.getCreatedAt()));
        }
        return normal_alert_arr;
    }


    public Alert getNormalizedAlert(Datum ra){
        return new Alert(ra.getConditionValue(), ra.getId(), ra.getDeviceId(), ra.getConditionName(), ra.getDeviceName(), ra.getFeedValue(), ra.getAlarmName(), ra.getDescription(), ra.getCreatedAt());
    }

}

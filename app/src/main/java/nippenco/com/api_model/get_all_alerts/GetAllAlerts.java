
package nippenco.com.api_model.get_all_alerts;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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

}

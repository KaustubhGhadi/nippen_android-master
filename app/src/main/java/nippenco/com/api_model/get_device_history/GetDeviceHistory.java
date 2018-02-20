
package nippenco.com.api_model.get_device_history;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetDeviceHistory {

    @SerializedName("ResponseCode")
    @Expose
    private Integer responseCode;
    @SerializedName("Data")
    @Expose
    private Data data;

    /**
     * No args constructor for use in serialization
     * 
     */
    public GetDeviceHistory() {
    }

    /**
     * 
     * @param responseCode
     * @param data
     */
    public GetDeviceHistory(Integer responseCode, Data data) {
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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}

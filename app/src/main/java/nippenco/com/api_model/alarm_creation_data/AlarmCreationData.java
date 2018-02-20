
package nippenco.com.api_model.alarm_creation_data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AlarmCreationData {

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
    public AlarmCreationData() {
    }

    /**
     * 
     * @param responseCode
     * @param data
     */
    public AlarmCreationData(Integer responseCode, Data data) {
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

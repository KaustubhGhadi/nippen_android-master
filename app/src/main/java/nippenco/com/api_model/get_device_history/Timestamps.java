
package nippenco.com.api_model.get_device_history;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Timestamps {

    @SerializedName("to_date")
    @Expose
    private String toDate;
    @SerializedName("from_date")
    @Expose
    private String fromDate;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Timestamps() {
    }

    /**
     * 
     * @param fromDate
     * @param toDate
     */
    public Timestamps(String toDate, String fromDate) {
        super();
        this.toDate = toDate;
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

}

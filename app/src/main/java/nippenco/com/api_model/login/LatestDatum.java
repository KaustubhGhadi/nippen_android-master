
package nippenco.com.api_model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LatestDatum {

    @SerializedName("value")
    @Expose
    private Double value;
    @SerializedName("short_name")
    @Expose
    private String shortName;
    @SerializedName("name")
    @Expose
    private String name;

    /**
     * No args constructor for use in serialization
     * 
     */
    public LatestDatum() {
    }

    /**
     * 
     * @param name
     * @param value
     * @param shortName
     */
    public LatestDatum(Double value, String shortName, String name) {
        super();
        this.value = value;
        this.shortName = shortName;
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

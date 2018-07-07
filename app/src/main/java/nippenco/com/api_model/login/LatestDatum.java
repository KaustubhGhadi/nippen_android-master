
package nippenco.com.api_model.login;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LatestDatum {

    @SerializedName("short_name")
    @Expose
    private String shortName;
    @SerializedName("value")
    @Expose
    private Double value;
    @SerializedName("past_values")
    @Expose
    private List<Double> pastValues = null;
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
     * @param pastValues
     * @param name
     * @param value
     * @param shortName
     */
    public LatestDatum(String shortName, Double value, List<Double> pastValues, String name) {
        super();
        this.shortName = shortName;
        this.value = value;
        this.pastValues = pastValues;
        this.name = name;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public List<Double> getPastValues() {
        return pastValues;
    }

    public void setPastValues(List<Double> pastValues) {
        this.pastValues = pastValues;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}


package nippenco.com.api_model.login;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Timestamp {

    @SerializedName("value")
    @Expose
    private String value;
    @SerializedName("short_name")
    @Expose
    private String shortName;
    @SerializedName("past_values")
    @Expose
    private List<String> pastValues = null;
    @SerializedName("name")
    @Expose
    private String name;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Timestamp() {
    }

    /**
     * 
     * @param pastValues
     * @param name
     * @param value
     * @param shortName
     */
    public Timestamp(String value, String shortName, List<String> pastValues, String name) {
        super();
        this.value = value;
        this.shortName = shortName;
        this.pastValues = pastValues;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public List<String> getPastValues() {
        return pastValues;
    }

    public void setPastValues(List<String> pastValues) {
        this.pastValues = pastValues;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}


package nippenco.com.api_model.get_device_history;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeviceDatum {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("values")
    @Expose
    private List<String> values = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public DeviceDatum() {
    }

    /**
     * 
     * @param values
     * @param name
     */
    public DeviceDatum(String name, List<String> values) {
        super();
        this.name = name;
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

}

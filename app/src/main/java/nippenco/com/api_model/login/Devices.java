
package nippenco.com.api_model.login;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Devices {

    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("devices")
    @Expose
    private List<Device> devices = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Devices() {
    }

    /**
     * 
     * @param devices
     * @param type
     */
    public Devices(String type, List<Device> devices) {
        super();
        this.type = type;
        this.devices = devices;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

}

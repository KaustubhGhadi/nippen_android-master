
package nippenco.com.api_model.login;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Devices {

    @SerializedName("devices")
    @Expose
    private List<Device> devices = null;
    @SerializedName("type")
    @Expose
    private String type;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Devices() {
    }

    /**
     * 
     * @param type
     * @param devices
     */
    public Devices(List<Device> devices, String type) {
        super();
        this.devices = devices;
        this.type = type;
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void setDevices(List<Device> devices) {
        this.devices = devices;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}

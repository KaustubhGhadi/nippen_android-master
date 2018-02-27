
package nippenco.com.api_model.get_device_history;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Device {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("device_id")
    @Expose
    private String deviceId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Device() {
    }

    /**
     * 
     * @param id
     * @param name
     * @param deviceId
     */
    public Device(String name, Integer id, String deviceId) {
        super();
        this.name = name;
        this.id = id;
        this.deviceId = deviceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

}

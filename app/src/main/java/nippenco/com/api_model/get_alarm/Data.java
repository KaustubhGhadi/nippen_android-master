
package nippenco.com.api_model.get_alarm;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("condition")
    @Expose
    private Condition condition;
    @SerializedName("value")
    @Expose
    private Integer value;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("device")
    @Expose
    private Device device;
    @SerializedName("parameter")
    @Expose
    private Parameter parameter;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Data() {
    }

    /**
     * 
     * @param id
     * @param parameter
     * @param condition
     * @param name
     * @param device
     * @param value
     */
    public Data(Condition condition, Integer value, Integer id, String name, Device device, Parameter parameter) {
        super();
        this.condition = condition;
        this.value = value;
        this.id = id;
        this.name = name;
        this.device = device;
        this.parameter = parameter;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }

}


package nippenco.com.api_model.get_dashboard_preferences;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("visible")
    @Expose
    private Boolean visible;
    @SerializedName("order")
    @Expose
    private Integer order;
    @SerializedName("parameter")
    @Expose
    private Parameter parameter;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Datum() {
    }

    /**
     * 
     * @param parameter
     * @param order
     * @param visible
     */
    public Datum(Boolean visible, Integer order, Parameter parameter) {
        super();
        this.visible = visible;
        this.order = order;
        this.parameter = parameter;
    }

    public Boolean getVisible() {
        return visible;
    }

    public void setVisible(Boolean visible) {
        this.visible = visible;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Parameter getParameter() {
        return parameter;
    }

    public void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }

}

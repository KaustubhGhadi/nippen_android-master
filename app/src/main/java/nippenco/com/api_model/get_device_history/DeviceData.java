
package nippenco.com.api_model.get_device_history;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeviceData {

    @SerializedName("fr_hz")
    @Expose
    private List<Double> frHz = null;
    @SerializedName("pf3")
    @Expose
    private List<Double> pf3 = null;
    @SerializedName("pf2")
    @Expose
    private List<Double> pf2 = null;
    @SerializedName("v_vlnl1l2")
    @Expose
    private List<Double> vVlnl1l2 = null;
    @SerializedName("v_vlnl3l1")
    @Expose
    private List<Double> vVlnl3l1 = null;
    @SerializedName("ap_kwl1")
    @Expose
    private List<Double> apKwl1 = null;
    @SerializedName("rh")
    @Expose
    private List<Double> rh = null;
    @SerializedName("amps_i3")
    @Expose
    private List<Double> ampsI3 = null;
    @SerializedName("amps_i1")
    @Expose
    private List<Double> ampsI1 = null;
    @SerializedName("pf1")
    @Expose
    private List<Double> pf1 = null;
    @SerializedName("amps_i2")
    @Expose
    private List<Double> ampsI2 = null;
    @SerializedName("ap_kwl3")
    @Expose
    private List<Double> apKwl3 = null;
    @SerializedName("ap_kwl2")
    @Expose
    private List<Double> apKwl2 = null;
    @SerializedName("timestamps")
    @Expose
    private List<String> timestamps = null;

    /**
     * No args constructor for use in serialization
     * 
     */
    public DeviceData() {
    }

    /**
     * 
     * @param frHz
     * @param apKwl1
     * @param apKwl2
     * @param apKwl3
     * @param timestamps
     * @param ampsI1
     * @param pf2
     * @param pf3
     * @param rh
     * @param vVlnl3l1
     * @param ampsI2
     * @param ampsI3
     * @param pf1
     * @param vVlnl1l2
     */
    public DeviceData(List<Double> frHz, List<Double> pf3, List<Double> pf2, List<Double> vVlnl1l2, List<Double> vVlnl3l1, List<Double> apKwl1, List<Double> rh, List<Double> ampsI3, List<Double> ampsI1, List<Double> pf1, List<Double> ampsI2, List<Double> apKwl3, List<Double> apKwl2, List<String> timestamps) {
        super();
        this.frHz = frHz;
        this.pf3 = pf3;
        this.pf2 = pf2;
        this.vVlnl1l2 = vVlnl1l2;
        this.vVlnl3l1 = vVlnl3l1;
        this.apKwl1 = apKwl1;
        this.rh = rh;
        this.ampsI3 = ampsI3;
        this.ampsI1 = ampsI1;
        this.pf1 = pf1;
        this.ampsI2 = ampsI2;
        this.apKwl3 = apKwl3;
        this.apKwl2 = apKwl2;
        this.timestamps = timestamps;
    }

    public List<Double> getFrHz() {
        return frHz;
    }

    public void setFrHz(List<Double> frHz) {
        this.frHz = frHz;
    }

    public List<Double> getPf3() {
        return pf3;
    }

    public void setPf3(List<Double> pf3) {
        this.pf3 = pf3;
    }

    public List<Double> getPf2() {
        return pf2;
    }

    public void setPf2(List<Double> pf2) {
        this.pf2 = pf2;
    }

    public List<Double> getVVlnl1l2() {
        return vVlnl1l2;
    }

    public void setVVlnl1l2(List<Double> vVlnl1l2) {
        this.vVlnl1l2 = vVlnl1l2;
    }

    public List<Double> getVVlnl3l1() {
        return vVlnl3l1;
    }

    public void setVVlnl3l1(List<Double> vVlnl3l1) {
        this.vVlnl3l1 = vVlnl3l1;
    }

    public List<Double> getApKwl1() {
        return apKwl1;
    }

    public void setApKwl1(List<Double> apKwl1) {
        this.apKwl1 = apKwl1;
    }

    public List<Double> getRh() {
        return rh;
    }

    public void setRh(List<Double> rh) {
        this.rh = rh;
    }

    public List<Double> getAmpsI3() {
        return ampsI3;
    }

    public void setAmpsI3(List<Double> ampsI3) {
        this.ampsI3 = ampsI3;
    }

    public List<Double> getAmpsI1() {
        return ampsI1;
    }

    public void setAmpsI1(List<Double> ampsI1) {
        this.ampsI1 = ampsI1;
    }

    public List<Double> getPf1() {
        return pf1;
    }

    public void setPf1(List<Double> pf1) {
        this.pf1 = pf1;
    }

    public List<Double> getAmpsI2() {
        return ampsI2;
    }

    public void setAmpsI2(List<Double> ampsI2) {
        this.ampsI2 = ampsI2;
    }

    public List<Double> getApKwl3() {
        return apKwl3;
    }

    public void setApKwl3(List<Double> apKwl3) {
        this.apKwl3 = apKwl3;
    }

    public List<Double> getApKwl2() {
        return apKwl2;
    }

    public void setApKwl2(List<Double> apKwl2) {
        this.apKwl2 = apKwl2;
    }

    public List<String> getTimestamps() {
        return timestamps;
    }

    public void setTimestamps(List<String> timestamps) {
        this.timestamps = timestamps;
    }

}

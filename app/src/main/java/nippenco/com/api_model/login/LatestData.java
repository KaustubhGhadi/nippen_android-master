
package nippenco.com.api_model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LatestData {

    @SerializedName("%THD Amps-I1")
    @Expose
    private Double tHDAmpsI1;
    @SerializedName("%THD Amps-I3")
    @Expose
    private Double tHDAmpsI3;
    @SerializedName("Frequency Hz")
    @Expose
    private Double frequencyHz;
    @SerializedName("Communication Status")
    @Expose
    private Integer communicationStatus;
    @SerializedName("PT multiplier")
    @Expose
    private Integer pTMultiplier;
    @SerializedName("Amps I1")
    @Expose
    private Double ampsI1;
    @SerializedName("Volts VLN-L3L1")
    @Expose
    private Double voltsVLNL3L1;
    @SerializedName("Power Factor PF1")
    @Expose
    private Double powerFactorPF1;
    @SerializedName("%THD Amps-I2")
    @Expose
    private Double tHDAmpsI2;
    @SerializedName("Volts VLN-L1L2")
    @Expose
    private Double voltsVLNL1L2;
    @SerializedName("Volts VLN-L2L3")
    @Expose
    private Double voltsVLNL2L3;
    @SerializedName("Active Power kW-L3")
    @Expose
    private Double activePowerKWL3;
    @SerializedName("Active Power kW-L1")
    @Expose
    private Double activePowerKWL1;
    @SerializedName("CT multiplier")
    @Expose
    private Double cTMultiplier;
    @SerializedName("Amps I3")
    @Expose
    private Double ampsI3;
    @SerializedName("Amps I2")
    @Expose
    private Double ampsI2;
    @SerializedName("Run Hour")
    @Expose
    private Double runHour;
    @SerializedName("Power Factor PF3")
    @Expose
    private Double powerFactorPF3;
    @SerializedName("Active Power kW-L2")
    @Expose
    private Double activePowerKWL2;
    @SerializedName("Power Factor PF2")
    @Expose
    private Integer powerFactorPF2;

    /**
     * No args constructor for use in serialization
     * 
     */
    public LatestData() {
    }

    /**
     * 
     * @param powerFactorPF3
     * @param powerFactorPF2
     * @param voltsVLNL3L1
     * @param ampsI1
     * @param activePowerKWL1
     * @param activePowerKWL2
     * @param voltsVLNL1L2
     * @param activePowerKWL3
     * @param runHour
     * @param frequencyHz
     * @param ampsI2
     * @param voltsVLNL2L3
     * @param ampsI3
     * @param powerFactorPF1
     * @param pTMultiplier
     * @param communicationStatus
     * @param tHDAmpsI3
     * @param tHDAmpsI2
     * @param cTMultiplier
     * @param tHDAmpsI1
     */
    public LatestData(Double tHDAmpsI1, Double tHDAmpsI3, Double frequencyHz, Integer communicationStatus, Integer pTMultiplier, Double ampsI1, Double voltsVLNL3L1, Double powerFactorPF1, Double tHDAmpsI2, Double voltsVLNL1L2, Double voltsVLNL2L3, Double activePowerKWL3, Double activePowerKWL1, Double cTMultiplier, Double ampsI3, Double ampsI2, Double runHour, Double powerFactorPF3, Double activePowerKWL2, Integer powerFactorPF2) {
        super();
        this.tHDAmpsI1 = tHDAmpsI1;
        this.tHDAmpsI3 = tHDAmpsI3;
        this.frequencyHz = frequencyHz;
        this.communicationStatus = communicationStatus;
        this.pTMultiplier = pTMultiplier;
        this.ampsI1 = ampsI1;
        this.voltsVLNL3L1 = voltsVLNL3L1;
        this.powerFactorPF1 = powerFactorPF1;
        this.tHDAmpsI2 = tHDAmpsI2;
        this.voltsVLNL1L2 = voltsVLNL1L2;
        this.voltsVLNL2L3 = voltsVLNL2L3;
        this.activePowerKWL3 = activePowerKWL3;
        this.activePowerKWL1 = activePowerKWL1;
        this.cTMultiplier = cTMultiplier;
        this.ampsI3 = ampsI3;
        this.ampsI2 = ampsI2;
        this.runHour = runHour;
        this.powerFactorPF3 = powerFactorPF3;
        this.activePowerKWL2 = activePowerKWL2;
        this.powerFactorPF2 = powerFactorPF2;
    }

    public Double getTHDAmpsI1() {
        return tHDAmpsI1;
    }

    public void setTHDAmpsI1(Double tHDAmpsI1) {
        this.tHDAmpsI1 = tHDAmpsI1;
    }

    public Double getTHDAmpsI3() {
        return tHDAmpsI3;
    }

    public void setTHDAmpsI3(Double tHDAmpsI3) {
        this.tHDAmpsI3 = tHDAmpsI3;
    }

    public Double getFrequencyHz() {
        return frequencyHz;
    }

    public void setFrequencyHz(Double frequencyHz) {
        this.frequencyHz = frequencyHz;
    }

    public Integer getCommunicationStatus() {
        return communicationStatus;
    }

    public void setCommunicationStatus(Integer communicationStatus) {
        this.communicationStatus = communicationStatus;
    }

    public Integer getPTMultiplier() {
        return pTMultiplier;
    }

    public void setPTMultiplier(Integer pTMultiplier) {
        this.pTMultiplier = pTMultiplier;
    }

    public Double getAmpsI1() {
        return ampsI1;
    }

    public void setAmpsI1(Double ampsI1) {
        this.ampsI1 = ampsI1;
    }

    public Double getVoltsVLNL3L1() {
        return voltsVLNL3L1;
    }

    public void setVoltsVLNL3L1(Double voltsVLNL3L1) {
        this.voltsVLNL3L1 = voltsVLNL3L1;
    }

    public Double getPowerFactorPF1() {
        return powerFactorPF1;
    }

    public void setPowerFactorPF1(Double powerFactorPF1) {
        this.powerFactorPF1 = powerFactorPF1;
    }

    public Double getTHDAmpsI2() {
        return tHDAmpsI2;
    }

    public void setTHDAmpsI2(Double tHDAmpsI2) {
        this.tHDAmpsI2 = tHDAmpsI2;
    }

    public Double getVoltsVLNL1L2() {
        return voltsVLNL1L2;
    }

    public void setVoltsVLNL1L2(Double voltsVLNL1L2) {
        this.voltsVLNL1L2 = voltsVLNL1L2;
    }

    public Double getVoltsVLNL2L3() {
        return voltsVLNL2L3;
    }

    public void setVoltsVLNL2L3(Double voltsVLNL2L3) {
        this.voltsVLNL2L3 = voltsVLNL2L3;
    }

    public Double getActivePowerKWL3() {
        return activePowerKWL3;
    }

    public void setActivePowerKWL3(Double activePowerKWL3) {
        this.activePowerKWL3 = activePowerKWL3;
    }

    public Double getActivePowerKWL1() {
        return activePowerKWL1;
    }

    public void setActivePowerKWL1(Double activePowerKWL1) {
        this.activePowerKWL1 = activePowerKWL1;
    }

    public Double getCTMultiplier() {
        return cTMultiplier;
    }

    public void setCTMultiplier(Double cTMultiplier) {
        this.cTMultiplier = cTMultiplier;
    }

    public Double getAmpsI3() {
        return ampsI3;
    }

    public void setAmpsI3(Double ampsI3) {
        this.ampsI3 = ampsI3;
    }

    public Double getAmpsI2() {
        return ampsI2;
    }

    public void setAmpsI2(Double ampsI2) {
        this.ampsI2 = ampsI2;
    }

    public Double getRunHour() {
        return runHour;
    }

    public void setRunHour(Double runHour) {
        this.runHour = runHour;
    }

    public Double getPowerFactorPF3() {
        return powerFactorPF3;
    }

    public void setPowerFactorPF3(Double powerFactorPF3) {
        this.powerFactorPF3 = powerFactorPF3;
    }

    public Double getActivePowerKWL2() {
        return activePowerKWL2;
    }

    public void setActivePowerKWL2(Double activePowerKWL2) {
        this.activePowerKWL2 = activePowerKWL2;
    }

    public Integer getPowerFactorPF2() {
        return powerFactorPF2;
    }

    public void setPowerFactorPF2(Integer powerFactorPF2) {
        this.powerFactorPF2 = powerFactorPF2;
    }

}

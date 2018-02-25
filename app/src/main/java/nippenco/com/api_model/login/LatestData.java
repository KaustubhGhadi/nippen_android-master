
package nippenco.com.api_model.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LatestData {

    @SerializedName("Apparent Energy kVAh")
    @Expose
    private Double apparentEnergyKVAh;
    @SerializedName("Active Power kW-L1")
    @Expose
    private Double activePowerKWL1;
    @SerializedName("Active Power kW-L2")
    @Expose
    private Double activePowerKWL2;
    @SerializedName("%THD Amps-I3")
    @Expose
    private Double tHDAmpsI3;
    @SerializedName("Re active Power kVAr2")
    @Expose
    private Double reActivePowerKVAr2;
    @SerializedName("Run Hour")
    @Expose
    private Double runHour;
    @SerializedName("Amps I1")
    @Expose
    private Double ampsI1;
    @SerializedName("CT multiplier")
    @Expose
    private Double cTMultiplier;
    @SerializedName("Power Factor PF1")
    @Expose
    private Double powerFactorPF1;
    @SerializedName("Communication Status")
    @Expose
    private Double communicationStatus;
    @SerializedName("Active Power kW-L3")
    @Expose
    private Double activePowerKWL3;
    @SerializedName("Re active Power kVAr3")
    @Expose
    private Double reActivePowerKVAr3;
    @SerializedName("Re active Power kVAr1")
    @Expose
    private Double reActivePowerKVAr1;
    @SerializedName("Volts VLN-L1")
    @Expose
    private Double voltsVLNL1;
    @SerializedName("%THD VLN-L2")
    @Expose
    private Double tHDVLNL2;
    @SerializedName("%THD VLN-L3")
    @Expose
    private Double tHDVLNL3;
    @SerializedName("Volts VLN-L3")
    @Expose
    private Double voltsVLNL3;
    @SerializedName("Frequency Hz")
    @Expose
    private Double frequencyHz;
    @SerializedName("Volts VLN-L1L2")
    @Expose
    private Double voltsVLNL1L2;
    @SerializedName("Power Factor PF3")
    @Expose
    private Double powerFactorPF3;
    @SerializedName("Volts VLN-L3L1")
    @Expose
    private Double voltsVLNL3L1;
    @SerializedName("%THD Amps-I2")
    @Expose
    private Double tHDAmpsI2;
    @SerializedName("Power Factor PF2")
    @Expose
    private Double powerFactorPF2;
    @SerializedName("Amps I3")
    @Expose
    private Double ampsI3;
    @SerializedName("Active Energy kWh")
    @Expose
    private Double activeEnergyKWh;
    @SerializedName("Volts VLN-L2")
    @Expose
    private Double voltsVLNL2;
    @SerializedName("Apparent Power kVA3")
    @Expose
    private Double apparentPowerKVA3;
    @SerializedName("%THD VLN-L1")
    @Expose
    private Double tHDVLNL1;
    @SerializedName("%THD Amps-I1")
    @Expose
    private Double tHDAmpsI1;
    @SerializedName("Apparent Power kVA1")
    @Expose
    private Double apparentPowerKVA1;
    @SerializedName("PT multiplier")
    @Expose
    private Double pTMultiplier;
    @SerializedName("Apparent Power kVA2")
    @Expose
    private Double apparentPowerKVA2;
    @SerializedName("Amps I2")
    @Expose
    private Double ampsI2;
    @SerializedName("Volts VLN-L2L3")
    @Expose
    private Double voltsVLNL2L3;
    @SerializedName("kWh multiplier")
    @Expose
    private Double kWhMultiplier;

    /**
     * No args constructor for use in serialization
     *
     */
    public LatestData() {
    }

    /**
     *
     * @param reActivePowerKVAr3
     * @param activePowerKWL1
     * @param reActivePowerKVAr2
     * @param activePowerKWL2
     * @param activePowerKWL3
     * @param reActivePowerKVAr1
     * @param voltsVLNL3
     * @param voltsVLNL2
     * @param tHDAmpsI3
     * @param voltsVLNL1
     * @param apparentPowerKVA1
     * @param tHDAmpsI2
     * @param apparentPowerKVA2
     * @param tHDAmpsI1
     * @param apparentPowerKVA3
     * @param apparentEnergyKVAh
     * @param powerFactorPF3
     * @param voltsVLNL3L1
     * @param powerFactorPF2
     * @param ampsI1
     * @param kWhMultiplier
     * @param activeEnergyKWh
     * @param voltsVLNL1L2
     * @param runHour
     * @param frequencyHz
     * @param voltsVLNL2L3
     * @param ampsI2
     * @param ampsI3
     * @param powerFactorPF1
     * @param pTMultiplier
     * @param tHDVLNL1
     * @param communicationStatus
     * @param tHDVLNL3
     * @param tHDVLNL2
     * @param cTMultiplier
     */
    public LatestData(Double apparentEnergyKVAh, Double activePowerKWL1, Double activePowerKWL2, Double tHDAmpsI3, Double reActivePowerKVAr2, Double runHour, Double ampsI1, Double cTMultiplier, Double powerFactorPF1, Double communicationStatus, Double activePowerKWL3, Double reActivePowerKVAr3, Double reActivePowerKVAr1, Double voltsVLNL1, Double tHDVLNL2, Double tHDVLNL3, Double voltsVLNL3, Double frequencyHz, Double voltsVLNL1L2, Double powerFactorPF3, Double voltsVLNL3L1, Double tHDAmpsI2, Double powerFactorPF2, Double ampsI3, Double activeEnergyKWh, Double voltsVLNL2, Double apparentPowerKVA3, Double tHDVLNL1, Double tHDAmpsI1, Double apparentPowerKVA1, Double pTMultiplier, Double apparentPowerKVA2, Double ampsI2, Double voltsVLNL2L3, Double kWhMultiplier) {
        super();
        this.apparentEnergyKVAh = apparentEnergyKVAh;
        this.activePowerKWL1 = activePowerKWL1;
        this.activePowerKWL2 = activePowerKWL2;
        this.tHDAmpsI3 = tHDAmpsI3;
        this.reActivePowerKVAr2 = reActivePowerKVAr2;
        this.runHour = runHour;
        this.ampsI1 = ampsI1;
        this.cTMultiplier = cTMultiplier;
        this.powerFactorPF1 = powerFactorPF1;
        this.communicationStatus = communicationStatus;
        this.activePowerKWL3 = activePowerKWL3;
        this.reActivePowerKVAr3 = reActivePowerKVAr3;
        this.reActivePowerKVAr1 = reActivePowerKVAr1;
        this.voltsVLNL1 = voltsVLNL1;
        this.tHDVLNL2 = tHDVLNL2;
        this.tHDVLNL3 = tHDVLNL3;
        this.voltsVLNL3 = voltsVLNL3;
        this.frequencyHz = frequencyHz;
        this.voltsVLNL1L2 = voltsVLNL1L2;
        this.powerFactorPF3 = powerFactorPF3;
        this.voltsVLNL3L1 = voltsVLNL3L1;
        this.tHDAmpsI2 = tHDAmpsI2;
        this.powerFactorPF2 = powerFactorPF2;
        this.ampsI3 = ampsI3;
        this.activeEnergyKWh = activeEnergyKWh;
        this.voltsVLNL2 = voltsVLNL2;
        this.apparentPowerKVA3 = apparentPowerKVA3;
        this.tHDVLNL1 = tHDVLNL1;
        this.tHDAmpsI1 = tHDAmpsI1;
        this.apparentPowerKVA1 = apparentPowerKVA1;
        this.pTMultiplier = pTMultiplier;
        this.apparentPowerKVA2 = apparentPowerKVA2;
        this.ampsI2 = ampsI2;
        this.voltsVLNL2L3 = voltsVLNL2L3;
        this.kWhMultiplier = kWhMultiplier;
    }

    public Double getApparentEnergyKVAh() {
        return apparentEnergyKVAh;
    }

    public void setApparentEnergyKVAh(Double apparentEnergyKVAh) {
        this.apparentEnergyKVAh = apparentEnergyKVAh;
    }

    public Double getActivePowerKWL1() {
        return activePowerKWL1;
    }

    public void setActivePowerKWL1(Double activePowerKWL1) {
        this.activePowerKWL1 = activePowerKWL1;
    }

    public Double getActivePowerKWL2() {
        return activePowerKWL2;
    }

    public void setActivePowerKWL2(Double activePowerKWL2) {
        this.activePowerKWL2 = activePowerKWL2;
    }

    public Double getTHDAmpsI3() {
        return tHDAmpsI3;
    }

    public void setTHDAmpsI3(Double tHDAmpsI3) {
        this.tHDAmpsI3 = tHDAmpsI3;
    }

    public Double getReActivePowerKVAr2() {
        return reActivePowerKVAr2;
    }

    public void setReActivePowerKVAr2(Double reActivePowerKVAr2) {
        this.reActivePowerKVAr2 = reActivePowerKVAr2;
    }

    public Double getRunHour() {
        return runHour;
    }

    public void setRunHour(Double runHour) {
        this.runHour = runHour;
    }

    public Double getAmpsI1() {
        return ampsI1;
    }

    public void setAmpsI1(Double ampsI1) {
        this.ampsI1 = ampsI1;
    }

    public Double getCTMultiplier() {
        return cTMultiplier;
    }

    public void setCTMultiplier(Double cTMultiplier) {
        this.cTMultiplier = cTMultiplier;
    }

    public Double getPowerFactorPF1() {
        return powerFactorPF1;
    }

    public void setPowerFactorPF1(Double powerFactorPF1) {
        this.powerFactorPF1 = powerFactorPF1;
    }

    public Double getCommunicationStatus() {
        return communicationStatus;
    }

    public void setCommunicationStatus(Double communicationStatus) {
        this.communicationStatus = communicationStatus;
    }

    public Double getActivePowerKWL3() {
        return activePowerKWL3;
    }

    public void setActivePowerKWL3(Double activePowerKWL3) {
        this.activePowerKWL3 = activePowerKWL3;
    }

    public Double getReActivePowerKVAr3() {
        return reActivePowerKVAr3;
    }

    public void setReActivePowerKVAr3(Double reActivePowerKVAr3) {
        this.reActivePowerKVAr3 = reActivePowerKVAr3;
    }

    public Double getReActivePowerKVAr1() {
        return reActivePowerKVAr1;
    }

    public void setReActivePowerKVAr1(Double reActivePowerKVAr1) {
        this.reActivePowerKVAr1 = reActivePowerKVAr1;
    }

    public Double getVoltsVLNL1() {
        return voltsVLNL1;
    }

    public void setVoltsVLNL1(Double voltsVLNL1) {
        this.voltsVLNL1 = voltsVLNL1;
    }

    public Double getTHDVLNL2() {
        return tHDVLNL2;
    }

    public void setTHDVLNL2(Double tHDVLNL2) {
        this.tHDVLNL2 = tHDVLNL2;
    }

    public Double getTHDVLNL3() {
        return tHDVLNL3;
    }

    public void setTHDVLNL3(Double tHDVLNL3) {
        this.tHDVLNL3 = tHDVLNL3;
    }

    public Double getVoltsVLNL3() {
        return voltsVLNL3;
    }

    public void setVoltsVLNL3(Double voltsVLNL3) {
        this.voltsVLNL3 = voltsVLNL3;
    }

    public Double getFrequencyHz() {
        return frequencyHz;
    }

    public void setFrequencyHz(Double frequencyHz) {
        this.frequencyHz = frequencyHz;
    }

    public Double getVoltsVLNL1L2() {
        return voltsVLNL1L2;
    }

    public void setVoltsVLNL1L2(Double voltsVLNL1L2) {
        this.voltsVLNL1L2 = voltsVLNL1L2;
    }

    public Double getPowerFactorPF3() {
        return powerFactorPF3;
    }

    public void setPowerFactorPF3(Double powerFactorPF3) {
        this.powerFactorPF3 = powerFactorPF3;
    }

    public Double getVoltsVLNL3L1() {
        return voltsVLNL3L1;
    }

    public void setVoltsVLNL3L1(Double voltsVLNL3L1) {
        this.voltsVLNL3L1 = voltsVLNL3L1;
    }

    public Double getTHDAmpsI2() {
        return tHDAmpsI2;
    }

    public void setTHDAmpsI2(Double tHDAmpsI2) {
        this.tHDAmpsI2 = tHDAmpsI2;
    }

    public Double getPowerFactorPF2() {
        return powerFactorPF2;
    }

    public void setPowerFactorPF2(Double powerFactorPF2) {
        this.powerFactorPF2 = powerFactorPF2;
    }

    public Double getAmpsI3() {
        return ampsI3;
    }

    public void setAmpsI3(Double ampsI3) {
        this.ampsI3 = ampsI3;
    }

    public Double getActiveEnergyKWh() {
        return activeEnergyKWh;
    }

    public void setActiveEnergyKWh(Double activeEnergyKWh) {
        this.activeEnergyKWh = activeEnergyKWh;
    }

    public Double getVoltsVLNL2() {
        return voltsVLNL2;
    }

    public void setVoltsVLNL2(Double voltsVLNL2) {
        this.voltsVLNL2 = voltsVLNL2;
    }

    public Double getApparentPowerKVA3() {
        return apparentPowerKVA3;
    }

    public void setApparentPowerKVA3(Double apparentPowerKVA3) {
        this.apparentPowerKVA3 = apparentPowerKVA3;
    }

    public Double getTHDVLNL1() {
        return tHDVLNL1;
    }

    public void setTHDVLNL1(Double tHDVLNL1) {
        this.tHDVLNL1 = tHDVLNL1;
    }

    public Double getTHDAmpsI1() {
        return tHDAmpsI1;
    }

    public void setTHDAmpsI1(Double tHDAmpsI1) {
        this.tHDAmpsI1 = tHDAmpsI1;
    }

    public Double getApparentPowerKVA1() {
        return apparentPowerKVA1;
    }

    public void setApparentPowerKVA1(Double apparentPowerKVA1) {
        this.apparentPowerKVA1 = apparentPowerKVA1;
    }

    public Double getPTMultiplier() {
        return pTMultiplier;
    }

    public void setPTMultiplier(Double pTMultiplier) {
        this.pTMultiplier = pTMultiplier;
    }

    public Double getApparentPowerKVA2() {
        return apparentPowerKVA2;
    }

    public void setApparentPowerKVA2(Double apparentPowerKVA2) {
        this.apparentPowerKVA2 = apparentPowerKVA2;
    }

    public Double getAmpsI2() {
        return ampsI2;
    }

    public void setAmpsI2(Double ampsI2) {
        this.ampsI2 = ampsI2;
    }

    public Double getVoltsVLNL2L3() {
        return voltsVLNL2L3;
    }

    public void setVoltsVLNL2L3(Double voltsVLNL2L3) {
        this.voltsVLNL2L3 = voltsVLNL2L3;
    }

    public Double getKWhMultiplier() {
        return kWhMultiplier;
    }

    public void setKWhMultiplier(Double kWhMultiplier) {
        this.kWhMultiplier = kWhMultiplier;
    }

}

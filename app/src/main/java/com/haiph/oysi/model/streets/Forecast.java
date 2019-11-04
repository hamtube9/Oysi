
package com.haiph.oysi.model.streets;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Forecast {

    @SerializedName("ts")
    @Expose
    private String ts;
    @SerializedName("tp")
    @Expose
    private Integer tp;
    @SerializedName("tp_min")
    @Expose
    private Integer tpMin;
    @SerializedName("pr")
    @Expose
    private Integer pr;
    @SerializedName("hu")
    @Expose
    private Integer hu;
    @SerializedName("ws")
    @Expose
    private Integer ws;
    @SerializedName("wd")
    @Expose
    private Integer wd;
    @SerializedName("ic")
    @Expose
    private String ic;
    @SerializedName("conc")
    @Expose
    private Double conc;
    @SerializedName("aqius")
    @Expose
    private Integer aqius;
    @SerializedName("aqicn")
    @Expose
    private Integer aqicn;

    public String getTs() {
        return ts;
    }

    public void setTs(String ts) {
        this.ts = ts;
    }

    public Integer getTp() {
        return tp;
    }

    public void setTp(Integer tp) {
        this.tp = tp;
    }

    public Integer getTpMin() {
        return tpMin;
    }

    public void setTpMin(Integer tpMin) {
        this.tpMin = tpMin;
    }

    public Integer getPr() {
        return pr;
    }

    public void setPr(Integer pr) {
        this.pr = pr;
    }

    public Integer getHu() {
        return hu;
    }

    public void setHu(Integer hu) {
        this.hu = hu;
    }

    public Integer getWs() {
        return ws;
    }

    public void setWs(Integer ws) {
        this.ws = ws;
    }

    public Integer getWd() {
        return wd;
    }

    public void setWd(Integer wd) {
        this.wd = wd;
    }

    public String getIc() {
        return ic;
    }

    public void setIc(String ic) {
        this.ic = ic;
    }

    public Double getConc() {
        return conc;
    }

    public void setConc(Double conc) {
        this.conc = conc;
    }

    public Integer getAqius() {
        return aqius;
    }

    public void setAqius(Integer aqius) {
        this.aqius = aqius;
    }

    public Integer getAqicn() {
        return aqicn;
    }

    public void setAqicn(Integer aqicn) {
        this.aqicn = aqicn;
    }

}

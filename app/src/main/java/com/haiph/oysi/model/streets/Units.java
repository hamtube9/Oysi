
package com.haiph.oysi.model.streets;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Units {

    @SerializedName("s2")
    @Expose
    private String s2;
    @SerializedName("n2")
    @Expose
    private String n2;
    @SerializedName("o3")
    @Expose
    private String o3;
    @SerializedName("co")
    @Expose
    private String co;
    @SerializedName("p2")
    @Expose
    private String p2;
    @SerializedName("p1")
    @Expose
    private String p1;

    public String getS2() {
        return s2;
    }

    public void setS2(String s2) {
        this.s2 = s2;
    }

    public String getN2() {
        return n2;
    }

    public void setN2(String n2) {
        this.n2 = n2;
    }

    public String getO3() {
        return o3;
    }

    public void setO3(String o3) {
        this.o3 = o3;
    }

    public String getCo() {
        return co;
    }

    public void setCo(String co) {
        this.co = co;
    }

    public String getP2() {
        return p2;
    }

    public void setP2(String p2) {
        this.p2 = p2;
    }

    public String getP1() {
        return p1;
    }

    public void setP1(String p1) {
        this.p1 = p1;
    }

}

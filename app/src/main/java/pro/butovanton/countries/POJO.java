package pro.butovanton.countries;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class POJO {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("capital")
    @Expose
    private String capital;
    @SerializedName("flag")
    @Expose
    private String flag;
    @SerializedName("currencies")
    @Expose
    public List<Currencie> currencies = null;
    public class Currencie {
        @SerializedName("name")
        @Expose
        public String name;
    }

    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getcapital() {
        return capital;
    }

    public void setcapital(String capital) { this.capital = capital; }

    public String getflag() {
        return flag;
    }

    public void setflag(String flag) {
        this.flag = flag;
    }

    public List<String> getcurriencies () {
        List<String> liststring = new ArrayList<>();
        for (Currencie currencie :  currencies)
            liststring.add(currencie.name);
    return liststring;
    }

}

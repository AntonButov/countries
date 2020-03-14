package pro.butovanton.countries;

import java.util.List;

public class Countrie {
    String name;
    String capital;
    List<String> currencies;
    String flag;

    public Countrie(String name, String capital, List<String> curriencies, String flag) {
        this.name = name;
        this.capital = capital;
        this.currencies = curriencies;
        this.flag = flag;
    }
}

package pro.butovanton.countries;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "ctable")
public class Countrie {
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    String name;
    String capital;
    String currencie;
    String flag;
    String flagpatch;


    public Countrie(@NonNull String name, String capital, String currencie, String flag, String flagpatch) {
        this.name = name;
        this.capital = capital;
        this.currencie = currencie;
        this.flag = flag;
        this.flagpatch = flagpatch;
    }
}

package pro.butovanton.countries;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface cDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Countrie countrie);

    @Query("DELETE FROM ctable")
    void deleteAll();

    @Query("SELECT * from ctable ORDER BY name ASC")
    List<Countrie> getAll();
}

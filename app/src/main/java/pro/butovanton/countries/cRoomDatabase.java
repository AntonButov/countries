package pro.butovanton.countries;


import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;


@Database(entities = {Countrie.class}, version = 1)
public abstract class cRoomDatabase extends RoomDatabase {
    public abstract cDao cdao();

    private static cRoomDatabase INSTANCE;

    public static cRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (cRoomDatabase.class) {
                if (INSTANCE == null) {
                    // Create database here
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            cRoomDatabase.class, "database")
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        //    new PopulateDbAsync(INSTANCE).execute();
        }
    };

}

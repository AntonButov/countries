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

 //   private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

 //       private final WordDao mDao;

 //      PopulateDbAsync(WordRoomDatabase db) {
   //         mDao = db.wordDao();
    //    }

  //      @Override
   //     protected Void doInBackground(final Void... params) {
    //        mDao.deleteAll();
   //         Word word = new Word("Hello");
   //         mDao.insert(word);
   //         word = new Word("World");
  //          mDao.insert(word);
   //         return null;
  //      }
  //  }
}

package in.awesomesearch.app.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import in.awesomesearch.app.data.dao.AwesomeItemDao;
import in.awesomesearch.app.data.dao.BookmarkGroupDao;
import in.awesomesearch.app.data.models.AwesomeItem;
import in.awesomesearch.app.data.models.BookmarkGroup;

@Database(entities = {BookmarkGroup.class, AwesomeItem.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;
    public abstract BookmarkGroupDao bookmarkGroupDao();
    public abstract AwesomeItemDao awesomeItemDao();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (AppDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class, "Awesome.db"
                    )
                            // NOTICE: use this only during development !
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }

}
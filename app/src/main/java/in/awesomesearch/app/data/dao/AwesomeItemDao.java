package in.awesomesearch.app.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import in.awesomesearch.app.data.models.AwesomeItem;

@Dao
public interface AwesomeItemDao {

    @Insert
    void insertItem(AwesomeItem item);

    @Query("SELECT * FROM AwesomeItem")
    LiveData<List<AwesomeItem>> getAllItems();

    @Query("SELECT * FROM AwesomeItem")
    List<AwesomeItem> getAllItemsSync();

    @Delete
    void deleteItem(AwesomeItem item);

}

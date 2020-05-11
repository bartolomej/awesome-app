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
    LiveData<List<AwesomeItem>> findAllItems();

    @Query("SELECT * FROM AwesomeItem WHERE uid = :uid")
    LiveData<AwesomeItem> findItem(String uid);

    @Query("SELECT * FROM AwesomeItem")
    List<AwesomeItem> findAllItemsSync();

    @Delete
    void deleteItem(AwesomeItem item);

}

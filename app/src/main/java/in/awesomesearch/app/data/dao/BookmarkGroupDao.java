package in.awesomesearch.app.data.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

import in.awesomesearch.app.data.models.BookmarkGroup;
import in.awesomesearch.app.data.models.GroupWithItems;

@Dao
public interface BookmarkGroupDao {

    @Insert
    void insertBookmarkGroup(BookmarkGroup group);

    @Transaction
    @Query("SELECT * FROM BookmarkGroup")
    LiveData<List<GroupWithItems>> getAllGroupsWithItems();

    @Transaction
    @Query("SELECT * FROM BookmarkGroup")
    List<GroupWithItems> getAllGroupsWithItemsSync();

    @Query("SELECT * FROM BookmarkGroup")
    LiveData<List<BookmarkGroup>> getAllBookmarkGroups();

    @Query("SELECT * FROM BookmarkGroup")
    List<BookmarkGroup> getAllBookmarkGroupsSync();

    @Delete
    void deleteGroup(BookmarkGroup group);
}

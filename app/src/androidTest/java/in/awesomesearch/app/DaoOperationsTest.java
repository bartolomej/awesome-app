package in.awesomesearch.app;

import android.content.Context;
import android.util.Log;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.security.acl.Group;
import java.util.List;

import in.awesomesearch.app.data.AppDatabase;
import in.awesomesearch.app.data.dao.AwesomeItemDao;
import in.awesomesearch.app.data.dao.BookmarkGroupDao;
import in.awesomesearch.app.data.models.AwesomeItem;
import in.awesomesearch.app.data.models.BookmarkGroup;
import in.awesomesearch.app.data.models.GroupWithItems;
import in.awesomesearch.app.data.models.RepoInfo;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class DaoOperationsTest {

    private static final String TAG = "DaoOperationsTest";
    private AppDatabase appDatabase;
    private BookmarkGroupDao bookmarkGroupDao;
    private AwesomeItemDao awesomeItemDao;

    @Before
    public void initDb() {
        Context context = ApplicationProvider.getApplicationContext();
        appDatabase = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        bookmarkGroupDao = appDatabase.bookmarkGroupDao();
        awesomeItemDao = appDatabase.awesomeItemDao();
    }

    @After
    public void closeDb() throws IOException {
        appDatabase.close();
    }

    @Test
    public void writeBookmarkGroupTest() {
        BookmarkGroup bookmarkGroup = new BookmarkGroup("FirstGroup");
        bookmarkGroupDao.insertBookmarkGroup(bookmarkGroup);
        List<BookmarkGroup> bookmarks = bookmarkGroupDao.getAllBookmarkGroups();

        assertEquals(bookmarks.size(), 1);
        assertEquals(bookmarks.get(0).name, "FirstGroup");
    }

    @Test
    public void writeAwesomeItem() {
        AwesomeItem item = new AwesomeItem("1");
        item.extras = new RepoInfo();
        item.groupId = "Group1";
        item.extras.githubForks = 1;
        awesomeItemDao.insertItem(item);

        List<AwesomeItem> items = awesomeItemDao.getAllItemsSync();
        assertEquals(items.size(), 1);
        assertEquals(items.get(0).uid, "1");
        assertEquals(items.get(0).extras.githubForks, 1);
    }

    @Test
    public void writeItemsAndBookmarks() {
        BookmarkGroup group = new BookmarkGroup("Group1");
        AwesomeItem item = new AwesomeItem("1");
        item.extras = new RepoInfo();
        item.groupId = "Group1";
        item.description = "Test description";
        item.extras.githubForks = 1;

        bookmarkGroupDao.insertBookmarkGroup(group);
        awesomeItemDao.insertItem(item);

        List<GroupWithItems> groupWithItems = bookmarkGroupDao.getAllGroupsWithItemsSync();
        assertEquals(groupWithItems.size(), 1);
        assertEquals(groupWithItems.get(0).items.size(), 1);
        assertEquals(groupWithItems.get(0).bookmarkGroup.name, "Group1");
        assertEquals(groupWithItems.get(0).items.get(0).extras.githubForks, 1);
    }
}
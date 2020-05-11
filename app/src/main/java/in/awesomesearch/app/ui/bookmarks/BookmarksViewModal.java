package in.awesomesearch.app.ui.bookmarks;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import in.awesomesearch.app.AwesomeApplication;
import in.awesomesearch.app.data.Repository;
import in.awesomesearch.app.data.models.BookmarkGroup;
import in.awesomesearch.app.data.models.GroupWithItems;

public class BookmarksViewModal extends ViewModel {

    public BookmarksViewModal() { }

    LiveData<List<GroupWithItems>> getGroupsWithItems() {
        return Repository.getInstance().bookmarksWithGroups();
    }

    BookmarkGroup getBookmarkGroupValue (String uid) {
        return Repository.getInstance().getBookmarkGroup(uid).getValue();
    }

    LiveData<BookmarkGroup> getBookmarkGroup (String uid) {
        return Repository.getInstance().getBookmarkGroup(uid);
    }

    void addBookmarkGroup (String name) {
        BookmarkGroup bookmarkGroup = new BookmarkGroup(name);
        Repository.getInstance().addBookmarkGroup(bookmarkGroup);
    }

    void removeBookmarkGroup (BookmarkGroup group) {
        Repository.getInstance().removeBookmarkGroup(group);
    }

}
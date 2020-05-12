package in.awesomesearch.app.ui.bookmarks;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import in.awesomesearch.app.AwesomeApplication;
import in.awesomesearch.app.R;
import in.awesomesearch.app.data.Repository;
import in.awesomesearch.app.data.models.BookmarkGroup;
import in.awesomesearch.app.data.models.GroupWithItems;
import in.awesomesearch.app.data.models.UserMessage;

public class BookmarksViewModal extends ViewModel {

    private MutableLiveData<UserMessage> message;
    private MutableLiveData<List<GroupWithItems>> groupsWithItems;
    private MutableLiveData<GroupWithItems> groupWithItems;

    public BookmarksViewModal() {
        groupsWithItems = new MutableLiveData<>();
        groupWithItems = new MutableLiveData<>();
        message = new MutableLiveData<>();
    }

    LiveData<UserMessage> getMessage() {
        return message;
    }

    LiveData<List<GroupWithItems>> getGroupsWithItems() {
        Repository.getInstance().bookmarksWithGroups().observeForever(new Observer<List<GroupWithItems>>() {
            @Override
            public void onChanged(List<GroupWithItems> result) {
                groupsWithItems.setValue(result);
                if (result.size() == 0) {
                    message.setValue(new UserMessage(
                            R.string.no_bookmarks_title,
                            R.string.no_bookmarks_desc,
                            R.drawable.ic_space_book
                    ));
                } else {
                    message.setValue(null);
                }
            }
        });
        return groupsWithItems;
    }

    BookmarkGroup getBookmarkGroupValue (String uid) {
        return Repository.getInstance().getBookmarkGroup(uid).getValue();
    }

    LiveData<GroupWithItems> getGroupWithItems (String uid) {
        Repository.getInstance().getGroupWithItems(uid).observeForever(new Observer<GroupWithItems>() {
            @Override
            public void onChanged(GroupWithItems result) {
                if (result != null && !result.hasItems()) {
                    message.postValue(new UserMessage(
                            R.string.no_items_in_group_title,
                            R.string.no_items_in_group_desc,
                            R.drawable.ic_ufo
                    ));
                } else {
                    message.postValue(null);
                }
                groupWithItems.postValue(result);
            }
        });
        return groupWithItems;
    }

    void addBookmarkGroup (String name) {
        BookmarkGroup bookmarkGroup = new BookmarkGroup(name);
        Repository.getInstance().addBookmarkGroup(bookmarkGroup);
    }

    void removeBookmarkGroup (BookmarkGroup group) {
        Repository.getInstance().removeBookmarkGroup(group);
    }

}
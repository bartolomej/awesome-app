package in.awesomesearch.app.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.util.List;

import in.awesomesearch.app.AwesomeError;
import in.awesomesearch.app.data.Repository;
import in.awesomesearch.app.data.Resource;
import in.awesomesearch.app.data.models.AwesomeItem;
import in.awesomesearch.app.data.models.BookmarkGroup;
import in.awesomesearch.app.data.models.GroupWithItems;

public class DetailsViewModel extends ViewModel {

    private MutableLiveData<AwesomeItem> itemMutableLiveData;
    private MutableLiveData<AwesomeError> errorMutableLiveData;

    public DetailsViewModel () {
        itemMutableLiveData = new MutableLiveData<>();
        errorMutableLiveData = new MutableLiveData<>();
    }

    LiveData<AwesomeItem> getAwesomeItem (String uid) {
        Repository.getInstance().getAwesomeItem(uid).observeForever(new Observer<Resource<AwesomeItem>>() {
            @Override
            public void onChanged(Resource<AwesomeItem> awesomeItemResource) {
                if (awesomeItemResource.error == null) {
                    itemMutableLiveData.postValue(awesomeItemResource.data);
                } else {
                    errorMutableLiveData.postValue(awesomeItemResource.error);
                }
            }
        });
        return itemMutableLiveData;
    }

    LiveData<List<GroupWithItems>> getGroupsWithItems() {
        return Repository.getInstance().bookmarksWithGroups();
    }

    void addToBookmarks (String uid, AwesomeItem item) {
        item.setBookmarkGroup(uid);
        Repository.getInstance().addItemToBookmarkGroup(item);
    }

    LiveData<AwesomeError> getAwesomeError () {
        return errorMutableLiveData;
    }


}

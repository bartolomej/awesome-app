package in.awesomesearch.app.ui.search;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import in.awesomesearch.app.data.models.AwesomeItem;
import in.awesomesearch.app.data.Repository;
import in.awesomesearch.app.data.Resource;
import in.awesomesearch.app.data.models.SearchResponse;

public class SearchViewModel extends ViewModel {

    private static final String TAG = "SearchViewModel";
    private MutableLiveData<String> query;
    private MutableLiveData<String> message;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<List<AwesomeItem>> searchItems;
    private SearchResponse response;
    private int pageIndex = 0;
    private int itemsPerPage = 20;

    public SearchViewModel () {
        query = new MutableLiveData<>();
        message = new MutableLiveData<>();
        isLoading = new MutableLiveData<>();
        isLoading.setValue(false);
        searchItems = new MutableLiveData<>();
        searchItems.setValue(new ArrayList<AwesomeItem>());
    }

    MutableLiveData<String> getMessage() {
        return message;
    }

    MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    String getQueryValue() {
        return query.getValue();
    }

    LiveData<List<AwesomeItem>> getObservableSearchItems() {
        return searchItems;
    }

    List<AwesomeItem> getSearchItems () {
        if (searchItems.getValue() != null) {
            return searchItems.getValue();
        } else {
            return new ArrayList<>();
        }
    }

    void searchQuery (String query) {
        this.query.postValue(query);
        this.isLoading.setValue(true);
        LiveData<Resource<SearchResponse>> source =
                Repository.getInstance().searchItems(query, pageIndex, itemsPerPage);
        source.observeForever(new Observer<Resource<SearchResponse>>() {
            @Override
            public void onChanged(Resource<SearchResponse> resource) {
                if (resource.data != null && resource.data.result.size() == 0) {
                    message.postValue("There seems to be nothing here :(");
                }
                if (resource.error != null) {
                    message.setValue(resource.error.getMessage());
                } else {
                    Log.d(TAG, "Items count: " + resource.data.result.size());
                    response = resource.data;
                    searchItems.postValue(resource.data.result);
                    message.setValue(null);
                }
                isLoading.postValue(false);
            }
        });
    }
}

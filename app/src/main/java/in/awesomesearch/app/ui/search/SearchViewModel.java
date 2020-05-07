package in.awesomesearch.app.ui.search;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import in.awesomesearch.app.data.AwesomeItem;
import in.awesomesearch.app.data.Repository;

public class SearchViewModel extends ViewModel {

    private Repository repository;
    private MutableLiveData<Boolean> isLoading;
    private MutableLiveData<List<AwesomeItem>> searchItems;

    public void init () {
        if (repository != null) {
            return;
        }
        repository = Repository.getInstance();
        searchItems = new MutableLiveData<>();
        searchItems.setValue(new ArrayList<>());
        isLoading = new MutableLiveData<>();
        isLoading.setValue(false);
    }

    public LiveData<Boolean> getIsLoading () {
        return isLoading;
    }

    public LiveData<List<AwesomeItem>> getSearchItems () {
        return searchItems;
    }

    public void searchAwesomeItems (String query) {
        isLoading.setValue(true);
        searchItems.postValue(repository.getAwesomeItems(query));
        isLoading.postValue(false);
    }

}

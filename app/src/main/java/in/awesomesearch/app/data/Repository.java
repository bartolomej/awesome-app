package in.awesomesearch.app.data;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.squareup.picasso.Request;

import java.util.List;

import in.awesomesearch.app.AwesomeApplication;
import in.awesomesearch.app.AwesomeError;
import in.awesomesearch.app.data.dao.AwesomeItemDao;
import in.awesomesearch.app.data.dao.BookmarkGroupDao;
import in.awesomesearch.app.data.models.AwesomeItem;
import in.awesomesearch.app.data.models.BookmarkGroup;
import in.awesomesearch.app.data.models.GroupWithItems;
import in.awesomesearch.app.data.models.SearchResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    private static final String TAG = "Repository";
    private static Repository instance;
    private Call<SearchResponse> searchRequest;

    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }

    public LiveData<List<GroupWithItems>> bookmarksWithGroups () {
        BookmarkGroupDao dao = AwesomeApplication.getDatabase().bookmarkGroupDao();
        return dao.getAllGroupsWithItems();
    }

    public LiveData<List<BookmarkGroup>> bookmarkGroups () {
        BookmarkGroupDao dao = AwesomeApplication.getDatabase().bookmarkGroupDao();
        return dao.getAllBookmarkGroups();
    }

    public void addBookmarkGroup(BookmarkGroup bookmarkGroup) {
        BookmarkGroupDao dao = AwesomeApplication.getDatabase().bookmarkGroupDao();
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                dao.insertBookmarkGroup(bookmarkGroup);
            }
        });
    }

    public void addAwesomeItem(AwesomeItem item) {
        AwesomeItemDao dao = AwesomeApplication.getDatabase().awesomeItemDao();
        dao.insertItem(item);
    }

    public LiveData<Resource<SearchResponse>> searchItems(String query, int page, int items) {
        MutableLiveData<Resource<SearchResponse>> result = new MutableLiveData<>();
        AwesomeService service = AwesomeService.Factory.create();
        if (searchRequest != null && !searchRequest.isCanceled()) {
            searchRequest.cancel();
        }
        searchRequest = service.search(query, page, items);
        searchRequest.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> res) {
                if (res.code() == 200) {
                    result.postValue(Resource.success(res.body()));
                } else {
                    result.postValue(Resource.failed(AwesomeError.network(res.message())));
                }
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                result.postValue(Resource.failed(AwesomeError.network(t.getMessage())));
            }
        });
        return result;
    }

    public LiveData<Resource<AwesomeItem>> getAwesomeItem (String uid) {
        AwesomeService service = AwesomeService.Factory.create();
        Call<AwesomeItem> request = service.object(uid);
        MutableLiveData<Resource<AwesomeItem>> result = new MutableLiveData<>();
        request.enqueue(new Callback<AwesomeItem>() {
            @Override
            public void onResponse(Call<AwesomeItem> call, Response<AwesomeItem> res) {
                if (res.code() == 200) {
                    result.postValue(Resource.success(res.body()));
                } else {
                    result.postValue(Resource.failed(AwesomeError.network(res.message())));
                }
            }

            @Override
            public void onFailure(Call<AwesomeItem> call, Throwable t) {
                result.postValue(Resource.failed(AwesomeError.network(t.getMessage())));
            }
        });
        return result;
    }
}

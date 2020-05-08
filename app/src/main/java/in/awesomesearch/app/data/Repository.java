package in.awesomesearch.app.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import in.awesomesearch.app.AwesomeError;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    private static final String TAG = "Repository";
    private static Repository instance;
    private Call<List<AwesomeItem>> searchRequest;

    public static Repository getInstance() {
        if (instance == null) {
            instance = new Repository();
        }
        return instance;
    }

    public LiveData<Resource<List<AwesomeItem>>> searchItems(String query) {
        MutableLiveData<Resource<List<AwesomeItem>>> result = new MutableLiveData<>();
        AwesomeService service = AwesomeService.Factory.create();
        if (searchRequest != null && !searchRequest.isCanceled()) {
            searchRequest.cancel();
        }
        searchRequest = service.search(query);
        searchRequest.enqueue(new Callback<List<AwesomeItem>>() {
            @Override
            public void onResponse(Call<List<AwesomeItem>> call, Response<List<AwesomeItem>> res) {
                if (res.code() == 200) {
                    result.postValue(Resource.success(res.body()));
                } else {
                    result.postValue(Resource.failed(AwesomeError.network(res.message())));
                }
            }

            @Override
            public void onFailure(Call<List<AwesomeItem>> call, Throwable t) {
                result.postValue(Resource.failed(AwesomeError.network(t.getMessage())));
            }
        });
        return result;
    }
}

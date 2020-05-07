package in.awesomesearch.app.data;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Repository {

    private static final String TAG = "Repository";
    private static Repository instance;
    private MutableLiveData<List<AwesomeItem>> awesomeItems;

    private Repository () {
        awesomeItems = new MutableLiveData<>();
        awesomeItems.setValue(new ArrayList<>());
    }

    /**
     * Singleton pattern.
     * More about synchronization
     * https://stackoverflow.com/questions/1085709/what-does-synchronized-mean
     */
    public static Repository getInstance() {
        if (instance == null) {
            synchronized (Repository.class) {
                if (instance == null) {
                    instance = new Repository();
                }
            }
        }
        return instance;
    }

    public LiveData<List<AwesomeItem>> getAwesomeItems() {
        return awesomeItems;
    }

    public void searchAwesomeItems(String query) {
        AwesomeService service = AwesomeService.Factory.create();
        service.search(query).enqueue(new Callback<List<AwesomeItem>>() {
            @Override
            public void onResponse(Call<List<AwesomeItem>> call, Response<List<AwesomeItem>> response) {
                if (response.code() == 200) {
                    awesomeItems.postValue(response.body());
                }
            }
            @Override
            public void onFailure(Call<List<AwesomeItem>> call, Throwable t) {
            }
        });
    }
}

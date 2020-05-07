package in.awesomesearch.app.tasks;

import android.os.AsyncTask;

import java.util.ArrayList;
import java.util.List;

import in.awesomesearch.app.data.AwesomeItem;
import in.awesomesearch.app.data.AwesomeService;
import retrofit2.Response;

public class SearchQueryTask extends AsyncTask<String, Void, ArrayList<AwesomeItem>> {

    private Exception exception;

    protected ArrayList<AwesomeItem> doInBackground(String... query) {
        try {
            AwesomeService service = AwesomeService.Factory.create();
            Response<List<AwesomeItem>> response = service.search(query[0]).execute();
            if (response.code() == 200) {
                return (ArrayList<AwesomeItem>) response.body();
            } else {
                return null;
            }
        } catch (Exception e) {
            this.exception = e;
            return null;
        }
    }
}
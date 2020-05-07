package in.awesomesearch.app.data;

import java.util.List;
import java.util.concurrent.ExecutionException;

import in.awesomesearch.app.tasks.SearchQueryTask;

public class Repository {

    private static Repository instance;
    private List<AwesomeItem> awesomeItems;

    private Repository () {

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

    public List<AwesomeItem> getAwesomeItems (String query) {
        try {
            awesomeItems = new SearchQueryTask().execute(query).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return awesomeItems;
    }
}

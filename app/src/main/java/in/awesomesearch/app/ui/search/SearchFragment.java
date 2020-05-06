package in.awesomesearch.app.ui.search;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import in.awesomesearch.app.AwesomeApplication;
import in.awesomesearch.app.AwesomeItem;
import in.awesomesearch.app.AwesomeService;
import in.awesomesearch.app.R;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private String TAG = "BookmarksFragment";
    private RecyclerView recyclerView;
    private ResultListAdapter resultListAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        // load test data
        ArrayList<AwesomeItem> items = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            AwesomeItem item = new AwesomeItem();
            item.title = "Some title";
            item.description = "Some description";
            item.image = "https://developer.abstract.com/img/social.png";
            items.add(item);
        }

        recyclerView = root.findViewById(R.id.results_view);
        resultListAdapter = new ResultListAdapter(this.getContext(), items);
        recyclerView.setAdapter(resultListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        SearchView searchView = root.findViewById(R.id.search_field);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.d(TAG, "onSubmit: " + query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG, "onTextChange: " + newText);
                return false;
            }
        });
        return root;
    }

}

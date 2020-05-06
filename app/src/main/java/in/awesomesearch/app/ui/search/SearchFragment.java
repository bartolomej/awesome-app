package in.awesomesearch.app.ui.search;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import in.awesomesearch.app.AwesomeApplication;
import in.awesomesearch.app.AwesomeItem;
import in.awesomesearch.app.R;
import in.awesomesearch.app.SearchQueryTask;

public class SearchFragment extends Fragment {

    private String TAG = "SearchFragment";
    private RecyclerView recyclerView;
    private ResultListAdapter resultListAdapter;

    // TODO: this is very bad - do not do this ! (this is just a test)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        recyclerView = root.findViewById(R.id.results_view);
        resultListAdapter = new ResultListAdapter(this.getContext());
        recyclerView.setAdapter(resultListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        EditText searchView = root.findViewById(R.id.search_field);
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                AsyncTask<String, Void, ArrayList<AwesomeItem>> task = new SearchQueryTask().execute(s.toString());
                try {
                    ArrayList<AwesomeItem> results = task.get();
                    resultListAdapter.setItems(results);
                    resultListAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return root;
    }

}

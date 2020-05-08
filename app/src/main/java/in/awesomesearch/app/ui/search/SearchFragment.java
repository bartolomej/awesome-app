package in.awesomesearch.app.ui.search;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.awesomesearch.app.R;
import in.awesomesearch.app.data.AwesomeItem;


public class SearchFragment extends Fragment {

    private String TAG = "SearchFragment";
    private RecyclerView recyclerView;
    private TextView messageText;
    private SearchAdapter resultListAdapter;
    private ProgressBar searchProgressBar;
    private EditText searchField;
    private View root;
    private SearchViewModel viewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        root = inflater.inflate(R.layout.fragment_search, container, false);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        initViews();
        registerListeners();
        registerStateObservers();
    }

    private void initViews() {
        searchField = root.findViewById(R.id.search_field);
        searchField.setText(viewModel.getQuery().getValue());
        messageText = root.findViewById(R.id.search_message_text);
        searchProgressBar = root.findViewById(R.id.search_progress_bar);
        recyclerView = root.findViewById(R.id.results_view);
        resultListAdapter = new SearchAdapter(this.getContext(), viewModel.getSearchItems());
        recyclerView.setAdapter(resultListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

    /**
     * Observes and reacts to state changes by updating views.
     */
    private void registerStateObservers () {
        viewModel.getObservableSearchItems().observe(this, new Observer<List<AwesomeItem>>() {
            @Override
            public void onChanged(List<AwesomeItem> listResource) {
                resultListAdapter.setItems(listResource);
                resultListAdapter.notifyDataSetChanged();
            }
        });
        viewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    showSearchProgress();
                } else {
                    hideSearchProgress();
                }
            }
        });
        viewModel.getMessage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                if (s != null) {
                    messageText.setVisibility(View.VISIBLE);
                    messageText.setText(R.string.search_empty_message);
                } else {
                    messageText.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * Listens to user interaction events.
     */
    private void registerListeners() {
        searchField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void afterTextChanged(Editable s) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                viewModel.searchQuery(s.toString());
            }
        });
        resultListAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = recyclerView.getChildLayoutPosition(v);
                AwesomeItem item = viewModel.getSearchItems().get(itemPosition);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.url));
                startActivity(browserIntent);
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.d(TAG, "Scrolled dx: " + dx + " dy: " + dy);
                // TODO: setup pagination
            }
        });
    }

    private void showSearchProgress() {
        searchProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideSearchProgress() {
        searchProgressBar.setVisibility(View.GONE);
    }

}

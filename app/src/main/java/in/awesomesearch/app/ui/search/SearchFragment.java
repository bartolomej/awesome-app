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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import in.awesomesearch.app.R;
import in.awesomesearch.app.data.AwesomeItem;
import in.awesomesearch.app.data.Repository;

public class SearchFragment extends Fragment {

    private String TAG = "SearchFragment";
    private RecyclerView recyclerView;
    private TextView messageText;
    private SearchAdapter resultListAdapter;
    private ProgressBar searchProgressBar;
    private View root;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_search, container, false);
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        initViews();
        registerSearchFieldListener();
        Log.d(TAG, "onStart");

        // observe search result state change
        Repository.getInstance().getAwesomeItems().observe(this, new Observer<List<AwesomeItem>>() {
            @Override
            public void onChanged(List<AwesomeItem> awesomeItems) {
                resultListAdapter.setItems((ArrayList<AwesomeItem>) awesomeItems);
                resultListAdapter.notifyDataSetChanged();
                hideSearchProgress();
                if (awesomeItems.size() == 0) {
                    messageText.setVisibility(View.VISIBLE);
                    messageText.setText(R.string.search_empty_message);
                } else {
                    messageText.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initViews() {
        messageText = root.findViewById(R.id.search_message_text);
        searchProgressBar = root.findViewById(R.id.search_progress_bar);
        recyclerView = root.findViewById(R.id.results_view);
        resultListAdapter = new SearchAdapter(this.getContext(), Repository.getInstance().getAwesomeItems().getValue());
        resultListAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = recyclerView.getChildLayoutPosition(v);
                AwesomeItem item = Repository.getInstance().getAwesomeItems().getValue().get(itemPosition);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(item.url));
                startActivity(browserIntent);
            }
        });
        recyclerView.setAdapter(resultListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }

    private void registerSearchFieldListener() {
        EditText searchView = root.findViewById(R.id.search_field);
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                Repository.getInstance().searchAwesomeItems(s.toString());
                showSearchProgress();
            }
        });
    }

    private void showSearchProgress () {
        searchProgressBar.setVisibility(View.VISIBLE);
    }

    private void hideSearchProgress () {
        searchProgressBar.setVisibility(View.GONE);
    }

}

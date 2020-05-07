package in.awesomesearch.app;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import in.awesomesearch.app.data.AwesomeItem;
import in.awesomesearch.app.ui.search.SearchAdapter;
import in.awesomesearch.app.ui.search.SearchViewModel;

/**
 * EXAMPLE APPS:
 * - https://github.com/MindorksOpenSource/android-mvp-architecture
 * - https://github.com/android/architecture-components-samples/tree/master/BasicSample
 * - https://github.com/ivacf/archi
 * - https://github.com/k0shk0sh/FastHub
 *
 * SPLASH SCREEN / ON BOARDING:
 * - https://www.youtube.com/watch?v=JLIFqqnSNmg
 * - https://www.youtube.com/watch?v=pwcG6npiXyo
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private SearchAdapter resultListAdapter;
    private SearchViewModel searchViewModel;
    private ProgressBar searchProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_bookmarks)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        searchViewModel.init();

        // observe search result state change
        searchViewModel.getSearchItems().observe(this, new Observer<List<AwesomeItem>>() {
            @Override
            public void onChanged(List<AwesomeItem> awesomeItems) {
                Log.d(TAG, "Awesome list changed: " + awesomeItems.size());
                resultListAdapter.setItems((ArrayList<AwesomeItem>) awesomeItems);
                resultListAdapter.notifyDataSetChanged();
            }
        });

        // observe loading state change when searching
        searchViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                Log.d(TAG, "Loading state changed " + aBoolean);
                if (aBoolean) {
                    showSearchProgress();
                } else {
                    hideSearchProgress();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        initViews();
        registerSearchFieldListener();
    }

    private void initViews() {
        searchProgressBar = findViewById(R.id.search_progress_bar);
        recyclerView = findViewById(R.id.results_view);
        resultListAdapter = new SearchAdapter(this, searchViewModel.getSearchItems().getValue());
        recyclerView.setAdapter(resultListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void registerSearchFieldListener() {
        EditText searchView = findViewById(R.id.search_field);
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) { }

            @Override
            public void afterTextChanged(Editable s) {
                searchViewModel.searchAwesomeItems(s.toString());
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

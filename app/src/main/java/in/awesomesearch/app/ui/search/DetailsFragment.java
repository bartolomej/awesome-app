package in.awesomesearch.app.ui.search;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import in.awesomesearch.app.AwesomeError;
import in.awesomesearch.app.R;
import in.awesomesearch.app.data.models.AwesomeItem;


public class DetailsFragment extends Fragment {

    private static final String TAG = "DetailsFragment";
    private DetailsViewModel viewModel;
    private String uid;
    private String url;
    private View root;
    private TextView titleView;
    private TextView descriptionView;
    private ImageView imageView;
    private Button urlButton;
    private Button bookmarkButton;


    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_details, container, false);
        initViews();
        registerInteractionListeners();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        uid = DetailsFragmentArgs.fromBundle(getArguments()).getItemUid();
        viewModel = new ViewModelProvider(this).get(DetailsViewModel.class);
        setupNavigation(view);
        registerStateObservers();
    }

    private void setupNavigation (View view) {
        NavController navController = Navigation.findNavController(view);
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).build();
        Toolbar toolbar = root.findViewById(R.id.details_top_menu);
        toolbar.findViewById(R.id.add_to_bookmarks_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Add to bookmarks clicked");
            }
        });

        NavigationUI.setupWithNavController(
                toolbar, navController, appBarConfiguration);
    }

    private void initViews () {
        titleView = root.findViewById(R.id.details_title);
        descriptionView = root.findViewById(R.id.details_description);
        imageView = root.findViewById(R.id.details_image);
        urlButton = root.findViewById(R.id.details_url_btn);
        bookmarkButton = root.findViewById(R.id.details_bookmark_btn);
    }

    private void registerStateObservers () {
        viewModel.getAwesomeItem(uid).observe(getViewLifecycleOwner(), new Observer<AwesomeItem>() {
            @Override
            public void onChanged(AwesomeItem awesomeItem) {
                Log.d(TAG, awesomeItem.toString());
                titleView.setText(awesomeItem.title);
                descriptionView.setText(awesomeItem.description);
                Picasso.get().load(awesomeItem.image).into(imageView);
                url = awesomeItem.url;
            }
        });
        viewModel.getAwesomeError().observe(getViewLifecycleOwner(), new Observer<AwesomeError>() {
            @Override
            public void onChanged(AwesomeError awesomeError) {
                Log.d(TAG, "Error: " + awesomeError.getMessage());
            }
        });
    }

    private void registerInteractionListeners () {
        bookmarkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        urlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPageInBrowser(url);
            }
        });
    }

    private void openPageInBrowser(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }
}

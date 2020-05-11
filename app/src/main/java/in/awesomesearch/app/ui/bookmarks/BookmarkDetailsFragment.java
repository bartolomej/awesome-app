package in.awesomesearch.app.ui.bookmarks;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import in.awesomesearch.app.R;
import in.awesomesearch.app.data.models.AwesomeItem;
import in.awesomesearch.app.data.models.BookmarkGroup;
import in.awesomesearch.app.data.models.GroupWithItems;
import in.awesomesearch.app.ui.AwesomeListAdapter;


public class BookmarkDetailsFragment extends Fragment {

    private View root;
    private Toolbar toolbar;
    private NavController navController;
    private BookmarkGroup bookmarkGroup;
    private BookmarksViewModal viewModel;
    private AwesomeListAdapter awesomeListAdapter;
    private List<AwesomeItem> awesomeItems;
    private RecyclerView recyclerView;
    private String groupUid;

    public BookmarkDetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        groupUid = BookmarkDetailsFragmentArgs.fromBundle(getArguments()).getGroupUid();
        viewModel = new ViewModelProvider(this).get(BookmarksViewModal.class);
        setupNavigation(view);
        registerStateObservers();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_bookmark_details, container, false);
        setupViews();
        return root;
    }

    private void setupViews() {
        awesomeListAdapter = new AwesomeListAdapter(this.getContext());
        recyclerView = root.findViewById(R.id.bookmark_items_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerView.setAdapter(awesomeListAdapter);
        toolbar = root.findViewById(R.id.bookmark_details_toolbar);
    }

    private void setupNavigation(View view) {
        navController = Navigation.findNavController(view);
        AppBarConfiguration appBarConfiguration =
                new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
    }

    private void registerStateObservers() {
        viewModel.getGroupWithItems(groupUid).observe(this.getViewLifecycleOwner(), new Observer<GroupWithItems>() {
            @Override
            public void onChanged(GroupWithItems group) {
                List<AwesomeItem> items = group.items;
                bookmarkGroup = group.bookmarkGroup;
                awesomeListAdapter.setItems(items);
                awesomeItems = items;
            }
        });
        awesomeListAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int itemPosition = recyclerView.getChildLayoutPosition(v);
                AwesomeItem item = awesomeItems.get(itemPosition);
                openPageInBrowser(item.url);
            }
        });
        toolbar.findViewById(R.id.edit_bookmark_delete_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.removeBookmarkGroup(bookmarkGroup);
                navController.popBackStack();
            }
        });
    }

    private void openPageInBrowser(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

}

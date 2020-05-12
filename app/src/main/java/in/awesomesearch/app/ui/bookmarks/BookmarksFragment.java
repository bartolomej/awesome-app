package in.awesomesearch.app.ui.bookmarks;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import in.awesomesearch.app.R;
import in.awesomesearch.app.data.models.AwesomeItem;
import in.awesomesearch.app.data.models.GroupWithItems;
import in.awesomesearch.app.ui.search.SearchFragmentDirections;

public class BookmarksFragment extends Fragment {

    private String TAG = "BookmarksFragment";
    private View root;
    private Toolbar toolbar;
    private BookmarksViewModal viewModel;
    private RecyclerView bookmarksRecyclerView;
    private BookmarksListAdapter bookmarksListAdapter;
    private List<GroupWithItems> groupWithItems;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(BookmarksViewModal.class);
        root = inflater.inflate(R.layout.fragment_bookmarks, container, false);
        setupViews();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        registerStateObservers();
        registerInteractionListeners();
    }

    private void setupViews() {
        toolbar = root.findViewById(R.id.bookmarks_toolbar);
        bookmarksRecyclerView = root.findViewById(R.id.bookmarks_recycler_view);
        bookmarksListAdapter = new BookmarksListAdapter(this.getContext());
        bookmarksRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        bookmarksRecyclerView.setAdapter(bookmarksListAdapter);
        toolbar.setTitle(null);
        toolbar.setNavigationIcon(null);
    }

    private void registerStateObservers() {
        viewModel.getGroupsWithItems().observe(this.getViewLifecycleOwner(), new Observer<List<GroupWithItems>>() {
            @Override
            public void onChanged(List<GroupWithItems> bookmarkGroups) {
                bookmarksListAdapter.setItems(bookmarkGroups);
                groupWithItems = bookmarkGroups;
            }
        });
        bookmarksListAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO; refactor
                int itemPosition = bookmarksRecyclerView.getChildLayoutPosition(v);
                GroupWithItems item = groupWithItems.get(itemPosition);
                Navigation.findNavController(v).navigate(
                        BookmarksFragmentDirections.actionNavigationBookmarksToBookmarkDetailsFragment(item.bookmarkGroup.name)
                );
            }
        });
    }

    private void registerInteractionListeners () {
        toolbar.findViewById(R.id.add_bookmark_group_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(root).navigate(
                        BookmarksFragmentDirections.actionNavigationBookmarksToBookmarkEditFragment(null)
                );
            }
        });
    }
}

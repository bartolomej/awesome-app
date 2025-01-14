package in.awesomesearch.app.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import in.awesomesearch.app.R;
import in.awesomesearch.app.data.models.AwesomeItem;

public class AwesomeListAdapter extends RecyclerView.Adapter<SearchItemViewHolder> {

    private static final String TAG = "AwesomeListAdapter";
    private List<AwesomeItem> items;
    private LayoutInflater layoutInflater;
    private View.OnClickListener onClickListener;
    private Context context;

    public AwesomeListAdapter(Context context) {
        this.context = context;
        this.items = new ArrayList<>();
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public SearchItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.awesome_item,
                parent, false);
        view.setOnClickListener(onClickListener);
        return new SearchItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchItemViewHolder holder, int position) {
        AwesomeItem current = items.get(position);
        holder.setTitle(current.title);
        holder.setDescription(current.description);
        holder.setImage(current.image);
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setItems(List<AwesomeItem> items) {
        if (items != null) {
            this.items = items;
        } else {
            this.items = new ArrayList<>();
        }
        notifyDataSetChanged();
    }
}

class SearchItemViewHolder extends RecyclerView.ViewHolder {

    final TextView titleText;
    final TextView descriptionText;
    final ImageView imageView;
    final LinearLayout tagsLinearLayout;

    SearchItemViewHolder(View itemView) {
        super(itemView);
        titleText = itemView.findViewById(R.id.item_title);
        descriptionText = itemView.findViewById(R.id.item_description);
        imageView = itemView.findViewById(R.id.item_image);
        tagsLinearLayout = itemView.findViewById(R.id.tags_linear_layout);
    }

    void setDescription(String description) {
        descriptionText.setText(description);
    }

    void setTitle(String title) {
        titleText.setText(title);
    }

    void setImage(String url) {
        // https://square.github.io/picasso/
        Picasso.get().load(url).into(imageView);
    }
}
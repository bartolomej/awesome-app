package in.awesomesearch.app.ui.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import in.awesomesearch.app.R;
import in.awesomesearch.app.data.models.AwesomeItem;

public class SearchAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    private List<AwesomeItem> items;
    private LayoutInflater layoutInflater;
    private View.OnClickListener onClickListener;


    public SearchAdapter(Context context, List<AwesomeItem> items) {
        this.items = items;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setOnClickListener(View.OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setItems (List<AwesomeItem> items) {
        if (items != null) {
            this.items = items;
        }
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.awesome_item,
                parent, false);
        view.setOnClickListener(onClickListener);
        return new ItemViewHolder(view, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        AwesomeItem current = items.get(position);
        holder.title.setText(current.title);
        holder.description.setText(current.description);
        // https://square.github.io/picasso/
        Picasso.get().load(current.image).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }
}

class ItemViewHolder extends RecyclerView.ViewHolder {

    final SearchAdapter adapter;
    public final TextView title;
    public final TextView description;
    public final ImageView image;

    public ItemViewHolder(View itemView, SearchAdapter adapter) {
        super(itemView);
        title = itemView.findViewById(R.id.item_title);
        description = itemView.findViewById(R.id.item_description);
        image = itemView.findViewById(R.id.item_image);
        this.adapter = adapter;
    }
}
package in.awesomesearch.app.ui.search;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.net.URI;
import java.util.ArrayList;

import in.awesomesearch.app.AwesomeItem;
import in.awesomesearch.app.DownloadImageTask;
import in.awesomesearch.app.R;

public class ResultListAdapter extends RecyclerView.Adapter<ItemViewHolder> {

    private ArrayList<AwesomeItem> items;
    private LayoutInflater layoutInflater;

    public ResultListAdapter(Context context, ArrayList<AwesomeItem> items) {
        this.items = items;
        layoutInflater = LayoutInflater.from(context);
    }

    public ResultListAdapter(Context context) {
        this.items = new ArrayList<>();
        layoutInflater = LayoutInflater.from(context);
    }

    public void setItems (ArrayList<AwesomeItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = layoutInflater.inflate(R.layout.awesome_item,
                parent, false);
        return new ItemViewHolder(mItemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        AwesomeItem current = items.get(position);
        holder.title.setText(current.title);
        holder.description.setText(current.description);
        new DownloadImageTask(holder.image).execute(current.image);
    }

    @Override
    public int getItemCount() {
        return this.items.size();
    }
}

class ItemViewHolder extends RecyclerView.ViewHolder {

    final ResultListAdapter adapter;
    public final TextView title;
    public final TextView description;
    public final ImageView image;

    public ItemViewHolder(View itemView, ResultListAdapter adapter) {
        super(itemView);
        title = itemView.findViewById(R.id.item_title);
        description = itemView.findViewById(R.id.item_description);
        image = itemView.findViewById(R.id.item_image);
        this.adapter = adapter;
    }
}
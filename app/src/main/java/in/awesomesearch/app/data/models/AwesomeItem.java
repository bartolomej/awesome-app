package in.awesomesearch.app.data.models;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@Entity
public class AwesomeItem {

    @NonNull
    @PrimaryKey
    public String uid;
    @SerializedName(value = "source")
    public String sourceRepoId;
    public String groupId;
    @SerializedName(value = "object_type")
    public String type;
    public String author;
    public String title;
    public String description;
    public String url;
    public String image;
    public int stars;
    public int forks;
    @Embedded
    public ArrayList<String> tags;
    @Embedded
    public ArrayList<String> urls;

    public AwesomeItem(@NonNull String uid) {
        this.uid = uid;
    }

    public boolean hasTags() {
        return this.tags.size() > 0;
    }

    @NonNull
    public String toString () {
        return String.format("AwesomeItem[uid:%s, title:%s]", this.uid, this.title);
    }
}
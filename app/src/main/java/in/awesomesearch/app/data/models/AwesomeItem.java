package in.awesomesearch.app.data.models;

import androidx.annotation.NonNull;
import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class AwesomeItem {

    @NonNull
    @PrimaryKey
    public String uid;
    public String groupId;
    public String type;
    public String title;
    public String description;
    public String url;
    public String image;
    @Embedded
    public ArrayList<String> tags;
    @Embedded
    public RepoInfo extras;

    public AwesomeItem(@NonNull String uid) {
        this.uid = uid;
    }

    public boolean hasExtras() {
        return this.extras.githubForks != 0 && this.extras.githubStars != 0;
    }

    public boolean hasTags() {
        return this.tags.size() > 0;
    }
}
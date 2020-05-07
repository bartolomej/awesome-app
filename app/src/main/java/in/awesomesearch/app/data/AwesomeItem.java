package in.awesomesearch.app.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class AwesomeItem {

    public String type;
    public String title;
    public String description;
    public String url;
    public String image;
    public ArrayList<String> tags;
    public ItemExtras extras;

    public AwesomeItem () {}

    public boolean hasExtras () {
        return this.extras.githubForks != 0 && this.extras.githubStars != 0;
    }

    public boolean hasTags () {
        return this.tags.size() > 0;
    }
}

class ItemExtras {

    @SerializedName("stars")
    public int githubStars;
    @SerializedName("forks")
    public int githubForks;
}
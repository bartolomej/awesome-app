package in.awesomesearch.app.data.models;

import com.google.gson.annotations.SerializedName;

public class RepoInfo {
    @SerializedName("stars")
    public int githubStars;
    @SerializedName("forks")
    public int githubForks;
}
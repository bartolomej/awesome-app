package in.awesomesearch.app.data.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class BookmarkGroup {

    @NonNull
    @PrimaryKey
    public String name;

    public BookmarkGroup(@NonNull String name) {
        this.name = name;
    }

}

package in.awesomesearch.app.data.models;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class GroupWithItems {
    @Embedded
    public BookmarkGroup bookmarkGroup;

    @Relation(
            parentColumn = "name",
            entityColumn = "groupId"
    )
    public List<AwesomeItem> items;
}

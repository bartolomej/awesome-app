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

    public int getItemsCount () {
        if (items != null) {
            return items.size();
        } else {
            return 0;
        }
    }

    public boolean hasItems () {
        return this.items != null && this.items.size() > 0;
    }
}

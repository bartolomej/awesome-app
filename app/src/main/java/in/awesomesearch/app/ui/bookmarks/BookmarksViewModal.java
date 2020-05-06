package in.awesomesearch.app.ui.bookmarks;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class BookmarksViewModal extends ViewModel {

    private MutableLiveData<String> mText;

    public BookmarksViewModal() {
        mText = new MutableLiveData<>();
        mText.setValue("This is dashboard fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
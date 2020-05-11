package in.awesomesearch.app;

import android.app.Application;

import in.awesomesearch.app.data.AppDatabase;

public class AwesomeApplication extends Application {

    private static AppDatabase appDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        appDatabase = AppDatabase.getInstance(this);
    }

    public static AppDatabase getDatabase () {
        return appDatabase;
    }
}

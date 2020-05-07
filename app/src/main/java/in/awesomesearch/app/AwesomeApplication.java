package in.awesomesearch.app;

import android.app.Application;
import android.content.Context;

import in.awesomesearch.app.data.AwesomeService;

public class AwesomeApplication extends Application {

    private AwesomeService awesomeService;

    public static AwesomeApplication get(Context context) {
        return (AwesomeApplication) context.getApplicationContext();
    }

    public AwesomeService getAwesomeService() {
        if (awesomeService == null) {
            awesomeService = AwesomeService.Factory.create();
        }
        return awesomeService;
    }
}

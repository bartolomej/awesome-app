package in.awesomesearch.app;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

/**
 * EXAMPLE APPS:
 * - https://github.com/android/architecture-components-samples/tree/master/BasicSample
 * - https://github.com/ivacf/archi
 * - https://github.com/k0shk0sh/FastHub
 * - https://github.com/android/architecture-components-samples/tree/6248bed977e7a82d6f3199e8a940a39b7d6f051c/GithubBrowserSample/app/src/main/java/com/android/example/github
 * SPLASH SCREEN / ON BOARDING:
 * - https://www.youtube.com/watch?v=JLIFqqnSNmg
 * - https://www.youtube.com/watch?v=pwcG6npiXyo
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_bookmarks)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }

}

package eu.vk.trackerapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.google.android.material.navigation.NavigationView;

import java.time.LocalDate;

import eu.vk.trackerapp.ui.CreationFragment;
import eu.vk.trackerapp.ui.ItemFragment;
import eu.vk.trackerapp.ui.ItemListAdapter;
import eu.vk.trackerapp.ui.UserFragment;
import eu.vk.trackerapp.ui.model.Item;
import eu.vk.trackerapp.ui.storage.DatabaseProvider;

public class MainActivity extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener {
    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionMenu fabMenu = findViewById(R.id.fab_menu);

        FloatingActionButton fabTraining = findViewById(R.id.fab_create_training);
        FloatingActionButton fabSleeping = findViewById(R.id.fab_create_sleeping);
        FloatingActionButton fabEating = findViewById(R.id.fab_create_eating);

        fabEating.setOnClickListener(v -> new CreationFragment()
                .show(getSupportFragmentManager(), "MealCreation"));

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_items, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                new UserFragment().show(getSupportFragmentManager(), "UserFragment");
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onListFragmentInteraction(Item item) {
        new AlertDialog.Builder(this)
                .setTitle("Ištrinti ar redaguoti")
                .setMessage("Ištrinti įrašą ar redaguoti?")
                .setPositiveButton("Redaguoti", (dialog, which) -> new CreationFragment(item).show(getSupportFragmentManager(), "MealEdit"))
                .setNegativeButton("Ištrinti", (dialog, which) -> DatabaseProvider.getInstance().itemDao().delete(item))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
}

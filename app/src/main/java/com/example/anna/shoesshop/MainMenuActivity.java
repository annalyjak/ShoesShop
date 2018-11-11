package com.example.anna.shoesshop;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.example.anna.shoesshop.controller.AboutUsFragment;
import com.example.anna.shoesshop.controller.CategoriesFragment;
import com.example.anna.shoesshop.controller.FAQFragment;
import com.example.anna.shoesshop.controller.FavouritesFragment;
import com.example.anna.shoesshop.controller.adapters.DeliveryAdapter;
import com.example.anna.shoesshop.model.repo.Session;
import com.example.anna.shoesshop.model.product.Product;
import com.example.anna.shoesshop.model.repo.DBHelper;
import com.example.anna.shoesshop.model.repo.LocalDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainMenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentManager mFragmentManager;
    private Fragment fragment = null;
    private DBHelper database;
    private static Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("GRACE SHOP");
        toolbar.setTitleTextColor(getResources().getColor(R.color.dark));
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = findViewById(R.id.fab);
//        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show());

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mFragmentManager = getSupportFragmentManager();

        fragment = CategoriesFragment.newInstance();
        mFragmentManager
                .beginTransaction()
                .replace(R.id.main_frame, fragment)
                .commit();
        //HERE LOCAL DATABASE INSTRUCTIONS ARE EXECUTED:
        database = new LocalDatabase(getApplicationContext());
        session = new Session(database);
//        LocalDatabase.addProducts(getApplicationContext());
//        ((LocalDatabase) database).addProducts();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_fav) {
            List<Product> list = new ArrayList<>();
            //TODO downoload from database
            fragment = FavouritesFragment.newInstance(null);
            return true;
        }

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_bag) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            fragment = CategoriesFragment.newInstance();
        } else if (id == R.id.nav_orders) {

        } else if (id == R.id.nav_my_profile) {

        } else if (id == R.id.nav_fav_prod) {
            List<Product> list = new ArrayList<>();
            //TODO downoload from database
            fragment = FavouritesFragment.newInstance(null);

        } else if (id == R.id.nav_dostawa) {
            displayDeliveryInfo();

        } else if (id == R.id.nav_faq) {
            fragment = FAQFragment.newInstance();

        } else if (id == R.id.nav_about_us) {
            fragment = AboutUsFragment.newInstance();
        }

        mFragmentManager
                .beginTransaction()
                .replace(R.id.main_frame, fragment)
                //TODO zastanowić się czy robić stos
//                .addToBackStack(null)
                .commit();

//        mDrawerLayout.closeDrawers();

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    private void displayDeliveryInfo() {
        final Dialog dialog = new Dialog(this);

        View view = getLayoutInflater().inflate(R.layout.delivery_dialog, null);

        ListView listView = view.findViewById(R.id.dialog_list);
        DeliveryAdapter adapter = new DeliveryAdapter(
                MainMenuActivity.this,
                DBHelper.getDeliveryList());
        listView.setAdapter(adapter);

        Button button = view.findViewById(R.id.button2);
        button.setOnClickListener(view1 -> dialog.dismiss());

        dialog.setContentView(view);
        dialog.show();
    }


    /**
     * The first method for FAQFragment's handler
     * @param view for button action
     */
    public void onClickQ1(View view) {
        if(fragment instanceof FAQFragment){
            ((FAQFragment) fragment).showAndswer1();
        }
    }

    public void onClickQ2(View view) {
        if(fragment instanceof FAQFragment){
            ((FAQFragment) fragment).showAndswer2();
        }
    }

    public void onClickQ3(View view) {
        if(fragment instanceof FAQFragment){
            ((FAQFragment) fragment).showAndswer3();
        }
    }

    public void onClickQ4(View view) {
        if(fragment instanceof FAQFragment){
            ((FAQFragment) fragment).showAndswer4();
        }
    }
    /*
     * End of FAQ handlers
     */
}

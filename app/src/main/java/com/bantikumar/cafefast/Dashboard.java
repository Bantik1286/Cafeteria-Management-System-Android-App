package com.bantikumar.cafefast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

public class Dashboard extends AppCompatActivity {
    Toolbar toolbar ;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    BottomNavigationView bottomNavigationView;
    Fragment fragment=null;
    public static TextView fullname;
    public static Student student = null;
    Bundle bundle;
    public static List<Item> itemList;
    Dialog progressDialog;
    Database db;
    public static OrderClass order = null;
    public static String email;



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        MenuItem m = menu.findItem(R.id.search_box);
        SearchView s = (SearchView)m.getActionView();
        s.setQueryHint("Search items or categories");
        s.setImeOptions(EditorInfo.IME_ACTION_DONE);
        s.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(fragment instanceof HomeFragement)
                {
                    HomeFragement.itemAdapter.getFilter().filter(newText);
                }
                if(fragment instanceof FavouriteFragement){
                    FavouriteFragement.itemAdapter.getFilter().filter(newText);
                }
                return false;
            }
        });
        s.setIconifiedByDefault(false);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh_dashboard:
                startActivity(getIntent());
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        bundle = getIntent().getExtras();
        email = bundle.getString("EMAIL");
        bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.home_nav:
                        fragment = new HomeFragement();
                        break;
                    case R.id.favourite_nav:
                        fragment = new FavouriteFragement();
                        break;
                    case R.id.cart_nav:
                        fragment = new CartFragement();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,fragment).commit();

                return true;
            }
        });
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Dashboard");
        navigationView = findViewById(R.id.nav);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        CartFragement.cartOrder=false;
        drawerLayout = findViewById(R.id.drawer);
        fullname = navigationView.getHeaderView(0).findViewById(R.id.full_name_drawer_header);
        if(student !=null)
         fullname.setText(student.getFirstname() + " "+student.getLastname());
        else
            fullname.setText("-----");
        toggle = new ActionBarDrawerToggle(Dashboard.this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.logout_icon:
                        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.remove("EMAIL");
                        editor.commit();
                        Intent i = new Intent(Dashboard.this,MainActivity.class);
                        startActivity(i);
                        finish();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.order_icon_drawer:
                        Intent in = new Intent(Dashboard.this,Order.class);
                        in.putExtra("EMAIL",bundle.getString("EMAIL"));
                        startActivity(in);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.profile_btn_nav:
                        Intent in2 = new Intent(Dashboard.this,ProfileActivity.class);
                        startActivity(in2);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case R.id.order_history_icon:
                        Intent in3 = new Intent(Dashboard.this,OrderHistory.class);
                        startActivity(in3);
                        drawerLayout.closeDrawer(GravityCompat.START);
                        default:
                        drawerLayout.closeDrawer(GravityCompat.START);
                }
                return true;
            }
        });

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("EMAIL", bundle.getString("EMAIL"));
        editor.commit();

        db=new Database(bundle.getString("EMAIL"));

        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                student = null;
                progressDialog = new Dialog(Dashboard.this);
                progressDialog.setContentView(R.layout.loading_dialog);
                progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

            @Override
            protected Object doInBackground(Object[] objects) {
                student = db.login(bundle.getString("EMAIL"));
                itemList = db.getAllItems(bundle.getString("EMAIL"));
                db.getCartItems();
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                super.onPostExecute(o);
                    progressDialog.dismiss();
                    fragment = new HomeFragement();
                    if(student!=null)
                        fullname.setText(student.getFirstname() + " "+student.getLastname());
                    if (itemList != null)
                        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout, fragment).commit();
                    else
                        Toast.makeText(Dashboard.this, db.error, Toast.LENGTH_SHORT).show();
            }
        }.execute();

    }
}
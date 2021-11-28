package com.bantikumar.cafefast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class Dashboard extends AppCompatActivity {
    Toolbar tbar ;
    ActionBarDrawerToggle toogle;
    NavigationView nav;
    DrawerLayout drawerLayout;
    BottomNavigationView btmnav;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        MenuItem m = menu.findItem(R.id.search_box);

        SearchView s = (SearchView)m.getActionView();
        s.setQueryHint("Search items or categories");
        s.setIconifiedByDefault(false);
        return super.onCreateOptionsMenu(menu);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        btmnav = findViewById(R.id.bottom_nav);
        btmnav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment f=null;
                switch (item.getItemId())
                {
                    case R.id.home_nav:
                        f = new HomeFragement();
                        break;
                    case R.id.favourite_nav:
                        f = new FavouriteFragement();
                        break;
                    case R.id.cart_nav:
                        f = new CartFragement();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,f).commit();

                return true;
            }
        });
        tbar = findViewById(R.id.toolbar);
        nav = findViewById(R.id.nav);
        tbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(tbar);
        drawerLayout = findViewById(R.id.drawer);
        toogle = new ActionBarDrawerToggle(this,drawerLayout,tbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(toogle);
        toogle.syncState();
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
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
                        startActivity(in);
                        break;
                    case R.id.profile_btn_nav:
                        Intent in2 = new Intent(Dashboard.this,ProfileActivity.class);
                        startActivity(in2);
                        break;
                    default:
                        drawerLayout.closeDrawer(GravityCompat.START);
                }
                return true;
            }
        });
        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        SharedPreferences.Editor editor = pref.edit();
        Bundle b = getIntent().getExtras();
        editor.putString("EMAIL", b.getString("EMAIL"));
        editor.commit();
    }
}
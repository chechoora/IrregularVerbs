package com.example.irregularverbs.app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.SimpleAdapter;
import com.example.irregularverbs.fragments.myFragment;
import com.example.irregularverbs.fragments.mySecFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends FragmentActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    SimpleAdapter menuAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    SharedPreferences sp;
    Editor ed;
    Support ourFragment;
    SearchView mSearchView;
    boolean shouldGoInvisible;
    MenuItem searchItem;


    //fragments stuff
    int savepos;
    public static int fragpos0 = 0, fragpos1 = 0;
    Fragment fragment0, fragment1;


    public static DataBase db;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    String[] menuTitles = {"All", "History"};
    int[] menuIc = {R.drawable.ic_device_all,
            R.drawable.ic_device_access_sd_storage};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DataBase(this);
        mTitle = mDrawerTitle = getTitle();
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow,
                GravityCompat.START);
        setAdapter();
        mDrawerList.setAdapter(menuAdapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        mDrawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                shouldGoInvisible = false;
                searchItem.setVisible(true);
                closeOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mDrawerTitle);
                mSearchView.setQuery("", false);
                mSearchView.clearFocus();
                searchItem.setVisible(false);
                mSearchView.setIconified(true);
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        if (savedInstanceState == null) {
            selectItem(savepos);
        }

    }

    private void setAdapter() {


        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>(2);
        Map<String, Object> m;

        for (int j = 0; j < menuIc.length; j++) {
            m = new HashMap<String, Object>();
            m.put("icon", menuIc[j]);
            m.put("title", menuTitles[j]);
            data.add(m);
        }
        // массив имен атрибутов, из которых будут читаться данные
        String[] from = {"icon", "title"};
        // массив ID View-компонентов, в которые будут вставлять данные
        int[] to = {R.id.ivImgTitle, R.id.tvTitle};
        menuAdapter = new SimpleAdapter(this, data, R.layout.drawer_list,
                from, to);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem settings = menu.findItem(R.id.settings);
        settings.setIntent(new Intent(this, PrefActivity.class));
        searchItem = menu.findItem(R.id.action_search);
        mSearchView = (SearchView) searchItem.getActionView();
        mSearchView.setOnQueryTextListener(new OnQueryTextListener() {

            @Override
            public boolean onQueryTextChange(String newText) {
                String haduken = newText.toLowerCase();
                ourFragment.search(haduken);
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                String haduken = query.toLowerCase();
                ourFragment.search(haduken);
                mSearchView.clearFocus();
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        for (int i = 0; i < menu.size(); i++)
            menu.getItem(i).setVisible(!shouldGoInvisible);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        return super.onOptionsItemSelected(item);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        mDrawerList.setItemChecked(position, true);
        setTitle(menuTitles[position]);
        mDrawerLayout.closeDrawer(mDrawerList);
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (position) {
            case 0:
                fragment0 = new myFragment();
                savepos = 0;
                ourFragment = (Support) fragment0;
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment0).commit();
                break;
            case 1:
                fragment1 = new mySecFragment();
                savepos = 1;
                ourFragment = (Support) fragment1;
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment1).commit();
                break;
            default:
                break;
        }
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sp = getSharedPreferences("myPref", MODE_PRIVATE);
        ed = sp.edit();
        int clrH = sp.getInt("clearH", 0);
        if (clrH == 1) {
            clearHistoryList();
            ed.putInt("clearH", 0);
            ed.commit();
        }
        int clrF = sp.getInt("clearF", 0);
        if (clrF == 1) {
            ed.putInt("clearF", 0);
            ed.commit();
        }
    }


    private void clearHistoryList() {
        db.delete();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("type", savepos);
        outState.putInt("fragpos0", fragpos0);
        outState.putInt("fragpos1", fragpos1);
        if (savepos == 0)
            getSupportFragmentManager().putFragment(outState, "curfrag",
                    fragment0);
        else
            getSupportFragmentManager().putFragment(outState, "curfrag",
                    fragment1);


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        savepos = savedInstanceState.getInt("type");
        fragpos0 = savedInstanceState.getInt("fragpos0");
        fragpos1 = savedInstanceState.getInt("fragpos1");
        if (savepos == 0)
            fragment0 = getSupportFragmentManager().getFragment(
                    savedInstanceState, "curfrag");
        else
            fragment1 = getSupportFragmentManager().getFragment(
                    savedInstanceState, "curfrag");
        ourFragment = (Support) getSupportFragmentManager().getFragment(
                savedInstanceState, "curfrag");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

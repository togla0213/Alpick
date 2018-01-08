package yalantis.com.sidemenu.sample;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import yalantis.com.sidemenu.sample.fragment.HomeFragment;
import yalantis.com.sidemenu.sample.fragment.SearchFragment;
import yalantis.com.sidemenu.sample.fragment.beerFrag;
import yalantis.com.sidemenu.sample.fragment.cogFrag;
import yalantis.com.sidemenu.sample.fragment.sojuFrag;
import yalantis.com.sidemenu.sample.fragment.wineFrag;
import yalantis.com.sidemenu.sample.legacy.AwesomeActivity;
import yalantis.com.sidemenu.sample.legacy.CameraActivity;

public class BookActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        int[] icons = {
                R.drawable.sojucol,
                R.drawable.beercol,

                //fake center fragment, so that it creates place for raised center tab.
                R.drawable.tab_search,

                R.drawable.winecol,
                R.drawable.cognaccol
        };
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.main_tab_content);

        setupViewPager(viewPager);


        tabLayout.setupWithViewPager(viewPager);

        for (int i = 0; i < icons.length; i++) {
            tabLayout.getTabAt(i).setIcon(icons[i]);
        }
        tabLayout.getTabAt(0).select();
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.insertNewFragment(new sojuFrag());
        adapter.insertNewFragment(new beerFrag());

        //fake center fragment, so that it creates place for raised center tab.
        adapter.insertNewFragment(new SearchFragment());

        adapter.insertNewFragment(new wineFrag());
        adapter.insertNewFragment(new cogFrag());
        viewPager.setAdapter(adapter);
    }



    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }
        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void insertNewFragment(Fragment fragment) {
            mFragmentList.add(fragment);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = this.getMenuInflater();
        inflater.inflate(R.menu.main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_settings:
                Intent settingsIntent = new Intent(this, AwesomeActivity.class);
                startActivity(settingsIntent);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void openCameraActivity(View b)
    {
        Intent intent = new Intent(BookActivity.this, CameraActivity.class);
        startActivity(intent);
    }

}
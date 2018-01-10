package yalantis.com.sidemenu.sample;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.sample.fragment.ContentFragment;
import yalantis.com.sidemenu.sample.fragment.HomeFragment;
import yalantis.com.sidemenu.sample.fragment.SearchFragment;
import yalantis.com.sidemenu.sample.fragment.beerFrag;
import yalantis.com.sidemenu.sample.fragment.cogFrag;
import yalantis.com.sidemenu.sample.fragment.sojuFrag;
import yalantis.com.sidemenu.sample.fragment.wineFrag;
import yalantis.com.sidemenu.sample.legacy.AwesomeActivity;
import yalantis.com.sidemenu.sample.legacy.CameraActivity;
import yalantis.com.sidemenu.util.ViewAnimator;

public class BookActivity extends AppCompatActivity implements ViewAnimator.ViewAnimatorListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> list = new ArrayList<>();
    private ContentFragment contentFragment;
    private ViewAnimator viewAnimator;
    private int res = R.drawable.content_music;
    private LinearLayout linearLayout;

    ImageButton camera = null;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        userId = getIntent().getStringExtra("id");

        contentFragment = ContentFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.book_frame, contentFragment)
                .commit();
        drawerLayout = (DrawerLayout) findViewById(R.id.book_drawer_layout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        linearLayout = (LinearLayout) findViewById(R.id.book_left_drawer);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });


        setActionBar();
        createMenuList();
        viewAnimator = new ViewAnimator<>(this, list, contentFragment, drawerLayout, this);

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

    private void createMenuList() {
        SlideMenuItem menuItem0 = new SlideMenuItem(ContentFragment.CLOSE, R.drawable.icn_close);
        list.add(menuItem0);
        SlideMenuItem menuItem = new SlideMenuItem(ContentFragment.HOME, R.drawable.home3);
        list.add(menuItem);
        SlideMenuItem menuItem2 = new SlideMenuItem(ContentFragment.TEST, R.drawable.test3);
        list.add(menuItem2);
        SlideMenuItem menuItem3 = new SlideMenuItem(ContentFragment.THEME, R.drawable.theme3);
        list.add(menuItem3);
        SlideMenuItem menuItem4 = new SlideMenuItem(ContentFragment.BOOK, R.drawable.collection3);
        list.add(menuItem4);
        SlideMenuItem menuItem5 = new SlideMenuItem(ContentFragment.DIC, R.drawable.dictionary3);
        list.add(menuItem5);
        SlideMenuItem menuItem6 = new SlideMenuItem(ContentFragment.MYPAGE, R.drawable.mypage3);
        list.add(menuItem6);
    }


    private void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.book_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                linearLayout.removeAllViews();
                linearLayout.invalidate();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && linearLayout.getChildCount() == 0)
                    viewAnimator.showMenuContent();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    private ScreenShotable replaceFragment(ScreenShotable screenShotable, int topPosition) {
        this.res = this.res == R.drawable.content_music ? R.drawable.content_films : R.drawable.content_music;
        View view = findViewById(R.id.book_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);

        //findViewById(R.id.content_overlay).setBackgroundDrawable(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();
        ContentFragment contentFragment = ContentFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.book_frame, contentFragment).commit();
        return contentFragment;
    }

    private ScreenShotable replaceHome(ScreenShotable screenShotable, int topPosition) {
        Intent it_main = new Intent(BookActivity.this, HomeActivity.class);
        it_main.putExtra("id", userId);
        startActivity(it_main);
        finish();

        return screenShotable;
    }

    private ScreenShotable replaceTest(ScreenShotable screenShotable, int topPosition) {
        Intent it_main = new Intent(BookActivity.this, TestActivity.class);
        it_main.putExtra("id", userId);
        startActivity(it_main);
        finish();

        return screenShotable;
    }

    private ScreenShotable replaceTheme(ScreenShotable screenShotable, int topPosition) {
        Intent it_main = new Intent(BookActivity.this, ThemeActivity.class);
        it_main.putExtra("id", userId);
        startActivity(it_main);
        finish();

        return screenShotable;
    }

    private ScreenShotable replaceBook(ScreenShotable screenShotable, int topPosition) {
        Intent it_main = new Intent(BookActivity.this, BookActivity.class);
        startActivity(it_main);

        return screenShotable;
    }

    private ScreenShotable replaceDic(ScreenShotable screenShotable, int topPosition) {
        Intent it_main = new Intent(BookActivity.this, DicActivity.class);
        it_main.putExtra("id", userId);
        startActivity(it_main);
        finish();

        return screenShotable;
    }

    private ScreenShotable replaceMypage(ScreenShotable screenShotable, int topPosition) {
        Intent it_main = new Intent(BookActivity.this, MypageActivity.class);
        it_main.putExtra("id", userId);
        startActivity(it_main);
        finish();

        return screenShotable;
    }

    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        Log.v("MenuItem : ", slideMenuItem.getName());
        switch (slideMenuItem.getName()) {
            case ContentFragment.CLOSE:
            case ContentFragment.BOOK:
                return screenShotable;
            case ContentFragment.HOME:
                return replaceHome(screenShotable, position);
            case ContentFragment.TEST:
                return replaceTest(screenShotable, position);
            case ContentFragment.DIC:
                return replaceDic(screenShotable, position);
            case ContentFragment.THEME:
                return replaceTheme(screenShotable, position);
            case ContentFragment.MYPAGE:
                return replaceMypage(screenShotable, position);
            default:
                return replaceFragment(screenShotable, position);
        }
    }

    @Override
    public void disableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(false);
    }

    @Override
    public void enableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.closeDrawers();
    }

    @Override
    public void addViewToContainer(View view) {
        linearLayout.addView(view);
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
        Intent intent = new Intent(BookActivity.this, barcode.class);
        startActivity(intent);
    }

}
package yalantis.com.sidemenu.sample;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.sample.fragment.ContentFragment;
import yalantis.com.sidemenu.util.ViewAnimator;

public class TestActivity extends AppCompatActivity implements ViewAnimator.ViewAnimatorListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> list = new ArrayList<>();
    private ContentFragment contentFragment;
    private ViewAnimator viewAnimator;
    private int res = R.drawable.content_music;
    private LinearLayout linearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        contentFragment = ContentFragment.newInstance(R.drawable.content_music);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.test_frame, contentFragment)
                .commit();
        drawerLayout = (DrawerLayout) findViewById(R.id.test_drawer_layout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        linearLayout = (LinearLayout) findViewById(R.id.test_left_drawer);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });


        setActionBar();
        createMenuList();
        viewAnimator = new ViewAnimator<>(this, list, contentFragment, drawerLayout, this);
    }

    private void createMenuList() {
        SlideMenuItem menuItem0 = new SlideMenuItem(ContentFragment.CLOSE, R.drawable.icn_close);
        list.add(menuItem0);
        SlideMenuItem menuItem = new SlideMenuItem(ContentFragment.HOME, R.drawable.home);
        list.add(menuItem);
        SlideMenuItem menuItem2 = new SlideMenuItem(ContentFragment.TEST, R.drawable.test);
        list.add(menuItem2);
        SlideMenuItem menuItem3 = new SlideMenuItem(ContentFragment.THEME, R.drawable.theme);
        list.add(menuItem3);
        SlideMenuItem menuItem4 = new SlideMenuItem(ContentFragment.BOOK, R.drawable.dictionary);
        list.add(menuItem4);
        SlideMenuItem menuItem5 = new SlideMenuItem(ContentFragment.DIC, R.drawable.idea);
        list.add(menuItem5);
        SlideMenuItem menuItem6 = new SlideMenuItem(ContentFragment.MYPAGE, R.drawable.mypage);
        list.add(menuItem6);
    }


    private void setActionBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.test_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Alpick");
        getSupportActionBar().setIcon(R.drawable.alpick_icon_re);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private ScreenShotable replaceFragment(ScreenShotable screenShotable, int topPosition) {
        this.res = this.res == R.drawable.content_music ? R.drawable.content_films : R.drawable.content_music;
        View view = findViewById(R.id.test_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);

        //findViewById(R.id.content_overlay).setBackgroundDrawable(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();
        ContentFragment contentFragment = ContentFragment.newInstance(this.res);
        getSupportFragmentManager().beginTransaction().replace(R.id.test_frame, contentFragment).commit();
        return contentFragment;
    }

    private ScreenShotable replaceHome(ScreenShotable screenShotable, int topPosition) {
        Intent it_main = new Intent(TestActivity.this, HomeActivity.class);
        it_main.addFlags(it_main.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(it_main);

        return screenShotable;
    }

    private ScreenShotable replaceTest(ScreenShotable screenShotable, int topPosition) {
        Intent it_main = new Intent(TestActivity.this, TestActivity.class);
        startActivity(it_main);

        return screenShotable;
    }

    private ScreenShotable replaceTheme(ScreenShotable screenShotable, int topPosition) {
        Intent it_main = new Intent(TestActivity.this, ThemeActivity.class);
        it_main.addFlags(it_main.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(it_main);

        return screenShotable;
    }

    private ScreenShotable replaceBook(ScreenShotable screenShotable, int topPosition) {
        Intent it_main = new Intent(TestActivity.this, BookActivity.class);
        it_main.addFlags(it_main.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(it_main);

        return screenShotable;
    }

    private ScreenShotable replaceDic(ScreenShotable screenShotable, int topPosition) {
        Intent it_main = new Intent(TestActivity.this, DicActivity.class);
        it_main.addFlags(it_main.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(it_main);

        return screenShotable;
    }

    private ScreenShotable replaceMypage(ScreenShotable screenShotable, int topPosition) {
        Intent it_main = new Intent(TestActivity.this, MypageActivity.class);
        it_main.addFlags(it_main.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(it_main);

        return screenShotable;
    }

    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        Log.v("MenuItem : ", slideMenuItem.getName());
        switch (slideMenuItem.getName()) {
            case ContentFragment.CLOSE:
            case ContentFragment.TEST:
                return screenShotable;
            case ContentFragment.HOME:
                return replaceHome(screenShotable, position);
            case ContentFragment.THEME:
                return replaceTheme(screenShotable, position);
            case ContentFragment.BOOK:
                return replaceBook(screenShotable, position);
            case ContentFragment.DIC:
                return replaceDic(screenShotable, position);
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
}

package yalantis.com.sidemenu.sample;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.sample.com.VO.Al_dictVO;
import yalantis.com.sidemenu.sample.com.VO.Al_infoVO;
import yalantis.com.sidemenu.sample.fragment.ContentFragment;
import yalantis.com.sidemenu.util.ViewAnimator;

public class TestStartActivity extends AppCompatActivity implements ViewAnimator.ViewAnimatorListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> list = new ArrayList<>();
    private ContentFragment contentFragment;
    private ViewAnimator viewAnimator;
    private int res = R.drawable.content_music;
    private LinearLayout linearLayout;

    private Button btn_choice1;
    private Button btn_choice2;

    private int choice1[] = {R.drawable.mbti_ei_e1, R.drawable.mbti_ei_e2, R.drawable.mbti_ei_e3,
                                         R.drawable.mbti_sn_n1, R.drawable.mbti_sn_n2, R.drawable.mbti_sn_n3,
                                         R.drawable.mbti_tf_f1, R.drawable.mbti_tf_f2, R.drawable.mbti_tf_f3,
                                         R.drawable.mbti_jp_j1, R.drawable.mbti_jp_j2, R.drawable.mbti_jp_j3};
    private int choice2[] = {R.drawable.mbti_ei_i1, R.drawable.mbti_ei_i2, R.drawable.mbti_ei_i3,
                                         R.drawable.mbti_sn_s1, R.drawable.mbti_sn_s2, R.drawable.mbti_sn_s3,
                                         R.drawable.mbti_tf_t1, R.drawable.mbti_tf_t2, R.drawable.mbti_tf_t3,
                                         R.drawable.mbti_jp_p1, R.drawable.mbti_jp_p2, R.drawable.mbti_jp_p3};

    private int i = 0;
    private StringBuffer answer = null;
    private String userId;
    private String user_type;
    static String anl_res;
    static String pro_no;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_start);

        userId = getIntent().getStringExtra("id");

        contentFragment = ContentFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.test_start_frame, contentFragment)
                .commit();
        drawerLayout = (DrawerLayout) findViewById(R.id.test_start_drawer_layout);
        drawerLayout.setScrimColor(Color.TRANSPARENT);
        linearLayout = (LinearLayout) findViewById(R.id.test_start_left_drawer);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });

        setActionBar();
        createMenuList();
        viewAnimator = new ViewAnimator<>(this, list, contentFragment, drawerLayout, this);

        btn_choice1 = (Button)findViewById(R.id.test_start_btn1);
        btn_choice2 = (Button)findViewById(R.id.test_start_btn2);
        answer = new StringBuffer();

        btn_choice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                if(i<=3) {
                    answer.append("E");
                } else if(i<=6){
                    answer.append("N");
                } else if(i<=9) {
                    answer.append("F");
                } else {
                    answer.append("J");
                }
                if(i < choice1.length) {
                    btn_choice1.setBackgroundResource(choice1[i]);
                    btn_choice2.setBackgroundResource(choice2[i]);
                } else if (i >= choice1.length) {
                    user_type = testing(answer);

                    if(user_type != null) {
                        try {
                            new HttpUtil().execute();

                            Log.v("testing result : ", pro_no);

                            Intent it_main = new Intent(TestStartActivity.this, TestResultActivity.class);
                            startActivity(it_main);
                            finish();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    Intent it_main = new Intent(TestStartActivity.this, TestResultActivity.class);
                    it_main.putExtra("id", userId);
                    it_main.putExtra("pro_no", pro_no);
                    it_main.putExtra("user_type", answer.toString());
                    startActivity(it_main);
                    finish();
                }

            }
        });

        btn_choice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i++;
                if(i<=3) {
                    answer.append("I");
                } else if(i<=6){
                    answer.append("S");
                } else if(i<=9) {
                    answer.append("T");
                } else {
                    answer.append("P");
                }
                if(i < choice1.length) {
                    btn_choice1.setBackgroundResource(choice1[i]);
                    btn_choice2.setBackgroundResource(choice2[i]);
                } else if (i >= choice1.length) {
                    user_type = testing(answer);

                    if(user_type != null) {
                        try {
                            new HttpUtil().execute();

                            Log.v("testing result : ", pro_no);

                            Intent it_main = new Intent(TestStartActivity.this, TestResultActivity.class);
                            it_main.putExtra("id", userId);
                            it_main.putExtra("pro_no", pro_no);
                            it_main.putExtra("user_type", answer.toString());
                            startActivity(it_main);
                            finish();

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        });
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.test_start_toolbar);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private ScreenShotable replaceFragment(ScreenShotable screenShotable, int topPosition) {
        this.res = this.res == R.drawable.content_music ? R.drawable.content_films : R.drawable.content_music;
        View view = findViewById(R.id.test_start_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);

        //findViewById(R.id.content_overlay).setBackgroundDrawable(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();
        ContentFragment contentFragment = ContentFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.test_start_frame, contentFragment).commit();
        return contentFragment;
    }

    private ScreenShotable replaceHome(ScreenShotable screenShotable, int topPosition) {
        Intent it_main = new Intent(TestStartActivity.this, HomeActivity.class);
        it_main.putExtra("id", userId);
        startActivity(it_main);
        finish();

        return screenShotable;
    }

    private ScreenShotable replaceTest(ScreenShotable screenShotable, int topPosition) {
        Intent it_main = new Intent(TestStartActivity.this, TestActivity.class);
        it_main.putExtra("id", userId);
        startActivity(it_main);
        finish();

        return screenShotable;
    }

    private ScreenShotable replaceTheme(ScreenShotable screenShotable, int topPosition) {
        Intent it_main = new Intent(TestStartActivity.this, ThemeActivity.class);
        it_main.putExtra("id", userId);
        startActivity(it_main);
        finish();

        return screenShotable;
    }

    private ScreenShotable replaceBook(ScreenShotable screenShotable, int topPosition) {
        Intent it_main = new Intent(TestStartActivity.this, BookActivity.class);
        it_main.putExtra("id", userId);
        startActivity(it_main);
        finish();

        return screenShotable;
    }

    private ScreenShotable replaceDic(ScreenShotable screenShotable, int topPosition) {
        Intent it_main = new Intent(TestStartActivity.this, DicActivity.class);
        it_main.putExtra("id", userId);
        startActivity(it_main);
        finish();

        return screenShotable;
    }

    private ScreenShotable replaceMypage(ScreenShotable screenShotable, int topPosition) {
        Intent it_main = new Intent(TestStartActivity.this, MypageActivity.class);
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
                return screenShotable;
            case ContentFragment.TEST:
                return replaceTest(screenShotable, position);
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

    public String testing(StringBuffer str) {
        String temp = str.toString();

        String t_result = "";

        if(getCharNumber(temp, 'E') > getCharNumber(temp, 'I')){
            t_result += 'E';
        } else {
            t_result += 'I';
        }
        if(getCharNumber(temp, 'S') > getCharNumber(temp, 'N')){
            t_result += 'S';
        } else {
            t_result += 'N';
        }
        if(getCharNumber(temp, 'T') > getCharNumber(temp, 'F')){
            t_result += 'T';
        } else {
            t_result += 'F';
        }
        if(getCharNumber(temp, 'J') > getCharNumber(temp, 'P')){
            t_result += 'J';
        } else {
            t_result += 'P';
        }

        return t_result;
    }

    public int getCharNumber(String str, char c) {
        int count = 0;
        for(int i=0;i<str.length();i++) {
            if(str.charAt(i) == c)
                count++;
        }
        return count;
    }

    public class HttpUtil extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            StringBuffer response = null;

            try {

                String url = "http://knjas.or.kr:8084/alpick/AnalysisService?id=" + userId + "&user_type="+user_type;
                URL obj = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) obj.openConnection();

                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);

                conn.connect();

                int retCode = conn.getResponseCode();

                InputStream is = conn.getInputStream();
                BufferedReader bfr = new BufferedReader(new InputStreamReader(is));
                String line;
                response = new StringBuffer();

                while ((line = bfr.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
                bfr.close();

            } catch (Exception e) {
                e.printStackTrace();
            }



            return response.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            //res = response.toString();   //여기로 JSON값이 들어옴

            String[] temp = result.split("---");

            pro_no = temp[1];
            anl_res = temp[0];

        }




    }

}


package yalantis.com.sidemenu.sample;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;

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

import yalantis.com.sidemenu.sample.com.VO.Al_dictVO;
import yalantis.com.sidemenu.sample.com.VO.MemberVO;

public class GradeCognacActivity extends AppCompatActivity {
    int[] rating = {R.id.bar_cognac01, R.id.bar_cognac02, R.id.bar_cognac03, R.id.bar_cognac04, R.id.bar_cognac05, R.id.bar_cognac06, R.id.bar_cognac07};
    String[] soju_no = {"02_0001","02_0002","02_0003","02_0004","02_0005","02_0006","02_0007"};
    String[] beer_no = {"01_0001","01_0002","01_0003","01_0004","01_0005","01_0006","01_0007"};
    String[] wine_no = {"03_0001","03_0002","03_0003","03_0004","03_0005","03_0006","03_0007"};
    String[] cognac_no = {"04_0001","04_0002","04_0003","04_0004","04_0005","04_0006","04_0007"};
    float[] ratingScore = new float[rating.length];
    float[] sojuScore;
    float[] beerScore;
    float[] wineScore;
    String user_id;

    RatingBar[] mRating = new RatingBar[rating.length];

    ArrayList<Al_dictVO> dic_list = null;
    MemberVO mvo = null;

    Button btn_next;
    Button btn_pre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_cognac);

        dic_list = new ArrayList<Al_dictVO>();

        Intent sojuIntent = getIntent();
        sojuScore = sojuIntent.getFloatArrayExtra("sojuScore");
        beerScore = sojuIntent.getFloatArrayExtra("beerScore");
        wineScore = sojuIntent.getFloatArrayExtra("wineScore");
        user_id = sojuIntent.getStringExtra("id");

        for(int i = 0 ; i < mRating.length; i++) {
            mRating[i] = (RatingBar)findViewById(rating[i]);
        }

        btn_pre = (Button)findViewById(R.id.cognac_pre);
        btn_next = (Button)findViewById(R.id.cognac_next);

        for(int i = 0 ; i < mRating.length; i++) {
            final int index = i;
            mRating[i].setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) { // ratingbar :
                    ratingScore[index] = rating;
                }

            });
        }

        for(int i = 0; i<rating.length; i++) {
            dic_list.add(new Al_dictVO(beer_no[i],user_id,beerScore[i]+""));
            dic_list.add(new Al_dictVO(soju_no[i],user_id,sojuScore[i]+""));
            dic_list.add(new Al_dictVO(wine_no[i],user_id,wineScore[i]+""));
            dic_list.add(new Al_dictVO(cognac_no[i],user_id,ratingScore[i]+""));
        }

        btn_pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    new HttpUtil().execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(dic_list != null) {
                Intent intent = new Intent(GradeCognacActivity.this, HomeActivity.class);
                intent.putExtra("id", user_id);
                intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);}
            }
        });
    }

    public class HttpUtil extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            StringBuffer response = null;

            Gson gson;

            try {
                gson = new Gson();

                String data = gson.toJson(dic_list);

                String url = "http://knjas.or.kr:8084/alpick/FisrtDict?id=" + user_id +"&dic_list="+data;
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

                Log.v("로그확인 : ", "확인됨11");

                while ((line = bfr.readLine()) != null) {
                    Log.v("로그확인 : ", "확인됨");
                    response.append(line);
                    response.append('\r');
                }
                bfr.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

            Log.v("Response: ", response.toString());

            return response.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            //res = response.toString();   //여기로 JSON값이 들
            Gson gson = new Gson();
            Type type2 = new TypeToken<ArrayList<Al_dictVO>>() {}.getType();

            String[] temp = result.split("---");

            Log.v("temp length : ", temp.length + "");
            Log.v("Result : ", result);

            Log.v("dic_list", temp[1]);
            Log.v("member : ", temp[2]);

            dic_list = gson.fromJson(temp[1], type2);
            mvo = gson.fromJson(temp[2], new TypeToken<MemberVO>(){}.getType());

        }
    }
}

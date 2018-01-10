package yalantis.com.sidemenu.sample;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
import yalantis.com.sidemenu.sample.com.VO.Al_infoVO;
import yalantis.com.sidemenu.sample.com.VO.MemberVO;

public class SignupActivity extends AppCompatActivity {
    private EditText id;
    private EditText pw;
    private EditText nick;
    private EditText year;
    private Button join;

    String user_id = null;
    String user_pw = null;
    String user_nick = null;
    String user_year = null;

    String rr= null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        id = (EditText)findViewById(R.id.join_edt_id);
        pw = (EditText)findViewById(R.id.join_edt_pw);
        nick = (EditText)findViewById(R.id.join_edt_nick);
        year = (EditText)findViewById(R.id.join_edt_year);

        join = (Button)findViewById(R.id.login_join);

        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                user_id = id.getText().toString();
                user_pw = pw.getText().toString();
                user_nick = nick.getText().toString();
                user_year = year.getText().toString();



                try {

                    new HttpUtil().execute();

                    if(rr != null) {

                        Intent intent = new Intent(SignupActivity.this, GradeSojuActivity.class);
                        intent.putExtra("id", user_id);
                        startActivity(intent);
                        finish();

                    }
                } catch(Exception e) {
                    e.printStackTrace();
                }


            }
        });
    }


    public class HttpUtil extends AsyncTask<String, Void, String> {

        String result = null;

        ArrayList<Al_infoVO> info_list = null;
        ArrayList<Al_dictVO> dic_list = null;
        MemberVO mvo = null;

        @Override
        protected String doInBackground(String... strings) {

            StringBuffer response = null;

            try {
                String data_id = URLEncoder.encode(user_id, "euc-kr");
                String data_pw = URLEncoder.encode(user_pw, "euc-kr");
                String data_nick = URLEncoder.encode(user_nick,"euc-kr");
                String data_year = URLEncoder.encode(user_year,"euc-kr");

                String url = "http://knjas.or.kr:8084/alpick/JoinService?id=" + data_id + "&pw=" + data_pw+"&nickname="+data_nick+"&year="+data_year;
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

            }

            return response.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            //res = response.toString();   //여기로 JSON값이 들어옴

            rr = result;

        }

    }
}

package yalantis.com.sidemenu.sample;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
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
import java.lang.String;

import yalantis.com.sidemenu.sample.com.VO.Al_dictVO;
import yalantis.com.sidemenu.sample.com.VO.Al_infoVO;

public class LoginActivity extends AppCompatActivity {
    private EditText Id; // Button1 을 통해 서버로 보낼 데이터1
    private EditText Pw; // Button1 을 통해 서버로 보낼 데이터2
    private Button Login; //Button1 > 서버 통신
    private Button Join; //Button 2 > Intent

    String user_id = null;
    String user_pw = null;

    ArrayList<Al_infoVO> info_list = null;
    ArrayList<Al_dictVO> dic_list = null;
    String fjresponse = null;
    String nick = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Id = (EditText)findViewById(R.id.login_edt_id);
        Pw = (EditText)findViewById(R.id.login_edt_pw);
        Login = (Button)findViewById(R.id.login);
        Join = (Button)findViewById(R.id.login_join);

        Join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String response = "-1";

                user_id = Id.getText().toString();
                user_pw = Pw.getText().toString();

                try {
                    new HttpUtil().execute();
                } catch(Exception e) {
                    e.printStackTrace();
                }


                if(info_list!=null) {
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });


    }

    public class HttpUtil extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {

            StringBuffer response = null;

            try {
                String data_id = URLEncoder.encode(user_id, "euc-kr");
                String data_pw = URLEncoder.encode(user_pw, "euc-kr");

                String url = "http://knjas.or.kr:8084/alpick/LoginService?id=" + data_id + "&pw=" + data_pw;
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

            Log.v("onPostExecute : ", "ㅇㅇ");

            Gson gson = new Gson();
            Type type1 = new TypeToken<ArrayList<Al_infoVO>>() {}.getType();
            Type type2 = new TypeToken<ArrayList<Al_dictVO>>() {}.getType();

            String[] temp = result.toString().split("---");

            Log.v("info_list",temp[1]);
            Log.v("dic_list", temp[2]);

            info_list = gson.fromJson(temp[1], type1);
            dic_list = gson.fromJson(temp[2], type2);

        }

    }

}
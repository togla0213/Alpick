package yalantis.com.sidemenu.sample;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

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
import yalantis.com.sidemenu.sample.com.VO.MemberVO;

public class LoginActivity extends AppCompatActivity {
    private EditText Id; // Button1 을 통해 서버로 보낼 데이터1
    private EditText Pw; // Button1 을 통해 서버로 보낼 데이터2
    private Button Login; //Button1 > 서버 통신
    private Button Join; //Button 2 > Intent

    String user_id = null;
    String user_pw = null;
    //String loginId, loginPwd;

    ArrayList<Al_infoVO> info_list = null;
    ArrayList<Al_dictVO> dic_list = null;
    MemberVO mvo = null;
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

//        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
//        //처음에는 SharedPreferences에 아무런 정보도 없으므로 값을 저장할 키들을 생성한다.
//        // getString의 첫 번째 인자는 저장될 키, 두 번쨰 인자는 값입니다.
//        // 첨엔 값이 없으므로 키값은 원하는 것으로 하시고 값을 null을 줍니다.
//        loginId = auto.getString("inputId",null);
//        loginPwd = auto.getString("inputPwd",null);
//
//        //MainActivity로 들어왔을 때 loginId와 loginPwd값을 가져와서 null이 아니면 값을 가져와 id가 부르곰이고 pwd가 네이버 이면 자동적으로 액티비티 이동.
//        if(loginId !=null && loginPwd != null) {
//            if(loginId.equals("1") && loginPwd.equals("1")) {
//                Toast.makeText(LoginActivity.this, loginId +"님 자동로그인되었습니다", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        }

        //id와 pwd가 null이면 Mainactivity가 보여짐.
        //else if(loginId == null && loginPwd == null){

            Login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    user_id = Id.getText().toString();
                    user_pw = Pw.getText().toString();

                    try {
                        new HttpUtil().execute();
                    } catch(Exception e) {
                        e.printStackTrace();
                    }

//                    Log.v("userID : ", user_id);
//                    Log.v("userPW : ", user_pw);
//                    Log.v("DBPW : ", mvo.getPw());
//                    Log.v("DBID : ", mvo.getId());

                    if(info_list!=null) {

//                        if (user_id.equals(mvo.getId()) && user_pw.equals(mvo.getPw())) {
//                            SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
//                            //아이디가 '부르곰'이고 비밀번호가 '네이버'일 경우 SharedPreferences.Editor를 통해
//                            //auto의 loginId와 loginPwd에 값을 저장해 줍니다.
//                            SharedPreferences.Editor autoLogin = auto.edit();
//                            autoLogin.putString("inputId", user_id);
//                            autoLogin.putString("inputPwd", user_pw);
//                            //꼭 commit()을 해줘야 값이 저장됩니다 ㅎㅎ
//                            autoLogin.commit();
//                            Toast.makeText(LoginActivity.this, user_id+"님 환영합니다.", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                            intent.putExtra("id", user_id);
//                            startActivity(intent);
//                            finish();
//                        }
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        intent.putExtra("id", user_id);
                        startActivity(intent);
                        finish();
                    }
                }
            });
       // }
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
                e.printStackTrace();
            }

            Log.v("result", response.toString());

            return response.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            //res = response.toString();   //여기로 JSON값이 들어옴

            Gson gson = new Gson();
            Type type1 = new TypeToken<ArrayList<Al_infoVO>>() {}.getType();
            Type type2 = new TypeToken<ArrayList<Al_dictVO>>() {}.getType();

            String[] temp = result.toString().split("---");

            Log.v("temp length : ", temp.length + "");
            Log.v("info_list",temp[1]);
            Log.v("dic_list", temp[2]);
            Log.v("member : ", temp[3]);

            info_list = gson.fromJson(temp[1], type1);
            dic_list = gson.fromJson(temp[2], type2);
            mvo = gson.fromJson(temp[3], new TypeToken<MemberVO>(){}.getType());

        }

        public ArrayList<Al_infoVO> getInfo_list(){
            return info_list;
        }

        public ArrayList<Al_dictVO> getDic_list() {
            return dic_list;
        }

        public MemberVO getMvo() {
            return mvo;
        }


    }

}
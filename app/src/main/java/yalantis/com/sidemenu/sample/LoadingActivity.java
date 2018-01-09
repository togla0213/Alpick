package yalantis.com.sidemenu.sample;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LoadingActivity extends AppCompatActivity {

    Thread thread = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        thread = new Thread(new timeThread());
        thread.start();
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if(msg.arg1 == 1) {
                Intent it_next = new Intent(LoadingActivity.this, LoginActivity.class);
                startActivity(it_next);
                finish();
            }
        }
    };

    public class timeThread implements Runnable{
        @Override
        public void run() {
            for(int i = 0; i < 2; i++) {
                Message msg = new Message();
                msg.arg1 = i;
                handler.sendMessage(msg);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

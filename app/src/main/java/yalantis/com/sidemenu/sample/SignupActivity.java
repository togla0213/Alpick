package yalantis.com.sidemenu.sample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SignupActivity extends AppCompatActivity {
    private EditText id;
    private EditText pw;
    private EditText nick;
    private EditText year;
    private Button join;

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
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}

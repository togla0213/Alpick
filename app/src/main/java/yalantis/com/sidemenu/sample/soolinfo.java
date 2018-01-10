package yalantis.com.sidemenu.sample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class soolinfo extends AppCompatActivity {
    Button okay = null;
    ImageView img = null;
    TextView name = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soolinfo);
        okay = (Button) findViewById(R.id.okay);
        img= (ImageView)findViewById(R.id.img);
        name=(TextView)findViewById(R.id.soolname);
        Intent getintent=getIntent();
        String code = getintent.getStringExtra("result");

        if(code.equals("8801152135235")){
            name.setText("처음처럼");
            img.setImageResource(R.drawable.a02_0006);
        }else if (code.equals("8801858044701")){
            name.setText("CASS/fresh");
            img.setImageResource(R.drawable.a01_0001);
        }

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(soolinfo.this, BookActivity.class);
                startActivity(intent);
            }
        });

    }
}

package yalantis.com.sidemenu.sample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

public class GradeSojuActivity extends AppCompatActivity {
    int[] rating = {R.id.bar_soju01, R.id.bar_soju02, R.id.bar_soju03, R.id.bar_soju04, R.id.bar_soju05, R.id.bar_soju06, R.id.bar_soju07};
    float[] ratingScore = new float[rating.length];
    String user_id;

    RatingBar[] mRating = new RatingBar[rating.length];
    Button btn_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_soju);

        user_id = getIntent().getStringExtra("id");

        for(int i = 0 ; i < mRating.length; i++) {
            mRating[i] = (RatingBar)findViewById(rating[i]);
        }
        btn_next = (Button)findViewById(R.id.soju_next);

        for(int i = 0 ; i < mRating.length; i++) {
            final int index = i;
            mRating[i].setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) { // ratingbar :
                    ratingScore[index] = rating;
                }

            });
        }

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GradeSojuActivity.this, GradeBeerActivity.class);
                intent.putExtra("id", user_id);
                intent.putExtra("sojuScore", ratingScore);
                startActivity(intent);
            }
        });

    }
}

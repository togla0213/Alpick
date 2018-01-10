package yalantis.com.sidemenu.sample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RatingBar;

public class GradeBeerActivity extends AppCompatActivity {
    int[] rating = {R.id.bar_beer01, R.id.bar_beer02, R.id.bar_beer03, R.id.bar_beer04, R.id.bar_beer05, R.id.bar_beer06, R.id.bar_beer07};
    float[] ratingScore = new float[rating.length];
    float[] sojuScore;
    String user_id;

    RatingBar[] mRating = new RatingBar[rating.length];

    Button btn_next;
    Button btn_pre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grade_beer);

        Intent sojuIntent = getIntent();
        sojuScore = sojuIntent.getFloatArrayExtra("sojuScore");
        user_id = sojuIntent.getStringExtra("id");

        for(int i = 0 ; i < mRating.length; i++) {
            mRating[i] = (RatingBar)findViewById(rating[i]);
        }

        btn_pre = (Button)findViewById(R.id.beer_pre);
        btn_next = (Button)findViewById(R.id.beer_next);

        for(int i = 0 ; i < mRating.length; i++) {
            final int index = i;
            mRating[i].setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {

                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) { // ratingbar :
                    ratingScore[index] = rating;
                }

            });
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
                Intent intent = new Intent(GradeBeerActivity.this, GradeWineActivity.class);
                intent.putExtra("id", user_id);
                intent.putExtra("sojuScore", sojuScore);
                intent.putExtra("beerScore", ratingScore);
                startActivity(intent);
            }
        });
    }
}

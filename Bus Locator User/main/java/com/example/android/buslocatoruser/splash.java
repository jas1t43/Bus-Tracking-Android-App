package com.example.android.buslocatoruser;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class splash extends AppCompatActivity {
ImageView im;
    TextView bus_txt;
    TextView loc_txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        im= (ImageView) findViewById(R.id.im_view);
        Animation img= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_left);
        im.setAnimation(img);

        Animation bus_anim= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.push_down_in);
        bus_txt=(TextView)findViewById(R.id.bus_text);
        bus_txt.setAnimation(bus_anim);
        bus_anim.setDuration(1000);
        bus_anim.setStartOffset(2000);
        Animation loc_anim= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.push_up_in);
        loc_txt=(TextView)findViewById(R.id.locator_text);
        loc_txt.setAnimation(loc_anim);
        loc_anim.setDuration(1000);
        loc_anim.setStartOffset(2000);

        loc_anim.setAnimationListener(new Animation.AnimationListener() {
                                           @Override
                                           public void onAnimationStart(Animation animation) {

                                           }

                                           @Override
                                           public void onAnimationEnd(Animation animation) {

                                            finish();
                                               startActivity(new Intent(
                                                       getApplicationContext(),MainActivity.class
                                               ));
                                           }

                                           @Override
                                           public void onAnimationRepeat(Animation animation) {

                                           }
                                       }
        );

    }
}

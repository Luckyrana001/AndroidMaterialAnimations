package com.example.ranalucky.androidmaterialanimations;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    View container;
    boolean playAnimations = true;

    private View welcome,profilePic,loginBtn ;EditText emailEt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setLayout();


    }


    /*alpha change animation on first time window will get focus */
    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus)
        {
            showContainer();
           // ShowOtherItemsAnimation();

            playAnimations = false;
        }

    }

    private void showContainer() {
        container.animate().alpha(1f).setDuration(1000)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                       /* This listener will start animation after fade in/ alpha animation completes
                        it will increases the clarity of animation.*/
                        super.onAnimationEnd(animation);
                        ShowOtherItemsAnimation();

                    }
                });
    }



    private void ShowOtherItemsAnimation() {
        float startWelcomeX = 0 - welcome.getWidth();
        float endWelcome = welcome.getX();
        ObjectAnimator welcomeAnimator = ObjectAnimator.ofFloat(welcome,View.X , startWelcomeX,endWelcome);
        welcomeAnimator.setDuration(1000);

        /*a listener to set welcome text visible only after animation start*/
        welcomeAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                welcome.setVisibility(View.VISIBLE);

            }
        });
       // welcomeAnimator.start();


        /*profile pic animation*/
        PropertyValuesHolder scaleXholder = PropertyValuesHolder.ofFloat(View.SCALE_X,1f);
        PropertyValuesHolder scaleYholder = PropertyValuesHolder.ofFloat(View.SCALE_Y,1f);

        ObjectAnimator animateProfilePic = ObjectAnimator.ofPropertyValuesHolder(profilePic, scaleXholder,scaleYholder);
        animateProfilePic.setDuration(1000);
        //animateProfilePic.start();

        /*login button animation*/
        ObjectAnimator loginBtnAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(this,R.animator.sign_in_anim);
        loginBtnAnimator.setTarget(loginBtn);
       // loginBtnAnimator.start();


        /*this part will handle the sequencing of animations*/
        AnimatorSet set = new AnimatorSet();
        set.play(welcomeAnimator).after(animateProfilePic);
        set.play(welcomeAnimator).before(loginBtnAnimator);
        set.start();

    }

    private void setLayout() {
        welcome = findViewById(R.id.welecomeLabel);
        loginBtn = findViewById(R.id.loginBtn);
        emailEt = (EditText)findViewById(R.id.emailEt);
        container = findViewById(R.id.container);

        profilePic = findViewById(R.id.profilePic);

        emailEt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus && emailEt.getText().toString().equals("abc"))
                {
                    changeProfilePic();
                }
            }
        });

    }

    private void changeProfilePic() {
        profilePic.animate().rotationY(90).setDuration(500).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ((ImageView)profilePic).setImageResource(R.drawable.ic_launcher);
                profilePic.animate().rotationY(0).setDuration(500).setInterpolator(new OvershootInterpolator());
            }
        });
    }
}

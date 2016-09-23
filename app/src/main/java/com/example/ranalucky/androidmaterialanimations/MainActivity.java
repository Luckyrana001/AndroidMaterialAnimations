package com.example.ranalucky.androidmaterialanimations;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    View container;
    boolean playAnimations = true;

    private View welcome,profilePic,loginBtn;
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
        welcome.setVisibility(View.VISIBLE);
        welcomeAnimator.start();


        /*profile pic animation*/
        PropertyValuesHolder scaleXholder = PropertyValuesHolder.ofFloat(View.SCALE_X,1f);
        PropertyValuesHolder scaleYholder = PropertyValuesHolder.ofFloat(View.SCALE_Y,1f);

        ObjectAnimator animateProfilePic = ObjectAnimator.ofPropertyValuesHolder(profilePic, scaleXholder,scaleYholder);
        animateProfilePic.setDuration(1000);
        animateProfilePic.start();

        /*login button animation*/
        ObjectAnimator loginBtnAnimator = (ObjectAnimator) AnimatorInflater.loadAnimator(this,R.animator.sign_in_anim);
        loginBtnAnimator.setTarget(loginBtn);
        loginBtnAnimator.start();

    }

    private void setLayout() {
        welcome = findViewById(R.id.welecomeLabel);

        loginBtn = findViewById(R.id.loginBtn);

        container = findViewById(R.id.container);

        profilePic = findViewById(R.id.profilePic);
    }
}

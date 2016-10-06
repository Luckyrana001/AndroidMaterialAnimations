package com.example.ranalucky.androidmaterialanimations;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    View container;
    boolean playAnimations = true;

    private View welcome,profilePic,loginBtn ;EditText emailEt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/*
        this method add the common enter / exit transition to activity
*/
        setupWindowAnimations();
        setLayout();


    }

    private void setupWindowAnimations() {
        // Re-enter transition is executed when returning to this activity
        Slide slideTransition = new Slide();
        slideTransition.setSlideEdge(Gravity.LEFT);
        slideTransition.setDuration(500);
        getWindow().setReenterTransition(slideTransition);
        getWindow().setExitTransition(slideTransition);
    }
    private void setLayout() {
        welcome = findViewById(R.id.welecomeLabel);
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


        loginBtn = findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               openSecondAnimation();
            }
        });
    }

    private void openSecondAnimation() {
        Intent openSecondActivity = new Intent(getApplicationContext(),SecondActivity.class);
        ActivityOptions opt = ActivityOptions.makeSceneTransitionAnimation(
                this,
                new Pair<View,String>(welcome,"welcome"),
                new Pair<View,String>(profilePic,"profile_pic")
        );


        startActivity(openSecondActivity,opt.toBundle());
    }


    /*  All animations starting from here
    alpha change animation on first time window will get focus */
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

        /*startWelcomeX is the staring point of animation */
        float startWelcomeX = 0 - welcome.getWidth();
        /*x coordinate is the end point of aniation because this animation we are starting from minus X */
        float endWelcome = welcome.getX();

       /* ObjectAnimator is a subclass of ValueAnimator provides support for animating properties on target objects.
                The constructors of this class take parameters to define the target object that will be animated as well
                as the name of the property that will be animated. Appropriate set/get functions are then determined
        internally and the animation will call these functions as necessary to animate the property.*/

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

        /*PropertyValuesHolder class holds information about a property and the values that  property should take on during an animation.
        PropertyValuesHolder objects can be used to create animations with ValueAnimator or
        ObjectAnimator that operate on several different properties in parallel.*/

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

        /*AnimatorSet class plays a set of Animator objects in the specified order. Animations can be set up to play together, in sequence, or after a specified delay.
        There are two different approaches to adding animations to a AnimatorSet: either the playTogether() or playSequentially() methods
        can be called to add a set of animations all at once, or the play(Animator) can be used in conjunction with methods in the Builder class to add animations one by one.
       */


        AnimatorSet set = new AnimatorSet();
        set.play(welcomeAnimator).after(animateProfilePic);
        set.play(welcomeAnimator).before(loginBtnAnimator);
        set.start();

    }


    private void changeProfilePic() {

        /*Animate profile pic flip animation*/
        profilePic.animate().rotationY(90).setDuration(500).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ((ImageView)profilePic).setImageResource(R.drawable.ic_launcher);

                /*OvershootInterpolator is an interpolator whose change flings forward and overshoots the last value then comes back.*/
                profilePic.animate().rotationY(0).setDuration(500).setInterpolator(new OvershootInterpolator());
            }
        });
    }
}

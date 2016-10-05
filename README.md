# AndroidMaterialAnimations

This sample code includes following animations:



Activity transition animation




Android Tween Animations: 

<img src="https://github.com/Luckyrana001/AndroidMaterialAnimations/blob/master/anim.gif" width="350"/>




In above example i have used 

Welcome text animation:<br>
/*startWelcomeX is the staring point of animation */<br>
float startWelcomeX = 0 - welcome.getWidth();<br>
/*x coordinate is the end point of aniation because this animation we are starting from minus X */<br>
float endWelcome = welcome.getX();<br>

/* ObjectAnimator is a subclass of ValueAnimator provides support for animating properties on target objects.<br>
        The constructors of this class take parameters to define the target object that will be animated as well<br>
        as the name of the property that will be animated. Appropriate set/get functions are then determined<br>
internally and the animation will call these functions as necessary to animate the property.*/<br>

ObjectAnimator welcomeAnimator = ObjectAnimator.ofFloat(welcome,View.X , startWelcomeX,endWelcome);<br>
welcomeAnimator.setDuration(1000);<br>

/*a listener to set welcome text visible only after animation start*/<br>
welcomeAnimator.addListener(new AnimatorListenerAdapter() {<br>
    @Override<br>
    public void onAnimationStart(Animator animation) {<br>
        super.onAnimationStart(animation);<br>
        welcome.setVisibility(View.VISIBLE);<br>

    }<br>
});<br>
welcomeAnimator.start();<br><br><br><br>




profile pic scale animation<br><br>

/*PropertyValuesHolder class holds information about a property and the values that  property should take on during an animation.<br>
PropertyValuesHolder objects can be used to create animations with ValueAnimator or<br>
ObjectAnimator that operate on several different properties in parallel.*/<br>

PropertyValuesHolder scaleXholder = PropertyValuesHolder.ofFloat(View.SCALE_X,1f);<br>
PropertyValuesHolder scaleYholder = PropertyValuesHolder.ofFloat(View.SCALE_Y,1f);<br><br>

ObjectAnimator animateProfilePic = ObjectAnimator.ofPropertyValuesHolder(profilePic, scaleXholder,scaleYholder);<br>
animateProfilePic.setDuration(1000);<br>
animateProfilePic.start();<br><br><br><br>




/* login button animation */<br>
ObjectAnimator loginBtnAnimator = (ObjectAnimator) <br>
/* sign_in_anim is the custom xml animation i have used in the project */<br>
AnimatorInflater.loadAnimator(this,R.animator.sign_in_anim);<br>
loginBtnAnimator.setTarget(loginBtn);<br>
loginBtnAnimator.start();<br><br><br>


/*this part will handle the sequencing of animations*/<br><br>

/*AnimatorSet class plays a set of Animator objects in the specified order. Animations can be set up to play together, in sequence, or after a specified delay.<br>
There are two different approaches to adding animations to a AnimatorSet: either the playTogether() or playSequentially() methods<br>
can be called to add a set of animations all at once, or the play(Animator) can be used in conjunction with methods in the Builder class to add animations one by one.<br>
*/<br><br><br>


AnimatorSet set = new AnimatorSet();<br>
set.play(welcomeAnimator).after(animateProfilePic);<br>
set.play(welcomeAnimator).before(loginBtnAnimator);<br>
set.start();<br><br><br>



And when user type the abc and start typing password , i am adding below animation to flip image on EditText focus chang listener:<br>

/*Animate profile pic flip animation*/<br>
profilePic.animate().rotationY(90).setDuration(500).setListener(new AnimatorListenerAdapter() {<br>
   @Override<br>
   public void onAnimationEnd(Animator animation) {<br>
       super.onAnimationEnd(animation);<br>
       ((ImageView)profilePic).setImageResource(R.drawable.ic_launcher);<br><br>

       /*OvershootInterpolator is an interpolator whose change flings forward and overshoots the last value then comes back.*/<br><br>
       profilePic.animate().rotationY(0).setDuration(500).setInterpolator(new OvershootInterpolator());<br>
   }<br>
});<br><br><br><br>








Activity Switch - Content transition animation<br><br><br>



<img src="https://github.com/Luckyrana001/AndroidMaterialAnimations/blob/master/anim1.gif" width="350"/><br><br>



To accomplish activity transition animation , we can use following code<br><br>

a) Enable Window Content Transition<br>
This is something you need to set up once on your app styles.xml.<br>
values/styles.xml<br>
<style name="MaterialAnimations" parent="@style/Theme.AppCompat.Light.NoActionBar"><br>
    ...<br><br>
    <item name="android:windowContentTransitions">true</item<br>
    ...<br>
</style><br>
Here you can also specify default enter, exit and shared element transitions for the whole app if you want<br>
<style name="MaterialAnimations" parent="@style/Theme.AppCompat.Light.NoActionBar"><br>
    ...<br>
    <!-- specify enter and exit transitions --><br>
    <item name="android:windowEnterTransition">@transition/explode</item><br>
    <item name="android:windowExitTransition">@transition/explode</item><br><br>

    <!-- specify shared element transitions --><br>
    <item name="android:windowSharedElementEnterTransition">@transition/changebounds</item><br>
    <item name="android:windowSharedElementExitTransition">@transition/changebounds</item><br>
    ...<br>
</style><br>
b) Define a common transition name<br>
To make the trick you need to give both, origin and target views, the same android:transitionName. They may have different ids or<br> properties, but android:transitionName must be the same.<br>
layout/activity_a.xml<br>
<ImageView<br>
        android:id="@+id/small_blue_icon"<br>
        style="@style/MaterialAnimations.Icon.Small"<br>
        android:src="@drawable/circle"<br>
        android:transitionName="@string/profile_pic_anim" /><br>
layout/activity_b.xml<br>
<ImageView<br>
        android:id="@+id/big_blue_icon"<br>
        style="@style/MaterialAnimations.Icon.Big"<br>
        android:src="@drawable/circle"<br>
        android:transitionName="@string/profile_pic_anim" /><br><br>

In the above xml code , ‘profile_pic_anim’ is the transition name used to show content transition from activity_a to activity_b.<br><br><br><br>












Sample Code of above animation is shared on GitHub:<br>
https://github.com/Luckyrana001/whatsAppGroupContactSelector<br><br>

Tween Animation:<br>
https://github.com/Luckyrana001/AndroidMaterialAnimations<br><br>

Other Example can be seen at :<br>
https://github.com/lgvalle/Material-Animations<br><br>

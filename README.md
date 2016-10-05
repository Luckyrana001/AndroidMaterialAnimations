# AndroidMaterialAnimations

This sample code includes following animations:



Activity transition animation




Android Tween Animations: 

<img src="https://github.com/Luckyrana001/AndroidMaterialAnimations/blob/master/anim.gif" width="350"/>




In above example i have used 

Welcome text animation
:
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
welcomeAnimator.start();




profile pic scale animation

/*PropertyValuesHolder class holds information about a property and the values that  property should take on during an animation.
PropertyValuesHolder objects can be used to create animations with ValueAnimator or
ObjectAnimator that operate on several different properties in parallel.*/

PropertyValuesHolder scaleXholder = PropertyValuesHolder.ofFloat(View.SCALE_X,1f);
PropertyValuesHolder scaleYholder = PropertyValuesHolder.ofFloat(View.SCALE_Y,1f);

ObjectAnimator animateProfilePic = ObjectAnimator.ofPropertyValuesHolder(profilePic, scaleXholder,scaleYholder);
animateProfilePic.setDuration(1000);
animateProfilePic.start();




/* login button animation */
ObjectAnimator loginBtnAnimator = (ObjectAnimator) 
/* sign_in_anim is the custom xml animation i have used in the project */
AnimatorInflater.loadAnimator(this,R.animator.sign_in_anim);
loginBtnAnimator.setTarget(loginBtn);
loginBtnAnimator.start();


/*this part will handle the sequencing of animations*/

/*AnimatorSet class plays a set of Animator objects in the specified order. Animations can be set up to play together, in sequence, or after a specified delay.
There are two different approaches to adding animations to a AnimatorSet: either the playTogether() or playSequentially() methods
can be called to add a set of animations all at once, or the play(Animator) can be used in conjunction with methods in the Builder class to add animations one by one.
*/


AnimatorSet set = new AnimatorSet();
set.play(welcomeAnimator).after(animateProfilePic);
set.play(welcomeAnimator).before(loginBtnAnimator);
set.start();



And when user type the abc and start typing password , i am adding below animation to flip image on EditText focus chang listener:

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








Activity Switch - Content transition animation



<img src="https://github.com/Luckyrana001/AndroidMaterialAnimations/blob/master/anim1.gif" width="350"/>



To accomplish activity transition animation , we can use following code

a) Enable Window Content Transition
This is something you need to set up once on your app styles.xml.
values/styles.xml
<style name="MaterialAnimations" parent="@style/Theme.AppCompat.Light.NoActionBar">
    ...
    <item name="android:windowContentTransitions">true</item
    ...
</style>
Here you can also specify default enter, exit and shared element transitions for the whole app if you want
<style name="MaterialAnimations" parent="@style/Theme.AppCompat.Light.NoActionBar">
    ...
    <!-- specify enter and exit transitions -->
    <item name="android:windowEnterTransition">@transition/explode</item>
    <item name="android:windowExitTransition">@transition/explode</item>

    <!-- specify shared element transitions -->
    <item name="android:windowSharedElementEnterTransition">@transition/changebounds</item>
    <item name="android:windowSharedElementExitTransition">@transition/changebounds</item>
    ...
</style>
b) Define a common transition name
To make the trick you need to give both, origin and target views, the same android:transitionName. They may have different ids or properties, but android:transitionName must be the same.
layout/activity_a.xml
<ImageView
        android:id="@+id/small_blue_icon"
        style="@style/MaterialAnimations.Icon.Small"
        android:src="@drawable/circle"
        android:transitionName="@string/profile_pic_anim" />
layout/activity_b.xml
<ImageView
        android:id="@+id/big_blue_icon"
        style="@style/MaterialAnimations.Icon.Big"
        android:src="@drawable/circle"
        android:transitionName="@string/profile_pic_anim" />

In the above xml code , ‘profile_pic_anim’ is the transition name used to show content transition from activity_a to activity_b.












Sample Code of above animation is shared on GitHub:
https://github.com/Luckyrana001/whatsAppGroupContactSelector

Tween Animation:
https://github.com/Luckyrana001/AndroidMaterialAnimations

Other Example can be seen at :
https://github.com/lgvalle/Material-Animations

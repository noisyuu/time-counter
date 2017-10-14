package team10.myapplication;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.ActivityOptions;
import android.graphics.Color;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.transition.Slide;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    int i =0;
    CountDownTimer mCountDownTimer;
    ProgressBar mProgressBar;
    TextView percentage;
    Boolean fixed = false;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);


        percentage = (TextView) findViewById(R.id.hello);
        //setupWindowAnimations();
        Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(this).toBundle();

        mProgressBar=(ProgressBar)findViewById(R.id.progressBar);
        mProgressBar.setProgress(i);
        button = (Button) findViewById(R.id.button);
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                String TAG = "Tag";
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    i =0;
                    fixed = false;
                    mProgressBar.setProgress(i);
                    mProgressBar.setAlpha(1.0f);
                    percentage.setAlpha(1.0f);
                    mProgressBar.setVisibility(View.VISIBLE);
                    percentage.setVisibility(View.VISIBLE);

                    mCountDownTimer=new CountDownTimer(10000,50) {

                        @Override
                        public void onTick(long millisUntilFinished) {
                            Log.v("Log_tag", "Tick of Progress"+ i+ millisUntilFinished);
                            i++;
                            mProgressBar.setProgress((int)i*100/(10000/50));
                            percentage.setText(i*100/(10000/50)+" %");
                        }

                        @Override
                        public void onFinish() {
                            //Do what you want
                            i++;
                            mProgressBar.setProgress(100);
                            percentage.setText("FIXED");
                            fixed =true;
                            animation();                        }
                    };
                    mCountDownTimer.start();
                    return true;
                }
                if (event.getAction()==MotionEvent.ACTION_MOVE ){
                    return true;
                }
                if(event.getAction()==MotionEvent.ACTION_UP){
                    if(!fixed){
                        percentage.setText("CANCEL");
                        animation();
                    }
                    mCountDownTimer.cancel();
                    return true;
                }
                return false;
            }
        });






    }

    private void animation() {
        button.setEnabled(false);
        AnimatorSet textFlash = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.flashing);
        textFlash.setTarget(mProgressBar);

        AnimatorSet progressBarFlash = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.flashing);
        progressBarFlash.setTarget(percentage);

        final AnimatorSet bothAnimatorSet = new AnimatorSet();
        bothAnimatorSet.playTogether(textFlash, progressBarFlash);
        bothAnimatorSet.start();
        bothAnimatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationCancel(Animator animator) {
                //TODO
                Log.d("anime","cancel");
                return;
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                mProgressBar.setVisibility(View.INVISIBLE);
                percentage.setVisibility(View.INVISIBLE);
                Log.d("anime","end");
                button.setEnabled(true);
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
                //TODO
                Log.d("anime","repeat");
            }

            @Override
            public void onAnimationStart(Animator animator) {
                //
                Log.d("anime","start");
            }
        });
    }

    private void cancel_animation(){

    }

    private void setUpButtonListner(){

    }

    private void setupWindowAnimations() {
        Fade fade = new Fade();
        fade.setDuration(1000);
        getWindow().setEnterTransition(fade);
        Slide slide = new Slide();
        slide.setDuration(1000);
        getWindow().setReturnTransition(slide);
    }
}

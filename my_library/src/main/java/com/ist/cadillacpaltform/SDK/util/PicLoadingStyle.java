package com.ist.cadillacpaltform.SDK.util;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;

import com.facebook.drawee.drawable.ProgressBarDrawable;
import com.ist.cadillacpaltform.R;

/**
 * Created by czh on 2017/4/9.
 */

public class PicLoadingStyle {
    private Drawable questionPicLoad  = new ProgressBarDrawable();
    private AnimationDrawable questionPicLoad2 = new AnimationDrawable();

    public PicLoadingStyle(Activity activity){
        Drawable drawable = activity.getResources().getDrawable(R.drawable.animation_pic_load);
        questionPicLoad2.addFrame(drawable,200);
        questionPicLoad2.setOneShot(false);
    }

    public Drawable getQuestionPicLoad() {
        return questionPicLoad;
    }

    public AnimationDrawable getQuestionPicLoad2() {
        return questionPicLoad2;
    }


}

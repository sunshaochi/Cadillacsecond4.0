package cadillac.example.com.cadillac.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by bitch-1 on 2017/8/18.
 */

public class MyViewPage extends ViewPager{
  private float preX;

    public MyViewPage(Context context) {
        super(context);
    }

    public MyViewPage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
      boolean res=super.onInterceptTouchEvent(ev);
        if(ev.getAction() == MotionEvent.ACTION_DOWN) {
            preX = ev.getX();
        } else {
            if( Math.abs(ev.getX() - preX)> 4 ) {
                return true;
            } else {
                preX = ev.getX();
            }
        }
        return res;
    }
}

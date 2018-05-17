package cadillac.example.com.cadillac.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by bitch-1 on 2016/5/12.
 */
public class MyListview extends ListView {
    public MyListview(Context context) {
        super(context);}

    public MyListview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public MyListview(Context context, AttributeSet attrs,
                      int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    /**
     * 重写该方法、达到使ListView适应ScrollView的效果
     */
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }


}

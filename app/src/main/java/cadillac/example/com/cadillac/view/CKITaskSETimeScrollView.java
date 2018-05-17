package cadillac.example.com.cadillac.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**应用在时间-时间表页的scrollview
 * @author CK
 *
 */
public class CKITaskSETimeScrollView extends ScrollView {
	Boolean isLayout = true;
	ScrollAdapter adapter;
	private int childPosition;
	LinearLayout container;
	public CKITaskSETimeScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setAdapter(ScrollAdapter adapter) {
		isLayout = true;
		this.adapter = adapter;
		this.requestLayout();
	}
	
	/**移除adapter中的所有view
	 * @param adapter
	 */
	public void removeAdapter(ScrollAdapter adapter){
		for(int i =0;i<adapter.getCount();i++){
			container.removeView(adapter.getView(i));
		}
//		container.removeAllViews();
		this.invalidate();
	}
	
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if (isLayout) {
			isLayout = false;
			try {
				container = (LinearLayout) getChildAt(0);
				childPosition = container.getChildCount();
				if (adapter != null) {
					for (int i = 0; i < adapter.getCount(); i++) {
						container.addView(adapter.getView(i));
					}
				}
			} catch (Exception e) {
				System.out.println("this scrollView must only linearLayout child");
			}

		}
	}

	public interface ScrollAdapter {
		public int getCount();

		public View getView(int position);
	}
}

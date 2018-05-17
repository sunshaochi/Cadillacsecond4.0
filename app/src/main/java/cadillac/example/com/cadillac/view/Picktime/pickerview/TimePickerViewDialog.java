package cadillac.example.com.cadillac.view.Picktime.pickerview;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;


import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Color;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.view.Picktime.lib.WheelView;
import cadillac.example.com.cadillac.view.Picktime.view.WheelTime;

/**
 * Created by Sai on 15/11/22.
 */
public class TimePickerViewDialog implements View.OnClickListener {

	WheelView mothview;//因为凯迪拉克里面只需要选择一个年所以要想办法把月份那个隐藏掉
	WheelTime wheelTime;
	private Button btnSubmit, btnCancel;
	private TextView tvTitle;
	private static final String TAG_SUBMIT = "submit";
	private static final String TAG_CANCEL = "cancel";
	private OnTimeSelectListener timeSelectListener;
	private RelativeLayout customTitle;
	private Dialog dialog;
	private OnTimeDialogDismiss onTimeDialogDismiss;

	public void setOnTimeDialogDismiss(OnTimeDialogDismiss onTimeDialogDismiss) {
		this.onTimeDialogDismiss = onTimeDialogDismiss;
	}

	public interface OnTimeDialogDismiss {
		void onDismiss(DialogInterface dialog);
	}

	public TimePickerViewDialog(Activity context, TimePickerView.Type type) {
		dialog = new Dialog(context);
		View contentView = LayoutInflater.from(context).inflate(R.layout.pickerview_time, null);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(contentView);
		dialog.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				if (onTimeDialogDismiss != null) {
					onTimeDialogDismiss.onDismiss(dialog);
				}
			}
		});
		Window window = dialog.getWindow();
		//window.setBackgroundDrawable(new ColorDrawable(Color.GRAY));
		window.setBackgroundDrawableResource(android.R.color.transparent);
		window.setGravity(Gravity.BOTTOM); // 此处可以设置dialog显示的位置
		WindowManager windowManager = context.getWindowManager();
		Display display = windowManager.getDefaultDisplay();
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.width = (int) (display.getWidth()); // 设置宽度
		window.setAttributes(lp);

		mothview= (WheelView) contentView.findViewById(R.id.month);

		customTitle = (RelativeLayout) contentView.findViewById(R.id.custom_title);
		// -----确定和取消按钮
		btnSubmit = (Button) contentView.findViewById(R.id.btnSubmit);
		btnSubmit.setTag(TAG_SUBMIT);
		btnCancel = (Button) contentView.findViewById(R.id.btnCancel);
		btnCancel.setTag(TAG_CANCEL);
		btnSubmit.setOnClickListener(this);
		btnCancel.setOnClickListener(this);
		// 顶部标题
		tvTitle = (TextView) contentView.findViewById(R.id.tvTitle);
		// ----时间转轮
		final View timepickerview = contentView.findViewById(R.id.timepicker);
		wheelTime = new WheelTime(timepickerview, type);

		// 默认选中当前时间
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		wheelTime.setPicker(year, month, day, hours, minute);
	}

	public Dialog getDialog() {
		return dialog;
	}

	/**
	 * 设置可以选择的时间范围
	 *
	 * @param startYear
	 * @param endYear
	 */
	public void setRange(Calendar startDate, Calendar endDate) {
		wheelTime.setStartDate(startDate);
		wheelTime.setEndDate(endDate);
	}

	/**
	 * 设置选中时间
	 * 
	 * @param date
	 */
	public void setTime(Calendar calendar) {
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		int hours = calendar.get(Calendar.HOUR_OF_DAY);
		int minute = calendar.get(Calendar.MINUTE);
		wheelTime.setPicker(year, month, day, hours, minute);
	}

	/**
	 * 设置是否循环滚动
	 *
	 * @param cyclic
	 */
	public void setCyclic(boolean cyclic) {
		wheelTime.setCyclic(cyclic);
	}

	@Override
	public void onClick(View v) {
		String tag = (String) v.getTag();
		if (tag.equals(TAG_CANCEL)) {
			dialog.dismiss();
			timeSelectListener.onCancel();
			return;
		} else {
			if (timeSelectListener != null) {
				try {
					Date date = WheelTime.dateFormat.parse(wheelTime.getTime());
					timeSelectListener.onTimeSelect(date);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			dialog.dismiss();
			return;
		}
	}

	public interface OnTimeSelectListener {
		public void onTimeSelect(Date date);

		public void onCancel();
	}

	/**
	 * 设置简便的标题事件侦听
	 * 
	 * @param timeSelectListener
	 */
	public void setOnTimeSelectListener(OnTimeSelectListener timeSelectListener) {
		this.timeSelectListener = timeSelectListener;
	}

	public void setTitle(String title) {
		tvTitle.setText(title);
	}

	public void setTitleColor(String colorStr) {
		tvTitle.setTextColor(Color.parseColor(colorStr));
	}

	public void setButtonLeftText(String str) {
		btnCancel.setText(str);
	}

	public void setButtonRightText(String str) {
		btnSubmit.setText(str);
	}

	public void setButtonLeftBackgroundRes(int res) {
		btnCancel.setBackgroundResource(res);
	}

	public void setButtonRightBackgroundRes(int res) {
		btnSubmit.setBackgroundResource(res);
	}

	public void setTitleBackgroundColor(String colorStr) {
		customTitle.setBackgroundColor(Color.parseColor(colorStr));
	}

	public void setButtonLeftTextColor(String str) {
		btnCancel.setTextColor(Color.parseColor(str));
	}

	public void setButtonRightTextColor(String str) {
		btnSubmit.setTextColor(Color.parseColor(str));
	}
	public void setCanceledOnTouchOutside(boolean b){
			dialog.setCanceledOnTouchOutside(b);
	}

	public void setMothGone(boolean b){
		if(b){
			mothview.setVisibility(View.GONE);
		}else {
			mothview.setVisibility(View.VISIBLE);
		}
	}
}

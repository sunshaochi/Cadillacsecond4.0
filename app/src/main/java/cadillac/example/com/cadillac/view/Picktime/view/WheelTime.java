package cadillac.example.com.cadillac.view.Picktime.view;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;



import android.content.Context;
import android.util.Log;
import android.view.View;

import cadillac.example.com.cadillac.R;
import cadillac.example.com.cadillac.view.Picktime.adapter.NumericWheelAdapter;
import cadillac.example.com.cadillac.view.Picktime.lib.WheelView;
import cadillac.example.com.cadillac.view.Picktime.listener.OnItemSelectedListener;
import cadillac.example.com.cadillac.view.Picktime.pickerview.TimePickerView;

public class WheelTime {
	public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private View view;
	private WheelView wv_year;
	private WheelView wv_month;
	private WheelView wv_day;
	private WheelView wv_hours;
	private WheelView wv_mins;

	private TimePickerView.Type type;
	public static final int DEFULT_START_YEAR = 1990;
	public static final int DEFULT_END_YEAR = 2100;
	private Calendar startDate, endDate;
	private NumericWheelAdapter yearAdapter, monthAdapter, dayAdapter, hoursAdapter, minsAdapter;
	private int selectYear, selectMonth, selectDay, selectHours, selectMin;
	List<String> list_big, list_little;

	public WheelTime(View view) {
		this(view, TimePickerView.Type.ALL);
	}

	public WheelTime(View view, TimePickerView.Type type) {
		super();
		this.view = view;
		this.type = type;
		setView(view);
		startDate = Calendar.getInstance();
		startDate.set(Calendar.YEAR, DEFULT_START_YEAR);
		getDateMin(startDate);
		endDate = Calendar.getInstance();
		endDate.set(Calendar.YEAR, DEFULT_END_YEAR);
		getDateMax(endDate);

	}

	public void setPicker(int year, int month, int day) {
		this.setPicker(year, month, day, 0, 0);
	}

	/**
	 * @Description: TODO 弹出日期时间选择器
	 */
	public void setPicker(int year, int month, int day, int h, int m) {
		selectYear = year;
		selectMonth = month + 1;
		selectDay = day;
		selectHours = h;
		selectMin = m;
		// 添加大小月月份并将其转换为list,方便之后的判断
		String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
		String[] months_little = { "4", "6", "9", "11" };

		list_big = Arrays.asList(months_big);
		list_little = Arrays.asList(months_little);

		Context context = view.getContext();
		// 年
		wv_year = (WheelView) view.findViewById(R.id.year);
		yearAdapter = new NumericWheelAdapter(startDate.get(Calendar.YEAR), endDate.get(Calendar.YEAR));
		wv_year.setAdapter(yearAdapter);// 设置"年"的显示数据
		wv_year.setLabel(context.getString(R.string.pickerview_year));// 添加文字
		wv_year.setCurrentItem(year - startDate.get(Calendar.YEAR));// 初始化时显示的数据

		// 月
		wv_month = (WheelView) view.findViewById(R.id.month);
		wv_month.setLabel(context.getString(R.string.pickerview_month));

		// 日
		wv_day = (WheelView) view.findViewById(R.id.day);
		wv_day.setLabel(context.getString(R.string.pickerview_day));

		wv_hours = (WheelView) view.findViewById(R.id.hour);
		wv_hours.setLabel(context.getString(R.string.pickerview_hours));// 添加文字

		wv_mins = (WheelView) view.findViewById(R.id.min);
		wv_mins.setLabel(context.getString(R.string.pickerview_minutes));// 添加文字
		init();

		// 添加"年"监听
		OnItemSelectedListener wheelListener_year = new OnItemSelectedListener() {
			@Override
			public void onItemSelected(int index) {
				selectYear = index + (Integer) yearAdapter.getItem(0);
				refresh(tagYear);
			}
		};
		// 添加"月"监听
		OnItemSelectedListener wheelListener_month = new OnItemSelectedListener() {
			@Override
			public void onItemSelected(int index) {
				selectMonth = (Integer) monthAdapter.getItem(0) + index;
				refresh(tagMon);
			}
		};
		// 添加日的监听
		OnItemSelectedListener wheelListener_day = new OnItemSelectedListener() {
			@Override
			public void onItemSelected(int index) {
				selectDay = (Integer) dayAdapter.getItem(0) + index;
				refresh(tagDay);
			}
		};
		// 添加小时的监听
		OnItemSelectedListener wheelListener_hour = new OnItemSelectedListener() {
			@Override
			public void onItemSelected(int index) {
				selectHours = (Integer) hoursAdapter.getItem(0) + index;
				refresh(tagHour);
			}
		};
		// 添加分钟的监听
		OnItemSelectedListener wheelListener_min = new OnItemSelectedListener() {
			@Override
			public void onItemSelected(int index) {
				selectMin = (Integer) minsAdapter.getItem(0) + index;
			}
		};
		wv_year.setOnItemSelectedListener(wheelListener_year);
		wv_month.setOnItemSelectedListener(wheelListener_month);
		wv_day.setOnItemSelectedListener(wheelListener_day);
		wv_hours.setOnItemSelectedListener(wheelListener_hour);
		wv_mins.setOnItemSelectedListener(wheelListener_min);

		// 根据屏幕密度来指定选择器字体的大小(不同屏幕可能不同)
		int textSize = 6;
		switch (type) {
		case ALL:
			textSize = textSize * 3;
			break;
		case YEAR_MONTH_DAY:
			textSize = textSize * 4;
			wv_hours.setVisibility(View.GONE);
			wv_mins.setVisibility(View.GONE);
			break;
		case HOURS_MINS:
			textSize = textSize * 4;
			wv_year.setVisibility(View.GONE);
			wv_month.setVisibility(View.GONE);
			wv_day.setVisibility(View.GONE);
			break;
		case MONTH_DAY_HOUR_MIN:
			textSize = textSize * 3;
			wv_year.setVisibility(View.GONE);
			break;
		case YEAR_MONTH:
			textSize = textSize * 4;
			wv_day.setVisibility(View.GONE);
			wv_hours.setVisibility(View.GONE);
			wv_mins.setVisibility(View.GONE);
		}
		wv_day.setTextSize(textSize);
		wv_month.setTextSize(textSize);
		wv_year.setTextSize(textSize);
		wv_hours.setTextSize(textSize);
		wv_mins.setTextSize(textSize);

	}

	/**
	 * 根据传入的默认日期初始化
	 */
	private void init() {
		refreshMonth();
		refreshDay();
		refreshHour();
		refrshMin();
	}

	private final int tagYear = 10, tagMon = 9, tagDay = 8, tagHour = 7, tagMin = 6;

	/**
	 * 当发生时间的选择时,刷新所有的日期可选 年:10,月:9,日:8,时:7,分:6;
	 */
	private void refresh(int tag) {
		Log.e("tt", selectYear + "年 " + selectMonth + "月 " + selectDay + "日 " + selectHours + "时 " + selectMin + "分 ");
		if (tag > tagMin) {
			refrshMin();
			if (tag > tagHour) {
				refreshHour();
				if (tag > tagDay) {
					refreshDay();
					if (tag > tagMon) {
						refreshMonth();
					}
				}
			}
		}
	}

	private void refrshMin() {
		// 分
		int maxMin = 59, minMin = 0;
		if (hoursAdapter.getItemsCount() == 1) {
			if (yearAdapter.getItemsCount() == 1 && monthAdapter.getItemsCount() == 1 && dayAdapter.getItemsCount() == 1) {
				maxMin = endDate.get(Calendar.MINUTE);
				minMin = startDate.get(Calendar.MINUTE);
			} else {
				minMin = startDate.get(Calendar.MINUTE);
			}
		} else if (selectYear == startDate.get(Calendar.YEAR) && selectMonth == startDate.get(Calendar.MONTH) + 1
				&& selectDay == startDate.get(Calendar.DAY_OF_MONTH)
				&& selectHours == startDate.get(Calendar.HOUR_OF_DAY)) {
			minMin = startDate.get(Calendar.MINUTE);
		} else if (selectYear == endDate.get(Calendar.YEAR) && selectMonth == endDate.get(Calendar.MONTH) + 1
				&& selectDay == endDate.get(Calendar.DAY_OF_MONTH)
				&& selectHours == endDate.get(Calendar.HOUR_OF_DAY)) {
			maxMin = endDate.get(Calendar.MINUTE);
		}
		minsAdapter = new NumericWheelAdapter(minMin, maxMin);
		wv_mins.setAdapter(minsAdapter);
		if (selectMin < minMin) {
			selectMin = minMin;
		} else if (selectMin > maxMin) {
			selectMin = maxMin;
		}
		wv_mins.setCurrentItem(selectMin - minMin);
	}

	private void refreshHour() {
		// 时
		int maxHour = 23, minHour = 0;
		if (dayAdapter.getItemsCount() == 1) {
			if (yearAdapter.getItemsCount() == 1 && monthAdapter.getItemsCount() == 1) {
				maxHour = endDate.get(Calendar.HOUR_OF_DAY);
				minHour = startDate.get(Calendar.HOUR_OF_DAY);
			} else {
				minHour = startDate.get(Calendar.HOUR_OF_DAY);
			}
		} else if (selectYear == startDate.get(Calendar.YEAR) && selectMonth == startDate.get(Calendar.MONTH) + 1
				&& selectDay == startDate.get(Calendar.DAY_OF_MONTH)) {
			minHour = startDate.get(Calendar.HOUR_OF_DAY);
		} else if (selectYear == endDate.get(Calendar.YEAR) && selectMonth == endDate.get(Calendar.MONTH) + 1
				&& selectDay == endDate.get(Calendar.DAY_OF_MONTH)) {
			maxHour = endDate.get(Calendar.HOUR_OF_DAY);
		}
		hoursAdapter = new NumericWheelAdapter(minHour, maxHour);
		wv_hours.setAdapter(hoursAdapter);
		if (selectHours < minHour) {
			selectHours = minHour;
		} else if (selectHours > maxHour) {
			selectHours = maxHour;
		}
		wv_hours.setCurrentItem(selectHours - minHour);
	}

	private void refreshDay() {
		// 日
		// 判断大小月及是否闰年,用来确定"日"的数据
		int maxDayItem = 30;
		int minDay = 1;
		if (list_big.contains(String.valueOf(selectMonth))) {
			maxDayItem = 31;
		} else if (list_little.contains(String.valueOf(selectMonth))) {
			maxDayItem = 30;
		} else {
			// 闰年
			if ((selectYear % 4 == 0 && selectYear % 100 != 0) || selectYear % 400 == 0)
				maxDayItem = 29;
			else {
				maxDayItem = 28;
			}
		}

		if (monthAdapter.getItemsCount() == 1) {
			if (yearAdapter.getItemsCount() == 1) {
				minDay = startDate.get(Calendar.DAY_OF_MONTH);
				maxDayItem = endDate.get(Calendar.DAY_OF_MONTH);
			} else {
				minDay = startDate.get(Calendar.DAY_OF_MONTH);
			}
		} else if (selectYear == startDate.get(Calendar.YEAR) && selectMonth == startDate.get(Calendar.MONTH) + 1) {
			minDay = startDate.get(Calendar.DAY_OF_MONTH);
		} else if (selectYear == endDate.get(Calendar.YEAR) && selectMonth == endDate.get(Calendar.MONTH) + 1) {
			maxDayItem = endDate.get(Calendar.DAY_OF_MONTH);
		}
		dayAdapter = new NumericWheelAdapter(minDay, maxDayItem);
		wv_day.setAdapter(dayAdapter);
		if (selectDay < minDay) {
			selectDay = minDay;
		} else if (selectDay > maxDayItem) {
			selectDay = maxDayItem;
		}
		wv_day.setCurrentItem(selectDay - minDay);
	}

	private void refreshMonth() {
		// 月
		int maxMon = 12, minMon = 1;
		if (yearAdapter.getItemsCount() == 1) {
			maxMon = endDate.get(Calendar.MONTH) + 1;
			minMon = startDate.get(Calendar.MONTH) + 1;
		} else if (selectYear == startDate.get(Calendar.YEAR)) {
			minMon = startDate.get(Calendar.MONTH) + 1;
		} else if (selectYear == endDate.get(Calendar.YEAR)) {
			maxMon = endDate.get(Calendar.MONTH) + 1;
		}
		monthAdapter = new NumericWheelAdapter(minMon, maxMon);
		wv_month.setAdapter(monthAdapter);
		if (selectMonth < minMon) {
			selectMonth = minMon;
		} else if (selectMonth > maxMon) {
			selectMonth = maxMon;
		}
		wv_month.setCurrentItem(selectMonth - minMon);
	}

	/**
	 * 设置是否循环滚动
	 * 
	 * @param cyclic
	 */
	public void setCyclic(boolean cyclic) {
		wv_year.setCyclic(cyclic);
		wv_month.setCyclic(cyclic);
		wv_day.setCyclic(cyclic);
		wv_hours.setCyclic(cyclic);
		wv_mins.setCyclic(cyclic);
	}

	public String getTime() {
		StringBuffer sb = new StringBuffer();
		sb.append(selectYear).append("-").append(selectMonth).append("-").append(selectDay).append(" ")
				.append(selectHours).append(":").append(selectMin);
		return sb.toString();
	}

	public View getView() {
		return view;
	}

	public void setView(View view) {
		this.view = view;
	}

	public Calendar getStartYear() {
		return startDate;
	}

	public void setStartDate(Calendar date) {
		this.startDate = date;
	}

	public Calendar getEndYear() {
		return endDate;
	}

	public void setEndDate(Calendar date) {
		this.endDate = date;
	}

	public Calendar getDateMax(Calendar date) {
		date.set(date.get(Calendar.YEAR), 11, 31, 23, 59, 59);
		return date;
	}

	public Calendar getDateMin(Calendar date) {
		date.set(date.get(Calendar.YEAR), 0, 1, 0, 0, 0);
		return date;
	}
}

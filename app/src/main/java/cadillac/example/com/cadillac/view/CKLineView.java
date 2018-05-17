package cadillac.example.com.cadillac.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * 折线图
 * 
 * @author CK
 * 
 */
public class CKLineView extends View implements AnimatorUpdateListener {
	// data
	List<List<String>> dataSource;
	String[] yData;
	String[] xData;
	int type;

	private int color;

	List<List<LineViewPointBean>> data;
	// size
	int MARGIN_SIZE_HORIZONTAL , MARGIN_SIZE_VERTICAL;// 折线图的margin
	float TEXT_SIZE_X = 0, TEXT_SIZE_Y = 0, TEXT_SIZE_DATA = 0;// x轴文字,y轴文字,数据文字的大小
	int LENGTH_X, LENGTN_Y;// x,y轴的长度
	int SCALE_X, SCALE_Y;// x,y轴的刻度长度
	int width, height;// view的宽高
	// color
	String TEXT_COLOR_X, TEXT_COLOR_Y;//x轴文字颜色，y轴文字颜色
	String LINE_COLOR_X, LINE_COLOR_Y;//x轴线颜色，y轴线颜色
	String TEXT_COLOR_DATA[], LINE_COLOR_DATA[];//数据字体颜色，折线的颜色
	// position
	int xStart = 0, yStart =0;// x轴和y轴的起始点
	// paint
	Paint paintLineX, paintLineY, paintTextX, paintTextY, paintLineData[], paintTextData[];//画笔
	// touchRegion手指滑动区域
	int touchRegion = -1;
	// animation
	public float ClipX = 0.3f;



	/**
	 * @param dataSource
	 *            数据源-最外层list的长度代表每种数据有多少种data,内层list长度代表每种data的长度
	 * @param yData
	 *            y轴坐标值,分别传入0和最大值
	 * @param xData
	 *            x轴的坐标值
	 */
	public CKLineView(List<List<String>> dataSource, String[] yData, String[] xData,Context context,int color) {
		super(context);
		this.dataSource = dataSource;
		this.yData = yData;
		this.xData = xData;
		this.type=type;
		this.color=color;//用来判断是裸车毛利还是报表是否显示左边纵坐标的值的
		// 初始化颜色数组
		LINE_COLOR_DATA = new String[dataSource.size()];
		TEXT_COLOR_DATA = new String[dataSource.size()];
		// 初始化画笔
		paintLineData = new Paint[dataSource.size()];
		paintTextData = new Paint[dataSource.size()];
		ObjectAnimator animator = ObjectAnimator.ofFloat(this, "ClipX", 0f, 1f);
		animator.setDuration(5 * 1000);
		animator.addUpdateListener(this);
		animator.setInterpolator(new DecelerateInterpolator());
		animator.start();
		
		
	}

	public float getClipX() {
		return ClipX;
	}

	public void setClipX(float xClip) {
		this.ClipX = xClip;
	}

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int height = getMeasuredHeight();
		int width = getMeasuredWidth();
		canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));//原点抗锯齿
		// 初始化
		init();
		// 开始画线
		// 画x轴
		canvas.drawLine(xStart, yStart, xStart + (width - 2 * MARGIN_SIZE_HORIZONTAL), yStart, paintLineX);
		// 写x轴文字
		for (int i = 0; i < xData.length; i++) {
			if (i % 4 == 0) {//原来是除以2的用来适配裸车毛利的太多横坐标引起的遮挡问题
				float tempWidth = paintTextX.measureText(xData[i]);
				Path path = new Path();
                path.moveTo(xStart + i * SCALE_X - tempWidth / 2, yStart + TEXT_SIZE_X+TEXT_SIZE_X/2);
				path.lineTo(xStart + i * SCALE_X , yStart + TEXT_SIZE_X+TEXT_SIZE_X/2);
				path.lineTo(xStart + i * SCALE_X+ tempWidth / 2, yStart + TEXT_SIZE_X+TEXT_SIZE_X/2);
				canvas.drawTextOnPath(xData[i], path, 0, 5, paintTextX);

			}
		}
		// 画y轴
		for (int i = 0; i < xData.length; i++) {
			if (i != 0) {
					paintLineY.setColor(Color.parseColor("#ffffff"));

			} else {
					paintLineY.setColor(Color.parseColor("#777777"));

			}
			if (i == 0) {
				if (i != xData.length - 1) {
					canvas.drawLine(xStart, yStart, xStart + i * SCALE_X,
							yStart - (height -  MARGIN_SIZE_VERTICAL), paintLineY);
				}
			}
		}

		// 写y轴文字
		// for (int i = 0; i < yData.length; i++) {
		// }
		if (Float.parseFloat(yData[1]) == 0) {// 如果最大值和最小值都为0,那么应该做特殊处理.将0的位置设为y轴中间
			float tempWidth = paintTextX.measureText(yData[1]);
			canvas.drawText(yData[1], xStart - tempWidth - 15, yStart - 2 * SCALE_Y + TEXT_SIZE_Y / 2, paintTextY);
		} else {
			// 最小值
			float tempWidth = paintTextX.measureText(yData[0]);
			canvas.drawText(yData[0], xStart - tempWidth - 15, yStart - SCALE_Y / 2 + TEXT_SIZE_Y / 2, paintTextY);
			// 最大值
			String tempMaxY = yData[1];
			if (Integer.parseInt(yData[1]) > 1000) {
				tempMaxY = Integer.parseInt(yData[1]) / 1000 + "K";
			}
			float tempWidth1 = paintTextX.measureText(tempMaxY);
			canvas.drawText(tempMaxY, xStart - tempWidth1 - 15, yStart - 7 * SCALE_Y / 2 + TEXT_SIZE_Y / 2, paintTextY);
			// 中间值
			int temp = ((Integer.parseInt(yData[1]) + Integer.parseInt(yData[0])) / 2);

			if (temp > 0) {
				String tempMidY = "" + temp;
				if (temp > 1000) {
					tempMidY = temp / 1000 + "K";
				}
				float tempWidth2 = paintTextX.measureText(tempMidY);
				canvas.drawText(tempMidY, xStart - tempWidth2 - 15, yStart - 2 * SCALE_Y + TEXT_SIZE_Y / 2, paintTextY);
			}
		}
		canvas.clipRect(0, 0, width * ClipX, height);
		// 画数据
		for (int i = 0; i < data.size(); i++) {
			List<LineViewPointBean> temp = data.get(i);
			for (int j = 0; j < temp.size(); j++) {
				LineViewPointBean tempBean = temp.get(j);
				tempBean.drawPoint(canvas, paintLineData[i]);
				if (j > 0) {
					canvas.drawLine(temp.get(j - 1).getPosition().x, temp.get(j - 1).getPosition().y,
							temp.get(j).getPosition().x, temp.get(j).getPosition().y, paintLineData[i]);
				}
			}
		}
		List<LineViewPointBean> stockList = new ArrayList<>();
		// 将被选中的值保存在一个临时list中
		if (touchRegion >= 0) {// 初始化touchRegion为-1,如果有点击到相应区域会对此值进行更改为>=0的数
			for (int i = 0; i < data.size(); i++) {
				List<LineViewPointBean> temp = data.get(i);
				LineViewPointBean tempBean = temp.get(touchRegion);
				stockList.add(tempBean);
			}
			int temp = 0;
			for (int i = 0; i < stockList.size() - 1; i++) {
				temp = Integer.parseInt(stockList.get(i).getyData());
				if (temp > Integer.parseInt(stockList.get(i + 1).getyData())) {
					// 如果现在的比下一个大,那么交换位置,把临时变量设为下一个的值
					LineViewPointBean  tempLineViewBean = stockList.get(i);
					stockList.set(i, stockList.get(i+1));
					stockList.set(i+1, tempLineViewBean);
				} 
			}
		}

		// 画选中的点和文字
		if (touchRegion >= 0) {// 初始化touchRegion为-1,如果有点击到相应区域会对此值进行更改为>=0的数
			for (int i = 0; i < stockList.size(); i++) {
				switch (i) {
				case 0:
					canvas.drawText(stockList.get(i).getyData(), stockList.get(i).getPosition().x + 5,
							stockList.get(i).getPosition().y + 1.5f * TEXT_SIZE_DATA, paintTextData[i]);
					break;
				case 1:
					canvas.drawText(stockList.get(i).getyData(), stockList.get(i).getPosition().x + 5,
							stockList.get(i).getPosition().y + 1.0f * TEXT_SIZE_DATA, paintTextData[i]);

					break;
				default:
					break;
				}

			}
		}
	}

	void init() {
		// 获取控件宽高
		width = getMeasuredWidth();
		height = getMeasuredHeight();
		// 计算xStart,yStart
		xStart = MARGIN_SIZE_HORIZONTAL;
		yStart = MARGIN_SIZE_VERTICAL + (height - 2 * MARGIN_SIZE_VERTICAL);
		
		MARGIN_SIZE_HORIZONTAL = width/10;//y周距离左边
		MARGIN_SIZE_VERTICAL = height/3;//x周距离下边
		// 计算SCALE_X SCALE_Y
		SCALE_X = (width -2 * MARGIN_SIZE_HORIZONTAL) / (xData.length - 1);//x刻度长度
		SCALE_Y = (height -  MARGIN_SIZE_VERTICAL) / (yData.length + 2);//y刻度长度
		// 初始化颜色 可以在此处进行颜色的更改
		LINE_COLOR_X = "#333333";
		LINE_COLOR_Y = "#333333";
		TEXT_COLOR_X = "#333333";
		if(color==1) {
			TEXT_COLOR_Y = "#333333";
		}else {
			TEXT_COLOR_Y = "#ffffff";
		}
		for (int i = 0; i < TEXT_COLOR_DATA.length; i++) {
			TEXT_COLOR_DATA[i] = "#3399cc";
			LINE_COLOR_DATA[i] = "#666666";
		}
		if (TEXT_COLOR_DATA.length == 2) {//两条
			TEXT_COLOR_DATA[1] = "#666666";
			LINE_COLOR_DATA[1] = "#3399cc";
		}


		if (TEXT_COLOR_DATA.length ==3) {//三条
			TEXT_COLOR_DATA[0] = "#C15159";
			LINE_COLOR_DATA[0] = "#C15159";

			TEXT_COLOR_DATA[1] = "#A1C5AA";
			LINE_COLOR_DATA[1] = "#A1C5AA";

			TEXT_COLOR_DATA[2] = "#6EB3E4";
			LINE_COLOR_DATA[2] = "#6EB3E4";



		}


		if (TEXT_COLOR_DATA.length ==4) {//四条

			TEXT_COLOR_DATA[0] = "#C15159";
			LINE_COLOR_DATA[0] = "#C15159";

			TEXT_COLOR_DATA[1] = "#A1C5AA";
			LINE_COLOR_DATA[1] = "#A1C5AA";



			TEXT_COLOR_DATA[2] = "#F2D75F";
			LINE_COLOR_DATA[2] = "#F2D75F";

			TEXT_COLOR_DATA[3] = "#6EB3E4";
			LINE_COLOR_DATA[3] = "#6EB3E4";




		}

		// 初始化文字大小
		TEXT_SIZE_X = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics());//x轴上的数字大小
		TEXT_SIZE_Y = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 12, getResources().getDisplayMetrics());//y轴上的数字大小
		TEXT_SIZE_DATA = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 10, getResources().getDisplayMetrics());//数据数字大小
		// 初始化画笔
		paintLineX = paintSetColor(LINE_COLOR_X);
		paintLineX.setStrokeWidth(2);//设置线的宽度
		paintLineY = paintSetColor(LINE_COLOR_Y);
		paintLineY.setStrokeWidth(2);
		paintTextX = paintSetColor(TEXT_COLOR_X);
		paintTextX.setTextSize(TEXT_SIZE_X);
		paintTextY = paintSetColor(TEXT_COLOR_Y);
		paintTextY.setTextSize(TEXT_SIZE_Y);
		for (int i = 0; i < TEXT_COLOR_DATA.length; i++) {
			paintLineData[i] = paintSetColor(LINE_COLOR_DATA[i]);
			paintTextData[i] = paintSetColor(TEXT_COLOR_DATA[i]);
			paintTextData[i].setTextSize(TEXT_SIZE_DATA);
		}
		// 处理dataSource
		handleDataSource();
	}

	/**
	 * 初始化画笔的辅助方法.
	 * 
	 * @param
	 * @param color
	 */
	Paint paintSetColor(String color) {
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		paint.setAntiAlias(true);// 去锯齿
		paint.setColor(Color.parseColor(color));
		return paint;
	}

	void handleDataSource() {
		data = new ArrayList<List<LineViewPointBean>>();
		for (int i = 0; i < dataSource.size(); i++) {
			// 有多少条线
			List<String> tempDataSource = dataSource.get(i);
			List<LineViewPointBean> tempData = new ArrayList<LineViewPointBean>();
			for (int j = 0; j < tempDataSource.size(); j++) {
				// 每条线有多少点
				LineViewPointBean tempLineViewPointBean = new LineViewPointBean();
				tempLineViewPointBean.setxData(j + "");
				tempLineViewPointBean.setyData(tempDataSource.get(j));
				tempLineViewPointBean.setSort(i);
				PointF temPointF = new PointF();
				// 通过数据值,计算每个点
				Float x = 0f, y = 0f;
				x = (float) (xStart + j * SCALE_X);
				if (Float.parseFloat(yData[1]) == 0) {
					// 如果最大值和最小值都为0,那么应该做特殊处理.将0的位置设为y轴中间,同时所有的数据都为0
					y = (float) (yStart - 2 * SCALE_Y);
					tempLineViewPointBean.setyData("0");
				} else {
					y = yStart - SCALE_Y / 2 - ((Float.parseFloat(tempDataSource.get(j)) - Float.parseFloat(yData[0]))
							/ (Float.parseFloat(yData[1]) - Float.parseFloat(yData[0]))) * 3 * SCALE_Y;
				}
				temPointF.set(x, y);
				tempLineViewPointBean.setPosition(temPointF);
				tempData.add(tempLineViewPointBean);
			}
			data.add(tempData);
		}
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_MOVE:
			for (int i = 0; i < data.get(0).size(); i++) {
				RectF tempRectF = new RectF((float) (xStart + (i - 0.5) * SCALE_X), 0f,
						(float) (xStart + (i + 0.5) * SCALE_X), (float) yStart);
				if (tempRectF.contains(event.getX(), event.getY())) {
					touchRegion = i;
					if(color==2){//用来在裸车毛利那里点击不让显示值
						touchRegion = -1;
					}
					postInvalidate();
					return true;
				}
			}
			break;
		case MotionEvent.ACTION_DOWN:
			for (int i = 0; i < data.get(0).size(); i++) {
				RectF tempRectF = new RectF((float) (xStart + (i - 0.5) * SCALE_X), 0f,
						(float) (xStart + (i + 0.5) * SCALE_X), (float) yStart);
				if (tempRectF.contains(event.getX(), event.getY())) {
					touchRegion = i;
					if(color==2){//用来在裸车毛利那里点击不让显示值
						touchRegion = -1;
					}
					postInvalidate();
					return true;
				}
			}
			break;
		}
		return true;
	}

	/**
	 * 当数据发生变化时,调用的方法.如果没有变化的数据可以传null
	 * 
	 * @param dataSource
	 * @param yData
	 * @param xData
	 */
	public void DataSouceChanged(List<List<String>> dataSource, String[] yData, String[] xData) {
		if (dataSource != null) {
			this.dataSource = dataSource;
		}
		if (yData != null) {
			this.yData = yData;
		}
		if (xData != null) {
			this.xData = xData;
		}
		postInvalidate();
	};

	public String getTEXT_COLOR_X() {
		return TEXT_COLOR_X;
	}

	/**
	 * 设置x轴文字颜色的方法
	 * 
	 * @param tEXT_COLOR_X
	 */
	public void setTEXT_COLOR_X(String tEXT_COLOR_X) {
		TEXT_COLOR_X = tEXT_COLOR_X;
		postInvalidate();
	}

	public String getTEXT_COLOR_Y() {
		return TEXT_COLOR_Y;
	}

	/**
	 * 设置y轴文字颜色的方法
	 * 
	 * @param tEXT_COLOR_Y
	 */
	public void setTEXT_COLOR_Y(String tEXT_COLOR_Y) {
		TEXT_COLOR_Y = tEXT_COLOR_Y;
		postInvalidate();
	}

	public String getLINE_COLOR_X() {
		return LINE_COLOR_X;
	}

	/**
	 * 设置x轴颜色的方法
	 * 
	 * @param lINE_COLOR_X
	 */
	public void setLINE_COLOR_X(String lINE_COLOR_X) {
		LINE_COLOR_X = lINE_COLOR_X;
		postInvalidate();
	}

	public String getLINE_COLOR_Y() {
		return LINE_COLOR_Y;
	}

	/**
	 * 设置y轴颜色的方法
	 * 
	 * @param lINE_COLOR_Y
	 */
	public void setLINE_COLOR_Y(String lINE_COLOR_Y) {
		LINE_COLOR_Y = lINE_COLOR_Y;
		postInvalidate();
	}

	public String[] getTEXT_COLOR_DATA() {
		return TEXT_COLOR_DATA;
	}

	/**
	 * 设置数据文字颜色的方法,颜色顺序与数据顺序一致
	 * 
	 * @param tEXT_COLOR_DATA
	 */
	public void setTEXT_COLOR_DATA(String[] tEXT_COLOR_DATA) {
		if (tEXT_COLOR_DATA.length == TEXT_COLOR_DATA.length) {
			TEXT_COLOR_DATA = tEXT_COLOR_DATA;
			postInvalidate();
		}
	}

	public String[] getLINE_COLOR_DATA() {
		return LINE_COLOR_DATA;
	}

	/**
	 * 设置数据线的颜色的方法,颜色顺序与数据顺序一致
	 * 
	 * @param lINE_COLOR_DATA
	 */
	public void setLINE_COLOR_DATA(String[] lINE_COLOR_DATA) {
		if (lINE_COLOR_DATA.length == LINE_COLOR_DATA.length) {
			LINE_COLOR_DATA = lINE_COLOR_DATA;
			postInvalidate();
		}
	}

	/**
	 * 封装了LineView中数据点的类,每个点有4个属性,分别是他的x和y的值,以及在lineView中的position,还有他属于第几组数据
	 * 
	 * @author CK
	 * 
	 */
	class LineViewPointBean {
		String xData, yData;// x,y的数据
		PointF position;// 每个点在当前屏幕中的位置
		int sort;// 每个点属于哪类数据

		public String getxData() {
			return xData;
		}

		public void setxData(String xData) {
			this.xData = xData;
		}

		public String getyData() {
			return yData;
		}

		public void setyData(String yData) {
			this.yData = yData;
		}

		public PointF getPosition() {
			return position;
		}

		public void setPosition(PointF position) {
			this.position = position;
		}

		public int getSort() {
			return sort;
		}

		public void setSort(int sort) {
			this.sort = sort;
		}

		/**
		 * 每个点绘制自己的方法
		 * 
		 * @param canvas
		 * @param pointPaint
		 */
		public void drawPoint(Canvas canvas, Paint pointPaint) {
			canvas.drawCircle(position.x, position.y, 5, pointPaint);
		}

		@Override
		public String toString() {
			return "LineViewPointBean [xData=" + xData + ", yData=" + yData + ", position=" + position + ", sort="
					+ sort + "]";
		}
	}

	@Override
	public void onAnimationUpdate(ValueAnimator animation) {
		this.invalidate();
	}
}

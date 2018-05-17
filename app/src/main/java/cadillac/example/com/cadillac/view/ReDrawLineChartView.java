package cadillac.example.com.cadillac.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义多折线demo
 * Created by bitch-1 on 2017/7/4.
 */

public class ReDrawLineChartView extends View implements ValueAnimator.AnimatorUpdateListener{


    private String[] SPPED_SCALES1 = new String[6];//{ "0", "10", "20","30", "40","120"};
    private List<String> dates;//x轴坐标值
    private List<List<Float>> ydatalist;//y轴坐标值
//    private String[] colors = {"#cc0033", "#00406C", "#BDAB6F","#ffffff"};
//    private String []colors;
      private List<String>colors;
    private List<Float> ylist = new ArrayList<>();
    // animation
    public float ClipX = 0.3f;

    private Paint linePaint = new Paint();//背景线。x轴y轴
    private Paint textPaint = new Paint();//文字
    private Paint circelPaint = new Paint();//拐点圆圈。
    private Paint innerCircelPaint = new Paint();//内圆。
    private Paint chartLinePaint = new Paint();//折线


    //基准点。
    float topMargin ;//距离顶部距离
    float btmMargin ;//距离底部距离
    float xAxisTextMargin ;
    float gridX;
    float gridY ;
    float yStart;
    //XY间隔。
    float xSpace ;
    float ySpace;

    float finalMaxNum;
    List<List<LineViewPointBean>> data;

    public ReDrawLineChartView(Context context) {
        // TODO Auto-generated constructor stub
        super(context);

    }

    public ReDrawLineChartView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public ReDrawLineChartView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    //进行真实数据赋值。
    public void setData(List<String> dates, List<List<Float>> ydatalist,List<String>colors) {
        this.dates = dates;//x轴的数字
        this.ydatalist = ydatalist;//y轴数字
        this.colors=colors;

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

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制模式-虚线作为背景线。
        PathEffect effect = new DashPathEffect(new float[]{6, 6, 6, 6, 6}, 2);
        //背景虚线路径.
        Path path = new Path();

        linePaint.setStyle(Paint.Style.STROKE);//x轴y轴画笔
        linePaint.setStrokeWidth((float) 0.7);
        linePaint.setColor(Color.parseColor("#666666"));
        linePaint.setAntiAlias(true);// 锯齿不显示
        textPaint.setStyle(Paint.Style.FILL);// 设置非填充
        textPaint.setStrokeWidth(1);// 笔宽5像素
        textPaint.setColor(Color.parseColor("#666666"));// 设置为蓝笔
        textPaint.setAntiAlias(true);// 锯齿不显示
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(15);
        innerCircelPaint.setStyle(Paint.Style.FILL);
        innerCircelPaint.setStrokeWidth(1);
//        innerCircelPaint.setColor(Color.parseColor("#464646"));
        innerCircelPaint.setAntiAlias(true);


        chartLinePaint.setStyle(Paint.Style.FILL);//折线
        chartLinePaint.setStrokeWidth(3);
        chartLinePaint.setAntiAlias(true);




        ylist.clear();
        for (int i = 0; i < ydatalist.size(); i++) {
            for (int j = 0; j < ydatalist.get(i).size(); j++) {
                ylist.add(ydatalist.get(i).get(j));
            }
        }

        //TODO 算Y轴的刻度。
        float tempLine1Max = getMaxNumFromArr(ylist);//计算出集合里面最大值

        finalMaxNum = getRelativeNum(tempLine1Max);//根据最大值找出临近的最大值--方便Y刻度标注。
        for (int i = 0; i <= 5; i++) {//循环对Y刻度赋值。便于显示。
            if (tempLine1Max >= 0 && tempLine1Max < 1) {
                SPPED_SCALES1[i] = String.valueOf((int) ((float) finalMaxNum / 5 * i)) + "%";//百分数的Y轴刻度。
            } else {
                SPPED_SCALES1[i] = String.valueOf(0 + (int) ((float) finalMaxNum / 5 * i));
            }
        }

        //基准点。
        topMargin = 50;//距离顶部距离
        btmMargin = 10;//距离底部距离
        xAxisTextMargin = 30;
        gridX = 30 + 10;//x轴开始位置
        gridY = getHeight() - btmMargin;
        yStart = gridY - xAxisTextMargin;
        //XY间隔。
        xSpace = (float) ((getWidth() * 0.8) / dates.size());
        ySpace = (float) ((yStart - topMargin) / (SPPED_SCALES1.length - 1));

        handleData();

        float y = 0;//Y间隔。
        //画X轴+背景虚线。
        canvas.drawLine(gridX, yStart, getWidth() - 40, yStart, linePaint);//X轴.

        for (int n = 0; n < SPPED_SCALES1.length; n++) {
            y = yStart - n * ySpace;
            linePaint.setPathEffect(effect);//设法虚线间隔样式。
            //画除X轴之外的------背景虚线-------
            if (n > 0) {
                path.moveTo(gridX, y);//背景【虚线起点】。
                path.lineTo(getWidth() - 55 + 10, y);//背景【虚线终点】。
                canvas.drawPath(path, linePaint);
            }
            //画Y轴刻度。
            canvas.drawText(SPPED_SCALES1[n], gridX - 6 - 7, y, textPaint);
        }

        //绘制X刻度坐标。
        float x = 0;
        if (dates != null && dates.size() != 0) {
            for (int n = 0; n < dates.size(); n++) {
                //取X刻度坐标.
                x = gridX + (n + 1) * xSpace;//在原点(0,0)处不画刻度,向右移动一个跨度。
                //画X轴具体刻度值。
                if (dates.get(n) != null) {
                    canvas.drawLine(x, yStart, x, gridY - 18, linePaint);//短X刻度。
                    canvas.drawText(dates.get(n), x, gridY + 5, textPaint);//X具体刻度值。
                }
            }
        }

        //起始点。
        float lastPointX = 0;
        float lastPointY = 0;
        float currentPointX = 0;
        float currentPointY = 0;
        if (ydatalist != null && ydatalist.size() != 0) {
            for (int j = 0; j < ydatalist.size(); j++) {
                List<Float> list = ydatalist.get(j);
                //1.绘制折线。
                for (int n = 0; n < dates.size(); n++) {
                    //get current point
                    currentPointX = n * xSpace + xSpace + gridX;
                    currentPointY = (float) (yStart) - list.get(n) * (yStart - topMargin) / (finalMaxNum);
                    if (n > 0) {
                        chartLinePaint.setColor(Color.parseColor(colors.get(j)));
                        canvas.drawLine(lastPointX, lastPointY, currentPointX, currentPointY, chartLinePaint);//第一条线[黄色]
                    }
                    circelPaint.setColor(Color.parseColor(colors.get(j)));//(1)黄色
                    innerCircelPaint.setColor(Color.parseColor(colors.get(j)));//内圈也是黄色
                    canvas.drawCircle(currentPointX, currentPointY, 8, circelPaint);
                    canvas.drawCircle(currentPointX, currentPointY, 5, innerCircelPaint);
                    lastPointX = currentPointX;
                    lastPointY = currentPointY;
                }
            }
        }



    }

    /**
     * 处理数据
     */
    private void handleData() {
        data = new ArrayList<List<LineViewPointBean>>();
        for(int i=0;i<ydatalist.size();i++){
            List<Float> tempDataSource = ydatalist.get(i);
            List<LineViewPointBean> tempData = new ArrayList<LineViewPointBean>();

            for(int j=0;j<tempDataSource.size();j++){//每条线有多少点
                LineViewPointBean tempLineViewPointBean = new LineViewPointBean();
                tempLineViewPointBean.setxData(j + "");
                tempLineViewPointBean.setyData(tempDataSource.get(j)+"");
                PointF temPointF = new PointF();
                // 通过数据值,计算每个点
                Float x = 0f, y = 0f;
                x = gridX+ (i + 1) * xSpace;
                y= (float) (yStart) - tempDataSource.get(j) * (yStart - topMargin) / (finalMaxNum);
                temPointF.set(x, y);
                tempLineViewPointBean.setPosition(temPointF);
                tempData.add(tempLineViewPointBean);
            }
            data.add(tempData);
        }
    }

    int clickCount = 0;
    long firstClickTime = 0;
    long secondClickTime = 0;

    /**
     * 手势监听--处理双击事件。
     */


    /**
     * 取数组中的最大元素
     *
     * @param list 取值的数组
     * @return 返回数组中的最大值。
     */
    private float getMaxNumFromArr(List<Float> list) {
        float maxNum = list.get(0);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) > maxNum) {
                maxNum = list.get(i);
            }
        }
        return maxNum;
    }


    /**
     * 根据数组最大值来获取临近最大值--来作为刻度的最大值----方便分配刻度。
     *
     * @param num 传入的值
     * @return 返回num附近的整数值, 方便刻度分配。
     */
    private float getRelativeNum(float num) {
        float desNum = 0;
        if (num >= 0 && num < 1) {
            desNum = 10;
        } else if (num >= 1 && num <= 10) {
            desNum = 10;
        } else if (num >= 10 && num <= 20) {
            desNum = 20;
        } else if (num >= 20 && num <= 30) {
            desNum = 30;
        } else if (num >= 30 && num <= 40) {
            desNum = 40;
        } else if (num >= 40 && num <= 50) {
            desNum = 50;
        } else if (num >= 50 && num <= 100) {
            desNum = 100;
        } else if (num > 100 && num < 1000) {
            int num1 = (int) num % 100;//余数-个十位。
            int num2 = (int) num / 100;//高位-百位。
            if (num1 > 0 && num1 <= 50) {
                desNum = num2 * 100 + 50;//取临近的整数值作为Y轴刻度。
            } else if (num1 > 50 && num1 < 100) {
                desNum = num2 * 100 + 100;
            }
        } else if (num >= 1000 && num < 1500) {
            desNum = handleY(num, desNum, 1000);
        } else if (num >= 1500 && num < 2000) {
            desNum = handleY(num, desNum, 1000);
        } else if (num >= 2000 && num < 3000) {
            desNum = handleY(num, desNum, 1000);
        } else if (num >= 3000 && num < 4000) {
            desNum = handleY(num, desNum, 1000);
        } else if (num >= 4000 && num < 5000) {
            desNum = handleY(num, desNum, 1000);
        } else if (num >= 5000 && num < 10000) {
            desNum = handleY(num, desNum, 1000);
        }
        return desNum;
    }

    /**
     * 具体处理Y轴刻度
     *
     * @param sourceNum      传入的实际值
     * @param desNum         要返回的目标值
     * @param highPostionNum 实际值的最高位
     * @return 将传入的目标值赋值后返回
     */
    private float handleY(float sourceNum, float desNum, int highPostionNum) {
        int num1 = (int) sourceNum % highPostionNum;//余数-个十位。
        int num2 = (int) sourceNum / highPostionNum;//高位-百位。
        if (num1 > 0 && num1 <= highPostionNum / 2) {
            desNum = num2 * highPostionNum + highPostionNum / 2;//取临近的整数值作为Y轴刻度。
        } else if (num1 > highPostionNum / 2 && num1 < highPostionNum) {
            desNum = num2 * highPostionNum + highPostionNum;
        }
        return desNum;
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
    public void onAnimationUpdate(ValueAnimator valueAnimator) {
        this.invalidate();
    }
}
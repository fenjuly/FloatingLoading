package com.fenjuly.floatingloading;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by liurongchan on 15/4/27.
 */
public class FloatingLoading extends View {

    private static final int POINTS_COUNT = 4;

    private static final int[] default_colors = new int[] {Color.rgb(0, 155, 254), Color.rgb(6, 128, 209),
            Color.rgb(6, 109, 177), Color.rgb(255, 255, 255)
    };

    private static final int STATUS_BIG = 1;
    private static final int STATUS_MIDDLE = 2;
    private static final int STATUS_SMALL = 3;
    private static final int STATUS_NORMAL = 4;

    private int[] shownColors = new int[POINTS_COUNT];

    private Paint[] colorPaints = new Paint[POINTS_COUNT];

    private CirclePoint[] typePoints = new CirclePoint[POINTS_COUNT];

    private CirclePoint[] circlePoints = new CirclePoint[POINTS_COUNT];


    public FloatingLoading(Context context) {
        this(context, null);
    }

    public FloatingLoading(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatingLoading(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        final TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.FloatingLoading,
                defStyleAttr, 0);

        attributes.recycle();
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measure(widthMeasureSpec, true), measure(heightMeasureSpec, false));
    }

    private int measure(int measureSpec, boolean isWidth) {
        int result;
        int mode = MeasureSpec.getMode(measureSpec);
        int size = MeasureSpec.getSize(measureSpec);
        int padding = isWidth ? getPaddingLeft() + getPaddingRight() : getPaddingTop() + getPaddingBottom();
        if (mode == MeasureSpec.EXACTLY) {
            result = size;
        } else {
            result = isWidth ? getSuggestedMinimumWidth() : getSuggestedMinimumHeight();
            result += padding;
            if (mode == MeasureSpec.AT_MOST) {
                if (isWidth) {
                    result = Math.max(result, size);
                } else {
                    result = Math.min(result, size);
                }
            }
        }
        return result;
    }

    private int count = -1;
    @Override
    protected void onDraw(Canvas canvas) {
        count++;
        if (count == 0) {
            initializePoints();
        }

       int remainder = count % POINTS_COUNT;
       for (int i = 0; i < POINTS_COUNT; i++) {
           int orig_position = (i + POINTS_COUNT - remainder) % POINTS_COUNT;
           circlePoints[i].raduis = typePoints[orig_position].raduis;
           circlePoints[i].color = typePoints[orig_position].color;

           Log.e("exchange", i + "-" + orig_position);
       }

        for (CirclePoint c : circlePoints) {
            Log.e("point", c.toString());
        }
       for (int i = 0; i < POINTS_COUNT; i++) {
           CirclePoint p = circlePoints[i];
           Paint t = colorPaints[i];
           t.setColor(p.color);
           canvas.drawCircle(p.x, p.y, p.raduis, t);
       }
        postInvalidateDelayed(150);
    }

    protected void init() {
        initializePaints();

    }



    protected void initializePaints() {
        for (int i = 0; i < POINTS_COUNT; i++) {
            colorPaints[i] = new Paint(Paint.ANTI_ALIAS_FLAG);
            colorPaints[i].setColor(default_colors[i]);
        }
    }

    protected void initializePoints() {
        int width_step = getWidth() / 8;
        int height = getHeight();
        int raduis_step = height / 8;
        for (int i = 0; i < POINTS_COUNT; i++) {
            CirclePoint p = new CirclePoint();
            p.x = getPaddingLeft() + (2 * i + 1) * width_step;
            p.y = getPaddingTop() + height / 2;
            p.color = default_colors[i];
            if (i == 3) {
                p.raduis = circlePoints[1].raduis;
            } else {
                p.raduis = raduis_step * (int) Math.pow(2, i);
            }
            circlePoints[i] = p;
            typePoints[i] = new CirclePoint(p.x, p.y, p.raduis, p.color);
        }

    }

    static class CirclePoint {
        public float raduis;
        public float x;
        public float y;
        public int color;

    public CirclePoint() {
    }
    public CirclePoint(float x, float y, float raduis, int color) {
        this.x = x;
        this.y = y;
        this.raduis = raduis;
        this.color = color;
    }

        @Override
        public String toString() {
            return "CirclePoint{" +
                    "raduis=" + raduis +
                    ", x=" + x +
                    ", y=" + y +
                    ", color=" + color +
                    '}';
        }
    }

}

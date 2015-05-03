package com.fenjuly.mylibrary;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by fenjuly with love
 */
public class FloatingLoading extends View {

    private static final int POINTS_COUNT = 4;
    private static final int DURATION = 150;

    private static final int[] default_colors = new int[] {
            Color.rgb(0, 155, 254), Color.rgb(6, 128, 209),
            Color.rgb(6, 109, 177), Color.rgb(255, 255, 255)
    };

    private static final int[] color_resources = new int[] {
            R.styleable.FloatingLoading_loading_large_color,
            R.styleable.FloatingLoading_loading_middle_color,
            R.styleable.FloatingLoading_loading_small_color,
            R.styleable.FloatingLoading_loading_special_color
    };

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
        for (int i = 0; i < POINTS_COUNT; i++) {
            shownColors[i] = attributes.getColor(color_resources[i], default_colors[i]);
        }
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
    private int duration = DURATION;
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
       }

       for (int i = 0; i < POINTS_COUNT; i++) {
           CirclePoint p = circlePoints[i];
           Paint t = colorPaints[i];
           t.setColor(p.color);
           canvas.drawCircle(p.x, p.y, p.raduis, t);
       }
        postInvalidateDelayed(duration);
    }

    public void setPointColor(int position, int color) {
        if (!(position >= 0 && position < POINTS_COUNT))
            return;
        shownColors[position] = color;
        if (typePoints[position] != null) {
            typePoints[position].color = color;
        }
    }

    public void setPointsColor(int[] color_array) {
        if (color_array.length > POINTS_COUNT)
            return;
        for (int i = 0; i < POINTS_COUNT; i++) {
            shownColors[i] = color_array[i];
            if (typePoints[i] != null) {
                typePoints[i].color = color_array[i];
            }
        }
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    protected void init() {
        initializePaints();
    }


    protected void initializePaints() {
        for (int i = 0; i < POINTS_COUNT; i++) {
            colorPaints[i] = new Paint(Paint.ANTI_ALIAS_FLAG);
        }
    }

    protected void initializePoints() {
        int width_step = getWidth() / 8;
        int height = getHeight();
        int raduis_base = height / 3;
        for (int i = 0; i < POINTS_COUNT; i++) {
            CirclePoint p = new CirclePoint();
            p.x = getPaddingLeft() + (2 * i + 1) * width_step;
            p.y = getPaddingTop() + height / 2;
            p.color = shownColors[i];
            if (i == 3) {
                p.raduis = circlePoints[1].raduis;
            } else {
                p.raduis = raduis_base;
                raduis_base = raduis_base * 2 / 3;

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
    }
}

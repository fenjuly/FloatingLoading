package com.fenjuly.floatingloading;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by liurongchan on 15/4/27.
 */
public class FloatingLoading extends View {

    private static final String LOADING_COLOR = "loading_color";

    private int first_shown_color;
    private int second_shown_color;
    private int third_shown_color;

    private final int default_first_shown_color = Color.rgb(0, 155, 254);
    private final int default_second_shown_color = Color.rgb(6, 128, 209);
    private final int default_third_shown_color = Color.rgb(6, 109, 177);
    private final int default_color = Color.rgb(255, 255, 255);

    public FloatingLoading(Context context) {
        this(context, null);
    }

    public FloatingLoading(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatingLoading(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //load styled attributes.
        final TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.FloatingLoading,
                defStyleAttr, 0);

        first_shown_color = attributes.getColor(R.styleable.FloatingLoading_loading_color, default_first_shown_color);
        second_shown_color = default_second_shown_color;
        third_shown_color = default_third_shown_color;


        attributes.recycle();
    }
}

package com.fenjuly.floatingloading;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;

import com.fenjuly.mylibrary.FloatingLoading;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final FloatingLoading v1 = (FloatingLoading) findViewById(R.id.sampple1);
        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v1.setPointColor(0, Color.GREEN);
                v1.setDuration(2000);
            }
        });
    }
}

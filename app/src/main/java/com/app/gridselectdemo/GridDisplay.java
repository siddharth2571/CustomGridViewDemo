package com.app.gridselectdemo;

import android.app.AppComponentFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

public class GridDisplay extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PixelGridView pixelGrid = new PixelGridView(this);
        pixelGrid.setNumColumns(40);
        pixelGrid.setNumRows(40);

        setContentView(pixelGrid);
    }
}

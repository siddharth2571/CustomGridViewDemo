package com.app.gridselectdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioGroup;

import java.util.HashMap;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {

    RadioGroup radioGroup;
    private Paint blackPaint = new Paint();
    private Paint pinkPaint = new Paint();
    private Paint greenPaint = new Paint();
    LinearLayout llMain;
    int selectFlag = 0;
    private static boolean[][] cellChecked = new boolean[40][20];
    private static boolean[][] cellCheckedRed = new boolean[40][20];
    private static boolean[][] cellCheckedGreen = new boolean[40][20];
    private static HashMap<Integer, boolean[][]> hashMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        llMain = findViewById(R.id.llMain);
        final PixelGridView pixelGrid = new PixelGridView(this);
        pixelGrid.setNumColumns(40);
        pixelGrid.setNumRows(20);
        llMain.addView(pixelGrid);

        radioGroup = (RadioGroup) findViewById(R.id.myRadioGroup);
        pinkPaint.setColor(getResources().getColor(R.color.black));
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // find which radio button is selected
                if (checkedId == R.id.black) {
                    selectFlag = 0;
                    blackPaint.setColor(getResources().getColor(R.color.black));
                } else if (checkedId == R.id.red) {
                    selectFlag = 1;
                    pinkPaint.setColor(getResources().getColor(R.color.red));
                } else {
                    selectFlag = 2;
                    greenPaint.setColor(getResources().getColor(R.color.green));
                }

                for (int color : hashMap.keySet()) {
//                Log.e("data:", "--" + color + "," + hashMap.get(color).length + " :: " + hashMap.get(color)[hashMap.get(color).length - 1][0]);
                    boolean data[][] = hashMap.get(color);
                    for (int i = 0; i < data.length; i++) {
                        for (int j = 0; j < data[i].length; j++) {
                            if (data[i][j]) {
                                Log.e("data:", "--" + color + ",::" + data[i][j]);
                            }

                        }

                    }
                }
            }

        });
    }

    public class PixelGridView extends View {
        private int numColumns, numRows, numColor;
        private int cellWidth, cellHeight;
        Canvas canvas_;

        public PixelGridView(Context context) {
            this(context, null);
        }

        public PixelGridView(Context context, AttributeSet attrs) {
            super(context, attrs);
            blackPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        }

        public void setNumColumns(int numColumns) {
            this.numColumns = numColumns;
            calculateDimensions();
        }

        public int getNumColumns() {
            return numColumns;
        }

        public void setNumRows(int numRows) {
            this.numRows = numRows;
            calculateDimensions();
        }

        public void setNumColor(int numColor) {
            this.numColor = numColor;
            calculateDimensions();
        }

        public int getNumRows() {
            return numRows;
        }

        @Override
        protected void onSizeChanged(int w, int h, int oldw, int oldh) {
            super.onSizeChanged(w, h, oldw, oldh);
            calculateDimensions();
        }

        private void calculateDimensions() {
            if (numColumns < 1 || numRows < 1) {
                return;
            }

            cellWidth = getWidth() / numColumns;
            cellHeight = getHeight() / numRows;

            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            canvas.drawColor(Color.WHITE);
            canvas_ = canvas;

            if (numColumns == 0 || numRows == 0) {
                return;
            }

            int width = getWidth();
            int height = getHeight();

            for (int color : hashMap.keySet()) {
//                Log.e("data:", "--" + color + "," + hashMap.get(color).length + " :: " + hashMap.get(color)[hashMap.get(color).length - 1][0]);
                boolean data[][] = hashMap.get(color);
                if (color == 0) {
                    for (int i = 0; i < data.length; i++) {
                        for (int j = 0; j < data[i].length; j++) {
                            if (data[i][j]) {
                                Log.e("data:", "--" + color + ",::" + data[i][j] + ":: " + i + ";;" + j);
                                canvas.drawRect(i * cellWidth, j * cellHeight,
                                        (i + 1) * cellWidth, (j + 1) * cellHeight,
                                        blackPaint);
                            }
                        }
                    }
                } else if (color == 1) {
                    for (int i = 0; i < data.length; i++) {
                        for (int j = 0; j < data[i].length; j++) {
                            if (data[i][j]) {
                                Log.e("data:", "--" + color + ",::" + data[i][j] + ":: " + i + ";;" + j);
                                canvas.drawRect(i * cellWidth, j * cellHeight,
                                        (i + 1) * cellWidth, (j + 1) * cellHeight,
                                        pinkPaint);
                            }
                        }

                    }
                }
            }



            /*for (int i = 0; i < numColumns; i++) {
                for (int j = 0; j < numRows; j++) {
                    if (cellChecked[i][j]) {
                        Log.e("color", "::+" + selectFlag);
                        canvas.drawRect(i * cellWidth, j * cellHeight,
                                (i + 1) * cellWidth, (j + 1) * cellHeight,
                                blackPaint);
                    }
                }
            }*/


            for (int i = 1; i < numColumns; i++) {
                canvas.drawLine(i * cellWidth, 0, i * cellWidth, height, blackPaint);
            }

            for (int i = 1; i < numRows; i++)

            {
                canvas.drawLine(0, i * cellHeight, width, i * cellHeight, blackPaint);
            }
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_MOVE) {
                int column = (int) (event.getX() / cellWidth);
                int row = (int) (event.getY() / cellHeight);

                Log.e("selectFlag", "--" + selectFlag);

                if (column < 0 || row < 0) {
                    return false;
                }
//                cellChecked[column][row] = true;
//                hashMap.put(selectFlag, cellChecked);

                if(selectFlag==0){
                    cellChecked[column][row] = true;
                  hashMap.put(selectFlag, cellChecked);
                }else if(selectFlag==1){
                    cellCheckedRed[column][row] = true;
                    hashMap.put(selectFlag, cellCheckedRed);
                }else  if(selectFlag==2){
                    cellCheckedGreen[column][row] = true;
                    hashMap.put(selectFlag, cellCheckedGreen);
                }

                //

                /*if (selectFlag == 0) {
                    canvas_.drawRect(column * cellWidth, row * cellHeight,
                            (column + 1) * cellWidth, (row + 1) * cellHeight,
                            blackPaint);
                } else if (selectFlag == 1) {
                    cellCheckedRed[column][row] = true;
                    canvas_.drawRect(column * cellWidth, row * cellHeight,
                            (column + 1) * cellWidth, (row + 1) * cellHeight,
                            pinkPaint);
                } else {
                    cellCheckedGreen[column][row] = true;
                    canvas_.drawRect(column * cellWidth, row * cellHeight,
                            (column + 1) * cellWidth, (row + 1) * cellHeight,
                            greenPaint);
                }*/

                //cellChecked[column][row] = !cellChecked[column][row];
                invalidate();
            }
            return true;
        }
    }
}

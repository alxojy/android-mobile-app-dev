package com.fit2081.week10app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MotionEventCompat;

import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {


    TextView actionType;
    TextView getXY;
    TextView getRawXY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        View view=findViewById(R.id.frame_layout_id);
        actionType=findViewById(R.id.action_type);
        getXY=findViewById(R.id.get_x_y_id);
        getRawXY=findViewById(R.id.get_raw_x_y_id);
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int x=(int)event.getX();
                int y=(int)event.getY();
                int rawX=(int)event.getRawX();
                int rawY=(int)event.getRawY();
                getXY.setText(x+","+y);
                getRawXY.setText(rawX+","+rawY);

                int action = event.getActionMasked();
                switch(action) {
                    case (MotionEvent.ACTION_DOWN) :
                        actionType.setText("Down");
                        return true;
                    case (MotionEvent.ACTION_MOVE) :
                        actionType.setText("MOVE");
                        return true;
                    case (MotionEvent.ACTION_UP) :
                        actionType.setText("UP");
                        return true;
                    default :
                        return false;
                }
            }
        });
    }
}

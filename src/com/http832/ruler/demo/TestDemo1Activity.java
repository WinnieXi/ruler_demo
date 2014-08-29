package com.http832.ruler.demo;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.http832.ruler.R;
import com.http832.ruler.view.KeduView;
import com.http832.ruler.view.StaffView;

public class TestDemo1Activity extends Activity {
    
    private ImageView kedu_tiao;
    
    private LinearLayout kedu_linear;
    
    private LinearLayout staff_linear;
    
    private KeduView kedu;
    
    private StaffView staff;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kedu);
        
        kedu_linear = (LinearLayout)findViewById(R.id.kedu_linear);
        kedu = new KeduView(this, 0f, 0.1f);
        kedu_linear.addView(kedu);
        staff_linear = (LinearLayout)findViewById(R.id.staff_linear);
        staff = new StaffView(this, 7.5f, 0.5f, "cm");
        staff_linear.addView(staff);
        
        kedu_tiao = (ImageView)findViewById(R.id.kedu_tiao);
        kedu_tiao.setOnTouchListener(keduListener);
        
    }
    
    private ImageView.OnTouchListener keduListener = new ImageView.OnTouchListener() {
        private float initx = 0;
        
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    initx = event.getX();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float lastx = event.getX();
                    if (lastx > initx + 5) {
                        kedu.draw(1);
                        initx = lastx;
                    } else if (lastx < initx - 5) {
                        kedu.draw(-1);
                        initx = lastx;
                    }
                    break;
            }
            return true;
        }
    };
    
    public void drawImage() {
        
        //        Canvas canvas = new 
    }
    
}
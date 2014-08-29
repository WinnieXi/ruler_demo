package com.http832.ruler.demo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;

import com.http832.ruler.R;
import com.http832.ruler.view.HorizontalRulerView;
import com.http832.ruler.view.VerticalRulerView;

/**
 * <一句话功能简述>
 * @author  neusoft
 * @createon 2014年8月29日
 */
public class TestActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kedu_scroll);
        LinearLayout line = (LinearLayout)findViewById(R.id.kedu_linear);
        int w = getWindowManager().getDefaultDisplay().getWidth() / 2;
        HorizontalRulerView hruler = new HorizontalRulerView(this, 40, 1f, w);
        hruler.initBitMap(R.drawable.userinfo_scroll_year, R.drawable.userinfo_mid_pointer, 80, 90, 5);
        line.addView(hruler);
        hruler.registerCallback(mCallback1);
        LinearLayout line1 = (LinearLayout)findViewById(R.id.kedu_linear1);
        VerticalRulerView vRuler =
            new VerticalRulerView(this, 40, 1f, getWindowManager().getDefaultDisplay().getHeight() / 2);
        vRuler.initBitMap(R.drawable.userinfo_scroll_height, R.drawable.userinfo_mid_vertical_pointer, 175, 180, 4);
        line1.addView(vRuler);
        vRuler.registerCallback(mCallback);
        
        findViewById(R.id.btn).setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestActivity.this, TestDemo1Activity.class));
            }
        });
    }
    
    public int getStateHeight(Activity activity) {
        Rect rect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
        
        Display d = getWindowManager().getDefaultDisplay();
        System.out.println("----top----" + d.getHeight());
        DisplayMetrics dm = new DisplayMetrics();
        d.getMetrics(dm);
        System.out.println(rect.top + "----top----" + d.getHeight());
        return rect.top;
    }
    
    private HorizontalRulerView.ICallback mCallback1 = new HorizontalRulerView.ICallback() {
        
        @Override
        public void changeNum(float result) {
            System.out.println((25 + Math.round(result)) + "------" + result);
        }
        
    };
    
    private VerticalRulerView.ICallback mCallback = new VerticalRulerView.ICallback() {
        
        @Override
        public void changeNum(float result) {
            System.out.println(230 - Math.round(result) + "------");
        }
        
    };
}

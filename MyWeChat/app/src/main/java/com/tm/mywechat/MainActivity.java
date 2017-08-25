package com.tm.mywechat;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.FrameLayout;
import com.tm.mywechat.main_page.MyMainActivity;

import static com.tm.mywechat.FragmentUtils.bitmapToString;

public class MainActivity extends FragmentActivity {
    private FrameLayout frameLayout;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;

    private MyMainActivity myMainActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Resources res=this.getResources();

        initview();
        myfragmentmanager();



    }





    private void myfragmentmanager() {
        fragmentManager = this.getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();//获取一个事务， 该事务可以管理 fragment对象的添加、删除等操作

        /* 默认布局 */
        myMainActivity = new MyMainActivity();
        fragmentTransaction.add(R.id.trade_framlayout, myMainActivity);
        fragmentTransaction.commit();
    }

    private void initview() {
        frameLayout = (FrameLayout) findViewById(R.id.trade_framlayout);
    }
}

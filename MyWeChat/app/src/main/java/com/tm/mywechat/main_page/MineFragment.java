package com.tm.mywechat.main_page;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tm.mywechat.FragmentUtils;
import com.tm.mywechat.Login;
import com.tm.mywechat.MyGoods;
import com.tm.mywechat.R;
import com.tm.mywechat.UpdateUser;
import com.tm.mywechat.util.Constant;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2017/1/10.
 */
public class MineFragment extends Fragment {
    private EditText etAccount;
    private EditText etPassword;
    private TextView tvResult;

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.personal_information_activity, container, false);

        Button btn_change=(Button)view.findViewById(R.id.change);
        Button btn_gm=(Button)view.findViewById(R.id.gm);

        btn_change.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(Login.getLOGIN()==0)
                    Toast.makeText(getContext(),"您还没有登录",Toast.LENGTH_LONG).show();
                else {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), UpdateUser.class);
                    startActivity(intent);
                }


            }


        });

        btn_gm.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(Login.getLOGIN()==0)
                    Toast.makeText(getContext(),"您还没有登录",Toast.LENGTH_LONG).show();
                else {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), MyGoods.class);
                    startActivity(intent);
                }

            }


        });


/*
    *//*典型的子线程试图操作 UI 元素报错，为啥，因为网络请求是在新开的子线程中运行，
    当然不能直接拿到结果就给 TextView 赋值了！怎么做？Android 的Handler消息机制这不就用上了嘛！*//*
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                tvResult.setText(msg.obj.toString());
            }
        }
    };

    private void requestUsingHttpURLConnection() {
        // 网络通信属于典型的耗时操作，开启新线程进行网络请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL("https://www.baidu.com"); // 声明一个URL,注意——如果用百度首页实验，请使用https
                    connection = (HttpURLConnection) url.openConnection(); // 打开该URL连接
                    connection.setRequestMethod("GET"); // 设置请求方法，“POST或GET”，我们这里用GET，在说到POST的时候再用POST
                    connection.setConnectTimeout(8000); // 设置连接建立的超时时间
                    connection.setReadTimeout(8000); // 设置网络报文收发超时时间
                    InputStream in = connection.getInputStream();  // 通过连接的输入流获取下发报文，然后就是Java的流处理
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    //tvContent.setText(response.toString()); // 地雷
                    Message msg = new Message();
                    msg.what = 1;
                    msg.obj = response.toString();
                    Log.e("WangJ", response.toString());
                    handler.sendMessage(msg);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }*/
        return view;
    }
}

package com.tm.mywechat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tm.mywechat.http.bean.CommonRequest;
import com.tm.mywechat.http.bean.CommonResponse;
import com.tm.mywechat.http.interf.ResponseHandler;
import com.tm.mywechat.main_page.MyMainActivity;
import com.tm.mywechat.util.LoadingDialogUtil;

/**
 * Created by SJL on 2017/6/10.
 */

public class Login extends BaseActivity {
    private String URL_LOGIN = "http://192.168.43.165:8080/MyWorld_Service/LoginServlet";
    private String URL_REGISTER = "http://192.168.43.165:8080/MyWorld_Service/RegisterServlet";
    public static String account;
    public static Bitmap useicon;
    public static int LOGIN=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        final EditText etName = (EditText) findViewById(R.id.et_username);
        final EditText etPassword = (EditText) findViewById(R.id.et_pwd);
        TextView textView=(TextView)findViewById(R.id.tv_register);


        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,Register.class);
                startActivity(intent);

            }
        });
        TextView textView2=(TextView)findViewById(R.id.tv_guest);


        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Login.this,MainActivity.class);
                finish();
                startActivity(intent);

            }
        });


        Button btnLogin = (Button) findViewById(R.id.btn_login);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(etName.getText().toString(), etPassword.getText().toString());
            }
        });

    }
    public static int getLOGIN()
    {
        return LOGIN;
    }
    public static String getAccount()
    {
        return account;
    }
    private void login(String name, String password) {
       // final TextView tvRequest = (TextView) findViewById(R.id.tv_request);
       // final TextView tvResponse = (TextView) findViewById(R.id.tv_response);

        account=name;
        final CommonRequest request = new CommonRequest();
        request.addRequestParam("name", name);
        request.addRequestParam("password", password);
        sendHttpPostRequest(URL_LOGIN, request, new ResponseHandler() {
            @Override
            public void success(CommonResponse response) {

                LoadingDialogUtil.cancelLoading();
                Toast.makeText(Login.this,"登录成功",Toast.LENGTH_LONG).show();
                LOGIN=1;
                /*tvRequest.setText(request.getJsonStr());
                tvResponse.setText(response.getResCode() + "\n" + response.getResMsg());*/
                //DialogUtil.showHintDialog(MainActivity.this, "登陆成功啦！", false);
                Intent intent=new Intent(Login.this, MainActivity.class);
                finish();
                startActivity(intent);

            }

            @Override
            public void fail(String failCode, String failMsg) {

                Toast.makeText(Login.this,"账户或密码错误！",Toast.LENGTH_LONG).show();
               /* tvRequest.setText(request.getJsonStr());
                tvResponse.setText(failCode + "\n" + failMsg);
                DialogUtil.showHintDialog(MainActivity.this, true, "登陆失败", failCode + " : " + failMsg, "关闭对话框", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoadingDialogUtil.cancelLoading();
                        DialogUtil.dismissDialog();
                    }
                });*/
            }
        }, true);

    }

}

package com.tm.mywechat;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tm.mywechat.http.bean.CommonRequest;
import com.tm.mywechat.http.bean.CommonResponse;
import com.tm.mywechat.http.interf.ResponseHandler;
import com.tm.mywechat.main_page.AddFragment;
import com.tm.mywechat.main_page.MyMainActivity;
import com.tm.mywechat.util.LoadingDialogUtil;

import java.io.FileNotFoundException;

import static android.R.attr.bitmap;
import static com.tm.mywechat.FragmentUtils.bitmapToString;

/**
 * Created by SJL on 2017/6/11.
 */

public class Register extends BaseActivity {
    ImageView imageView;
    private static final int PICK_ALBUM = 1;
    private String userbitmap;
    private Bitmap userImg;
    private String URL_REGISTER = "http://192.168.43.165:8080/MyWorld_Service/RegisterServlet";
    private String icon;
    private static String icon1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        final EditText etName = (EditText) findViewById(R.id.et_zh);
        final EditText etPassword = (EditText) findViewById(R.id.et_mima);
        final EditText etnickname = (EditText) findViewById(R.id.et_nickname);
        final EditText etphone = (EditText) findViewById(R.id.et_phone);
        final EditText etaddress=(EditText)findViewById(R.id.et_address);


        Resources res=this.getResources();
        Drawable drawable=res.getDrawable(R.drawable.user);
        Drawable drawable1=res.getDrawable(R.drawable.pic2);
        BitmapDrawable bd=(BitmapDrawable)drawable;
        BitmapDrawable bd1=(BitmapDrawable)drawable1;
        Bitmap bm1 =bd1.getBitmap();
        icon1 = bitmapToString(bm1);

        Bitmap bm =bd.getBitmap();
        icon = bitmapToString(bm);

        imageView=(ImageView)findViewById(R.id.iv2);
        imageView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK,

                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, PICK_ALBUM );


            }


        });



        Button btnreg = (Button) findViewById(R.id.btn_register);
        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register(etName.getText().toString(), etPassword.getText().toString(),etnickname.getText().toString(),etphone.getText().toString(),etaddress.getText().toString(),userbitmap);
            }
        });

    }


    public static String getIcon1()
    {
        return icon1;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1) {
            Uri uri = data.getData();
            ContentResolver cr = getContentResolver();
            try {
                if (userImg != null)//如果不释放的话，不断取图片，将会内存不够
                    userImg.recycle();
                userImg = BitmapFactory.decodeStream(cr.openInputStream(uri));
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            System.out.println("the bmp toString: " + userImg);

            imageView.setImageBitmap(userImg);
            userbitmap = bitmapToString(userImg);


        }
    }


    public  String getIcon()
    {
        return icon;

    }


    private void register(String name, String password ,String nickname,String phone,String address,String usericon) {
        // final TextView tvRequest = (TextView) findViewById(R.id.tv_request);
        // final TextView tvResponse = (TextView) findViewById(R.id.tv_response);

        final CommonRequest request = new CommonRequest();
        request.addRequestParam("name", name);
        request.addRequestParam("password", password);
        request.addRequestParam("nickname", nickname);
        request.addRequestParam("phone", phone);
        request.addRequestParam("address",address);
        if(usericon==null) {
            String geticon=getIcon();
            request.addRequestParam("usericon",geticon);
        }
        else
        request.addRequestParam("usericon",usericon);
        request.addRequestParam("opt","1");
        request.addRequestParam("opt2","1");
        sendHttpPostRequest(URL_REGISTER, request, new ResponseHandler() {
            @Override
            public void success(CommonResponse response) {
                Toast.makeText(Register.this,"注册成功",Toast.LENGTH_LONG).show();
               // LoadingDialogUtil.cancelLoading();
                /*tvRequest.setText(request.getJsonStr());
                tvResponse.setText(response.getResCode() + "\n" + response.getResMsg());*/
                //DialogUtil.showHintDialog(MainActivity.this, "登陆成功啦！", false);
                Intent intent=new Intent(Register.this, Login.class);
                startActivity(intent);

            }

            @Override
            public void fail(String failCode, String failMsg) {
                if("100".equals(failCode))
                    Toast.makeText(Register.this,"该用户名已经被注册",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(Register.this,"注册成功",Toast.LENGTH_LONG).show();
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

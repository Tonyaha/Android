package com.tm.mywechat;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tm.mywechat.http.bean.CommonRequest;
import com.tm.mywechat.http.bean.CommonResponse;
import com.tm.mywechat.http.interf.ResponseHandler;
import com.tm.mywechat.main_page.HomePageFragment;
import com.tm.mywechat.main_page.MineFragment;
import com.tm.mywechat.util.DialogUtil;
import com.tm.mywechat.util.LoadingDialogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Iterator;

import static com.tm.mywechat.FragmentUtils.bitmapToString;

/**
 * Created by SJL on 2017/6/11.
 */

public class UpdateUser extends BaseActivity {
    ImageView imageView;
    private static final int PICK_ALBUM = 1;
    private String userbitmap;
    private Bitmap userImg;
    private String URL_REGISTER = "http://192.168.43.165:8080/MyWorld_Service/RegisterServlet";
    private EditText etPassword;
    private EditText etnickname;
    private EditText etphone;
    private EditText etaddress;
    private String nickname;
    private String phone;
    private String address;
    private String image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.updateuser);

         EditText etName = (EditText) findViewById(R.id.et_zh);
        etPassword = (EditText) findViewById(R.id.et_mima);
        etnickname = (EditText) findViewById(R.id.et_nickname);
        etphone = (EditText) findViewById(R.id.et_phone);
        etaddress = (EditText)findViewById(R.id.et_address);

        getData();


        imageView=(ImageView)findViewById(R.id.iv2);
        imageView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK,

                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(intent, PICK_ALBUM );


            }


        });



        Button change = (Button) findViewById(R.id.btn_change);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changem(  etnickname.getText().toString(), etphone.getText().toString(), etaddress.getText().toString(),userbitmap);
            }
        });

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


    private void changem(String nickname,String phone,String address,String usericon) {
        // final TextView tvRequest = (TextView) findViewById(R.id.tv_request);
        // final TextView tvResponse = (TextView) findViewById(R.id.tv_response);

        final CommonRequest request = new CommonRequest();
        //request.addRequestParam("password", password);
        request.addRequestParam("nickname", nickname);
        request.addRequestParam("phone", phone);
        request.addRequestParam("address",address);
        if(usericon==null)
            request.addRequestParam("usericon",image);
        else
            request.addRequestParam("usericon",usericon);
        request.addRequestParam("opt2","2");
        request.addRequestParam("opt","1");
        request.addRequestParam("name",Login.getAccount());
        sendHttpPostRequest(URL_REGISTER, request, new ResponseHandler() {
            @Override
            public void success(CommonResponse response) {
                LoadingDialogUtil.cancelLoading();
                Toast.makeText(UpdateUser.this,"修改成功",Toast.LENGTH_LONG).show();
                finish();
                /*tvRequest.setText(request.getJsonStr());
                tvResponse.setText(response.getResCode() + "\n" + response.getResMsg());*/
                //DialogUtil.showHintDialog(UpdateUser.this, "修改成功！", false);


            }

            @Override
            public void fail(String failCode, String failMsg) {
                Toast.makeText(UpdateUser.this,"修改成功",Toast.LENGTH_LONG).show();
                finish();
               /* DialogUtil.showHintDialog(UpdateUser.this, true, "修改失败", failCode + " : " + failMsg, "关闭对话框", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LoadingDialogUtil.cancelLoading();
                        DialogUtil.dismissDialog();
                    }
                });*/
            }
        }, true);

    }


    private void getData() {
        CommonRequest request = new CommonRequest();
        request.addRequestParam("opt","2");
        request.addRequestParam("name",Login.getAccount());
        sendHttpPostRequest(URL_REGISTER, request, new ResponseHandler() {
            @Override
            public void success(CommonResponse response) {
                LoadingDialogUtil.cancelLoading();


                String jsonString=response.getjsonString();
                setText(jsonString);
                Log.d("kllkklkllkll","h");

                /*if (response.getDataList().size() > 0) {
                    HomePageFragment.ProductAdapter adapter = new HomePageFragment.ProductAdapter(getContext(), response.getDataList());
                    lvProduct.setAdapter(adapter);
                } else {
                    //DialogUtil.showHintDialog(ListActivity.this, "列表数据为空", true);
                }*/
            }

            @Override
            public void fail(String failCode, String failMsg) {

                LoadingDialogUtil.cancelLoading();
            }
        }, true);
    }

    public void setText(String string)
    {

        //HashMap<String, String> data = new HashMap<String, String>();
        // 将json字符串转换成jsonObject
        try {
            JSONObject jsonObject = new JSONObject(string);
            nickname=jsonObject.getString("nickname");
            phone=jsonObject.getString("phone");
            address=jsonObject.getString("address");
            image=jsonObject.getString("usericon");
       /* Iterator it = jsonObject.keys();
        // 遍历jsonObject数据，添加到Map对象
        while (it.hasNext())
        {
            String key = String.valueOf(it.next());
            String value = (String) jsonObject.get(key);
            data.put(key, value);
        }*/
        }catch (JSONException e)
        {

        }


       // etPassword.setText(jsonObject.getString("nickname"));

       // etPassword.setText(data.get("password"));

       etnickname.setText(nickname);
        etphone.setText(phone);
        etaddress.setText(address);
        imageView.setImageBitmap(FragmentUtils.stringToImage(image));



    }

}

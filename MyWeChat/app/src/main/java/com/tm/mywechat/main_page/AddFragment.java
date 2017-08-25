package com.tm.mywechat.main_page;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.tm.mywechat.BaseActivity;
import com.tm.mywechat.FragmentUtils;
import com.tm.mywechat.ListActivity;
import com.tm.mywechat.Login;
import com.tm.mywechat.R;
import com.tm.mywechat.Register;
import com.tm.mywechat.http.Constant;
import com.tm.mywechat.http.HttpPostTask;
import com.tm.mywechat.http.bean.CommonRequest;
import com.tm.mywechat.http.bean.CommonResponse;
import com.tm.mywechat.http.interf.ResponseHandler;
import com.tm.mywechat.util.LoadingDialogUtil;

import java.io.FileNotFoundException;

import static com.tm.mywechat.FragmentUtils.bitmapToString;

/**
 * Created by TM on 2017/5/26.
 */

public class AddFragment extends Fragment {

    private static final String[] m={"校园代步","数码","电脑","数码配件","图书教材","运动健康","电器","校园网"};
    private Spinner spinner;
    private ArrayAdapter<String> adapter;
    private String type;
    private Uri albumUri;
    private String URL_PRODUCT = "http://192.168.43.165:8080/MyWorld_Service/ProductServlet";
    private static final int PICK_ALBUM = 1;
    private  ImageView imageView;
    private String bitmap;
    private static String icon;
    private Bitmap prodImg;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_activity,container,false);

        final EditText et_gname=(EditText)view.findViewById(R.id.et_gname);
        final EditText et_desc=(EditText)view.findViewById(R.id.et_desc);
        final EditText et_oldprice=(EditText)view.findViewById(R.id.et_oldprice);
        final EditText et_price=(EditText)view.findViewById(R.id.et_price);





        Button button=(Button)view.findViewById(R.id.fabu);
        imageView=(ImageView)view.findViewById(R.id.iv1);
        imageView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //调用相册
                FragmentUtils.pickAlbum(AddFragment.this,PICK_ALBUM);


            }


        });




        button.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(Login.getLOGIN()==0)
                    Toast.makeText(getContext(),"您还没有登录，不能发布",Toast.LENGTH_LONG).show();
                else
                fabu(et_gname.getText().toString(),et_desc.getText().toString(),et_oldprice.getText().toString(),et_price.getText().toString(),gettypeid(type),bitmap);
            }
        });


        spinner=(Spinner)view.findViewById(R.id.spinner);
        adapter=new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,m);

        //设置下拉列表风格
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new SpinnerSelectedListener());
        spinner.setVisibility(View.VISIBLE);

        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    switch(requestCode){
        case PICK_ALBUM:
            if(data!=null) {
                Uri uri = data.getData();
                ContentResolver cr = getActivity().getContentResolver();
                try {
                    if (prodImg != null)//如果不释放的话，不断取图片，将会内存不够
                        prodImg.recycle();
                    prodImg = BitmapFactory.decodeStream(cr.openInputStream(uri));
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println("the bmp toString: " + prodImg);
                if (prodImg != null) {
                    imageView.setImageBitmap(prodImg);
                    bitmap = bitmapToString(prodImg);
                } else {

                }
            }
            break;
    }
    }



    public   String getIcon()
    {

        Resources res=getActivity().getResources();
        Drawable drawable=res.getDrawable(R.drawable.pic2);
        BitmapDrawable bd=(BitmapDrawable)drawable;

        Bitmap bm =bd.getBitmap();
        icon =bitmapToString(bm);
        return icon;


    }



    private String gettypeid(String type){
        String typeid=new String();
        switch(type){
            case "校园代步":
                typeid="1";
                break;
            case "数码":
                typeid="2";
                break;
            case "电脑":
                typeid="3";
                break;
            case "数码配件":
                typeid="4";
                break;
            case "图书教材":
                typeid="5";
                break;
            case "运动健康":
                typeid="6";
                break;
            case "电器":
                typeid="7";
                break;
            case "校园网":
                typeid="8";
                break;

        }

        return typeid;
    }





    private void fabu(String gname,String desc,String oldprice,String price,String type,String bitmap){


        final CommonRequest request=new CommonRequest();

        request.addRequestParam("gname",gname);
        request.addRequestParam("desc",desc);
        request.addRequestParam("oldprice",oldprice);
        request.addRequestParam("price",price);
        request.addRequestParam("type",type);
        request.addRequestParam("opt","1");
        request.addRequestParam("opt2","1");
        if(bitmap==null) {
            String geticon= getIcon();
            request.addRequestParam("image",geticon);
        }
        else
            request.addRequestParam("image",bitmap);
        request.addRequestParam("account", Login.getAccount());

        sendHttpPostRequest(URL_PRODUCT, request, new ResponseHandler() {
            @Override
            public void success(CommonResponse response) {
                LoadingDialogUtil.cancelLoading();
                Toast.makeText(getContext(),"发布成功",Toast.LENGTH_LONG).show();

            }

            @Override
            public void fail(String failCode, String failMsg) {
                Toast.makeText(getContext(),"发布失败",Toast.LENGTH_LONG).show();
                //LoadingDialogUtil.cancelLoading();
            }
        }, true);

    }


    protected void sendHttpPostRequest(String url, CommonRequest request, ResponseHandler responseHandler, boolean showLoadingDialog) {
        new HttpPostTask(request, mHandler, responseHandler).execute(url);
        if(showLoadingDialog) {
            // LoadingDialogUtil.showLoadingDialog(BaseActivity.this);
        }
    }

    protected Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            if(msg.what == Constant.HANDLER_HTTP_SEND_FAIL) {
                // LogUtil.logErr(msg.obj.toString());

                // LoadingDialogUtil.cancelLoading();
                // DialogUtil.showHintDialog(BaseActivity.this, "请求发送失败，请重试", true);
            } else if (msg.what == Constant.HANDLER_HTTP_RECEIVE_FAIL) {
                // LogUtil.logErr(msg.obj.toString());

                //LoadingDialogUtil.cancelLoading();
                //  DialogUtil.showHintDialog(BaseActivity.this, "请求接受失败，请重试", true);
            }
        }
    };


    public class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            type=m[position];
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }


}

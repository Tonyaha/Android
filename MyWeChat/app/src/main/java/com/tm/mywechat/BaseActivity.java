package com.tm.mywechat;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.tm.mywechat.http.Constant;
import com.tm.mywechat.http.HttpPostTask;
import com.tm.mywechat.http.bean.CommonRequest;
import com.tm.mywechat.http.interf.ResponseHandler;


/**
 * 基类
 *
 * Created by WangJie on 2017-03-14.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
}

package com.tm.mywechat.main_page;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by TM on 2017/6/7.
 */
/**
 * AsyncTask类的三个泛型参数：
 * （1）Param 在执行AsyncTask是需要传入的参数，可用于后台任务中使用
 * （2）后台任务执行过程中，如果需要在UI上先是当前任务进度，则使用这里指定的泛型作为进度单位
 * （3）任务执行完毕后，如果需要对结果进行返回，则这里指定返回的数据类型
 */
class MyAsyncTask extends AsyncTask<String, Integer, String> {

    private TextView tv; // 举例一个UI元素，后边会用到

    public MyAsyncTask(TextView v) {
        tv = v;
    }

    @Override
    protected void onPreExecute() {
        Log.w("WangJ", "task onPreExecute()");
    }

    /**
     * @param params 这里的params是一个数组，即AsyncTask在激活运行是调用execute()方法传入的参数
     */
    @Override
    protected String doInBackground(String... params) {
        Log.w("WangJ", "task doInBackground()");
        HttpURLConnection connection = null;
        StringBuilder response = new StringBuilder();
        try {
            URL url = new URL(params[0]); // 声明一个URL,注意如果用百度首页实验，请使用https开头，否则获取不到返回报文
            connection = (HttpURLConnection) url.openConnection(); // 打开该URL连接
            connection.setRequestMethod("GET"); // 设置请求方法，“POST或GET”，我们这里用GET，在说到POST的时候再用POST
            connection.setConnectTimeout(80000); // 设置连接建立的超时时间
            connection.setReadTimeout(80000); // 设置网络报文收发超时时间
            InputStream in = connection.getInputStream();  // 通过连接的输入流获取下发报文，然后就是Java的流处理
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response.toString(); // 这里返回的结果就作为onPostExecute方法的入参
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        // 如果在doInBackground方法，那么就会立刻执行本方法
        // 本方法在UI线程中执行，可以更新UI元素，典型的就是更新进度条进度，一般是在下载时候使用
    }

    /**
     * 运行在UI线程中，所以可以直接操作UI元素
     * @param s
     */
    @Override
    protected void onPostExecute(String s) {
        Log.w("WangJ", "task onPostExecute()");
        tv.setText(s);
    }
}

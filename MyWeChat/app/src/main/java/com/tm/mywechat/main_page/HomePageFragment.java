package com.tm.mywechat.main_page;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.tm.mywechat.FragmentUtils;
import com.tm.mywechat.ListActivity;
import com.tm.mywechat.R;
import com.tm.mywechat.http.Constant;
import com.tm.mywechat.http.HttpPostTask;
import com.tm.mywechat.http.bean.CommonRequest;
import com.tm.mywechat.http.bean.CommonResponse;
import com.tm.mywechat.http.interf.ResponseHandler;
import com.tm.mywechat.util.LoadingDialogUtil;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/1/10.
 */
public class HomePageFragment extends Fragment {
    private String URL_PRODUCT = "http://192.168.43.165:8080/MyWorld_Service/ProductServlet";
    private ListView lvProduct;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_page_activity,container,false);
        lvProduct = (ListView)view.findViewById(R.id.lv1);


        getListData();
        return view;
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

   /* private void getListData() {
        CommonRequest request = new CommonRequest();
        request.addRequestParam("opt2","3");
        request.addRequestParam("opt","2");
        ListActivity listActivity= new ListActivity();
        listActivity.senHTTP2(request,lvProduct);
    }*/

    private void getListData() {
        CommonRequest request = new CommonRequest();
        request.addRequestParam("opt2","3");
        request.addRequestParam("opt","2");
        sendHttpPostRequest(URL_PRODUCT, request, new ResponseHandler() {
            @Override
            public void success(CommonResponse response) {
                LoadingDialogUtil.cancelLoading();

                if (response.getDataList().size() > 0) {
                    ProductAdapter adapter = new ProductAdapter(getContext(), response.getDataList());
                    lvProduct.setAdapter(adapter);
                } else {
                    //DialogUtil.showHintDialog(ListActivity.this, "列表数据为空", true);
                }
            }

            @Override
            public void fail(String failCode, String failMsg) {
                LoadingDialogUtil.cancelLoading();
            }
        }, true);
    }

    static class ProductAdapter extends BaseAdapter {
        private Context context;
        private ArrayList<HashMap<String, String>> list;

        public ProductAdapter(Context context, ArrayList<HashMap<String, String>> list) {
            this.context = context;
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ProductAdapter.ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.goods_view, parent, false);
                holder = new ProductAdapter.ViewHolder();
                holder.gName = (TextView) convertView.findViewById(R.id.g_name);
                holder.tvDescribe = (TextView) convertView.findViewById(R.id.desc);
                holder.tvPrice = (TextView) convertView.findViewById(R.id.price);
                holder.oldPrice=(TextView) convertView.findViewById(R.id.oldprice);
                holder.username=(TextView) convertView.findViewById(R.id.user_name);
                holder.imageView=(ImageView)convertView.findViewById(R.id.g_pic);
                holder.imageView2=(ImageView)convertView.findViewById(R.id.user_icon);

                convertView.setTag(holder);
            } else {
                holder = (ProductAdapter.ViewHolder) convertView.getTag();
            }

            HashMap<String, String> map = list.get(position);
            holder.gName.setText(map.get("name"));
            holder.tvDescribe.setText(map.get("describle"));
            holder.tvPrice.setText("价格："+map.get("price"));
            holder.oldPrice.setText("原价："+map.get("oldprice"));
            holder.username.setText(map.get("account"));
            holder.imageView2.setImageBitmap(FragmentUtils.stringToImage(map.get("usericon")));
            holder.imageView.setImageBitmap(FragmentUtils.stringToImage(map.get("image")));
            return convertView;
        }

        private static class ViewHolder {
            private TextView gName;
            private TextView tvDescribe;
            private TextView tvPrice;
            private TextView oldPrice;
            private TextView username;
            private ImageView imageView;
            private ImageView imageView2;
        }
    }



}

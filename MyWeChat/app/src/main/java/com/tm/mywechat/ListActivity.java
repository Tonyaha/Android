package com.tm.mywechat;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.tm.mywechat.http.bean.CommonRequest;
import com.tm.mywechat.http.bean.CommonResponse;
import com.tm.mywechat.http.interf.ResponseHandler;
//import com.tm.mywechat.util.DialogUtil;
import com.tm.mywechat.util.LoadingDialogUtil;

import java.util.ArrayList;
import java.util.HashMap;

public class ListActivity extends BaseActivity {
    private String URL_PRODUCT = "http://192.168.43.165:8080/MyWorld_Service/ProductServlet";
    ListView lvProduct;
    TextView textView;
    private  String paramKey;
    private String paramValue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goods_listview);

        Bundle bundle = this.getIntent().getExtras();
        paramKey=bundle.getString("分类名");
        paramValue=bundle.getString("分类ID");

        lvProduct = (ListView) findViewById(R.id.lv);
        textView = (TextView)findViewById(R.id.type);
        textView.setText(paramKey);
        getListData();
    }

    public void senHTTP(CommonRequest request)
    {

        sendHttpPostRequest(URL_PRODUCT, request, new ResponseHandler() {
            @Override
            public void success(CommonResponse response) {
                LoadingDialogUtil.cancelLoading();
                Toast.makeText(ListActivity.this,"发布成功",Toast.LENGTH_LONG).show();

            }

            @Override
            public void fail(String failCode, String failMsg) {
                Toast.makeText(ListActivity.this,"发布失败",Toast.LENGTH_LONG).show();
                //LoadingDialogUtil.cancelLoading();
            }
        }, true);
    }




    private void getListData() {
        CommonRequest request = new CommonRequest();
        request.addRequestParam("type",paramValue);
        request.addRequestParam("opt","2");
        request.addRequestParam("opt2","2");
        sendHttpPostRequest(URL_PRODUCT, request, new ResponseHandler() {
            @Override
            public void success(CommonResponse response) {
                LoadingDialogUtil.cancelLoading();

                if (response.getDataList().size() > 0) {
                    ProductAdapter adapter = new ProductAdapter(ListActivity.this, response.getDataList());
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
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.goods_view, parent, false);
                holder = new ViewHolder();
                holder.gName = (TextView) convertView.findViewById(R.id.g_name);
                holder.tvDescribe = (TextView) convertView.findViewById(R.id.desc);
                holder.tvPrice = (TextView) convertView.findViewById(R.id.price);
                holder.oldPrice=(TextView) convertView.findViewById(R.id.oldprice);
                holder.username=(TextView) convertView.findViewById(R.id.user_name);
                holder.phone=(TextView) convertView.findViewById(R.id.phone);
                holder.imageView=(ImageView)convertView.findViewById(R.id.g_pic);
                holder.imageView2=(ImageView)convertView.findViewById(R.id.user_icon);

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            HashMap<String, String> map = list.get(position);
            holder.gName.setText(map.get("name"));
            holder.tvDescribe.setText(map.get("describle"));
            holder.tvPrice.setText("价格："+map.get("price"));
            holder.oldPrice.setText("原价："+map.get("oldprice"));
            holder.username.setText(map.get("account"));
            holder.phone.setText("联系 ："+map.get("phone"));
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
            private TextView phone;
            private ImageView imageView;
            private ImageView imageView2;

        }
    }
}

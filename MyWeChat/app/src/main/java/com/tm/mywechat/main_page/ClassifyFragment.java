package com.tm.mywechat.main_page;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.tm.mywechat.ListActivity;
import com.tm.mywechat.R;

/**
 * Created by Administrator on 2017/1/10.
 */
public class ClassifyFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.classify_activity,container,false);
        RelativeLayout tp1=(RelativeLayout)view.findViewById(R.id.tp1);
        RelativeLayout tp2=(RelativeLayout)view.findViewById(R.id.tp2);
        RelativeLayout tp3=(RelativeLayout)view.findViewById(R.id.tp3);
        RelativeLayout tp4=(RelativeLayout)view.findViewById(R.id.tp4);
        RelativeLayout tp5=(RelativeLayout)view.findViewById(R.id.tp5);
        RelativeLayout tp6=(RelativeLayout)view.findViewById(R.id.tp6);
        RelativeLayout tp7=(RelativeLayout)view.findViewById(R.id.tp7);
        RelativeLayout tp8=(RelativeLayout)view.findViewById(R.id.tp8);

        myOnclick myOnclick = new myOnclick();

        tp1.setOnClickListener(myOnclick);
        tp2.setOnClickListener(myOnclick);
        tp3.setOnClickListener(myOnclick);
        tp4.setOnClickListener(myOnclick);
        tp5.setOnClickListener(myOnclick);
        tp6.setOnClickListener(myOnclick);
        tp7.setOnClickListener(myOnclick);
        tp8.setOnClickListener(myOnclick);



        return view;
    }

    public class myOnclick implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();
            Bundle bundle = new Bundle();
            switch (v.getId()) {
                case R.id.tp1:

                    intent.setClass(getActivity(), ListActivity.class);
                    bundle.putString("分类名", "校园代步");
                    bundle.putString("分类ID", "1");
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                case R.id.tp2:

                    intent.setClass(getActivity(), ListActivity.class);
                    bundle.putString("分类名", "数码");
                    bundle.putString("分类ID", "2");
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                case R.id.tp3:
                    intent.setClass(getActivity(), ListActivity.class);
                    bundle.putString("分类名", "电脑");
                    bundle.putString("分类ID", "3");
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                case R.id.tp4:
                    intent.setClass(getActivity(), ListActivity.class);
                    bundle.putString("分类名", "数码配件");
                    bundle.putString("分类ID", "4");
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                case R.id.tp5:
                    intent.setClass(getActivity(), ListActivity.class);
                    bundle.putString("分类名", "图书教材");
                    bundle.putString("分类ID", "5");
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                case R.id.tp6:
                    intent.setClass(getActivity(), ListActivity.class);
                    bundle.putString("分类名", "运动健身");
                    bundle.putString("分类ID", "6");
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                case R.id.tp7:
                    intent.setClass(getActivity(), ListActivity.class);
                    bundle.putString("分类名", "电器");
                    bundle.putString("分类ID", "7");
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                case R.id.tp8:
                    intent.setClass(getActivity(), ListActivity.class);
                    bundle.putString("分类名", "校园网");
                    bundle.putString("分类ID", "8");
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;

            }
        }
    }
}
/*
    public void onClick(View v)
    {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        switch(v.getId())
        {
            case R.id.tp1:

                intent.setClass(getActivity(), ListActivity.class);
                bundle.putString("分类名","type");
                bundle.putString("分类ID","1");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.tp2:

                intent.setClass(getActivity(), ListActivity.class);
                bundle.putString("分类名","type");
                bundle.putString("分类ID","2");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.tp3:
                intent.setClass(getActivity(), ListActivity.class);
                bundle.putString("分类名","type");
                bundle.putString("分类ID","3");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.tp4:
                intent.setClass(getActivity(), ListActivity.class);
                bundle.putString("分类名","数码配件");
                bundle.putString("分类ID","4");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.tp5:
                intent.setClass(getActivity(), ListActivity.class);
                bundle.putString("分类名","图书教材");
                bundle.putString("分类ID","5");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.tp6:
                intent.setClass(getActivity(), ListActivity.class);
                bundle.putString("分类名","运动健身");
                bundle.putString("分类ID","6");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.tp7:
                intent.setClass(getActivity(), ListActivity.class);
                bundle.putString("分类名","电器");
                bundle.putString("分类ID","7");
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.tp8:
                intent.setClass(getActivity(), ListActivity.class);
                bundle.putString("分类名","校园网");
                bundle.putString("分类ID","8");
                intent.putExtras(bundle);
                startActivity(intent);
                break;

        }
    }*/




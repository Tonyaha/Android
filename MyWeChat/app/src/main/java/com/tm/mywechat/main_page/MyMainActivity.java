package com.tm.mywechat.main_page;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.tm.mywechat.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/10.
 */
public class MyMainActivity extends Fragment {
        private List<Fragment> fragment;
        private View view;
        private RadioGroup radioGroup;
        private ViewPager viewPager;

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            view = inflater.inflate(R.layout.mymain_activity, container, false);
            fragment = new ArrayList<Fragment>();

            HomePageFragment messageFragment = new HomePageFragment(); // 顺序对界面有影响
            ClassifyFragment maillistFragment = new ClassifyFragment();
            AddFragment addFragment = new AddFragment();
            //ShoppingCartFragment foundFragment =new ShoppingCartFragment();
            MineFragment mineFragment = new MineFragment();

            fragment.add(messageFragment);
            fragment.add(maillistFragment);
            fragment.add(addFragment);
            //fragment.add(foundFragment);
            fragment.add(mineFragment);


            viewPager = (ViewPager) view.findViewById(R.id.viewPage);

            viewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
                @Override
                public Fragment getItem(int position) {
                    return fragment.get(position);
                }

                @Override
                public int getCount() {
                    return fragment.size();
                }
            });
            viewPager.setCurrentItem(0);

            initMenuItem();

        /* 将每个page与底部的menu绑定 */
            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }


                /* position是在界面滑动到的页面的 下标 编号 */
                @Override
                public void onPageSelected(int position) {
                    RadioButton radioButton = (RadioButton) radioGroup.getChildAt(position);
                    // Toast.makeText(getActivity(),""+position,Toast.LENGTH_LONG).show();
                    radioButton.setChecked(true);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
            return view;
        }

        private void initMenuItem() {
            radioGroup = (RadioGroup) view.findViewById(R.id.qs_top_radioGroup);
            radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    int index = group.getCheckedRadioButtonId(); //通过 group 找对应按钮 id
                    RadioButton rb = (RadioButton) view.findViewById(index);
                    rb.setChecked(true);

                    for(int i=0;i<group.getChildCount();i++){
                        RadioButton rb_01 = (RadioButton) group.getChildAt(i);
                        if(rb_01.isChecked()){
                            viewPager.setCurrentItem(i);
                            //Toast.makeText(getActivity(),"第 "+(i+1)+"个界面",Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
                }
            });
        }
}

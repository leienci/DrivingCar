package com.test.drivingcar.ui;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.orhanobut.logger.Logger;
import com.test.drivingcar.R;
import com.test.drivingcar.base.BaseFragment;
import com.test.drivingcar.databinding.FragmentHomeBinding;
import com.test.drivingcar.model.CityBean;
import com.test.drivingcar.ui.adapter.ViewPage2Adapter;
import com.test.drivingcar.ui.dialog.CarTypeDialog;
import com.test.drivingcar.utils.HttpUtils;
import com.test.drivingcar.utils.JsonUtils;
import com.test.drivingcar.utils.MmkvUtil;
import com.test.drivingcar.utils.ScaleTransitionPagerTitleView;

import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.TreeMap;

public class TabHomeFragment extends BaseFragment<FragmentHomeBinding> {
    private FragmentHomeBinding mBinding;
    private String[] tabTitle = new String[]{"科目一", "科目二", "科目三", "科目四"};
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private FragmentContainerHelper fragmentContainerHelper;

    @Override
    public FragmentHomeBinding getViewBinding(LayoutInflater inflater, ViewGroup container) {
        mBinding = FragmentHomeBinding.inflate(inflater, container, false);
        return mBinding;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initViewPage();
        initCarType();
    }

    @Override
    protected void initData() {
        httpCity();
        int carType = MmkvUtil.getMmkv().decodeInt("mmkv_car_type", 1);
        mBinding.tvCarType.setText(carType == 1 ? "小车" : carType == 2 ? "货车" : carType == 3 ? "客车" : "摩托车");
    }

    @Override
    public void onBaseClick(View v) {

    }

    @Override
    public void onDestroy() {
        mBinding.viewPage.unregisterOnPageChangeCallback(mOnPageChangeCallback);
        super.onDestroy();
    }

    private void initViewPage() {
        fragments.add(SubjectFragment.getInstance(1));
        fragments.add(SubjectFragment.getInstance(2));
        fragments.add(SubjectFragment.getInstance(3));
        fragments.add(SubjectFragment.getInstance(4));
        mBinding.viewPage.setAdapter(new ViewPage2Adapter(instance, fragments));
        mBinding.viewPage.setCurrentItem(1);
        initMagicIndicator();
    }

    private void initMagicIndicator() {
        MagicIndicator magicIndicator = mBinding.magicIndeicator;

//        magicIndicator.setBackgroundColor(Color.WHITE);
        CommonNavigator commonNavigator = new CommonNavigator(instance);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return tabTitle.length;
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(tabTitle[index]);
                simplePagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
                simplePagerTitleView.setTypeface(Typeface.DEFAULT_BOLD);
                simplePagerTitleView.setNormalColor(instance.getResources().getColor(R.color.ang_text_base_99));
                simplePagerTitleView.setSelectedColor(instance.getResources().getColor(R.color.ang_text_base));
                simplePagerTitleView.setOnClickListener(v -> mBinding.viewPage.setCurrentItem(index));
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
                linePagerIndicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                linePagerIndicator.setRoundRadius(5f);
                linePagerIndicator.setLineWidth(UIUtil.dip2px(context, 16));
                linePagerIndicator.setColors(instance.getResources().getColor(R.color.ang_09A6FE));
                return linePagerIndicator;
            }


        });
        magicIndicator.setNavigator(commonNavigator);
        fragmentContainerHelper = new FragmentContainerHelper(magicIndicator);
        fragmentContainerHelper.setInterpolator(new OvershootInterpolator(2.0f));
        fragmentContainerHelper.setDuration(300);
        mBinding.viewPage.registerOnPageChangeCallback(mOnPageChangeCallback);

    }

    private final ViewPager2.OnPageChangeCallback mOnPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageSelected(int position) {
            fragmentContainerHelper.handlePageSelected(position);
            super.onPageSelected(position);
        }


        @Override
        public void onPageScrollStateChanged(int state) {
            super.onPageScrollStateChanged(state);
        }


    };

    /*获取城市定位*/
    private void httpCity() {
        HttpUtils.getInstance().url("https://whois.pconline.com.cn/ipJson.jsp?json=true")
                .post(new TreeMap<>()).start(new HttpUtils.BaseCallBack() {
                    @Override
                    public void onError(String e) {
                        Logger.t("HTTP").e(e);
                    }

                    @Override
                    public void onResponse(String body) {
                        Logger.t("HTTP").json(body);
                        CityBean cityBean = JsonUtils.fromJson(body, CityBean.class);
                        if (cityBean != null) {
                            mBinding.tvCity.setText(cityBean.getCity());
                        }
                    }
                });
    }

    private void initCarType() {
        mBinding.tvCarType.setOnClickListener(v -> {
            CarTypeDialog carTypeDialog = new CarTypeDialog(instance);
            carTypeDialog.setDialogInter(type -> {
                MmkvUtil.getMmkv().encode("mmkv_car_type", type);
                if (type == 1) {
                    mBinding.tvCarType.setText("小车");
                } else if (type == 2) {
                    mBinding.tvCarType.setText("货车");
                } else if (type == 3) {
                    mBinding.tvCarType.setText("客车");
                } else {
                    mBinding.tvCarType.setText("摩托车");
                }
            });
            carTypeDialog.show();
        });
    }
}

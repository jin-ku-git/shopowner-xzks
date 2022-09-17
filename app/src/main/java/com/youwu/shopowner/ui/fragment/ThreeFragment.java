package com.youwu.shopowner.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.tabs.TabLayout;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.xuexiang.xui.widget.button.SmoothCheckBox;
import com.youwu.shopowner.BR;
import com.youwu.shopowner.R;
import com.youwu.shopowner.app.AppApplication;
import com.youwu.shopowner.app.AppViewModelFactory;
import com.youwu.shopowner.databinding.FragmentThreeBinding;
import com.youwu.shopowner.ui.fragment.adapter.OrderAdapter;
import com.youwu.shopowner.ui.fragment.adapter.ScrollLeftAdapter;
import com.youwu.shopowner.ui.fragment.adapter.ScrollRightAdapter;
import com.youwu.shopowner.ui.fragment.adapter.ShoppingRecycleAdapter;
import com.youwu.shopowner.ui.fragment.adapter.WMOrderAdapter;
import com.youwu.shopowner.ui.fragment.bean.GroupBean;
import com.youwu.shopowner.ui.fragment.bean.SAASOrderBean;
import com.youwu.shopowner.ui.fragment.bean.ScrollBean;
import com.youwu.shopowner.ui.fragment.bean.XXCOrderBean;
import com.youwu.shopowner.ui.order_goods.ConfirmOrderActivity;
import com.youwu.shopowner.ui.order_goods.OrderDetailsActivity;
import com.youwu.shopowner.utils_view.BigDecimalUtils;
import com.youwu.shopowner.utils_view.DividerItemDecorations;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.KLog;

import static me.goldze.mvvmhabit.base.BaseActivity.subZeroAndDot;

/**
 * 2022/09/12
 */

public class ThreeFragment extends BaseFragment<FragmentThreeBinding,ThreeViewModel> {

    OrderAdapter mOrderAdapter;
    List<String> list=new ArrayList<>();

    int order_status = 0;//状态 1 待接单 2 待出餐 3 待退款
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_three;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public ThreeViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(ThreeViewModel.class);
    }


    @Override
    public void initData() {
        super.initData();

        viewModel.null_type.set(1);

        for (int i=0;i<10;i++){
            list.add(""+i);
        }
        initTabData();
        initWMRecyclerView();
    }

    private void initTabData() {


        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //1 待接单 2 待出餐 3 待退货

                order_status = tab.getPosition();


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * 订单
     */
    private void initWMRecyclerView() {

        //创建adapter
        mOrderAdapter = new OrderAdapter(getContext(), list);
        //给RecyclerView设置adapter
        binding.wmRecyclerView.setAdapter(mOrderAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局

        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        binding.wmRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        //设置item的分割线
        if (binding.wmRecyclerView.getItemDecorationCount() == 0) {
            binding.wmRecyclerView.addItemDecoration(new DividerItemDecorations(getContext(), DividerItemDecorations.VERTICAL));
        }

        mOrderAdapter.setOnItemClickListener(new OrderAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, String data, int position) {
                if (order_status!=4){
                    startActivity(OrderDetailsActivity.class);
                }
            }
        });


    }

}

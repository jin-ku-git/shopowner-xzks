package com.youwu.shopowner.ui.goods_operate;

import android.app.Dialog;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;
import com.youwu.shopowner.BR;
import com.youwu.shopowner.R;
import com.youwu.shopowner.app.AppApplication;
import com.youwu.shopowner.app.AppViewModelFactory;
import com.youwu.shopowner.databinding.ActivityInventoryDetailsBinding;
import com.youwu.shopowner.databinding.ActivityRecordInventoryDetailsBinding;
import com.youwu.shopowner.toast.RxToast;
import com.youwu.shopowner.ui.fragment.adapter.InventoryDetailsRecycleAdapter;
import com.youwu.shopowner.ui.fragment.adapter.InventoryRecycleAdapter;
import com.youwu.shopowner.ui.fragment.bean.ScrollBean;
import com.youwu.shopowner.ui.order_record.RecordActivity;
import com.youwu.shopowner.utils_view.BigDecimalUtils;
import com.youwu.shopowner.utils_view.DividerItemDecorations;
import com.youwu.shopowner.utils_view.StatusBarUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.KLog;

/**
 * 盘点记录详情
 * @author: Administrator
 * @date: 2022/11/14
 */
public class RecordInventoryDetailsActivity extends BaseActivity<ActivityRecordInventoryDetailsBinding, RecordInventoryDetailsViewModel> {


    String TotalPrice;
    String TotalType;
    String TotalQuantity;
    int goods_num;

    private TimePickerView pvCustomTime;//时间选择器

    //购物车recyclerveiw的适配器
    private InventoryDetailsRecycleAdapter mInventoryRecycleAdapter;
    //定义以goodsentity实体类为对象的数据集合
    private ArrayList<ScrollBean.SAASOrderBean> ShoppingEntityList = new ArrayList<ScrollBean.SAASOrderBean>();

    String store_id;//门店id
    @Override
    public void initParam() {
        super.initParam();

    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_record_inventory_details;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
    @Override
    public RecordInventoryDetailsViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(RecordInventoryDetailsViewModel.class);
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.IntegerEvent.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch (integer){




                }
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setRootViewFitsSystemWindows(this, true);
        //修改状态栏是状态栏透明
        StatusBarUtil.setTransparentForWindow(this);
        StatusBarUtil.setDarkMode(this);//使状态栏字体变为黑色

        store_id= AppApplication.spUtils.getString("StoreId");
        viewModel.TotalPrice.set(TotalPrice);
        viewModel.TotalType.set(TotalType);
        viewModel.TotalQuantity.set(TotalQuantity);

//        initRecyclerView();


    }



    /**
     * 购物车列表
     */
    private void initRecyclerView() {


        //创建adapter
        mInventoryRecycleAdapter = new InventoryDetailsRecycleAdapter(this, ShoppingEntityList);
        //给RecyclerView设置adapter
        binding.goodsRecyclerview.setAdapter(mInventoryRecycleAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局

        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        binding.goodsRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        //设置item的分割线
        if (binding.goodsRecyclerview.getItemDecorationCount() == 0) {
            binding.goodsRecyclerview.addItemDecoration(new DividerItemDecorations(this, DividerItemDecorations.VERTICAL));
        }

    }

    /**
     * 计算价格
     *
     *
     */
    private void cll() {

        double prick=0.0;
        int quantity=0;
        for (int i=0;i<ShoppingEntityList.size();i++){
            prick+= BigDecimalUtils.formatRoundUp((Double.parseDouble(ShoppingEntityList.get(i).getOrder_price())*ShoppingEntityList.get(i).getChange_stock()),2);
            quantity+=ShoppingEntityList.get(i).getChange_stock();
        }

        viewModel.TotalPrice.set(prick+"");
        viewModel.TotalType.set(ShoppingEntityList.size()+"");
        viewModel.TotalQuantity.set(quantity+"");


    }




    public ArrayList duplicateRemovalBySet(ArrayList<ScrollBean.SAASOrderBean> list){
        Set set = new HashSet();
        list.addAll(set);
        for(int i = 0;i < list.size();i++){
            set.add(list.get(i));
        }
        ArrayList newlist = new ArrayList();
        newlist.addAll(set);
        return newlist;
    }
}

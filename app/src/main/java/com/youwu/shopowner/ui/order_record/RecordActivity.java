package com.youwu.shopowner.ui.order_record;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youwu.shopowner.BR;
import com.youwu.shopowner.R;
import com.youwu.shopowner.app.AppApplication;
import com.youwu.shopowner.app.AppViewModelFactory;
import com.youwu.shopowner.databinding.ActivityOrderGoodsListBinding;
import com.youwu.shopowner.databinding.ActivityRecordBinding;
import com.youwu.shopowner.toast.RxToast;
import com.youwu.shopowner.ui.order_record.adapder.OrderGoodsAdapter;
import com.youwu.shopowner.ui.order_record.adapder.RecordAdapter;
import com.youwu.shopowner.ui.order_record.bean.OrderGoodsBean;
import com.youwu.shopowner.ui.order_record.bean.RecordBean;
import com.youwu.shopowner.utils_view.DividerItemDecorations;
import com.youwu.shopowner.utils_view.StatusBarUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import me.goldze.mvvmhabit.base.BaseActivity;

/**
 * 盘点 记录页面
 *
 * @date: 2022/9/22
 */
public class RecordActivity extends BaseActivity<ActivityRecordBinding, RecordViewModel> {



    String store_id;//门店id


    RecordAdapter mRecordAdapter;
    //定义以goodsentity实体类为对象的数据集合
    private ArrayList<RecordBean.RowsBean> rowsBeansList = new ArrayList<>();


    int type;

    int page=1;
    @Override
    public void initParam() {
        super.initParam();
        type=getIntent().getIntExtra("type",0);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_record;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public RecordViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(RecordViewModel.class);
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
        viewModel.recordBeanEvent.observe(this, new Observer<ArrayList<RecordBean.RowsBean>>() {
            @Override
            public void onChanged(ArrayList<RecordBean.RowsBean> rowsBeans) {
                if (page==1){
                    rowsBeansList =rowsBeans;
                }else {
                    rowsBeansList.addAll(rowsBeans);
                }

                if (rowsBeansList.size()==0){
                    viewModel.null_type.set(0);
                }else {
                    viewModel.null_type.set(1);
                }

                initRecyclerView();
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

        if (type==1){
            binding.textTop.setText("报损记录");
        }else if (type==2){
            binding.textTop.setText("盘点记录");
        }else if (type==3){
            binding.textTop.setText("沽清记录");
        }else {
            binding.textTop.setText("传错了");
        }



        //刷新
        binding.scSmartrefreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
//                list.clear();
                page=1;
                //获取订单列表
                initRecord_list();
                refreshLayout.finishRefresh(true);
            }
        });
        //加载
        binding.scSmartrefreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                //获取订单列表
                initRecord_list();
                refreshLayout.finishLoadMore(true);//加载完成
            }
        });


        store_id= AppApplication.spUtils.getString("StoreId");
        initRecord_list();
    }

    /**
     * 获取订货列表
     */
    private void initRecord_list() {

        if (type==1){
            viewModel.Record_list(page,"30",store_id);

        }else if (type==2){
            viewModel.Inventory_Record_list(page,"30",store_id);
        }else {
            viewModel.Sell_Off_Record_list(page,"30",store_id);
        }
    }

    /**
     * 订单详情记录
     */
    private void initRecyclerView() {
        //创建adapter
        mRecordAdapter = new RecordAdapter(this, rowsBeansList,type);
        //给RecyclerView设置adapter
        binding.recyclerView.setAdapter(mRecordAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局

        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        binding.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        //设置item的分割线
        if (binding.recyclerView.getItemDecorationCount()==0) {
            binding.recyclerView.addItemDecoration(new DividerItemDecorations(this, DividerItemDecorations.VERTICAL));
        }
        mRecordAdapter.setOnItemClickListener(new RecordAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, RecordBean.RowsBean data, int position) {
                RxToast.normal(data.getCashier_name());
            }
        });
    }

}


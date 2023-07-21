package com.youwu.shopowner.ui.goods_operate;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
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

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.gson.Gson;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youwu.shopowner.BR;
import com.youwu.shopowner.R;
import com.youwu.shopowner.app.AppApplication;
import com.youwu.shopowner.app.AppViewModelFactory;
import com.youwu.shopowner.databinding.ActivityGoodsSetUpBinding;
import com.youwu.shopowner.databinding.ActivityReturnGoodsBinding;
import com.youwu.shopowner.db.GoodsDao;
import com.youwu.shopowner.toast.RxToast;
import com.youwu.shopowner.ui.fragment.adapter.GoodsSetUpRightAdapter;
import com.youwu.shopowner.ui.fragment.adapter.ScrollLeftAdapter;
import com.youwu.shopowner.ui.fragment.adapter.ScrollRightAdapter;
import com.youwu.shopowner.ui.fragment.adapter.ShoppingRecycleAdapter;
import com.youwu.shopowner.ui.fragment.bean.CommunityBean;
import com.youwu.shopowner.ui.fragment.bean.GroupBean;
import com.youwu.shopowner.ui.fragment.bean.ScrollBean;
import com.youwu.shopowner.ui.goods_operate.adapter.GodosSetUpRightAdapter;
import com.youwu.shopowner.ui.goods_operate.adapter.GoodsSetUpAdapter;
import com.youwu.shopowner.utils_view.BigDecimalUtils;
import com.youwu.shopowner.utils_view.DividerItemDecorations;
import com.youwu.shopowner.utils_view.StatusBarUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.KLog;

/**
 * 商品管理页面
 * @author: Administrator
 * @date: 2022/11/02
 */
public class GoodsSetUpActivity extends BaseActivity<ActivityGoodsSetUpBinding, GoodsSetUpViewModel> {


    //分类recyclerveiw的适配器
    private GoodsSetUpAdapter mCommunityRecycleAdapter;
    //定义以CommunityEntityList实体类为对象的数据集合
    private ArrayList<GroupBean> CommunityEntityList = new ArrayList<>();

    //商品recyclerveiw的适配器
    private GodosSetUpRightAdapter mCabinetListRecycleAdapter;
    //定义以CabinetEntityList实体类为对象的数据集合
    private ArrayList<CommunityBean> CabinetEntityList = new ArrayList<CommunityBean>();

    //右侧title在数据中所对应的position集合
    private List<Integer> tPosition = new ArrayList<>();
    private Context mContext;
    //title的高度
    private int tHeight;
    //记录右侧当前可见的第一个item的position
    private int first = 0;
    private GridLayoutManager rightManager;

    public GoodsDao goodsDao;

    String store_id;

    private   CommunityBean communityBean;
    private int Number;


    int page=1;
    private int limit = 100;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_goods_set_up;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public GoodsSetUpViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(GoodsSetUpViewModel.class);
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.IntegerEvent.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch (integer){
                    case 1:
                        RxToast.showTipToast(GoodsSetUpActivity.this,"修改成功！");
                        CabinetEntityList.set(Number,communityBean);

                        mCabinetListRecycleAdapter.notifyItemChanged(Number);

                        break;

                }
            }
        });

        //商品群组返回数据
        viewModel.groupList.observe(this, new Observer<ArrayList<GroupBean>>() {
            @Override
            public void onChanged(ArrayList<GroupBean> groupBeans) {
                CommunityEntityList.clear();
                CommunityEntityList.addAll(groupBeans);

                viewModel.getGoodsGroup(store_id,page+"",limit+"");

                initRecyclerView();
            }
        });
        //商品返回数据
        viewModel.goodList.observe(this, new Observer<ArrayList<CommunityBean>>() {
            @Override
            public void onChanged(ArrayList<CommunityBean> goodBeans) {


                initRecyclerViewTow();
                goodsDao.deleteAllData();
                goodsDao.initTable(goodBeans);

                if (goodBeans.size() == limit) {
                    page++;
                    viewModel.getGoodsGroup(store_id, page + "", limit + "");
                    return;
                }
                //关闭对话框
                dismissDialog();
                initGoodsList(CommunityEntityList.get(0).getId() + "");



            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        initGoodsCategory();
    }

    @Override
    public void initData() {
        super.initData();
        mContext = this;
        StatusBarUtil.setRootViewFitsSystemWindows(this, true);
        //修改状态栏是状态栏透明
        StatusBarUtil.setTransparentForWindow(this);
        StatusBarUtil.setDarkMode(this);//使状态栏字体变为黑色

        goodsDao = new GoodsDao(this);
        boolean dataExist = goodsDao.isDataExist();
        if (dataExist) {
            goodsDao.deleteAllData();
        }


        store_id= AppApplication.spUtils.getString("StoreId");




        //刷新
        binding.scSmartrefreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

                page=1;
                //获取订单列表
                viewModel.getGoodsGroup(store_id,page+"",limit+"");
                refreshLayout.finishRefresh(true);
            }
        });
        //加载
        binding.scSmartrefreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                //获取订单列表
                viewModel.getGoodsGroup(store_id,page+"",limit+"");
                refreshLayout.finishLoadMore(true);//加载完成
            }
        });

    }

    /**
     * 分类
     */
    private void initRecyclerView() {
        //一级分类
        //创建adapter
        mCommunityRecycleAdapter = new GoodsSetUpAdapter(this, CommunityEntityList);
        //给RecyclerView设置adapter
        binding.recLeft.setAdapter(mCommunityRecycleAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局

        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        binding.recLeft.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        //设置item的分割线
        if (binding.recLeft.getItemDecorationCount() == 0) {
            binding.recLeft.addItemDecoration(new DividerItemDecorations(this, DividerItemDecorations.VERTICAL));
        }
        mCommunityRecycleAdapter.setOnScanningListener(new GoodsSetUpAdapter.OnScanningListener() {
            @Override
            public void onScanning(GroupBean groupBean) {

                viewModel.getGoodsGroup(store_id,page+"",limit+"");
            }
        });
    }

    //    刷新商品列表
    private void initGoodsList(String goods_id) {

        if (page==1){
            CabinetEntityList.clear();
        }

        CabinetEntityList.addAll(goodsDao.getGoodListByGroupId(goods_id));

        initRecyclerViewTow();
    }
    /**
     * 商品列表
     */
    private void initRecyclerViewTow() {
        //创建adapter
        mCabinetListRecycleAdapter = new GodosSetUpRightAdapter(this, CabinetEntityList);
        //给RecyclerView设置adapter
        binding.recRight.setAdapter(mCabinetListRecycleAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局

        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        binding.recRight.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        //设置item的分割线
        if (binding.recRight.getItemDecorationCount() == 0) {
            binding.recRight.addItemDecoration(new DividerItemDecorations(this, DividerItemDecorations.VERTICAL));
        }
        mCabinetListRecycleAdapter.setOnChangeListener(new GodosSetUpRightAdapter.OnChangeListener() {
            @Override
            public void onChange(CommunityBean date, int type,int position) {

                communityBean=date;
                Number=position;
                switch (type){
                    case 1://上架
                        TipsToast("确认要上架该商品吗？",date,type);
                        break;
                    case 2://下架
                        TipsToast("确认要下架该商品吗？",date,type);
                        break;
                    case 3://修改
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("CommunityBean",date);
                        startActivity(SetUpGoodsActivity.class,bundle);
                        break;
                }
            }
        });

    }


    /**
     * 获取群组
     */
    private void initGoodsCategory() {
        viewModel.getGoodsGroup(store_id);
    }


    private void TipsToast(String content,CommunityBean date, int type){
        new  XPopup.Builder(GoodsSetUpActivity.this)
                .maxWidth((int) (widths * 0.8))
                .maxHeight((int) (height*0.6))
                .asConfirm("提示", content, "取消", "确认", new OnConfirmListener() {
                    @Override
                    public void onConfirm() {
                        communityBean.setStatus(type);
                        viewModel.updateStoreGoods(date.getType()+"",date.getGoods_sku(),date.getPackage_id()+"",date.getGoods_name(),date.getGoods_price(),date.getMarket_price(),type,date.getStock());

                    }
                }, null,false)
                .show();
    }


    /**
     * 获得资源 dimens (dp)
     *
     * @param context
     * @param id      资源id
     * @return
     */
    public float getDimens(Context context, int id) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        float px = context.getResources().getDimension(id);
        return px / dm.density;
    }

    /**
     * dp转px
     *
     * @param context
     * @param dp
     * @return
     */
    public int dpToPx(Context context, float dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (int) ((dp * displayMetrics.density) + 0.5f);
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

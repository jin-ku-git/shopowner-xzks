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

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.youwu.shopowner.BR;
import com.youwu.shopowner.R;
import com.youwu.shopowner.app.AppApplication;
import com.youwu.shopowner.app.AppViewModelFactory;
import com.youwu.shopowner.databinding.ActivityInventoryBinding;
import com.youwu.shopowner.databinding.ActivitySellOffBinding;
import com.youwu.shopowner.db.InventoryDao;
import com.youwu.shopowner.ui.fragment.adapter.InventoryRecycleAdapter;
import com.youwu.shopowner.ui.fragment.adapter.InventoryRightAdapter;
import com.youwu.shopowner.ui.fragment.adapter.ScrollLeftAdapter;
import com.youwu.shopowner.ui.fragment.adapter.SellOffRecycleAdapter;
import com.youwu.shopowner.ui.fragment.adapter.SellOffRightAdapter;
import com.youwu.shopowner.ui.fragment.bean.CommunityBean;
import com.youwu.shopowner.ui.fragment.bean.GroupBean;
import com.youwu.shopowner.ui.fragment.bean.ScrollBean;
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
 * 沽清页面
 * @author: Administrator
 * @date: 2022/9/20
 */
public class SellOffActivity extends BaseActivity<ActivitySellOffBinding, SellOffViewModel> {


    private List<GroupBean> left=new ArrayList<>();
    private List<ScrollBean> right=new ArrayList<>();
    private ScrollLeftAdapter leftAdapter;
    private SellOffRightAdapter rightAdapter;
    //右侧title在数据中所对应的position集合
    private List<Integer> tPosition = new ArrayList<>();
    private Context mContext;
    //title的高度
    private int tHeight;
    //记录右侧当前可见的第一个item的position
    private int first = 0;
    private GridLayoutManager rightManager;


    //购物车recyclerveiw的适配器
    private SellOffRecycleAdapter mSellOffRecycleAdapter;
    //定义以goodsentity实体类为对象的数据集合
    private ArrayList<ScrollBean.SAASOrderBean> ShoppingEntityList = new ArrayList<ScrollBean.SAASOrderBean>();


    String store_id;

    //柜子接口走了几次
    private int Cabinet_type = 0;
    private int page=1;
    private int limit=100;
    public InventoryDao inventoryDao;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_sell_off;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public SellOffViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(SellOffViewModel.class);
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.IntegerEvent.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch (integer){
                    case 1://打开购物车
                        showJournalDialog();
                        break;
                    case 2://提交
                        Bundle bundle=new Bundle();
                        bundle.putString("TotalPrice",viewModel.TotalPrice.get());
                        bundle.putString("TotalType",viewModel.TotalType.get());
                        bundle.putString("TotalQuantity",viewModel.TotalQuantity.get());
                        bundle.putSerializable("ShoppingEntityList",ShoppingEntityList);
                        startActivity(SellOffDetailsActivity.class,bundle);
                        break;


                }
            }
        });

        //分类回调
        viewModel.groupList.observe(this, new Observer<ArrayList<GroupBean>>() {
            @Override
            public void onChanged(ArrayList<GroupBean> groupBeans) {
                if (left.size()>0){
                    left.clear();
                }
                left.addAll(groupBeans);

//                for (int i=0;i<groupBeans.size();i++){
//                    //获取商品信息
//                    viewModel.initOrder_info(store_id,groupBeans.get(i).getName(),subZeroAndDot(groupBeans.get(i).getId()));
//                }

                viewModel.getStockGoodsList(store_id,"0",page+"",limit+"","2");


            }
        });
        //商品回调
        viewModel.OrderListBean.observe(this, new Observer<ArrayList<ScrollBean>>() {
            @Override
            public void onChanged(ArrayList<ScrollBean> saasOrderBeans) {

                right.addAll(saasOrderBeans);
                Cabinet_type++;
                if (Cabinet_type==left.size()){
                    Cabinet_type=0;
                    left.clear();
                    for (int i=0;i<right.size();i++){
                        if (right.get(i).isHeader){
                            GroupBean bean= new GroupBean();
                            bean.setName(right.get(i).header);
                            bean.setId(right.get(i).id);
                            left.add(bean);
                        }
                    }
                    KLog.d("走了几次left.size():"+left.size());
                    tPosition.clear();
                    first=0;
                    leftAdapter=null;
                    rightAdapter=null;
                    rightManager=null;
                    initDatas();
                    initLeft();
                    initRight();
                }

            }
        });

        viewModel.goodList.observe(this, new Observer<ArrayList<CommunityBean>>() {
            @Override
            public void onChanged(ArrayList<CommunityBean> communityBeans) {

                inventoryDao.initTable(communityBeans);

                if (communityBeans.size() == limit) {
                    page++;
                    viewModel.getStockGoodsList(store_id,"0",page+"",limit+"","2");
                    return;
                }
                for (int i=0;i<left.size();i++){
                    //获取商品信息
                    List<CommunityBean> communityBeans1= inventoryDao.getGoodListByCategoryId(subZeroAndDot(left.get(i).getId()));
                    if (communityBeans1.size()!=0){
                        ArrayList<ScrollBean> list=new ArrayList<>();

                        String name=left.get(i).getName();

                        for (int j=0;j<communityBeans1.size();j++){
                            ScrollBean.SAASOrderBean dataBean=new ScrollBean.SAASOrderBean();
                            dataBean.setStock(communityBeans1.get(j).getStock());
                            dataBean.setGoods_name(communityBeans1.get(j).getGoods_name());
                            dataBean.setOrder_price(communityBeans1.get(j).getGoods_price());
                            dataBean.setGoods_sku(communityBeans1.get(j).getGoods_sku());
                            dataBean.setGoods_id(communityBeans1.get(j).getGoods_id());
                            dataBean.setGoods_img(communityBeans1.get(j).getGoods_img());
                            if (j==0){
                                list.add(new ScrollBean(true, name,store_id));
                            }
                            list.add(new ScrollBean(dataBean));
                        }
                        right.addAll(list);
                    }

                }
                KLog.a("right1:"+right.size());
                KLog.a("Cabinet_type:"+Cabinet_type);
                KLog.a("left:"+left.size());

                left.clear();
                KLog.a("right:"+right.size());
                for (int w=0;w<right.size();w++){
                    if (right.get(w).isHeader){
                        GroupBean bean= new GroupBean();
                        bean.setName(right.get(w).header);
                        bean.setId(right.get(w).id);
                        left.add(bean);
                    }
                }
                KLog.d("走了几次left.size():"+left.size());
                tPosition.clear();
                first=0;
                leftAdapter=null;
                rightAdapter=null;
                rightManager=null;
                initDatas();
                initLeft();
                initRight();



            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        mContext = this;
        StatusBarUtil.setRootViewFitsSystemWindows(this, true);
        //修改状态栏是状态栏透明
        StatusBarUtil.setTransparentForWindow(this);
        StatusBarUtil.setDarkMode(this);//使状态栏字体变为黑色

        inventoryDao = new InventoryDao(this);
        boolean dataExists = inventoryDao.isDataExist();
        if (dataExists) {
            inventoryDao.deleteAllData();
        }

        viewModel.shopping_visibility.set(0);

        store_id= AppApplication.spUtils.getString("StoreId");

        initGoodsCategory();

    }

    /**
     * 获取分类
     */
    private void initGoodsCategory() {
        viewModel.getGoodsCategory();
    }

    //获取数据(若请求服务端数据,请求到的列表需有序排列)
    private void initDatas() {


        for (int i = 0; i < right.size(); i++) {
            if (right.get(i).isHeader) {
                //遍历右侧列表,判断如果是header,则将此header在右侧列表中所在的position添加到集合中
                tPosition.add(i);
            }
        }
    }

    private void initRight() {

        rightManager = new GridLayoutManager(mContext, 1);

        if (rightAdapter == null) {
            rightAdapter = new SellOffRightAdapter(R.layout.sell_off_right, R.layout.layout_right_title, null);
            binding.recRight.setLayoutManager(rightManager);
            binding.recRight.addItemDecoration(new RecyclerView.ItemDecoration() {
                @Override
                public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                    super.getItemOffsets(outRect, view, parent, state);
                    outRect.set(0
                            , 0
                            , 0
                            , dpToPx(mContext, getDimens(mContext, R.dimen.dp3)));
                }
            });
            binding.recRight.setAdapter(rightAdapter);


            rightAdapter.setOnChangeListener(new SellOffRightAdapter.OnChangeListener() {
                @Override
                public void onChange(ScrollBean lists) {
                    ScrollBean scrollBean=lists;

                    //获取下标
                    int  position=right.indexOf(scrollBean);

                    right.set(position,lists);

                    if (ShoppingEntityList.size()>0){
                        List<ScrollBean.SAASOrderBean> lsit=new ArrayList<>();



                        lsit.addAll(ShoppingEntityList);

                        for (int i=0;i<lsit.size();i++) {
                            if (ShoppingEntityList.get(i).getGoods_sku().equals(lists.t.getGoods_sku())){
                                ShoppingEntityList.get(i).setQuantity(lists.t.getQuantity());
                            }else {
                                ShoppingEntityList.add(scrollBean.t);
                            }
                        }
                    }else  {
                        ShoppingEntityList.add(scrollBean.t);
                    }
                    cll(1);

                }
            });
        } else {
            rightAdapter.notifyDataSetChanged();
        }

        rightAdapter.setNewData(right);

        //设置右侧初始title
        if (right.get(first).isHeader) {
            binding.rightTitle.setText(right.get(first).header);
        }

        binding.recRight.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //获取右侧title的高度
                tHeight = binding.rightTitle.getHeight();
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                //判断如果是header
                if (right.get(first).isHeader) {
                    //获取此组名item的view
                    View view = rightManager.findViewByPosition(first);
                    if (view != null) {
                        //如果此组名item顶部和父容器顶部距离大于等于title的高度,则设置偏移量
                        if (view.getTop() >= tHeight) {
                            binding.rightTitle.setY(view.getTop() - tHeight);
                        } else {
                            //否则不设置
                            binding.rightTitle.setY(0);
                        }
                    }
                }

                //因为每次滑动之后,右侧列表中可见的第一个item的position肯定会改变,并且右侧列表中可见的第一个item的position变换了之后,
                //才有可能改变右侧title的值,所以这个方法内的逻辑在右侧可见的第一个item的position改变之后一定会执行
                int firstPosition = rightManager.findFirstVisibleItemPosition();
                if (first != firstPosition && firstPosition >= 0) {
                    //给first赋值
                    first = firstPosition;
                    //不设置Y轴的偏移量
                    binding.rightTitle.setY(0);

                    //判断如果右侧可见的第一个item是否是header,设置相应的值
                    if (right.get(first).isHeader) {
                        binding.rightTitle.setText(right.get(first).header);
                    } else {
                        binding.rightTitle.setText(right.get(first).t.getStore_name());
                    }
                }

                //遍历左边列表,列表对应的内容等于右边的title,则设置左侧对应item高亮
                for (int i = 0; i < left.size(); i++) {
                    if (left.get(i).getName().equals(binding.rightTitle.getText().toString())) {
                        leftAdapter.selectItem(i);
                    }
                }

                //如果右边最后一个完全显示的item的position,等于bean中最后一条数据的position(也就是右侧列表拉到底了),
                //则设置左侧列表最后一条item高亮
                if (rightManager.findLastCompletelyVisibleItemPosition() == right.size() - 1) {
                    leftAdapter.selectItem(left.size() - 1);
                }
            }
        });
    }

    private void initLeft() {
        if (leftAdapter == null) {
            leftAdapter = new ScrollLeftAdapter(R.layout.scroll_left, null);
            binding.recLeft.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

            binding.recLeft.setAdapter(leftAdapter);
            //设置item的分割线
            if (binding.recLeft.getItemDecorationCount()==0) {
                binding.recLeft.addItemDecoration(new DividerItemDecorations(this, DividerItemDecorations.VERTICAL));
            }
        } else {
            leftAdapter.notifyDataSetChanged();
        }

        leftAdapter.setNewData(left);

        leftAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    //点击左侧列表的相应item,右侧列表相应的title置顶显示
                    //(最后一组内容若不能填充右侧整个可见页面,则显示到右侧列表的最底端)
                    case R.id.item:
                        leftAdapter.selectItem(position);
                        rightManager.scrollToPositionWithOffset(tPosition.get(position), 0);
                        break;
                }
            }
        });
    }




    Dialog dialog_shopping;//购物车弹窗

    RecyclerView shopping_recyclerview;

    TextView TotalType;
    TextView TotalQuantity;
    /**
     * 购物车弹窗
     */
    private void showJournalDialog() {

        dialog_shopping = new Dialog(mContext, R.style.BottomDialog);

        //获取屏幕宽高
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int widths = size.x;
        int height = size.y;

        //获取界面
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.dialog_shoppings, null);
        //将界面填充到AlertDiaLog容器
        dialog_shopping.setContentView(dialogView);
        ViewGroup.LayoutParams layoutParams = dialogView.getLayoutParams();
        //设置弹窗宽高
        layoutParams.width = (int) (widths * 0.94);
        layoutParams.height = (int) (height*0.7);
        //将界面填充到AlertDiaLog容器
        dialogView.setLayoutParams(layoutParams);
        dialog_shopping.getWindow().setGravity(Gravity.BOTTOM);
        dialog_shopping.setCancelable(true);//点击外部消失弹窗

        WindowManager.LayoutParams params = dialog_shopping.getWindow().getAttributes();
        params.y = 10;
        dialog_shopping.getWindow().setAttributes(params);
        dialog_shopping.show();
        shopping_recyclerview=dialogView.findViewById(R.id.shopping_recyclerview);//返回

        LinearLayout linear_view=dialogView.findViewById(R.id.linear_view);
        TextView Submit=dialogView.findViewById(R.id.Submit);

        TotalType=dialogView.findViewById(R.id.TotalType);
        TotalQuantity=dialogView.findViewById(R.id.TotalQuantity);

        TotalType.setText(ShoppingEntityList.size()+"");
        TotalQuantity.setText(viewModel.TotalQuantity.get()+"");

        initRecyclerViewThree();

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewModel.IntegerEvent.setValue(2);
            }
        });

    }


    /**
     * 购物车列表
     */
    private void initRecyclerViewThree() {


        //创建adapter
        mSellOffRecycleAdapter = new SellOffRecycleAdapter(mContext, ShoppingEntityList);
        //给RecyclerView设置adapter
        shopping_recyclerview.setAdapter(mSellOffRecycleAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局

        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        shopping_recyclerview.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        //设置item的分割线
        if (shopping_recyclerview.getItemDecorationCount() == 0) {
            shopping_recyclerview.addItemDecoration(new DividerItemDecorations(mContext, DividerItemDecorations.VERTICAL));
        }
        /**
         * 加减
         */
        mSellOffRecycleAdapter.setOnChangeListener(new SellOffRecycleAdapter.OnChangeListener() {
            @Override
            public void onChange(ScrollBean.SAASOrderBean data, int position) {

                ShoppingEntityList.get(position).setQuantity(data.getQuantity());

                cll(2);

            }
        });
        /**
         * 删除
         */
        mSellOffRecycleAdapter.setOnDeleteListener(new SellOffRecycleAdapter.OnDeleteListener() {
            @Override
            public void onDelete(ScrollBean.SAASOrderBean data, int position) {
                ShoppingEntityList.get(position).setQuantity(0);
                ShoppingEntityList.remove(position);

                cll(2);
            }
        });
    }

    /**
     * 计算价格
     */
    private void cll(int type) {
        if (type==1){
            ShoppingEntityList=   duplicateRemovalBySet(ShoppingEntityList);
        }


        double prick=0.0;

        int stock=0;
        for (int i=0;i<ShoppingEntityList.size();i++){
            if (ShoppingEntityList.get(i).getQuantity()==0){
                ShoppingEntityList.remove(i);
            }
        }
        for (int i=0;i<ShoppingEntityList.size();i++){
                prick+= BigDecimalUtils.formatRoundUp((Double.parseDouble(ShoppingEntityList.get(i).getOrder_price())*ShoppingEntityList.get(i).getQuantity()),2);

            stock+=ShoppingEntityList.get(i).getStock();
        }



        rightAdapter.notifyDataSetChanged();
        if (mSellOffRecycleAdapter!=null){
            mSellOffRecycleAdapter.notifyDataSetChanged();
            TotalQuantity.setText(stock+"");
            TotalType.setText(ShoppingEntityList.size()+"");
        }



        viewModel.TotalPrice.set(prick+"");
        viewModel.shopping_visibility.set(ShoppingEntityList.size());
        viewModel.TotalType.set(ShoppingEntityList.size()+"");
        viewModel.TotalQuantity.set(stock+"");


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

package com.youwu.shopowner.ui.order_goods;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.youwu.shopowner.BR;
import com.youwu.shopowner.R;
import com.youwu.shopowner.app.AppApplication;
import com.youwu.shopowner.app.AppViewModelFactory;
import com.youwu.shopowner.databinding.ActivityConfirmOrderBinding;
import com.youwu.shopowner.toast.RxToast;
import com.youwu.shopowner.ui.fragment.adapter.ShoppingRecycleAdapter;
import com.youwu.shopowner.ui.fragment.bean.ScrollBean;
import com.youwu.shopowner.ui.order_goods.bean.OrderItemBean;
import com.youwu.shopowner.utils_view.BigDecimalUtils;
import com.youwu.shopowner.utils_view.DividerItemDecorations;
import com.youwu.shopowner.utils_view.StatusBarUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.KLog;

/**
 * 确认订货页面
 * @author: Administrator
 * @date: 2022/9/16
 */
public class ConfirmOrderActivity extends BaseActivity<ActivityConfirmOrderBinding, ConfirmOrderViewModel> {


    String TotalPrice;
    String TotalType;
    String TotalQuantity;

    private TimePickerView pvCustomTime;//时间选择器

    //购物车recyclerveiw的适配器
    private ShoppingRecycleAdapter mShoppingRecycleAdapter;
    //定义以goodsentity实体类为对象的数据集合
    private ArrayList<ScrollBean.SAASOrderBean> ShoppingEntityList = new ArrayList<ScrollBean.SAASOrderBean>();

    String store_id;//门店id
    @Override
    public void initParam() {
        super.initParam();
        TotalPrice=getIntent().getStringExtra("TotalPrice");
        TotalType=getIntent().getStringExtra("TotalType");
        TotalQuantity=getIntent().getStringExtra("TotalQuantity");
        ShoppingEntityList= (ArrayList<ScrollBean.SAASOrderBean>) getIntent().getSerializableExtra("ShoppingEntityList");
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_confirm_order;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
    @Override
    public ConfirmOrderViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(ConfirmOrderViewModel.class);
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.IntegerEvent.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch (integer){
                    case 1://确认下单
                        if (viewModel.estimate_time.get()==null||"".equals(viewModel.estimate_time.get())){
                            RxToast.normal("请选择预计收货时间");
                            return;
                        }
                        new  XPopup.Builder(ConfirmOrderActivity.this)
                                .maxWidth((int) (widths * 0.8))
                                .maxHeight((int) (height*0.5))
                                .asConfirm("提示", "确认订货信息无误后，是否选择订货？", "取消", "确认", new OnConfirmListener() {
                                    @Override
                                    public void onConfirm() {
                                        List<OrderItemBean> saasOrderList=new ArrayList<>();

                                        for (int i=0;i<ShoppingEntityList.size();i++){

                                            OrderItemBean orderItemBean=new OrderItemBean();
                                            orderItemBean.setGoods_id(ShoppingEntityList.get(i).getGoods_id()+"");
                                            orderItemBean.setGoods_sku(ShoppingEntityList.get(i).getGoods_sku());
                                            if (ShoppingEntityList.get(i).getQuantity()==0){
                                                orderItemBean.setOrder_quantity(0);

                                            }else {
                                                orderItemBean.setOrder_quantity(ShoppingEntityList.get(i).getQuantity());
                                            }

                                            orderItemBean.setOrder_price(ShoppingEntityList.get(i).getOrder_price()+"");
                                            orderItemBean.setGoods_name(ShoppingEntityList.get(i).getGoods_name());

                                            if (orderItemBean.getQuantity()!=0){
                                                saasOrderList.add(orderItemBean);
                                            }

                                        }
                                        String saasList = new Gson().toJson(saasOrderList);

                                        viewModel.add_order(store_id,saasList);
                                    }
                                }, null,false)
                                .show();

                        break;
                    case 2://选择预计收获时间
                        pvCustomTime.show(); //弹出自定义时间选择器
                        break;
                    case 3://订货成功
                        RxToast.showTipToast(ConfirmOrderActivity.this, "订货成功");
                        break;

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
        initRecyclerView();

        initCustomTimePicker();
    }



    /**
     * 购物车列表
     */
    private void initRecyclerView() {


        //创建adapter
        mShoppingRecycleAdapter = new ShoppingRecycleAdapter(this, ShoppingEntityList);
        //给RecyclerView设置adapter
        binding.goodsRecyclerview.setAdapter(mShoppingRecycleAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局

        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        binding.goodsRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        //设置item的分割线
        if (binding.goodsRecyclerview.getItemDecorationCount() == 0) {
            binding.goodsRecyclerview.addItemDecoration(new DividerItemDecorations(this, DividerItemDecorations.VERTICAL));
        }
        /**
         * 加减
         */
        mShoppingRecycleAdapter.setOnChangeListener(new ShoppingRecycleAdapter.OnChangeListener() {
            @Override
            public void onChange(ScrollBean.SAASOrderBean data, int position) {

                ShoppingEntityList.get(position).setQuantity(data.getQuantity());

                cll();

            }
        });
        /**
         * 删除
         */
        mShoppingRecycleAdapter.setOnDeleteListener(new ShoppingRecycleAdapter.OnDeleteListener() {
            @Override
            public void onDelete(ScrollBean.SAASOrderBean data, int position) {
                ShoppingEntityList.get(position).setQuantity(0);
                ShoppingEntityList.remove(position);

                cll();
            }
        });
    }

    /**
     * 计算价格
     */
    private void cll() {

        double prick=0.0;
        int quantity=0;
        for (int i=0;i<ShoppingEntityList.size();i++){
            prick+= BigDecimalUtils.formatRoundUp((Double.parseDouble(ShoppingEntityList.get(i).getOrder_price())*ShoppingEntityList.get(i).getQuantity()),2);
            quantity+=ShoppingEntityList.get(i).getQuantity();
        }

        viewModel.TotalPrice.set(prick+"");
        viewModel.TotalType.set(ShoppingEntityList.size()+"");
        viewModel.TotalQuantity.set(quantity+"");


    }


    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }


    private void initCustomTimePicker() {
        /**
         * @description
         *
         * 注意事项：
         * 1.自定义布局中，id为 optionspicker 或者 timepicker 的布局以及其子控件必须要有，否则会报空指针.
         * 具体可参考demo 里面的两个自定义layout布局。
         * 2.因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
         * setRangDate方法控制起始终止时间(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
         */
        Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(2009, 0, 1);
        Calendar endDate = Calendar.getInstance();

        endDate.set(Calendar.getInstance().get(Calendar.YEAR)+3, 12, 30);
        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
//                birthday.setText(getTime(date));
                KLog.d("选择时间："+getTime(date));
                KLog.d("现在时间："+getTime(Calendar.getInstance().getTime()));
                if (getTimeCompareSize(getTime(Calendar.getInstance().getTime()),getTime(date))==1){
                    RxToast.normal("结束时间小于当前时间！,请重新选择时间");

                }else {
                    viewModel.estimate_time.set(getTime(date));
                }
            }
        })
                .setLayoutRes(R.layout.pickerview_custom_time, new CustomListener() {

                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = (TextView) v.findViewById(R.id.tv_finish);
                        ImageView ivCancel = (ImageView) v.findViewById(R.id.iv_cancel);
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.returnData();
                                pvCustomTime.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomTime.dismiss();
                            }
                        });
                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})//分别对应年月日时分秒，默认全部显示
                .setContentTextSize(28)//滚轮文字大小
                .setTitleSize(26)//标题文字大小
                .setLineSpacingMultiplier(2.0f)//设置间距倍数
                .setItemVisibleCount(7)//设置最大可见数目
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(false)//是否循环滚动
                .setTitleBgColor(0xFFffffff)//标题背景颜色 Night mode
                .setBgColor(0xFFfafafa)//滚轮背景颜色 Night mode
//                .setRange(calendar.get(Calendar.YEAR) - 20, calendar.get(Calendar.YEAR) + 20)//默认是1900-2100年
                .setDate(selectedDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate, endDate)//起始终止年月日设定
                .setLabel("年", "月", "日", "时", "分", "秒")
                .isDialog(true)//是否显示为对话框样式
                .build();
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

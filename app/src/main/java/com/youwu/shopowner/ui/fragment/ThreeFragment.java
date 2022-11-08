package com.youwu.shopowner.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.android.material.tabs.TabLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youwu.shopowner.BR;
import com.youwu.shopowner.R;
import com.youwu.shopowner.app.AppApplication;
import com.youwu.shopowner.app.AppViewModelFactory;
import com.youwu.shopowner.app.UserUtils;
import com.youwu.shopowner.databinding.FragmentThreeBinding;
import com.youwu.shopowner.toast.RxToast;
import com.youwu.shopowner.ui.fragment.adapter.OrderAdapter;
import com.youwu.shopowner.ui.fragment.bean.SaleBillBean;
import com.youwu.shopowner.ui.login.LoginActivity;
import com.youwu.shopowner.ui.main.EventBusBean;
import com.youwu.shopowner.ui.order_goods.OrderDetailsActivity;
import com.youwu.shopowner.ui.order_goods.RefundOrderDetailsActivity;
import com.youwu.shopowner.utils_view.DividerItemDecorations;
import com.youwu.shopowner.utils_view.TimeUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.KLog;

import static me.goldze.mvvmhabit.base.BaseActivity.getTimeCompareSize;


/**
 * 2022/09/12
 */

public class ThreeFragment extends BaseFragment<FragmentThreeBinding,ThreeViewModel> {

    OrderAdapter mOrderAdapter;
    ArrayList<SaleBillBean> list=new ArrayList<>();

    int order_status=0;//状态 0所有 1.待接单2,待出餐  4.待取餐 5.退款
    int page=1;

    String start_time;
    String end_time;
    String tel="";
    String delivery_method="0";
    String StoreId;//店铺id



    String order_sn;

    private TimePickerView pvCustomTime;//时间选择器

    private int time_state;//1 开始 2 结束

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
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.IntegerEvent.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch (integer){
                   case  1:
                       showDialog();

                    break;
                }
            }
        });

        /**
         * 订单列表返回结果
         */
        viewModel.OrderList.observe(this, new Observer<ArrayList<SaleBillBean>>() {
            @Override
            public void onChanged(ArrayList<SaleBillBean> saleBillBeans) {
                if (page==1){
                    list.clear();
                }

                list.addAll(saleBillBeans);
                initRecyclerView();
            }
        });
    }

    @Override
    public void initData() {
        super.initData();

        StoreId= AppApplication.spUtils.getString("StoreId");

        viewModel.null_type.set(1);

        initTabData();
        initCustomTimePicker();
        //刷新
        binding.scSmartrefreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                list.clear();
                page=1;
                //获取订单列表
                initOrderList();
                refreshLayout.finishRefresh(true);
            }
        });
        //加载
        binding.scSmartrefreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                //获取订单列表
                initOrderList();
                refreshLayout.finishLoadMore(true);//加载完成
            }
        });


        //检查是否已经注册
        if(!EventBus.getDefault().isRegistered(this)){//是否注册eventbus的判断
            EventBus.getDefault().register(this);
        }
        initOrderList();
    }

    //MainActivity传递的数据
    @Subscribe
    public void onMQttBean(String  type) {

        if ("3".equals(type)){
            initOrderList();
        }
    };

    /**
     * 获取订单列表
     */
    private void initOrderList() {
        if (start_time==null){
            start_time="";
        }
        if (end_time==null){
            end_time="";
        }
        viewModel.order_list("".equals(start_time)?"":TimeUtil.getStart(start_time), "".equals(end_time)?"":TimeUtil.getEnd(end_time),delivery_method,tel,StoreId,order_status,page,order_sn);
    }

    //MainActivity传递的数据
    @Subscribe
    public void onMQttBean(EventBusBean eventBusBean) {
        viewModel.IntegerEvent.setValue(eventBusBean.getType());
        if (eventBusBean.getType()==3){
            order_status=eventBusBean.getState();
            initTabData();
        }
    };

    private void initTabData() {
        KLog.d("order_status:"+order_status);

        binding.tabLayout.getTabAt(order_status).select();
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //0所有 1.待接单2,待出餐  4.待取餐 5.退款
                page=1;
                if (tab.getPosition()==3||tab.getPosition()>3){
                    order_status = tab.getPosition()+1;
                }else {
                    order_status = tab.getPosition();
                }

                initOrderList();

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
    private void initRecyclerView() {

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
            public void OnItemClick(View view, SaleBillBean data, int position) {
                if (data.getRefund_type()==1){
                    Bundle bundle=new Bundle();
                    bundle.putString("order_sn",data.getOrder_sn());
                    startActivity(OrderDetailsActivity.class,bundle);
                }else {//退款订单详情
                    Bundle bundle=new Bundle();
                    bundle.putString("order_sn",data.getOrder_sn());
                    startActivity(RefundOrderDetailsActivity.class,bundle);}
            }
        });
        mOrderAdapter.setOnChangeListener(new OrderAdapter.OnChangeListener() {
            @Override
            public void onChange(SaleBillBean data, int position) {
                if (data.getRefund_type()==1){
                    Bundle bundle=new Bundle();
                    bundle.putString("order_sn",data.getOrder_sn());
                    startActivity(OrderDetailsActivity.class,bundle);
                }else {
                    Bundle bundle=new Bundle();
                    bundle.putString("order_sn",data.getOrder_sn());
                    startActivity(RefundOrderDetailsActivity.class,bundle);}
            }
        });


    }

    TextView start_time_text;
    TextView end_time_text;

    /**
     * 筛选弹窗
     */
    private void showDialog() {

        final Dialog dialog = new Dialog(getContext(), R.style.BottomDialog);

        //获取屏幕宽高
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int widths = size.x;
        int height = size.y;

        //获取界面
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.drawer_dialog, null);
        //将界面填充到AlertDiaLog容器
        dialog.setContentView(dialogView);


        ViewGroup.LayoutParams layoutParams = dialogView.getLayoutParams();
        //设置弹窗宽高
        layoutParams.width = (int) (widths);
        layoutParams.height = (int) (height*0.5);


//        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        dialogView.setLayoutParams(layoutParams);
        dialog.getWindow().setGravity(Gravity.TOP);
        dialog.setCancelable(true);//点击外部消失弹窗
        dialog.show();
        //初始化控件

        TextView determine_text = (TextView) dialogView.findViewById(R.id.determine_text);//确定
        TextView cancel_text = dialogView.findViewById(R.id.cancel_text);//取消
        EditText user_tel = dialogView.findViewById(R.id.user_tel);//用户手机号
        EditText order = dialogView.findViewById(R.id.order_sn);//订单号
         start_time_text = dialogView.findViewById(R.id.start_time);//开始时间
         end_time_text = dialogView.findViewById(R.id.end_time);//结束时间
        LinearLayout start_time_layout = dialogView.findViewById(R.id.start_time_layout);//开始时间
        LinearLayout end_time_layout = dialogView.findViewById(R.id.end_time_layout);//结束时间

        CheckBox modeOne =dialogView.findViewById(R.id.mode_one);
        CheckBox modeTwo =dialogView.findViewById(R.id.mode_two);
        CheckBox modeThree =dialogView.findViewById(R.id.mode_three);
        CheckBox modeFour =dialogView.findViewById(R.id.mode_four);

        if (tel!=null){
            user_tel.setText(tel);
        }
        if (order_sn!=null){
            order.setText(order_sn);
        }
        if (start_time!=null){
            start_time_text.setText(start_time);
        }
        if (end_time!=null){
            end_time_text.setText(end_time);
        }

        start_time_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time_state=1;
                pvCustomTime.show(); //弹出自定义时间选择器
            }
        });

        end_time_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time_state=2;
                pvCustomTime.show(); //弹出自定义时间选择器
            }
        });


        //取消
        cancel_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tel="";
                order_sn="";
                start_time="";
                end_time="";
                user_tel.setText("");
                order.setText("");
                start_time_text.setText("");
                end_time_text.setText("");


                modeOne.setChecked(false);
                modeTwo.setChecked(false);
                modeThree.setChecked(false);
                modeFour.setChecked(false);

            }
        });
        //确定
        determine_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                tel=user_tel.getText().toString();
                order_sn=order.getText().toString();


                List<Integer> list=new ArrayList<>();

                if (modeOne.isChecked()){
                    list.add(1);
                }
                if (modeTwo.isChecked()){
                    list.add(2);
                }
                if (modeThree.isChecked()){
                    list.add(3);
                }
                if (modeFour.isChecked()){
                    list.add(4);
                }
                 delivery_method="";
                for (int i=0;i<list.size();i++){
                    delivery_method+=list.get(i)+",";
                }

                initOrderList();

            }
        });
    }


    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
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
//        endDate.set(2299, 11, 31);
        //时间选择器 ，自定义布局
        pvCustomTime = new TimePickerBuilder(getContext(), new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调

                if (time_state==1){
                    start_time_text.setText(getTime(date));
                    start_time=getTime(date);
                }else {
                    if (getTimeCompareSize(start_time,getTime(date))==1){
                        RxToast.normal("结束时间小于当前时间！,请重新选择时间");
                    }else {
                        end_time_text.setText(getTime(date));
                        end_time=getTime(date);
                    }

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

    @Override
    public void onDestroy() {
        super.onDestroy();
        //反注册
        EventBus.getDefault().unregister(this);
    }

}

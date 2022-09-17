package com.youwu.shopowner.ui.order_goods;

import android.app.Dialog;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.tabs.TabLayout;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.xuexiang.xui.widget.button.SmoothCheckBox;
import com.youwu.shopowner.BR;
import com.youwu.shopowner.R;
import com.youwu.shopowner.UpData;
import com.youwu.shopowner.app.AppViewModelFactory;
import com.youwu.shopowner.bean.UpDateBean;
import com.youwu.shopowner.databinding.ActivityMainBinding;
import com.youwu.shopowner.databinding.ActivityOrderReceivingBinding;
import com.youwu.shopowner.toast.RxToast;
import com.youwu.shopowner.ui.fragment.adapter.WMOrderAdapter;
import com.youwu.shopowner.ui.fragment.bean.MqttBean;
import com.youwu.shopowner.ui.fragment.bean.XXCOrderBean;
import com.youwu.shopowner.ui.main.MainViewModel;
import com.youwu.shopowner.utils_view.DividerItemDecorations;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.KLog;

/**
 * @author: Administrator
 * @date: 2022/9/16
 */
public class OrderReceivingActivity extends BaseActivity<ActivityOrderReceivingBinding, OrderReceivingViewModel> {


    private ArrayList<XXCOrderBean> xxcOrderBeans = new ArrayList<>();

    int order_status = 1;//状态 1 待接单 2 待出餐 3 待退款

    WMOrderAdapter wmOrderAdapter;

    int widths;//屏幕长
    int height;//屏幕宽



    @Override
    public OrderReceivingViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(OrderReceivingViewModel.class);
    }

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_order_receiving;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }
    @Override
    public void initParam() {
        super.initParam();
        order_status=getIntent().getIntExtra("type",0);
    }

    @Override
    public void onResume() {
        super.onResume();
        initWMData(order_status);

        viewModel.getxcx_order_count();
    }

    @Override
    public void initData() {
        super.initData();
        getScreenSize();
        /**
         * 检查更新
         */
        viewModel.getAppVersion();

        //获取收银员信息
        viewModel.getMe();



        initTabData();
        initWMData(order_status);

        viewModel.getxcx_order_count();
    }

    @Override
    public void onStart() {
        super.onStart();
        //检查是否已经注册
        if(!EventBus.getDefault().isRegistered(this)){//是否注册eventbus的判断
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        //反注册
        EventBus.getDefault().unregister(this);
    }

    //MainActivity传递的数据
    @Subscribe
    public void onMQttBean(MqttBean mqttBean) {
        KLog.d("MainActivity传递："+mqttBean);

        viewModel.getxcx_order_count();

        initWMData(order_status);
    };

    /**
     * 获取屏幕长和高
     */
    private void getScreenSize() {
        //获取屏幕宽高
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        widths = size.x;
        height = size.y;
    }

    @Override
    public void initViewObservable() {
        viewModel.IntegerEvent.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch (integer){
                    case 1:
                        viewModel.getxcx_order_count();

                        initWMData(order_status);
                        break;
                    case 2://同意
                        initWMData(order_status);
                        RxToast.showTipToast(OrderReceivingActivity.this,"已同意！");
                        break;
                    case 3://拒绝
                        initWMData(order_status);
                        RxToast.showTipToast(OrderReceivingActivity.this,"已拒绝！");
                        break;

                }
            }
        });
        viewModel.upDateEvent.observe(this, new Observer<UpDateBean>() {
            @Override
            public void onChanged(UpDateBean upDateBean) {
                UpData.UpData(upDateBean);
            }
        });


        //小程序订单列表返回
        viewModel.xxc_order_list.observe(this, new Observer<ArrayList<XXCOrderBean>>() {
            @Override
            public void onChanged(ArrayList<XXCOrderBean> xxcOrder) {
                xxcOrderBeans = xxcOrder;

                initWMRecyclerView();

            }
        });


    }

    /**
     * 小程序订单
     */
    private void initWMRecyclerView() {


        //创建adapter
        wmOrderAdapter = new WMOrderAdapter(this, xxcOrderBeans);
        //给RecyclerView设置adapter
        binding.wmRecyclerView.setAdapter(wmOrderAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局

        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        binding.wmRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        //设置item的分割线
        if (binding.wmRecyclerView.getItemDecorationCount() == 0) {
            binding.wmRecyclerView.addItemDecoration(new DividerItemDecorations(this, DividerItemDecorations.VERTICAL));
        }

        wmOrderAdapter.setOnClickListener(new WMOrderAdapter.OnClickListener() {
            @Override
            public void onClick(final XXCOrderBean data, int position, final int status) {

                if (status==3){
                    new  XPopup.Builder(getBaseContext())
                            .maxWidth((int) (widths * 0.7))
                            .maxHeight((int) (height*0.4))
                            .asConfirm("提示", "拒单会退款，是否拒单?", "取消", "确认", new OnConfirmListener() {
                                @Override
                                public void onConfirm() {
                                    viewModel.edit_order_status(data.getOrder_sn(), status + "");
                                }
                            }, null,false)
                            .show();
                }else if (status==4){
                    new  XPopup.Builder(getBaseContext())
                            .maxWidth((int) (widths * 0.7))
                            .maxHeight((int) (height*0.4))
                            .asConfirm("提示", "是否出餐？", "取消", "确认", new OnConfirmListener() {
                                @Override
                                public void onConfirm() {
                                    viewModel.edit_order_status(data.getOrder_sn(), status + "");
                                }
                            }, null,false)
                            .show();

                }else {
                    viewModel.edit_order_status(data.getOrder_sn(), status + "");
                }


            }
        });

        wmOrderAdapter.setSelectionStatusOnClickListener(new WMOrderAdapter.SelectionStatusOnClickListener() {
            @Override
            public void SelectionStatusonClick(XXCOrderBean lists, int position, int status) {
                switch (status) {
                    case 1://拒绝

                        break;
                    case 2://同意

                        break;
                }
                SelectionDialog(lists.getOrder_sn(),status);
            }
        });

    }


    private void initTabData() {

        binding.tabLayout.getTabAt(order_status-1).select();
        binding.tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //1 待接单 2 待出餐 3 待退货

                order_status = tab.getPosition() + 1;

                viewModel.getxcx_order_count();
                initWMData(order_status);

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
     * 获取小程序订单列表
     *
     * @param type
     */
    private void initWMData(int type) {

        viewModel.xcx_order_list(type + "");
    }

    SmoothCheckBox yes_check;
    SmoothCheckBox no_check;
    /**
     * 退款拒绝还是同意弹窗
     */
    private void SelectionDialog(final String order_sn, final int status) {

        final Dialog dialog = new Dialog(this, R.style.BottomDialog);

        //获取屏幕宽高
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int widths = size.x;
        int height = size.y;

        //获取界面
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_selection, null);
        //将界面填充到AlertDiaLog容器
        dialog.setContentView(dialogView);
        ViewGroup.LayoutParams layoutParams = dialogView.getLayoutParams();
        //设置弹窗宽高
        layoutParams.width = (int) (widths * 0.8);
        layoutParams.height = (int) (height * 0.8);
        //将界面填充到AlertDiaLog容器
        dialogView.setLayoutParams(layoutParams);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCancelable(true);//点击外部消失弹窗
        dialog.show();

        //初始化控件
        TextView close_text = (TextView) dialogView.findViewById(R.id.close_text);//返回
        TextView selection_name = (TextView) dialogView.findViewById(R.id.selection_name);//
        TextView content = (TextView) dialogView.findViewById(R.id.content);//
        LinearLayout refund_layout = (LinearLayout) dialogView.findViewById(R.id.refund_layout);//
        LinearLayout agree_layout = (LinearLayout) dialogView.findViewById(R.id.agree_layout);//
        final EditText reason = (EditText) dialogView.findViewById(R.id.reason);//
        final TextView cancel = (TextView) dialogView.findViewById(R.id.cancel);//取消

        yes_check = dialog.findViewById(R.id.yes_check);
        no_check = dialog.findViewById(R.id.no_check);
        yes_check.setChecked(true);
        no_check.setChecked(false);

        final TextView confirm = (TextView) dialogView.findViewById(R.id.confirm);//确定
        if (status == 1) {
            selection_name.setText("同意");
            agree_layout.setVisibility(View.VISIBLE);
            refund_layout.setVisibility(View.GONE);

        } else {
            selection_name.setText("拒绝");
            agree_layout.setVisibility(View.GONE);
            refund_layout.setVisibility(View.VISIBLE);
        }
        //返回
        close_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        yes_check.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {

                if (isChecked){
                    no_check.setChecked(false);
                }
                KLog.d("同意：" + yes_check.isChecked() + "\n拒绝：" + no_check.isChecked());
            }
        });
        no_check.setOnCheckedChangeListener(new SmoothCheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SmoothCheckBox checkBox, boolean isChecked) {
                if (isChecked){
                    yes_check.setChecked(false);
                }
                KLog.d("同意：" + yes_check.isChecked() + "\n拒绝：" + no_check.isChecked());
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        //确定
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String refund_reason;
                refund_reason=reason.getText().toString();
                if (refund_reason==null){
                    refund_reason="";
                }
                viewModel.audit_order_refund(status,order_sn,refund_reason,yes_check.isChecked()?"1":"2",dialog);

                KLog.d("同意：" + yes_check.isChecked() + "\n拒绝：" + no_check.isChecked());

            }
        });

    }
}

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

import com.xuexiang.xui.widget.button.SmoothCheckBox;
import com.youwu.shopowner.BR;
import com.youwu.shopowner.R;
import com.youwu.shopowner.app.AppApplication;
import com.youwu.shopowner.app.AppViewModelFactory;
import com.youwu.shopowner.databinding.ActivityOrderDetailsBinding;
import com.youwu.shopowner.databinding.ActivityRefundOrderDetailsBinding;
import com.youwu.shopowner.toast.RxToast;
import com.youwu.shopowner.ui.fragment.bean.OrderDetailsBean;
import com.youwu.shopowner.ui.fragment.bean.RefundDetailsBean;
import com.youwu.shopowner.ui.fragment.bean.SaleBillBean;
import com.youwu.shopowner.ui.order_goods.adapter.OrderGoodsRecycleAdapter;
import com.youwu.shopowner.ui.order_goods.adapter.RefundImageRecycleAdapter;
import com.youwu.shopowner.utils_view.DividerItemDecorations;
import com.youwu.shopowner.utils_view.StatusBarUtil;

import java.util.ArrayList;

import me.goldze.mvvmhabit.base.BaseActivity;
import me.goldze.mvvmhabit.utils.KLog;

/**
 * @author: Administrator
 * @date: 2022/9/17
 */
public class RefundOrderDetailsActivity extends BaseActivity<ActivityRefundOrderDetailsBinding, RefundOrderDetailsViewModel> {


    //recyclerveiw的适配器
    private OrderGoodsRecycleAdapter mOrderGoodsRecycleAdapter;
    private RefundImageRecycleAdapter mRefundImageRecycleAdapter;
    //数据集合
    private ArrayList<OrderDetailsBean.GoodsListBean> mList = new ArrayList<>();
    private ArrayList<String> mImageList = new ArrayList<>();


    String order_sn;
    String store_id;
    @Override
    public void initParam() {
        super.initParam();
        order_sn=getIntent().getStringExtra("order_sn");
    }
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_refund_order_details;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public RefundOrderDetailsViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(RefundOrderDetailsViewModel.class);
    }

    @Override
    public void initViewObservable() {
        viewModel.IntegerEvent.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch (integer){
                    case 1:
                        initPrint();
                        break;
                    case 2://拒绝
                        SelectionDialog(order_sn,2);
                        break;
                    case 3://同意
                        SelectionDialog(order_sn,1);
                        break;
                    case 4:

                        initOrderDetails();
                        break;


                }
            }
        });
        //拨打电话
        viewModel.PhoneEvent.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                callPhone(s);
            }
        });

        viewModel.OrderDetailsLiveEvent.observe(this, new Observer<RefundDetailsBean>() {
            @Override
            public void onChanged(RefundDetailsBean orderDetailsBean) {
                if (mList.size()!=0){
                    mList.clear();
                }
                if (mImageList.size()!=0){
                    mImageList.clear();
                }

                for (int i=0;i<orderDetailsBean.getDetails().size();i++){
                    OrderDetailsBean.GoodsListBean goodsListBean=new OrderDetailsBean.GoodsListBean();
                    goodsListBean.setGoods_name(orderDetailsBean.getDetails().get(i).getGoods_name());
                    goodsListBean.setGoods_price(orderDetailsBean.getDetails().get(i).getGoods_price());
                    goodsListBean.setGoods_number(orderDetailsBean.getDetails().get(i).getGoods_number());
                    goodsListBean.setGoods_thumb(orderDetailsBean.getDetails().get(i).getGoods_thumb());
                    mList.add(goodsListBean);
                }

                mImageList.addAll(orderDetailsBean.getImage());

                initRecyclerView();
                initImageRecyclerView();
            }
        });
    }

    /**
     * 打印
     */
    private void initPrint() {
        viewModel.Print(order_sn,store_id);
    }

    @Override
    public void initData() {
        super.initData();
        StatusBarUtil.setRootViewFitsSystemWindows(this, true);
        //修改状态栏是状态栏透明
        StatusBarUtil.setTransparentForWindow(this);
        StatusBarUtil.setDarkMode(this);//使状态栏字体变为黑色
        store_id= AppApplication.spUtils.getString("StoreId");
        initOrderDetails();

    }

    /**
     * 查询订单详情
     */
    private void initOrderDetails() {
        viewModel.refund_details(order_sn);
    }

    /**
     * 商品列表
     */
    private void initRecyclerView() {
        //创建adapter
        mOrderGoodsRecycleAdapter = new OrderGoodsRecycleAdapter(this, mList);
        //给RecyclerView设置adapter
        binding.goodsRecyclerview.setAdapter(mOrderGoodsRecycleAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局

        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        binding.goodsRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.VERTICAL));
        //设置item的分割线
        if (binding.goodsRecyclerview.getItemDecorationCount() == 0) {
            binding.goodsRecyclerview.addItemDecoration(new DividerItemDecorations(this, DividerItemDecorations.VERTICAL));
        }
    }

    /**
     * 图片列表
     */
    private void initImageRecyclerView() {
        if (mImageList.size()==0){
            binding.refundImageLayout.setVisibility(View.GONE);
        }else {
            binding.refundImageLayout.setVisibility(View.VISIBLE);
        }
        //创建adapter
        mRefundImageRecycleAdapter = new RefundImageRecycleAdapter(this, mImageList);
        //给RecyclerView设置adapter
        binding.imageRecyclerview.setAdapter(mRefundImageRecycleAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局

        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        binding.imageRecyclerview.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayoutManager.HORIZONTAL));
        //设置item的分割线
        if (binding.imageRecyclerview.getItemDecorationCount() == 0) {
            binding.imageRecyclerview.addItemDecoration(new DividerItemDecorations(this, DividerItemDecorations.HORIZONTAL));
        }
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
        layoutParams.height = (int) (height * 0.4);
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

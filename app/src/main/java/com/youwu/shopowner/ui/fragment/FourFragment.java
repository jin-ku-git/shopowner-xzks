package com.youwu.shopowner.ui.fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;


import com.permissionx.guolindev.PermissionX;
import com.permissionx.guolindev.callback.RequestCallback;
import com.xuexiang.xqrcode.ui.CaptureActivity;
import com.youwu.shopowner.BR;
import com.youwu.shopowner.R;
import com.youwu.shopowner.app.AppApplication;
import com.youwu.shopowner.app.AppViewModelFactory;
import com.youwu.shopowner.app.UserUtils;
import com.youwu.shopowner.databinding.FragmentFourBinding;
import com.youwu.shopowner.toast.RxToast;
import com.youwu.shopowner.ui.goods_operate.InventoryActivity;
import com.youwu.shopowner.ui.goods_operate.LossReportingActivity;
import com.youwu.shopowner.ui.goods_operate.ReturnGoodsActivity;
import com.youwu.shopowner.ui.goods_operate.SellOffActivity;
import com.youwu.shopowner.ui.login.LoginActivity;
import com.youwu.shopowner.ui.main.EventBusBean;
import com.youwu.shopowner.ui.set_up.SettingsActivity;
import com.youwu.shopowner.utils.Constant;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import me.goldze.mvvmhabit.base.BaseFragment;
import me.goldze.mvvmhabit.utils.KLog;

import static android.app.Activity.RESULT_OK;

/**
 * 2022/09/12
 */

public class FourFragment extends BaseFragment<FragmentFourBinding,FourViewModel> {
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_four;
    }

    @Override
    public FourViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用NetWorkViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getActivity().getApplication());
        return ViewModelProviders.of(this, factory).get(FourViewModel.class);
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.IntegerEvent.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch (integer){
                    case 0:
                        EventBus.getDefault().post(new EventBusBean(3,0));
                        break;
                    case 1:
                        EventBus.getDefault().post(new EventBusBean(3,1));
                        break;
                    case 2:
                        EventBus.getDefault().post(new EventBusBean(3,2));
                        break;
                    case 3:
                        EventBus.getDefault().post(new EventBusBean(3,3));
                        break;
                    case 4:
                        EventBus.getDefault().post(new EventBusBean(3,4));
                        break;
                    case 5://核销
                        startQrCode();
                        break;
                    case 6://订货
                        EventBus.getDefault().post(new EventBusBean(2,0));

                        break;
                    case 7://退货
                        startActivity(ReturnGoodsActivity.class);
                        break;
                    case 8://报损商品

                       startActivity(LossReportingActivity.class);

                        break;
                    case 9://盘点

                        startActivity(InventoryActivity.class);

                        break;
                    case 10://沽清

                        startActivity(SellOffActivity.class);

                        break;
                    case 66:
                        RxToast.showTipToast(getActivity(),"核销成功！");
                        break;
                    case 88:
                        showDialog();
                        break;
                }
            }
        });

    }

    @Override
    public void initData() {
        super.initData();
        viewModel.nameEvent.set(AppApplication.spUtils.getString("Name"));
        viewModel.telEvent.set(AppApplication.spUtils.getString("tel"));

    }


    /**
     * 退出登录提示弹窗
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
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.tips_dialog, null);
        //将界面填充到AlertDiaLog容器
        dialog.setContentView(dialogView);


        ViewGroup.LayoutParams layoutParams = dialogView.getLayoutParams();
        //设置弹窗宽高
        layoutParams.width = (int) (widths * 0.7);
//        layoutParams.height = (int) (height);


//        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        dialogView.setLayoutParams(layoutParams);
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.setCancelable(false);//点击外部消失弹窗
        dialog.show();
        //初始化控件

        TextView determine_text = (TextView) dialogView.findViewById(R.id.determine_text);//确定
        TextView cancel_text = dialogView.findViewById(R.id.cancel_text);//取消

        //取消
        cancel_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        //确定
        determine_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("TestXML", 0);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.clear().commit();


                UserUtils.logout();


                Intent intent=new Intent();
                intent.setClass(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    // 开始扫码
    public void startQrCode(){
        PermissionX.init(this)
                .permissions(Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE)
                .request(new RequestCallback() {
                    @Override
                    public void onResult(boolean allGranted, @NonNull List<String> grantedList, @NonNull List<String> deniedList) {
                        if (allGranted) {
                            Intent intent = new Intent(getContext(), CaptureActivity.class);
                            startActivityForResult(intent, Constant.REQ_QR_CODE);
                        } else {
                            RxToast.normal("权限被拒绝，无法使用此功能");
                        }
                    }
                });
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //扫描结果回调
        if (requestCode == Constant.REQ_QR_CODE && resultCode == RESULT_OK) {
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString(Constant.INTENT_EXTRA_KEY_QR_SCAN);

            if (scanResult!=null){
                if (scanResult.startsWith("xzks")) {
                    String order_sn=scanResult.replace("xzks_","");
                    viewModel.close_order(order_sn);

                }
            }

        }

    }
}

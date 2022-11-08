package com.youwu.shopowner.ui.goods_operate;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.RadioGroup;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnConfirmListener;
import com.youwu.shopowner.BR;
import com.youwu.shopowner.R;
import com.youwu.shopowner.app.AppViewModelFactory;
import com.youwu.shopowner.app.UserUtils;
import com.youwu.shopowner.databinding.ActivitySetUpGoodsBinding;
import com.youwu.shopowner.databinding.ActivitySettingsBinding;
import com.youwu.shopowner.toast.RxToast;
import com.youwu.shopowner.ui.fragment.bean.CommunityBean;
import com.youwu.shopowner.ui.login.LoginActivity;
import com.youwu.shopowner.ui.set_up.ModifyPasswordViewModel;
import com.youwu.shopowner.utils_view.StatusBarUtil;

import me.goldze.mvvmhabit.base.BaseActivity;

/**
 * 修改商品
 * @author: Administrator
 * @date: 2022/11/02
 */
public class SetUpGoodsActivity extends BaseActivity<ActivitySetUpGoodsBinding, SetUpGoodsViewModel> {


    private CommunityBean date;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_set_up_goods;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public SetUpGoodsViewModel initViewModel() {
        //使用自定义的ViewModelFactory来创建ViewModel，如果不重写该方法，则默认会调用LoginViewModel(@NonNull Application application)构造方法
        AppViewModelFactory factory = AppViewModelFactory.getInstance(getApplication());
        return ViewModelProviders.of(this, factory).get(SetUpGoodsViewModel.class);
    }

    @Override
    public void initParam() {
        super.initParam();
        date= (CommunityBean) getIntent().getSerializableExtra("CommunityBean");
    }

    @Override
    public void initViewObservable() {
        viewModel.IntegerEvent.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                switch (integer){
                    case 1://确认修改
                        TipsToast("确认要修改该商品吗？");
                        break;

                    case 3:
                        RxToast.showTipToast(SetUpGoodsActivity.this,"修改成功！");
                        finish();
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
        StatusBarUtil.setDarkMode(this);//使状态栏字体变为白色

        if (date!=null){
            viewModel.GoodsName.set(date.getGoods_name());
            viewModel.GoodsPrice.set(date.getGoods_price());
            viewModel.MarketValue.set(date.getGoods_price());
            viewModel.GoodsStock.set(date.getStock()+"");
            viewModel.MarketValue.set(date.getMarket_price()+"");
            viewModel.type.set(date.getType());
            if (date.getStatus()==1){
                viewModel.statusField.set(1);
            }else {
                viewModel.statusField.set(2);
            }

        }

        binding.hideRadiusGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.hide_radius_yes:
                        viewModel.statusField.set(1);
                        break;

                    case R.id.hide_radius_no:
                        viewModel.statusField.set(2);
                        break;

                    default:
                        break;
                }
            }
        });
    }

    private void TipsToast(String content){
        new  XPopup.Builder(SetUpGoodsActivity.this)
                .maxWidth((int) (widths * 0.8))
                .maxHeight((int) (height*0.6))
                .asConfirm("提示", content, "取消", "确认", new OnConfirmListener() {
                    @Override
                    public void onConfirm() {
                    viewModel.updateStoreGoods(date.getType()+"",date.getGoods_sku(),date.getPackage_id()+"");

                    }
                }, null,false)
                .show();
    }
}

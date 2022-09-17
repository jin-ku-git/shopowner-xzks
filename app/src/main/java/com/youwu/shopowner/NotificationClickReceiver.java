package com.youwu.shopowner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.squareup.haha.perflib.Main;
import com.youwu.shopowner.ui.main.MainActivity;
import com.youwu.shopowner.ui.set_up.SettingsActivity;

import me.goldze.mvvmhabit.utils.KLog;

/**
 * @author: Administrator
 * @date: 2022/9/16
 */
public class NotificationClickReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        KLog.d("我被点击了");
        Intent newIntent = new Intent(context, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        newIntent.putExtra("type","2");
        context.startActivity(newIntent);
    }
}



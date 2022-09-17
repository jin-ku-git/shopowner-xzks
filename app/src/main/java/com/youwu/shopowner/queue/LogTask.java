package com.youwu.shopowner.queue;

import android.util.Log;

import com.youwu.shopowner.app.AppApplication;


/**
 * @author: Administrator
 * @date: 2022/8/12
 */
public class LogTask extends BaseTask {
    String name;
    String content;

    public LogTask(String name,String content) {
        this.name = name;
        this.content = content;
    }

    //执行任务方法，在这里实现你的任务具体内容
    @Override
    public void doTask() {
        super.doTask();
        Log.i("LogTask", "--doTask-" + name);

        //如果这个Task的执行时间是不确定的，比如上传图片，那么在上传成功后需要手动调用
        //unLockBlock方法解除阻塞，例如：
        AppApplication.mSpeechSynthesizer.speak(content);
        unLockBlock();
    }

    //任务执行完的回调，在这里你可以做些释放资源或者埋点之类的操作
    @Override
    public void finishTask() {
        super.finishTask();
        Log.i("LogTask", "--finishTask-" + name);
    }
}
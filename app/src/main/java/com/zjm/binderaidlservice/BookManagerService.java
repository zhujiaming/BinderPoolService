package com.zjm.binderaidlservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by zhujiaming on 2017/12/17.
 */

public class BookManagerService extends Service {


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        hint("onBind");
        return new BinderPoolImpl().asBinder();
    }

    @Override
    public void onCreate() {
        hint("onCreate");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        hint("onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        hint("onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        hint("onDestroy");
        super.onDestroy();
    }


    public static void hint(String msg) {
//        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
        Log.i("zhujm", "service: " + msg);
    }
}

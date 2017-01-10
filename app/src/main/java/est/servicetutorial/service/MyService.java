package est.servicetutorial.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Random;

/**
 * Created by Santosh on 10-01-2017.
 */

public class MyService extends Service {

    private int randomNumber;
    private boolean isServiceStarted;
    private IBinder binder = new MyServiceBinder();
    private static final String TAG ="MyService";
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG,"onBind");
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG,"onStartCommand");
        Log.i(TAG,"Thread ID : "+Thread.currentThread().getId());
        isServiceStarted = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                startRandomNumberGenerator();
            }
        }).start();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i(TAG,"onDestroy");
        stopRandomNumberGenerator();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.i(TAG,"onUnbind");
        return super.onUnbind(intent);

    }

    @Override
    public void onRebind(Intent intent) {
        super.onRebind(intent);
        Log.i(TAG,"onRebind");
    }

    private void startRandomNumberGenerator(){
        while (isServiceStarted){
            try {
                Thread.sleep(1000);
                if(isServiceStarted)
                    randomNumber = new Random().nextInt(100);
                    Log.i(TAG,"Thread ID : "+Thread.currentThread().getId()+" Random Number : "+randomNumber);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    private void stopRandomNumberGenerator(){
        isServiceStarted = false;
    }

    public int getRandomNumber(){
        return randomNumber;
    }


   public class MyServiceBinder extends Binder{
        public MyService getService(){
            return MyService.this;
        }
    }
}

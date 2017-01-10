package est.servicetutorial.activty;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import est.servicetutorial.R;
import est.servicetutorial.service.MyService;

public class MainActivity extends AppCompatActivity {
    private TextView tvRandomNumber;
    int count = 0;

    private boolean isBoundService;
    private ServiceConnection serviceConnection;
    private MyService myService;
    private Intent intent;
    private boolean stopLoop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvRandomNumber = (TextView)findViewById(R.id.tv_number);
        intent = new Intent(getApplicationContext(),MyService.class);
    }

    public void onClickStartService(View view){
        startService(intent);
    }
    public void onClickStopService(View view){
        stopService(intent);
    }
    public void onClickBindService(View view){
        if (serviceConnection == null){
            serviceConnection = new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    MyService.MyServiceBinder myServiceBinder = (MyService.MyServiceBinder)iBinder;
                    myService = myServiceBinder.getService();
                    isBoundService = true;
                }

                @Override
                public void onServiceDisconnected(ComponentName componentName) {
                    isBoundService = false;
                }
            };
        }
        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
    }
    public void onClickUnbindService(View view){
        if (isBoundService){
            unbindService(serviceConnection);
            isBoundService = false;
        }
    }
    public void onClickRandom(View view){
        if (isBoundService){
            tvRandomNumber.setText(" Random Number : "+myService.getRandomNumber());
        }else{
            tvRandomNumber.setText(" Service is not bind ");
        }
    }
}

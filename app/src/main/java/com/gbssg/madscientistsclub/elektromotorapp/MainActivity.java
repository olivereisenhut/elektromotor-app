package com.gbssg.madscientistsclub.elektromotorapp;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.SeekBar;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    ImageView imageEMotor;
    ImageView imageEMotorFakeBorder;
    SeekBar controlSpeed;

    private final BroadcastReceiver bluetoothReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                        BluetoothAdapter.ERROR);
                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        BluetoothConnectionSocket.closeConnection();
                        break;
                    case BluetoothAdapter.STATE_ON:
                        tryToConnectSocket();
                        break;
                }
            }
            changeColorOfBorderForState();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.gbssg.madscientistsclub.elektromotorapp.R.layout.activity_main);

        imageEMotor = findViewById(com.gbssg.madscientistsclub.elektromotorapp.R.id.imageEMotor);
        controlSpeed = findViewById(com.gbssg.madscientistsclub.elektromotorapp.R.id.controlSpeed);
        imageEMotorFakeBorder = findViewById(com.gbssg.madscientistsclub.elektromotorapp.R.id.imageEMotorFakeBorder);

        IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(bluetoothReceiver, filter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        tryToConnectSocket();
        controlSpeed.setOnSeekBarChangeListener(new ControlSpeedChangeListener());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(bluetoothReceiver);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        tryToConnectSocket();
    }

    public void changeColorOfBorderForState() {
        if (BluetoothConnectionSocket.getInstance() == null || !BluetoothConnectionSocket.canConnectToDefaultDevice()) {

            imageEMotorFakeBorder.setImageResource(R.color.colorDisconnected);
            final BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();

            if (defaultAdapter == null || !defaultAdapter.isEnabled()) {
                Intent turnBluetoothOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(turnBluetoothOn, 1);
            }
        } else {
            imageEMotorFakeBorder.setImageResource(R.color.colorConnected);
        }
    }

    private void tryToConnectSocket() {
        if (BluetoothConnectionSocket.getInstance() != null) {
            try {
                BluetoothConnectionSocket.getInstance().connect();
            } catch (IOException e) {
                BluetoothConnectionSocket.closeConnection();
                e.printStackTrace();
            }
        }
        changeColorOfBorderForState();
    }
}

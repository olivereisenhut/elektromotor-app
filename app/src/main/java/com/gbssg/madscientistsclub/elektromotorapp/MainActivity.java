package com.gbssg.madscientistsclub.elektromotorapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.SeekBar;

import java.io.IOException;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    ImageView imageEMotor;
    ImageView imageEMotorFakeBorder;
    SeekBar controlSpeed;
    BluetoothSocket socket;

    private final BroadcastReceiver bluetoothReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();

            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                        BluetoothAdapter.ERROR);
                changeColorOfBorderForState();
            }
        }
    };

    private BluetoothAdapter bluetoothAdapter;
    private Set<BluetoothDevice> pairedDevices;

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
        socket = BluetoothConnectionSocket.getInstance();
        try {
            socket.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        changeColorOfBorderForState();
        controlSpeed.setOnSeekBarChangeListener(new ControlSpeedChangeListener());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(bluetoothReceiver);
    }

    public void changeColorOfBorderForState() {
        if (socket == null) {
            imageEMotorFakeBorder.setImageResource(com.gbssg.madscientistsclub.elektromotorapp.R.color.colorDisconnected);
            Intent turnBluetoothOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnBluetoothOn, 1);
        } else {
            imageEMotorFakeBorder.setImageResource(com.gbssg.madscientistsclub.elektromotorapp.R.color.colorConnected);
        }
    }
}

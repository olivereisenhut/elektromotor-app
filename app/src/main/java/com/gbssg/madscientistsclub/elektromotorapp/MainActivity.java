package com.gbssg.madscientistsclub.elektromotorapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.gbssg.madscientistsclub.elektromotorapp.bluetooth.BluetoothBroadcastReceiver;
import com.gbssg.madscientistsclub.elektromotorapp.bluetooth.BluetoothConnectionManager;
import com.gbssg.madscientistsclub.elektromotorapp.bluetooth.BluetoothConnectionSocket;
import com.gbssg.madscientistsclub.elektromotorapp.bluetooth.BluetoothPairedDelegate;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements BluetoothPairedDelegate {

    ImageView imageEMotor;
    ImageView imageEMotorFakeBorder;
    SeekBar controlSpeed;
    BluetoothConnectionManager bluetoothConnectionManager;
    final BroadcastReceiver bluetoothReceiver = new BluetoothBroadcastReceiver();


    @Override
    public void pairingFinished(boolean successful) {
        changeColorOfBorderForState(successful);
    }

    public BluetoothConnectionManager getBluetoothConnectionManager() {
        return bluetoothConnectionManager;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.gbssg.madscientistsclub.elektromotorapp.R.layout.activity_main);

        imageEMotor = findViewById(com.gbssg.madscientistsclub.elektromotorapp.R.id.imageEMotor);
        controlSpeed = findViewById(com.gbssg.madscientistsclub.elektromotorapp.R.id.controlSpeed);
        controlSpeed.setEnabled(false);
        imageEMotorFakeBorder = findViewById(com.gbssg.madscientistsclub.elektromotorapp.R.id.imageEMotorFakeBorder);

        bluetoothConnectionManager = new BluetoothConnectionManager(this);

        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        filter.addAction(BluetoothDevice.ACTION_ACL_CONNECTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
        filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
        registerReceiver(bluetoothReceiver, filter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        forceToEnableBluetooth();
        controlSpeed.setOnSeekBarChangeListener(new ControlSpeedChangeListener());
    }

    @Override
    protected void onResume() {
        super.onResume();
        forceToEnableBluetooth();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(bluetoothReceiver);
        bluetoothConnectionManager.tryCloseSocket();
    }

    @Override
    protected void onStop() {
        super.onStop();
        bluetoothConnectionManager.tryCloseSocket();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        forceToEnableBluetooth();
    }



    private void forceToEnableBluetooth() {
        final BluetoothAdapter defaultAdapter = BluetoothAdapter.getDefaultAdapter();

        if (defaultAdapter == null || !defaultAdapter.isEnabled()) {
            Intent turnBluetoothOn = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(turnBluetoothOn, 1);
        }
        bluetoothConnectionManager.tryConnectSocket();
    }

    private void changeColorOfBorderForState(boolean successful) {
        if (successful) {
            imageEMotorFakeBorder.setImageResource(R.color.colorConnected);

        } else {
            imageEMotorFakeBorder.setImageResource(R.color.colorDisconnected);
        }
    }
}

package com.gbssg.madscientistsclub.elektromotorapp.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.gbssg.madscientistsclub.elektromotorapp.MainActivity;


public class BluetoothBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        MainActivity mainActivity = (MainActivity) context;
        final String action = intent.getAction();
        if (mainActivity != null && action != null) {

            if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                final int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                        BluetoothAdapter.ERROR);
                switch (state) {
                    case BluetoothAdapter.STATE_OFF:
                        mainActivity.getBluetoothConnectionManager().tryCloseSocket();
                        mainActivity.forceToEnableBluetooth();
                        break;
                    case BluetoothAdapter.STATE_ON:
                        mainActivity.getBluetoothConnectionManager().tryConnectSocket();
                        break;
                }
            } else {
                mainActivity.getBluetoothConnectionManager().tryCloseSocket();
            }
        }
    }
}

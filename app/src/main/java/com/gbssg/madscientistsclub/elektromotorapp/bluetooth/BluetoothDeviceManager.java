package com.gbssg.madscientistsclub.elektromotorapp.bluetooth;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import java.util.Set;

public class BluetoothDeviceManager {

    public static BluetoothDevice getDefaultDevice() {
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> devices = adapter.getBondedDevices();
        BluetoothDevice arduino = null;
        for (BluetoothDevice device : devices) {
            if (device.getName().equals("HMSoft")) {
                arduino = device;
                break;
            }
        }
        return arduino;
    }
}

package com.gbssg.madscientistsclub.elektromotorapp.bluetooth;


import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import java.util.Set;

class BluetoothDeviceManager {

    static BluetoothDevice getDefaultDevice() {
        final BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        final Set<BluetoothDevice> devices = adapter.getBondedDevices();
        BluetoothDevice arduino = null;
        for (final BluetoothDevice device : devices) {
            if (device.getName().equals("HMSoft")) {
                arduino = device;
                break;
            }
        }
        return arduino;
    }
}

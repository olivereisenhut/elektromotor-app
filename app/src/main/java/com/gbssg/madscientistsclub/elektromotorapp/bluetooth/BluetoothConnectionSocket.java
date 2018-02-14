package com.gbssg.madscientistsclub.elektromotorapp.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.util.UUID;

public class BluetoothConnectionSocket {
    private static BluetoothSocket socket;
    private static final UUID THE_MAGIC_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public static BluetoothSocket getDefaultInstance() {
        if (socket == null) {
            BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
            BluetoothDevice arduino = BluetoothDeviceManager.getDefaultDevice();

            if (arduino == null) {
                return null;
            }

            BluetoothDevice device = adapter.getRemoteDevice(arduino.getAddress());
            try {
                socket = device.createInsecureRfcommSocketToServiceRecord(THE_MAGIC_UUID);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return socket;
    }
}

package com.gbssg.madscientistsclub.elektromotorapp.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.util.UUID;

public class BluetoothConnectionSocket {
    private static final UUID THE_MAGIC_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static BluetoothSocket socket;

    public static BluetoothSocket getDefaultInstance() {
        if (socket == null) {
            final BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
            final BluetoothDevice arduino = BluetoothDeviceManager.getDefaultDevice();

            if (arduino == null) {
                return null;
            }

            final BluetoothDevice device = adapter.getRemoteDevice(arduino.getAddress());
            try {
                socket = device.createInsecureRfcommSocketToServiceRecord(THE_MAGIC_UUID);
            } catch (final IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return socket;
    }
}

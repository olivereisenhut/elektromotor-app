package com.gbssg.madscientistsclub.elektromotorapp;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

class BluetoothConnectionSocket {
    private static  BluetoothSocket socket;
    private static final UUID THE_MAGIC_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

   static BluetoothSocket getInstance() {
        if (socket == null) {
            BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
            BluetoothDevice arduino = getDefaultDevice(adapter);

            if (arduino == null) {
                return null;
            }

            BluetoothDevice device = adapter.getRemoteDevice(arduino.getAddress());
            try {
                socket = device.createInsecureRfcommSocketToServiceRecord(THE_MAGIC_UUID);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return socket;
    }

    static void closeConnection() {
        try {
            socket.close();
            socket = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static boolean canConnectToDefaultDevice() {
        try {
            socket.connect();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static BluetoothDevice getDefaultDevice(BluetoothAdapter adapter) {
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

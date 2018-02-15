package com.gbssg.madscientistsclub.elektromotorapp.bluetooth;


import android.bluetooth.BluetoothSocket;

import java.io.IOException;

public class BluetoothConnectionManager {
    private BluetoothSocket socket;
    private BluetoothPairedDelegate delegate;

    public BluetoothConnectionManager(final BluetoothPairedDelegate delegate) {
        this.delegate = delegate;
        this.socket = BluetoothConnectionSocket.getDefaultInstance();
        this.tryConnectSocket();
    }

    public void tryConnectSocket() {
        if (socket != null) {
            try {
                if (!socket.isConnected()) {
                    socket.connect();
                }
                delegate.pairingFinished(true);
            } catch (final IOException e) {
                e.printStackTrace();
                delegate.pairingFinished(false);
            }
        }
        this.socket = BluetoothConnectionSocket.getDefaultInstance();
    }

    public void tryCloseSocket() {
        if (socket != null) {
            try {
                socket.close();
            } catch (final IOException ignored) {
            }
        }
    }
}

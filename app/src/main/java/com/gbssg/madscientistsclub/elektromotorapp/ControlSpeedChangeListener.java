package com.gbssg.madscientistsclub.elektromotorapp;

import android.bluetooth.BluetoothSocket;
import android.widget.SeekBar;

import java.io.IOException;


public class ControlSpeedChangeListener implements SeekBar.OnSeekBarChangeListener {

    private BluetoothSocket socket;

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (socket == null) {
            socket = BluetoothConnectionSocket.getInstance();
        }
        else {
            try {
                socket.getOutputStream().write(progress);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }
}

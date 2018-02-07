package com.gbssg.madscientistsclub.elektromotorapp;

import android.widget.SeekBar;

import com.gbssg.madscientistsclub.elektromotorapp.bluetooth.BluetoothConnectionSocket;

import java.io.IOException;


public class ControlSpeedChangeListener implements SeekBar.OnSeekBarChangeListener {

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (BluetoothConnectionSocket.getDefaultInstance() != null) {
            try {
                BluetoothConnectionSocket.getDefaultInstance().getOutputStream().write(progress);
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

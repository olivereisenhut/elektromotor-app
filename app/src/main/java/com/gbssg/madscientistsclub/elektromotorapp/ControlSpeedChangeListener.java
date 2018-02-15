package com.gbssg.madscientistsclub.elektromotorapp;

import android.widget.SeekBar;

import com.gbssg.madscientistsclub.elektromotorapp.bluetooth.BluetoothConnectionSocket;

import java.io.IOException;


public class ControlSpeedChangeListener implements SeekBar.OnSeekBarChangeListener {

    @Override
    public void onProgressChanged(final SeekBar seekBar, final int progress, final boolean fromUser) {
        if (BluetoothConnectionSocket.getDefaultInstance() != null) {
            try {
                BluetoothConnectionSocket.getDefaultInstance().getOutputStream().write(progress);
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onStartTrackingTouch(final SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(final SeekBar seekBar) {
    }
}

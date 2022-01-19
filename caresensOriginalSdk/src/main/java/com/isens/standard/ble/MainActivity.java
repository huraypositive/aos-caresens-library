// Main functions
//## START SCAN : onStartScanClick
//## STOP SCAN : onStopScanClick
//## GET TOTAL STORED DATA COUNT : requestTotalDataCnt
//## DOWNLOAD ALL DATA : onDownloadAllClick
//## DOWNLOAD GREATER OR EQUAL DATA : onDownloadGreaterOrEqualClick
//## SYNCHRONIZE TIME : onSynchronizeTimeClick
//## DISCONNECT : onDisconnectClick

package com.isens.standard.ble;

import static com.isens.standard.ble.Util.mainActivity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = this;
    }
}
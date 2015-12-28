package com.liuguangqiang.permissionhelper.sample;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.liuguangqiang.permissionhelper.annotations.PermissionDenied;
import com.liuguangqiang.permissionhelper.annotations.PermissionGranted;
import com.liuguangqiang.permissionhelper.PermissionHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initViews();
    }

    private void initViews() {
        Button btnCamera = (Button) findViewById(R.id.btn_permission_camera);
        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestCameraPermission();
            }
        });

        Button btnLocation = (Button) findViewById(R.id.btn_permission_location);
        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestLocationPermission();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionHelper.getInstance().onRequestPermissionsResult(this, permissions, grantResults);
    }

    private void requestCameraPermission() {
        PermissionHelper.getInstance().requestPermission(MainActivity.this, Manifest.permission.CAMERA);
    }

    private void requestLocationPermission() {
        if (PermissionHelper.getInstance().hasPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)) {
            locationGranted();
        } else {
            PermissionHelper.getInstance().requestPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION);
        }
    }

    @PermissionGranted(permission = Manifest.permission.CAMERA)
    public void cameraGranted() {
        Log.i("PermissionHelper", "cameraGranted");
    }

    @PermissionDenied(permission = Manifest.permission.CAMERA)
    public void cameraDenied() {
        Log.i("PermissionHelper", "cameraDenied");
    }

    @PermissionGranted(permission = Manifest.permission.ACCESS_FINE_LOCATION)
    public void locationGranted() {
        Log.i("PermissionHelper", "locationGranted");
    }

    @PermissionDenied(permission = Manifest.permission.ACCESS_FINE_LOCATION)
    public void locationDenied() {
        Log.i("PermissionHelper", "locationDenied");
    }

}

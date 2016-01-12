package com.liuguangqiang.permissionhelper.sample;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.liuguangqiang.permissionhelper.annotations.PermissionDenied;
import com.liuguangqiang.permissionhelper.annotations.PermissionGranted;
import com.liuguangqiang.permissionhelper.PermissionHelper;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "PermissionHelper";

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
        btnCamera.setOnClickListener(this);

        Button btnLocation = (Button) findViewById(R.id.btn_permission_location);
        btnLocation.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_permission_camera:
                requestCameraPermission();
                break;
            case R.id.btn_permission_location:
                requestLocationPermission();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionHelper.getInstance().onRequestPermissionsResult(this, permissions, grantResults);
    }

    /**
     * Request Permission CAMERA.
     */
    private void requestCameraPermission() {
        PermissionHelper.getInstance().requestPermission(this, Manifest.permission.CAMERA, "You need to allow access to CAMERA");
    }

    /**
     * Request Permission ACCESS_FINE_LOCATION.
     */
    private void requestLocationPermission() {
        PermissionHelper.getInstance().requestPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
    }

    @PermissionGranted(permission = Manifest.permission.CAMERA)
    public void cameraGranted() {
        toast("Permission CAMERA has been granted.");
    }

    @PermissionDenied(permission = Manifest.permission.CAMERA)
    public void cameraDenied() {
        toast("Permission CAMERA has been denied.");
    }

    @PermissionGranted(permission = Manifest.permission.ACCESS_FINE_LOCATION)
    public void locationGranted() {
        toast("Permission ACCESS_FINE_LOCATION has been granted.");
    }

    @PermissionDenied(permission = Manifest.permission.ACCESS_FINE_LOCATION)
    public void locationDenied() {
        toast("Permission ACCESS_FINE_LOCATION has been denied.");
    }

    private void toast(String msg) {
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
    }

}

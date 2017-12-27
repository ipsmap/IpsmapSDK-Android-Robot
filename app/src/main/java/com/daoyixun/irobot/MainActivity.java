package com.daoyixun.irobot;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

//    private IpsmapRobotFragment ipsmapTVFragment;
    protected static final int MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 0x01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView(savedInstanceState);
    }

    private void initView(Bundle savedInstanceState) {

        if (hasPermission(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET)) {
            showFragment(savedInstanceState);
        } else {
            requestPermission(MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE);
//            requestPermission(MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE );
        }


    }

    private void showFragment(Bundle savedInstanceState) {
        String id = "lGaWCUtqoj";
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                if (savedInstanceState != null) {
//                    ipsmapTVFragment = (IpsmapRobotFragment) getSupportFragmentManager().findFragmentByTag("ipsmap");
//                    ipsmapTVFragment.onDestroy();
////            ipsmapTVFragment = IpsmapRobotFragment.getInstance(targetId);
//                    ipsmapTVFragment = IpsmapRobotFragment.getInstance();
//                    getSupportFragmentManager().beginTransaction()
//                            .add(R.id.fl_content, ipsmapTVFragment, "ipsmap")
//                            .commit();
//                } else {
////            ipsmapTVFragment = IpsmapRobotFragment.getInstance(targetId);
//                    ipsmapTVFragment = IpsmapRobotFragment.getInstance();
//                    getSupportFragmentManager().beginTransaction()
//                            .add(R.id.fl_content, ipsmapTVFragment, "ipsmap")
//                            .commit();
//                }

            }
        }, 1500);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
        android.os.Process.killProcess(android.os.Process.myPid());
    }



    /**
     * 多权限判断
     *
     * @param permissons
     * @return
     */
    public boolean hasPermission(String... permissons) {
        for (String permisson : permissons) {
            if ((ContextCompat.checkSelfPermission(
                    this,
                    permisson) != PackageManager.PERMISSION_GRANTED)) {
                return false;
            }
        }
        return true;
    }

    // 权限申请
    public void requestPermission(int requestCode, String... permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }
    }


    /**
     * 获取应用详情页面intent
     *
     * @return
     */
    private Intent getAppDetailSettingIntent() {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", getPackageName(), null));
        } else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", getPackageName());
        }
        return localIntent;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION:
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_DENIED) {
                        if (i == 0) {
//                            T.showShort(R.string.ipsmap_please_grant_location);
                        } else if (i == 1) {
//                            T.showShort(R.string.ipsmap_please_grant_permission_write);
                        }

                        return;
                    }
                }

                if (hasPermission(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.INTERNET)) {
                    showFragment(null);
                }
                break;

        }
    }
}

package com.example.test1.utils.extension

import android.Manifest
import android.content.pm.PackageManager

import android.os.Build

import android.app.Activity


object PermissionUtils {
    /**
     * the permission that needs
     */
    private val PERMISSIONS_CAMERA_AND_STORAGE = arrayOf<String>(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
    )

    /**
     * after Android 6.0 the some permission needs to be request dynamically
     *
     * @param activity
     * @param requestCode
     * @return
     */
    fun isGrantExternalRW(activity: Activity, requestCode: Int): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val storagePermission =
                activity.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            val cameraPermission = activity.checkSelfPermission(Manifest.permission.CAMERA)

            // check the permissions
            if (storagePermission != PackageManager.PERMISSION_GRANTED) {
                // request
                activity.requestPermissions(PERMISSIONS_CAMERA_AND_STORAGE, requestCode)
                return false
            }
        }
        return true
    }
}
package com.elmorshdi.hapd

import android.Manifest
import android.content.Context
import android.os.Build
import pub.devrel.easypermissions.EasyPermissions

object  Utility {
    fun hasPermissions(context: Context) =EasyPermissions.hasPermissions(
        context,
        Manifest.permission.CALL_PHONE
    )

}
package com.elmorshdi.hapd

import android.Manifest
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import pub.devrel.easypermissions.AppSettingsDialog

import android.content.Intent

import pub.devrel.easypermissions.EasyPermissions

import androidx.annotation.NonNull

import android.widget.Toast

import pub.devrel.easypermissions.AfterPermissionGranted





class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Get NavHost and NavController
        val navHostFrag =
            supportFragmentManager.findFragmentById(R.id.nav_host_frag) as NavHostFragment
        navController = navHostFrag.navController

        // Get AppBarConfiguration
        appBarConfiguration = AppBarConfiguration(navController.graph)

        // Link ActionBar with NavController
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    @AfterPermissionGranted(123)
    private fun permissions() {
        val perms =
            arrayOf<String>(Manifest.permission.CALL_PHONE)
        if (EasyPermissions.hasPermissions(this, *perms)) {
            Toast.makeText(this, "Opening camera", Toast.LENGTH_SHORT).show()
        } else {
            EasyPermissions.requestPermissions(
                this, "We need permissions because this and that",
                123, *perms
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    fun onPermissionsGranted(requestCode: Int, perms: List<String?>) {}
    fun onPermissionsDenied(requestCode: Int, perms: List<String?>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return item.onNavDestinationSelected(navController)
                || super.onOptionsItemSelected(item)
    }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}

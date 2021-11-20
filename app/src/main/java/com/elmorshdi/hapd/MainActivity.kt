package com.elmorshdi.hapd

import android.Manifest
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import pub.devrel.easypermissions.AppSettingsDialog

import android.content.Intent
import android.util.Log

import pub.devrel.easypermissions.EasyPermissions

import androidx.annotation.NonNull

import android.widget.Toast

import pub.devrel.easypermissions.AfterPermissionGranted
import java.util.*
import android.app.AlarmManager

import android.app.PendingIntent
import android.content.Context
import android.provider.CalendarContract

import android.content.ContentValues
import android.net.Uri
import androidx.navigation.ui.*
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavigationView :BottomNavigationView=findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setupWithNavController(navController)
        // Get NavHost and NavController


        // Get AppBarConfiguration
//        appBarConfiguration = AppBarConfiguration(navController.graph)

        // Link ActionBar with NavController
//        setupActionBarWithNavController(navController, appBarConfiguration)

    }

//    private fun addRemeind() {
//        val startMillis: Long = Calendar.getInstance().run {
//            set(2012, 0, 19, 7, 30)
//            timeInMillis
//        }
//        val endMillis: Long = Calendar.getInstance().run {
//            set(2012, 0, 19, 8, 30)
//            timeInMillis
//        }
//        val intent = Intent(Intent.ACTION_INSERT)
//            .setData(CalendarContract.Events.CONTENT_URI)
//            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, startMillis)
//            .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endMillis)
//            .putExtra(CalendarContract.CalendarAlerts.BEGIN, true)
//            .putExtra(CalendarContract.Events.TITLE, "Yoga")
//            .putExtra(CalendarContract.Events.DESCRIPTION, "Group class")
//            .putExtra(CalendarContract.Events.EVENT_LOCATION, "The gym")
//            .putExtra(
//                CalendarContract.Events.AVAILABILITY,
//                CalendarContract.Events.AVAILABILITY_BUSY
//            )
//            .putExtra(Intent.EXTRA_EMAIL, "rowan@example.com,trevor@example.com")
//        startActivity(intent)
//    }


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

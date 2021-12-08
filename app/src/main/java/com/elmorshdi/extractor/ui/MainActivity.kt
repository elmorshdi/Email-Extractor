package com.elmorshdi.extractor.ui

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.elmorshdi.extractor.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var labelText:TextView
    private lateinit var actionbar:CardView
    private lateinit var bottomBar: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        labelText=findViewById(R.id.title_Text)
        actionbar=findViewById(R.id.m_action_bar)
        bottomBar=findViewById(R.id.bottom_card)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavigationView :BottomNavigationView=findViewById(R.id.bottomNavigationView)
        bottomNavigationView.setupWithNavController(navController)
        // Get NavHost and NavController

        navController
            .addOnDestinationChangedListener(NavController
                .OnDestinationChangedListener { controller, destination, arguments ->
                    if (destination.label=="Extractor"||destination.label=="Settings"||destination.label=="Alerts Calendar"){
                        actionbar.visibility=View.VISIBLE
                        bottomBar.visibility=View.VISIBLE
                        labelText.text=destination.label
                    }else{
                        bottomBar.visibility=View.GONE
                        actionbar.visibility=View.GONE
                    }

                })

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

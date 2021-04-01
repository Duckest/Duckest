package com.duckest.duckest.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.duckest.duckest.R
import com.duckest.duckest.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityHomeBinding>(this, R.layout.activity_home)
        val toolbar = binding.navigationDrawer.toolbar
        setSupportActionBar(toolbar)
        drawerLayout = binding.drawerLayout
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.home_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        //лочим свайп менюшки в не стартовом фрагменте
        navController.addOnDestinationChangedListener { nc: NavController, nd: NavDestination, args: Bundle? ->
            if (nd.id == nc.graph.startDestination) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }
        binding.navView.setupWithNavController(navController)
        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.home_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

}
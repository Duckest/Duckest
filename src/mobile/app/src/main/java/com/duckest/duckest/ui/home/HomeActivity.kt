package com.duckest.duckest.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.duckest.duckest.MainActivity
import com.duckest.duckest.R
import com.duckest.duckest.Utils
import com.duckest.duckest.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController
    private val vm: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utils.context = this
        val binding =
            DataBindingUtil.setContentView<ActivityHomeBinding>(this, R.layout.activity_home)
        val toolbar = binding.navigationDrawer.toolbar
        setSupportActionBar(toolbar)
        drawerLayout = binding.drawerLayout
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.home_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        //лочим свайп менюшки в не стартовом фрагменте
        navController.addOnDestinationChangedListener { nc: NavController, nd: NavDestination, args: Bundle? ->
            if (nd.id == nc.graph.startDestination || nd.id == R.id.levelFragment) {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED)
            } else {
                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
            }
        }
        binding.navView.setupWithNavController(navController)
        appBarConfiguration =
            AppBarConfiguration(setOf(R.id.feedFragment, R.id.levelFragment, R.id.testIntro), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        item.onNavDestinationSelected(findNavController(R.id.home_host_fragment))
                || super.onOptionsItemSelected(item)

    fun showLoginScreen(item: MenuItem) {
        vm.clear()
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.home_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}
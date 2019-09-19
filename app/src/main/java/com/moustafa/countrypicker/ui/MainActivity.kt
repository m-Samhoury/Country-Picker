package com.moustafa.countrypicker.ui

import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.moustafa.countrypicker.R
import com.moustafa.countrypicker.base.BaseActivity

/**
 * @author moustafasamhoury
 * created on Thursday, 19 Sep, 2019
 */

class MainActivity : BaseActivity(R.layout.activity_main) {

    private val appBarConfiguration = AppBarConfiguration(
        emptySet()
    )

    override fun setupViews() {
        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return

        setupActionBarWithNavController(
            navController = host.navController,
            configuration = appBarConfiguration
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration)
    }

}
package com.racso.pokeapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.racso.pokeapp.R
import com.racso.pokeapp.core.hide
import com.racso.pokeapp.core.show
import com.racso.pokeapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController
    lateinit var binding: ActivityMainBinding
    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var toolbar: Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment
        navController = navHostFragment.navController
        setupToolbar()
        setupBottomNavigation()
        setupAppBarConfig()
        setupListeners()

    }

    fun setupBottomNavigation(){
        bottomNavigationView = binding.bottomNav
        bottomNavigationView.setupWithNavController(navController)
    }

    fun setupToolbar(){
        toolbar = binding.myToolbar
        setSupportActionBar(toolbar)
        toolbar.setupWithNavController(navController)
    }

    fun setupAppBarConfig(){
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.pokemonsFragment, R.id.favoritesFragment))
        setupActionBarWithNavController( navController, appBarConfiguration)
    }

    fun setupListeners(){
        navController.addOnDestinationChangedListener { _, _, arguments ->
            if (arguments?.getBoolean("ShowBottomNavigation", true) == false) bottomNavigationView.hide() else bottomNavigationView.show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}
package com.racso.pokeapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(binding.navHostFragment.id) as NavHostFragment
        navController = navHostFragment.navController

        setupBottomNavigation()
        setupAppBar()
        setupListeners()
    }

    fun setupBottomNavigation(){
        bottomNavigationView = binding.bottomNav
        bottomNavigationView.setupWithNavController(navController)
    }

    fun setupAppBar(){
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.pokemonsFragment, R.id.locationsFragment, R.id.profileFragment))
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
package com.example.projetofirebase.presentation.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.MenuProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.projetofirebase.R
import com.example.projetofirebase.databinding.ActivityMainBinding
import com.example.projetofirebase.presentation.viewmodels.MainViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        MobileAds.initialize(this) {}
        val mAdView = binding.adView
        val adRequest = AdRequest.Builder().build()
        mAdView.loadAd(adRequest)

        toolbarConfiguration()
    }

    private fun toolbarConfiguration() {

        val navHostFragment =
            (supportFragmentManager.findFragmentById(binding.fragmentContainerView.id)) as NavHostFragment
        val navController = navHostFragment.navController
        binding.materialToolbar.setupWithNavController(navController)

        binding.materialToolbar.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu_direito, menu)
            }
            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.item_sair -> {
                        deslogarUsuario()
                    }
                }
                return true
            }
        })
    }

    private fun deslogarUsuario() {
        AlertDialog.Builder(this)
            .setTitle("Deslogar")
            .setMessage("Deseja sair?")
            .setNeutralButton("Não") { _, _ -> }
            .setPositiveButton("sim") { _, _ ->
                viewModel.deslogar()
                startActivity(Intent(this, LoginAtivity::class.java))
            }
            .create()
            .show()
    }
}
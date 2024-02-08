package com.example.projetofirebase.presentation.helper

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class Permissoes {

    companion object {

        fun verificarPermissoes(activity: Activity) {
            val permissaoCamera = ContextCompat.checkSelfPermission(
                activity, Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED

            val permissaoGaleria = ContextCompat.checkSelfPermission(
                activity, galeria()
            ) == PackageManager.PERMISSION_GRANTED

            val permissoesNegadas = mutableListOf<String>()
            if (!permissaoCamera) {
                permissoesNegadas.add(Manifest.permission.CAMERA)
            }
            if (!permissaoGaleria) {
                permissoesNegadas.add(galeria())
            }
            if (permissoesNegadas.isNotEmpty()) {
                ActivityCompat.requestPermissions(activity, permissoesNegadas.toTypedArray(), 0)
            }
        }

        private fun galeria(): String {
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                Manifest.permission.READ_MEDIA_IMAGES
            } else {
                Manifest.permission.READ_EXTERNAL_STORAGE
            }
        }
    }
}
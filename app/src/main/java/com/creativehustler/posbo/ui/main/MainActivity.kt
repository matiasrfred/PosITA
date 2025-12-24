package com.creativehustler.posbo.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.creativehustler.posbo.R
import com.creativehustler.posbo.databinding.ActivityMainBinding
import com.creativehustler.posbo.ui.login.LoginActivity
import com.creativehustler.posbo.ui.settings.SettingsActivity
import com.creativehustler.posbo.utils.ImmersiveHelper
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ImmersiveHelper.apply(this)
        setupActions()
        setupLogout()
    }

    private fun setupActions() {
        binding.gridMenu.getChildAt(0).setOnClickListener {
            // Ventas
        }
        binding.gridMenu.getChildAt(1).setOnClickListener {
            // Historial
        }
        binding.gridMenu.getChildAt(2).setOnClickListener {
            // Reportes
        }
        binding.gridMenu.getChildAt(3).setOnClickListener {
            // Inventario
        }
        binding.gridMenu.getChildAt(4).setOnClickListener {
            // Clientes
        }
        binding.gridMenu.getChildAt(5).setOnClickListener {
            // Ajustes
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }

    private fun setupLogout() {
        binding.btnLogout.setOnClickListener {

            mostrarConfirmacionLogout()
        }
    }

    private fun mostrarConfirmacionLogout() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_logout_confirm, null)

        val dialog = MaterialAlertDialogBuilder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        dialog.setOnShowListener {
            dialog.window?.decorView?.systemUiVisibility =
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
                        View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or
                        View.SYSTEM_UI_FLAG_FULLSCREEN
        }

        dialogView.findViewById<MaterialButton>(R.id.btnCancel).setOnClickListener {
            dialog.dismiss()
        }

        dialogView.findViewById<MaterialButton>(R.id.btnConfirm).setOnClickListener {
            dialog.dismiss()
            cerrarSesion()
        }

        dialog.show()
    }



    private fun cerrarSesion() {
        // Limpiar sesión
        val prefs = getSharedPreferences("session", MODE_PRIVATE)
        prefs.edit().clear().apply()

        // Volver al login
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }



    override fun onBackPressed() {
        // BACK bloqueado en POS
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            ImmersiveHelper.apply(this)
        }
    }

}

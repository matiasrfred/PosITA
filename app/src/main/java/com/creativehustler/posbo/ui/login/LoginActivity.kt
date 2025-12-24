package com.creativehustler.posbo.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.creativehustler.posbo.ui.main.MainActivity
import com.creativehustler.posbo.R
import com.creativehustler.posbo.data.db.AppDatabase
import com.creativehustler.posbo.data.db.entity.UserEntity
import com.creativehustler.posbo.utils.ImmersiveHelper

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val db = AppDatabase.getInstance(this)
        val userDao = db.userDao()

        if (userDao.countUsers() == 0) {
            userDao.insert(
                UserEntity(
                    username = "admin",
                    password = "1234",
                    role = "superadmin"
                )
            )
        }



        val etUser = findViewById<EditText>(R.id.etUser)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {

            val user = etUser.text.toString()
            val pass = etPassword.text.toString()

            val db = AppDatabase.getInstance(this)
            val usuario = db.userDao().login(user, pass)

            if (usuario != null) {
                guardarSesion(usuario)
                irAlMain()
            } else {
                mostrarError("Usuario o clave incorrectos")
            }
        }
        ImmersiveHelper.apply(this)

    }
    private fun irAlMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }

    private fun guardarSesion(user: UserEntity) {
        val prefs = getSharedPreferences("session", MODE_PRIVATE)
        prefs.edit()
            .putInt("user_id", user.id)
            .putString("username", user.username)
            .putString("role", user.role)
            .apply()
    }
    override fun onResume() {
        super.onResume()
        ImmersiveHelper.apply(this)
    }



}

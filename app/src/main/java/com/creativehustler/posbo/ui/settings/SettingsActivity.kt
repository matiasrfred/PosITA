package com.creativehustler.posbo.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.creativehustler.posbo.R
import com.creativehustler.posbo.ui.settings.company.CompanyConfigActivity
import com.creativehustler.posbo.ui.settings.products.ProductManagementActivity
import com.creativehustler.posbo.ui.settings.users.UserManagementActivity
import com.creativehustler.posbo.utils.ImmersiveHelper

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        findViewById<ImageButton>(R.id.btnBackHome).setOnClickListener {
            finish() // o startActivity(Intent(this, MainMenuActivity::class.java))
        }


        findViewById<Button>(R.id.btnCompany).setOnClickListener {
            startActivity(Intent(this, CompanyConfigActivity::class.java))
        }

        findViewById<Button>(R.id.btnUsers).setOnClickListener {
            startActivity(Intent(this, UserManagementActivity::class.java))
        }

        findViewById<Button>(R.id.btnProducts).setOnClickListener {
            startActivity(Intent(this, ProductManagementActivity::class.java))
        }
        ImmersiveHelper.apply(this)
    }

    override fun onResume() {
        super.onResume()
        ImmersiveHelper.apply(this)
    }


}

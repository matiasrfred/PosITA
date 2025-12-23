package com.creativehustler.posbo.ui.settings

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.creativehustler.posbo.R
import com.creativehustler.posbo.ui.settings.company.CompanyConfigActivity
import com.creativehustler.posbo.ui.settings.products.ProductManagementActivity
import com.creativehustler.posbo.ui.settings.users.UserManagementActivity

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        findViewById<Button>(R.id.btnCompany).setOnClickListener {
            startActivity(Intent(this, CompanyConfigActivity::class.java))
        }

        findViewById<Button>(R.id.btnUsers).setOnClickListener {
            startActivity(Intent(this, UserManagementActivity::class.java))
        }

        findViewById<Button>(R.id.btnProducts).setOnClickListener {
            startActivity(Intent(this, ProductManagementActivity::class.java))
        }

    }

}

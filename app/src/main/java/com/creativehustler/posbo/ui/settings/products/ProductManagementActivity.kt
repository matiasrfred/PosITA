package com.creativehustler.posbo.ui.settings.products

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.creativehustler.posbo.R
import com.creativehustler.posbo.utils.ImmersiveHelper

class ProductManagementActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_management)

        ImmersiveHelper.apply(this)

        findViewById<ImageButton>(R.id.btnBackProducts).setOnClickListener {
            finish()
        }

        findViewById<ImageButton>(R.id.btnDepartments).setOnClickListener {
            startActivity(Intent(this, DepartmentActivity::class.java))
        }

        findViewById<ImageButton>(R.id.btnCategories).setOnClickListener {
            startActivity(Intent(this, CategoryActivity::class.java))
        }

        findViewById<ImageButton>(R.id.btnSubcategories).setOnClickListener {
            startActivity(Intent(this, SubcategoryActivity::class.java))
        }

        findViewById<ImageButton>(R.id.btnProducts).setOnClickListener {
            Toast.makeText(this, getString(R.string.mantenedor_productos), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onResume() {
        super.onResume()
        ImmersiveHelper.apply(this)
    }
}

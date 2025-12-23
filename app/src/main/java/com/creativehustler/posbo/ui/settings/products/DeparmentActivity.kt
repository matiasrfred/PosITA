package com.creativehustler.posbo.ui.settings.products

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.creativehustler.posbo.R

class DepartmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_placeholder)

        findViewById<TextView>(R.id.tvPlaceholder).text =
            "Mantenedor de Departamentos\n(En construcción)"
    }
}

package com.creativehustler.posbo.ui.settings.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.creativehustler.posbo.R
import com.creativehustler.posbo.data.db.AppDatabase
import com.creativehustler.posbo.data.db.entity.CategoryEntity
import com.creativehustler.posbo.data.db.entity.CategoryWithDepartment
import com.creativehustler.posbo.utils.ImmersiveHelper
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText

class CategoryActivity : AppCompatActivity() {

    private val categoryDao by lazy { AppDatabase.getInstance(this).categoryDao() }
    private val departmentDao by lazy { AppDatabase.getInstance(this).departmentDao() }
    private lateinit var listContainer: LinearLayout
    private lateinit var addButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_management)

        ImmersiveHelper.apply(this)

        findViewById<ImageButton>(R.id.btnBackCategory).setOnClickListener {
            finish()
        }

        listContainer = findViewById(R.id.llCategoriesList)
        addButton = findViewById(R.id.btnAddCategory)

        addButton.setOnClickListener {
            showCategoryDialog()
        }

        refreshList()
    }

    override fun onResume() {
        super.onResume()
        ImmersiveHelper.apply(this)
    }

    private fun refreshList() {
        listContainer.removeAllViews()
        val categories = categoryDao.getAllWithDepartment()

        if (categories.isEmpty()) {
            val empty = TextView(this).apply {
                text = getString(R.string.sin_categorias)
                setTextColor(ContextCompat.getColor(this@CategoryActivity, R.color.text_secondary))
                textSize = 14f
            }
            listContainer.addView(empty)
            return
        }

        categories.forEach { listContainer.addView(createCategoryCard(it)) }
    }

    private fun createCategoryCard(category: CategoryWithDepartment): View {
        val card = LayoutInflater.from(this)
            .inflate(R.layout.item_category, listContainer, false)

        card.findViewById<TextView>(R.id.tvCategoryName).text = category.name
        card.findViewById<TextView>(R.id.tvCategoryDescription).text = category.description
        card.findViewById<TextView>(R.id.tvCategoryDepartment).text =
            category.departmentName ?: getString(R.string.sin_departamento)
        card.findViewById<TextView>(R.id.tvCategoryStatus).text =
            getString(if (category.active) R.string.activo else R.string.inactivo)

        card.setOnClickListener {
            showCategoryDialog(category)
        }

        return card
    }

    private fun showCategoryDialog(category: CategoryWithDepartment? = null) {
        val dialogView = LayoutInflater.from(this)
            .inflate(R.layout.dialog_category_form, null)

        val nameInput = dialogView.findViewById<TextInputEditText>(R.id.etDialogCategoryName)
        val descriptionInput = dialogView.findViewById<TextInputEditText>(R.id.etDialogCategoryDescription)
        val spinner = dialogView.findViewById<Spinner>(R.id.spinnerCategoryDepartment)
        val activeSwitch = dialogView.findViewById<SwitchMaterial>(R.id.dialogSwitchCategoryActive)
        val saveButton = dialogView.findViewById<MaterialButton>(R.id.btnDialogSaveCategory)

        val departments = departmentDao.getAll()
        if (departments.isEmpty()) {
            Toast.makeText(this, getString(R.string.sin_departamentos), Toast.LENGTH_SHORT).show()
            return
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, departments.map { it.name })
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        if (category != null) {
            nameInput.setText(category.name)
            descriptionInput.setText(category.description)
            activeSwitch.isChecked = category.active
            val selectedIndex = departments.indexOfFirst { it.id == category.departmentId }
            if (selectedIndex >= 0) spinner.setSelection(selectedIndex)
            saveButton.text = getString(R.string.actualizar)
        } else {
            activeSwitch.isChecked = true
        }

        val dialog = MaterialAlertDialogBuilder(this)
            .setView(dialogView)
            .setCancelable(true)
            .create()

        saveButton.setOnClickListener {
            val name = nameInput.text?.toString()?.trim().orEmpty()
            val description = descriptionInput.text?.toString()?.trim().orEmpty()
            if (name.isBlank() || description.isBlank()) {
                Toast.makeText(this, getString(R.string.complete_nombre_categoria), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val department = departments[spinner.selectedItemPosition]

            val toSave = category?.let {
                CategoryEntity(
                    id = it.id,
                    name = name,
                    description = description,
                    departmentId = department.id,
                    active = activeSwitch.isChecked
                )
            } ?: CategoryEntity(
                name = name,
                description = description,
                departmentId = department.id,
                active = activeSwitch.isChecked
            )

            if (category == null) {
                categoryDao.insert(toSave)
                Toast.makeText(this, getString(R.string.categoria_guardada), Toast.LENGTH_SHORT).show()
            } else {
                categoryDao.update(toSave)
                Toast.makeText(this, getString(R.string.categoria_actualizada), Toast.LENGTH_SHORT).show()
            }

            dialog.dismiss()
            refreshList()
        }

        dialog.show()
    }
}
package com.creativehustler.posbo.ui.settings.products

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.creativehustler.posbo.R

class CategoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
    }
}

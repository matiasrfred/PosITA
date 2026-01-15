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
import com.creativehustler.posbo.data.db.entity.SubcategoryEntity
import com.creativehustler.posbo.data.db.entity.SubcategoryWithCategory
import com.creativehustler.posbo.utils.ImmersiveHelper
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText

class SubcategoryActivity : AppCompatActivity() {

    private val subcategoryDao by lazy { AppDatabase.getInstance(this).subcategoryDao() }
    private val categoryDao by lazy { AppDatabase.getInstance(this).categoryDao() }
    private lateinit var listContainer: LinearLayout
    private lateinit var addButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subcategory_management)

        ImmersiveHelper.apply(this)

        findViewById<ImageButton>(R.id.btnBackSubcategory).setOnClickListener {
            finish()
        }

        listContainer = findViewById(R.id.llSubcategoriesList)
        addButton = findViewById(R.id.btnAddSubcategory)

        addButton.setOnClickListener {
            showSubcategoryDialog()
        }

        refreshList()
    }

    override fun onResume() {
        super.onResume()
        ImmersiveHelper.apply(this)
    }

    private fun refreshList() {
        listContainer.removeAllViews()
        val subcategories = subcategoryDao.getAllWithCategory()

        if (subcategories.isEmpty()) {
            val empty = TextView(this).apply {
                text = getString(R.string.sin_subcategorias)
                setTextColor(ContextCompat.getColor(this@SubcategoryActivity, R.color.text_secondary))
                textSize = 14f
            }
            listContainer.addView(empty)
            return
        }

        subcategories.forEach { listContainer.addView(createSubcategoryCard(it)) }
    }

    private fun createSubcategoryCard(subcategory: SubcategoryWithCategory): View {
        val card = LayoutInflater.from(this)
            .inflate(R.layout.item_subcategory, listContainer, false)

        card.findViewById<TextView>(R.id.tvSubcategoryName).text = subcategory.name
        card.findViewById<TextView>(R.id.tvSubcategoryDescription).text = subcategory.description
        card.findViewById<TextView>(R.id.tvSubcategoryCategory).text =
            subcategory.categoryName ?: getString(R.string.sin_categoria)
        card.findViewById<TextView>(R.id.tvSubcategoryStatus).text =
            getString(if (subcategory.active) R.string.activo else R.string.inactivo)

        card.setOnClickListener {
            showSubcategoryDialog(subcategory)
        }

        return card
    }

    private fun showSubcategoryDialog(subcategory: SubcategoryWithCategory? = null) {
        val dialogView = LayoutInflater.from(this)
            .inflate(R.layout.dialog_subcategory_form, null)

        val nameInput = dialogView.findViewById<TextInputEditText>(R.id.etDialogSubcategoryName)
        val descriptionInput = dialogView.findViewById<TextInputEditText>(R.id.etDialogSubcategoryDescription)
        val spinner = dialogView.findViewById<Spinner>(R.id.spinnerSubcategoryCategory)
        val activeSwitch = dialogView.findViewById<SwitchMaterial>(R.id.dialogSwitchSubcategoryActive)
        val saveButton = dialogView.findViewById<MaterialButton>(R.id.btnDialogSaveSubcategory)

        val categories = categoryDao.getAll()
        if (categories.isEmpty()) {
            Toast.makeText(this, getString(R.string.sin_categorias), Toast.LENGTH_SHORT).show()
            return
        }

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories.map { it.name })
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        if (subcategory != null) {
            nameInput.setText(subcategory.name)
            descriptionInput.setText(subcategory.description)
            activeSwitch.isChecked = subcategory.active
            val selectedIndex = categories.indexOfFirst { it.id == subcategory.categoryId }
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
                Toast.makeText(this, getString(R.string.complete_nombre_subcategoria), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val category = categories[spinner.selectedItemPosition]

            val toSave = subcategory?.let {
                SubcategoryEntity(
                    id = it.id,
                    name = name,
                    description = description,
                    categoryId = category.id,
                    active = activeSwitch.isChecked
                )
            } ?: SubcategoryEntity(
                name = name,
                description = description,
                categoryId = category.id,
                active = activeSwitch.isChecked
            )

            if (subcategory == null) {
                subcategoryDao.insert(toSave)
                Toast.makeText(this, getString(R.string.subcategoria_guardada), Toast.LENGTH_SHORT).show()
            } else {
                subcategoryDao.update(toSave)
                Toast.makeText(this, getString(R.string.subcategoria_actualizada), Toast.LENGTH_SHORT).show()
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

class SubcategoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subcategory)
    }
}

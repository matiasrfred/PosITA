package com.creativehustler.posbo.ui.settings.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.creativehustler.posbo.R
import com.creativehustler.posbo.data.db.AppDatabase
import com.creativehustler.posbo.data.db.entity.DepartmentEntity
import com.creativehustler.posbo.utils.ImmersiveHelper
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.switchmaterial.SwitchMaterial
import com.google.android.material.textfield.TextInputEditText

class DepartmentActivity : AppCompatActivity() {

    private val dao by lazy { AppDatabase.getInstance(this).departmentDao() }
    private lateinit var listContainer: LinearLayout
    private lateinit var addButton: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_department_management)

        ImmersiveHelper.apply(this)

        findViewById<ImageButton>(R.id.btnBackDepartment).setOnClickListener {
            finish()
        }

        listContainer = findViewById(R.id.llDepartmentsList)
        addButton = findViewById(R.id.btnAddDepartment)

        addButton.setOnClickListener {
            showDepartmentDialog()
        }

        refreshList()
    }

    override fun onResume() {
        super.onResume()
        ImmersiveHelper.apply(this)
    }

    private fun refreshList() {
        listContainer.removeAllViews()
        val departments = dao.getAll()

        if (departments.isEmpty()) {
            val empty = TextView(this).apply {
                text = getString(R.string.sin_departamentos)
                setTextColor(ContextCompat.getColor(this@DepartmentActivity, R.color.text_secondary))
                textSize = 14f
            }
            listContainer.addView(empty)
            return
        }

        departments.forEach { listContainer.addView(createDepartmentCard(it)) }
    }

    private fun createDepartmentCard(department: DepartmentEntity): View {
        val card = LayoutInflater.from(this)
            .inflate(R.layout.item_department, listContainer, false)

        card.findViewById<TextView>(R.id.tvDepartmentName).text = department.name
        card.findViewById<TextView>(R.id.tvDepartmentIva).text =
            if (department.iva) getString(R.string.si) else getString(R.string.no)
        card.findViewById<TextView>(R.id.tvDepartmentSale).text =
            if (department.allowsSale) getString(R.string.si) else getString(R.string.no)
        card.findViewById<TextView>(R.id.tvDepartmentActive).text =
            if (department.active) getString(R.string.si) else getString(R.string.no)

        card.setOnClickListener {
            showDepartmentDialog(department)
        }

        return card
    }

    private fun showDepartmentDialog(department: DepartmentEntity? = null) {
        val dialogView = LayoutInflater.from(this)
            .inflate(R.layout.dialog_department_form, null)

        val nameInput = dialogView.findViewById<TextInputEditText>(R.id.etDialogDepartmentName)
        val ivaSwitch = dialogView.findViewById<SwitchMaterial>(R.id.dialogSwitchIva)
        val saleSwitch = dialogView.findViewById<SwitchMaterial>(R.id.dialogSwitchPermiteVenta)
        val activeSwitch = dialogView.findViewById<SwitchMaterial>(R.id.dialogSwitchActivo)
        val saveButton = dialogView.findViewById<MaterialButton>(R.id.btnDialogSaveDepartment)

        if (department != null) {
            nameInput.setText(department.name)
            ivaSwitch.isChecked = department.iva
            saleSwitch.isChecked = department.allowsSale
            activeSwitch.isChecked = department.active
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
            if (name.isBlank()) {
                Toast.makeText(this, getString(R.string.complete_nombre_departamento), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val toSave = department?.copy(
                name = name,
                iva = ivaSwitch.isChecked,
                allowsSale = saleSwitch.isChecked,
                active = activeSwitch.isChecked
            ) ?: DepartmentEntity(
                name = name,
                iva = ivaSwitch.isChecked,
                allowsSale = saleSwitch.isChecked,
                active = activeSwitch.isChecked
            )

            val message = if (department == null) {
                dao.insert(toSave)
                R.string.departamento_guardado
            } else {
                dao.update(toSave)
                R.string.departamento_actualizado
            }

            Toast.makeText(this, getString(message), Toast.LENGTH_SHORT).show()
            dialog.dismiss()
            refreshList()
        }

        dialog.show()
    }
}

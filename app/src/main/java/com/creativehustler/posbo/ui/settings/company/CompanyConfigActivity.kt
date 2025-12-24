package com.creativehustler.posbo.ui.settings.company

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.creativehustler.posbo.R
import com.creativehustler.posbo.data.db.AppDatabase
import com.creativehustler.posbo.data.db.entity.CompanyInfoEntity
import com.creativehustler.posbo.utils.ImmersiveHelper
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class CompanyConfigActivity : AppCompatActivity() {

    private val dao by lazy { AppDatabase.getInstance(this).companyInfoDao() }
    private var currentInfo: CompanyInfoEntity? = null

    private val inputFieldIds = listOf(
        R.id.etRutEmpresa,
        R.id.etRazonSocial,
        R.id.etActividadEconomica,
        R.id.etGiro,
        R.id.etCodigoSucursal,
        R.id.etDireccion,
        R.id.etComuna,
        R.id.etCiudad,
        R.id.etRegion,
        R.id.etRutRepresentante,
        R.id.etNombreRepresentante,
        R.id.etTelefonoRepresentante,
        R.id.etCorreoRepresentante,
        R.id.etDispositivoId,
        R.id.etTpvDispositivo,
        R.id.etNombreDispositivo
    )

    private val inputFields by lazy {
        inputFieldIds.map { findViewById<TextInputEditText>(it) }
    }

    private val editButton by lazy { findViewById<MaterialButton>(R.id.id_edit) }
    private val saveButton by lazy { findViewById<MaterialButton>(R.id.btnSaveCompany) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_config)

        ImmersiveHelper.apply(this)

        findViewById<ImageButton>(R.id.btnBackCompany).setOnClickListener {
            finish()
        }

        editButton.setOnClickListener {
            setEditableState(true)
        }

        currentInfo = dao.getConfig()
        populateFields(currentInfo)
        setEditableState(currentInfo == null)

        saveButton.setOnClickListener {
            saveCompanyInfo()
        }
    }

    override fun onResume() {
        super.onResume()
        ImmersiveHelper.apply(this)
    }

    private fun populateFields(info: CompanyInfoEntity?) {
        info ?: return
        findViewById<TextInputEditText>(R.id.etRutEmpresa).setText(info.rutEmpresa)
        findViewById<TextInputEditText>(R.id.etRazonSocial).setText(info.razonSocial)
        findViewById<TextInputEditText>(R.id.etActividadEconomica).setText(info.actividadEconomica)
        findViewById<TextInputEditText>(R.id.etGiro).setText(info.giro)
        findViewById<TextInputEditText>(R.id.etCodigoSucursal).setText(info.codigoSucursal)
        findViewById<TextInputEditText>(R.id.etDireccion).setText(info.direccion)
        findViewById<TextInputEditText>(R.id.etComuna).setText(info.comuna)
        findViewById<TextInputEditText>(R.id.etCiudad).setText(info.ciudad)
        findViewById<TextInputEditText>(R.id.etRegion).setText(info.region)
        findViewById<TextInputEditText>(R.id.etRutRepresentante).setText(info.rutRepresentante)
        findViewById<TextInputEditText>(R.id.etNombreRepresentante).setText(info.nombreRepresentante)
        findViewById<TextInputEditText>(R.id.etTelefonoRepresentante).setText(info.telefonoRepresentante)
        findViewById<TextInputEditText>(R.id.etCorreoRepresentante).setText(info.correoRepresentante)
        findViewById<TextInputEditText>(R.id.etDispositivoId).setText(info.dispositivoId)
        findViewById<TextInputEditText>(R.id.etTpvDispositivo).setText(info.tpvDispositivo)
        findViewById<TextInputEditText>(R.id.etNombreDispositivo).setText(info.nombreDispositivo)
    }

    private fun saveCompanyInfo() {
        val info = CompanyInfoEntity(
            id = currentInfo?.id ?: 1,
            rutEmpresa = findViewById<TextInputEditText>(R.id.etRutEmpresa).text?.toString().orEmpty(),
            razonSocial = findViewById<TextInputEditText>(R.id.etRazonSocial).text?.toString().orEmpty(),
            actividadEconomica = findViewById<TextInputEditText>(R.id.etActividadEconomica).text?.toString().orEmpty(),
            giro = findViewById<TextInputEditText>(R.id.etGiro).text?.toString().orEmpty(),
            codigoSucursal = findViewById<TextInputEditText>(R.id.etCodigoSucursal).text?.toString().orEmpty(),
            direccion = findViewById<TextInputEditText>(R.id.etDireccion).text?.toString().orEmpty(),
            comuna = findViewById<TextInputEditText>(R.id.etComuna).text?.toString().orEmpty(),
            ciudad = findViewById<TextInputEditText>(R.id.etCiudad).text?.toString().orEmpty(),
            region = findViewById<TextInputEditText>(R.id.etRegion).text?.toString().orEmpty(),
            rutRepresentante = findViewById<TextInputEditText>(R.id.etRutRepresentante).text?.toString().orEmpty(),
            nombreRepresentante = findViewById<TextInputEditText>(R.id.etNombreRepresentante).text?.toString().orEmpty(),
            telefonoRepresentante = findViewById<TextInputEditText>(R.id.etTelefonoRepresentante).text?.toString().orEmpty(),
            correoRepresentante = findViewById<TextInputEditText>(R.id.etCorreoRepresentante).text?.toString().orEmpty(),
            dispositivoId = findViewById<TextInputEditText>(R.id.etDispositivoId).text?.toString().orEmpty(),
            tpvDispositivo = findViewById<TextInputEditText>(R.id.etTpvDispositivo).text?.toString().orEmpty(),
            nombreDispositivo = findViewById<TextInputEditText>(R.id.etNombreDispositivo).text?.toString().orEmpty()
        )

        dao.upsert(info)
        currentInfo = info
        setEditableState(false)
        Toast.makeText(this, getString(R.string.configuracion_guardada), Toast.LENGTH_SHORT).show()
    }

    private fun setEditableState(enabled: Boolean) {
        // Activa/desactiva campos y ajusta el botón de editar
        inputFields.forEach {
            it.isEnabled = enabled
            it.isFocusable = enabled
            it.isFocusableInTouchMode = enabled
            it.isCursorVisible = enabled
        }
        saveButton.isEnabled = enabled
        editButton.visibility = if (currentInfo != null && !enabled) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }
}

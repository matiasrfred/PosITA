package com.creativehustler.posbo.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "company_info")
data class CompanyInfoEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    // Seccion Empresa
    var rutEmpresa: String = "",
    var razonSocial: String = "",
    var actividadEconomica: String = "",
    var giro: String = "",
    var codigoSucursal: String = "",
    var direccion: String = "",
    var comuna: String = "",
    var ciudad: String = "",
    var region: String = "",

    // Seccion Representante Legal
    var rutRepresentante: String = "",
    var nombreRepresentante: String = "",
    var telefonoRepresentante: String = "",
    var correoRepresentante: String = "",

    // Seccion Dispositivo
    var dispositivoId: String = "",
    var tpvDispositivo: String = "",
    var nombreDispositivo: String = ""
)


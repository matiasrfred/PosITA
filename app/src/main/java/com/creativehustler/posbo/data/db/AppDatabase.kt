package com.creativehustler.posbo.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.creativehustler.posbo.data.db.dao.CompanyInfoDao
import com.creativehustler.posbo.data.db.dao.UserDao
import com.creativehustler.posbo.data.db.entity.CompanyInfoEntity
import com.creativehustler.posbo.data.db.entity.UserEntity

@Database(
    entities = [UserEntity::class, CompanyInfoEntity::class],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun companyInfoDao(): CompanyInfoDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE users ADD COLUMN firstName TEXT NOT NULL DEFAULT ''")
                database.execSQL("ALTER TABLE users ADD COLUMN lastName TEXT NOT NULL DEFAULT ''")
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    """
                        CREATE TABLE IF NOT EXISTS company_info (
                            id INTEGER PRIMARY KEY NOT NULL,
                            rutEmpresa TEXT NOT NULL,
                            razonSocial TEXT NOT NULL,
                            actividadEconomica TEXT NOT NULL,
                            giro TEXT NOT NULL,
                            codigoSucursal TEXT NOT NULL,
                            direccion TEXT NOT NULL,
                            comuna TEXT NOT NULL,
                            ciudad TEXT NOT NULL,
                            region TEXT NOT NULL,
                            rutRepresentante TEXT NOT NULL,
                            nombreRepresentante TEXT NOT NULL,
                            telefonoRepresentante TEXT NOT NULL,
                            correoRepresentante TEXT NOT NULL,
                            dispositivoId TEXT NOT NULL,
                            tpvDispositivo TEXT NOT NULL,
                            nombreDispositivo TEXT NOT NULL
                        )
                    """.trimIndent()
                )
            }
        }

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "pos_database"
                )
                    .allowMainThreadQueries() // aceptable para POS local
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .build().also { INSTANCE = it }
            }
        }
    }
}

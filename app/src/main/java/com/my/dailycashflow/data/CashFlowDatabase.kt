package com.my.dailycashflow.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [CashFlow::class, Category::class], version = 2, exportSchema = false)
abstract class CashFlowDatabase : RoomDatabase() {

    abstract fun cashFlowDao(): CashFlowDao



    companion object {
        @Volatile
        private var instance: CashFlowDatabase? = null


        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // 1. Buat tabel sementara dengan skema baru
                database.execSQL(
                    """
            CREATE TABLE cashflow_new (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                information TEXT,
                dateInMillis INTEGER,
                category_id INTEGER,
                amount INTEGER
            )
            """.trimIndent()
                )

                // 2. Pindahkan data dari tabel lama ke tabel baru
                database.execSQL(
                    """
            INSERT INTO cashflow_new (id, information, dateInMillis, category_id, amount)
            SELECT id, information, dateInMillis, category_id, amount
            FROM cashflow
            """.trimIndent()
                )

                // 3. Hapus tabel lama
                database.execSQL("DROP TABLE cashflow")

                // 4. Ganti nama tabel baru menjadi tabel asli
                database.execSQL("ALTER TABLE cashflow_new RENAME TO cashflow")

            }
        }

        fun getInstance(context: Context): CashFlowDatabase {
            return instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CashFlowDatabase::class.java,
                    "cashflow_database"
                ).addMigrations(MIGRATION_1_2)
                    .build()
                this.instance = instance
                instance
            }
        }

    }
}
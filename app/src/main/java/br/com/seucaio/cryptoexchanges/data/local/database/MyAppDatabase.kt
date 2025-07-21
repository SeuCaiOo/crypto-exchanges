package br.com.seucaio.cryptoexchanges.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.seucaio.cryptoexchanges.data.local.Converters
import br.com.seucaio.cryptoexchanges.data.local.dao.ExchangeDao
import br.com.seucaio.cryptoexchanges.data.local.entity.ExchangeEntity

@Database(entities = [ExchangeEntity::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class MyAppDatabase : RoomDatabase() {
    abstract fun exchangeDao(): ExchangeDao

    companion object {
        @Volatile
        private var INSTANCE: MyAppDatabase? = null
        const val DATABASE_NAME = "exchange_app_database"

        fun getDatabase(context: Context): MyAppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context = context.applicationContext,
                    klass = MyAppDatabase::class.java,
                    name = DATABASE_NAME
                )
                    .fallbackToDestructiveMigration(false)
                    .build()

                INSTANCE = instance
                instance
            }
        }
    }
}
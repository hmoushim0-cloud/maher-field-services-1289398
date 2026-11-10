package sa.factory.maherfieldservices.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MaherFieldEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun servicesDao(): MaherFieldDao
}

package kozlowska.martyna.kurs.android.task.database

import androidx.room.Database
import androidx.room.RoomDatabase
import kozlowska.martyna.kurs.android.task.model.Task

@Database(entities = [Task::class], version = 2)
abstract class AppDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao
}
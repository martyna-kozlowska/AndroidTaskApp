package kozlowska.martyna.kurs.android.task.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kozlowska.martyna.kurs.android.task.model.Task

@Dao
interface TaskDao {

    @Insert
    suspend fun insert(task: Task)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(taskList: List<Task>)

    @Query("SELECT * FROM task")
    suspend fun getAll(): List<Task>

    @Delete
    suspend fun delete(task: Task)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun edit(task: Task)
}
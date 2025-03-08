package kozlowska.martyna.kurs.android.task.util

import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import kozlowska.martyna.kurs.android.task.model.Task

object JsonConverter {
    private val moshi = Moshi.Builder().build()

    fun taskToJson(task: Task): String {
        val type = Task::class.java
        return moshi.adapter<Task>(type).toJson(task)
    }

    fun taskFromJson(json: String): Task? {
        val type = Task::class.java
        return moshi.adapter<Task>(type).fromJson(json)
    }

    fun taskListToJson(taskList: List<Task>): String {
        val type = Types.newParameterizedType(List::class.java, Task::class.java)
        return moshi.adapter<List<Task>>(type).toJson(taskList)
    }

    fun taskListFromJson(json: String): List<Task> {
        val type = Types.newParameterizedType(List::class.java, Task::class.java)
        return moshi.adapter<List<Task>>(type).fromJson(json) ?: emptyList()
    }
}
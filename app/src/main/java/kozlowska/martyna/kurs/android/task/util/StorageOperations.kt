package kozlowska.martyna.kurs.android.task.util

import android.content.Context
import kozlowska.martyna.kurs.android.task.model.Task

object StorageOperations {

    fun writeTaskList(context: Context, taskList: List<Task>) {
        val json = JsonConverter.taskListToJson(taskList)
        val sharedPrefs = context.getSharedPreferences(
            SHARED_PREFS_NAME, Context.MODE_PRIVATE
        ).edit()
        sharedPrefs.putString(TASK_LIST_KEY, json)
        sharedPrefs.apply()

    }

    fun readTaskList(context: Context): List<Task> {
        val sharedPrefs = context.getSharedPreferences(
            SHARED_PREFS_NAME,
            Context.MODE_PRIVATE
        )

        val json = sharedPrefs.getString(TASK_LIST_KEY, null)
        return if (json != null) JsonConverter.taskListFromJson(json) else emptyList()
    }

    private const val SHARED_PREFS_NAME = "TASK_SHARED_PREFS"
    private const val TASK_LIST_KEY = "TASK_LIST_KEY"
}
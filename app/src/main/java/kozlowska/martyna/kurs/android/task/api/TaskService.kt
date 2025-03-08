package kozlowska.martyna.kurs.android.task.api

import kozlowska.martyna.kurs.android.task.model.Task
import kozlowska.martyna.kurs.android.task.model.TaskIdResponse
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface TaskService {
    @POST("tasks.json")
    suspend fun add(@Body task: Task): TaskIdResponse

    @GET("tasks.json")
    suspend fun getAll(): Map<String, Task>

    @DELETE("tasks/{id}.json")
    suspend fun deleteTask(@Path("id") TaskId: String)

    @PUT("tasks/{id}.json")
    suspend fun editTask(
        @Path("id") taskId: String,
        @Body task: Task
    )
}
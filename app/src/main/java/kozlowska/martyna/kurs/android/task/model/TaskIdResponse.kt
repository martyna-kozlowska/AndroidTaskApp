package kozlowska.martyna.kurs.android.task.model

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)

data class TaskIdResponse(val name: String)

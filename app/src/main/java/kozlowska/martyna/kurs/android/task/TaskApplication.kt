package kozlowska.martyna.kurs.android.task

import android.app.Application
import android.util.Log
import kozlowska.martyna.kurs.android.task.api.ServiceConfiguration
import kozlowska.martyna.kurs.android.task.api.TaskNetworkRepository
import kozlowska.martyna.kurs.android.task.database.DatabaseConfiguration
import kozlowska.martyna.kurs.android.task.database.TaskDatabaseRepository
import kozlowska.martyna.kurs.android.task.viemodel.TaskViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class TaskApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Log.d("MyTaskApp", "Application on Create()")

        startKoin {
            androidContext(this@TaskApplication)
            modules(
                module {
                    single {DatabaseConfiguration.getDatabase(androidContext())}
                    factory {TaskDatabaseRepository(get())}

                    single {ServiceConfiguration.taskService}
                    factory { TaskNetworkRepository(get()) }

                    viewModel { TaskViewModel(get(), get()) }
                }
            )
        }
    }
}
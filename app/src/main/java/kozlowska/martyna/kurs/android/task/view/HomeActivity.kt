package kozlowska.martyna.kurs.android.task.view

import android.Manifest.permission.SEND_SMS
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsManager
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import kozlowska.martyna.kurs.android.task.model.TaskOperationStatus
import kozlowska.martyna.kurs.android.task.viemodel.TaskViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeActivity : ComponentActivity() {

    val taskViewModel by viewModel<TaskViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MyTaskApp", "HomeActivity onCreate()")
        taskViewModel.getAllTasks()

        setContent {
            homeView()
            observeGetAllTasksStatus()

            if (taskViewModel.sendSmsTaskStatus != null) {
                sendSmsAlertDialog()
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d("MyTaskApp", "HomeActivity() onNewIntent()")
        taskViewModel.getAllTasks()
    }

    private fun observeGetAllTasksStatus() {
        if (taskViewModel.getAllTasksStatus == TaskOperationStatus.ERROR) {
            Toast.makeText(this, "Tasks loaded from local stoeage",
                Toast.LENGTH_LONG).show()
        }
    }

    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun sendSmsAlertDialog() {
        var phoneNumber by rememberSaveable { mutableStateOf("") }
        var textToSend = "${taskViewModel.sendSmsTaskStatus?.title}" +
                "\n${taskViewModel.sendSmsTaskStatus?.description}"
        val sendSmsPermission = rememberPermissionState(permission = SEND_SMS)

        AlertDialog(
            onDismissRequest = {taskViewModel.sendSmsTaskStatus = null},
            dismissButton = {
                TextButton(onClick = {taskViewModel.sendSmsTaskStatus = null}) {
                    Text(text = "cancel")
                }
            },
            confirmButton = {

                if (sendSmsPermission.status.isGranted) {

                    TextButton(
                        onClick = {
                            taskViewModel.sendSmsTaskStatus = null

                            val smsManager: SmsManager =
                                this.getSystemService(SmsManager::class.java)
                            smsManager.sendTextMessage(
                                phoneNumber,
                                null,
                                textToSend,
                                null,
                                null
                            )

                            Toast.makeText(
                                this,
                                "SMS sent to $phoneNumber",
                                Toast.LENGTH_LONG)
                                .show()
                        }
                    ) {
                        Text(text = "Send")
                    }
                } else{
                    TextButton(onClick = {sendSmsPermission.launchPermissionRequest()}) {
                        Text(text = "Permission")
                    }
                }
            },
            title = {
                Text(text = "Send SMS")
            },
            text = {
                Column() {
                    Text(text = "Content:", fontWeight = FontWeight.Bold)
                    Text(text = textToSend)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = phoneNumber,
                        onValueChange = {phoneNumber = it},
                        label = { Text(text = "Phone number")},
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Phone
                        )
                    )
                }
            }
        )
    }

    @Composable
    fun taskListView() {
        val context = LocalContext.current

        Column(modifier = Modifier.padding(horizontal = 20.dp)) {
            Text(
                text = "Task list",
                fontSize = 30.sp,
                modifier = Modifier.padding(top = 20.dp)
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(
                    top = 16.dp,
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 64.dp
                ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(items = taskViewModel.taskList) { task->
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = task.colorType.color
                        ),

                        elevation = CardDefaults.elevatedCardElevation(
                            defaultElevation = 10.dp
                        ),
                        modifier = Modifier.clickable {
                            val intent = Intent(context, TaskActivity::class.java)
                            intent.putExtra("edit_task", task)
                            startActivity(intent)
                        }
                    ) {
                        Row() {
                            Column(modifier = Modifier
                                .padding(16.dp)
                                .weight(1f)
                            ) {
                                Text(
                                    text = task.title,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 20.sp

                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = task.description

                                )
                            }
                            Column(
                                modifier = Modifier.padding(
                                    top = 8.dp,
                                    bottom = 8.dp,
                                    end = 8.dp
                                )
                            ) {
                                IconButton(
                                    onClick = {
                                        taskViewModel.deleteTask(task)
                                    },
                                    modifier = Modifier.size(25.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = "delete task"
                                    )

                                }

                                Spacer(modifier = Modifier.height(16.dp))
                                IconButton(
                                    onClick = {
                                        taskViewModel.sendSmsTaskStatus = task },
                                    modifier = Modifier.size(25.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.Email,
                                        contentDescription = "send sms"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun homeView() {
        val context = LocalContext.current
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            when (taskViewModel.getAllTasksStatus){
                TaskOperationStatus.SUCCESS, TaskOperationStatus.ERROR -> {
                    if (taskViewModel.taskList.isEmpty()) {
                        Text(
                            text = "Empty task list",
                            fontSize = 20.sp,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    } else {
                        taskListView()
                    }
                }
                TaskOperationStatus.LOADING -> CircularProgressIndicator(Modifier.align(Alignment.Center))
                TaskOperationStatus.UNKNOWN -> {}
            }


            FloatingActionButton(
                onClick = {
                    val intent = Intent(context, TaskActivity::class.java)
                    startActivity(intent)
                },
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomEnd)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "add task")
            }
        }
    }

    @Composable
    fun HomeText(value: String?) {
        Text(text = value ?: "Witaj w domu")
    }
}

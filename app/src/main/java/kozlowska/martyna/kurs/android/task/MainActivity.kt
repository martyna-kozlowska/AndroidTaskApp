package kozlowska.martyna.kurs.android.task

import android.graphics.Paint.Align
import android.health.connect.datatypes.units.Volume
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kozlowska.martyna.kurs.android.task.ui.theme.TasksTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            //MySurface()
            //MyCard()
            //MyBox()
            //MyLayoutWeight()
            //MyClickButton()
            //MyNumberCounterButtonExcersise()
            //MyCheckbox()
            //MyRadioButton()
            //MySlider()
            //MyIconButton()
            //MyShowTimeViewExcersise()
            //MyLazyColumn()
            //MyLazyRow()
            //MyLazyColumnClickExcersise()
            MyLazyRowClickExcersise()
            //MyLazyColumnRowExercise()


        }
    }

    @Composable
    fun MyLazyColumnRowExercise() {
        LazyColumn() {
            items(count = 100) { columnIndex ->
                LazyRow() {
                    items(count = 100) { rowIndex ->
                        //MyLazyItem(number = columnIndex * rowIndex)
                    }
                }

            }
        }
    }

    @Composable
    fun MyLazyRowClickExcersise() {
        var selectedIndex by remember { mutableStateOf(-1) }

        LazyRow() {
            items(count = 100) { index ->
                Surface(
                    border = BorderStroke(1.dp, Color.Gray),
                    color = if (selectedIndex == index) Color.LightGray else Color.White,
                    modifier = Modifier
                        .clickable { selectedIndex = index }

                ) {
                    Text(text = "$index", modifier = Modifier.padding(10.dp))
                }
            }
        }
    }

    @Composable
    fun MyLazyColumnClickExcersise() {
        var counter by remember { mutableStateOf(0) }
        val itemList = remember { mutableStateListOf<Int>() }
        Column() {
            Button(onClick = {itemList.add(counter++)}) {
                Text(text = "Add")

            }
            LazyColumn() {
                items(items = itemList) { item ->
                    Text(text = "$item")
                }
            }
        }
    }

    @Composable
    fun MyLazyRow() {
        LazyRow() {
            item {Text(text = "Z")}
            items(count = 10) { index ->
                Text(text = "Item $index")
            }
        }
    }

    @Composable
    fun MyLazyColumn() {
       /* Column() {
            for (i in 0..100) {
                Text(text = "item $i")
        }
        }*/

        val itemList = remember { mutableStateListOf(1, 3, 5, 7, 9, 11) }

        LazyColumn() {
           /*for (i in 0..100) {
                item {Text(text = "Item $i")}
            }*/
            items(items = itemList) { item ->
                Text(text = "$item")
            }
        }
    }

    @Composable
    fun MyShowTimeViewExcersise() {
        var isSettingsShowed by remember { mutableStateOf(false) }
        var volumeValue by remember { mutableStateOf(0f) }

        Column() {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = isSettingsShowed,
                    onCheckedChange = {isSettingsShowed = it}
                )

                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "settings icon"
                )
                Text(text = "Ustawienia")
            }
            if (isSettingsShowed) {
                Column() {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Outlined.Notifications,
                            contentDescription = "Icon volume min"
                        )
                        Slider(
                            value = volumeValue,
                            onValueChange = {volumeValue = it},
                            modifier = Modifier.width(300.dp)
                        )
                        Icon(
                            imageVector = Icons.Filled.Notifications,
                            contentDescription = "Icon volume max",
                            tint = if (volumeValue > 0.8f) Color.Red else Color.Black
                        )
                    }
                }

            }
        }

    }

    @Composable
    fun MyIconButton() {
        var isFavorite by remember { mutableStateOf(false) }
        IconButton(onClick = {isFavorite = !isFavorite}) {
            Icon(
                imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                contentDescription = "favorite icon"
            )
        }
    }

    @Composable
    fun MySlider() {
        var sliderValue by remember { mutableStateOf(0f) }
        Slider(
            value = sliderValue,
            onValueChange = {value -> sliderValue = value},
            modifier = Modifier.padding(horizontal = 40.dp),
            valueRange = 0f..100f,
            steps = 2
        )
        Text(text = "$sliderValue")
    }

    @Composable
    fun MyRadioButton() {
        var isSelected by remember { mutableStateOf(true) }
        RadioButton(selected = isSelected, onClick = {if (isSelected) isSelected = false else isSelected = true})
    }

    @Composable
    fun MyCheckbox() {
        var isChecked by remember { mutableStateOf(false) }
        Checkbox(
            checked = isChecked,
            onCheckedChange = {value -> isChecked = value}
        )
    }

    @Composable
    fun MyNumberCounterButtonExcersise() {
        var number by remember { mutableStateOf(0) }

        Row() {
            Button(
                onClick = {number--},
                modifier = Modifier.size(30.dp),
                contentPadding = PaddingValues(2.dp),
                enabled = if (number < 0) true else false
            ) {
                Text(text = "-")
            }
            Text(text = "$number",
                modifier = Modifier
                    .size(height = 30.dp, width = 50.dp)
                    .padding(horizontal = 4.dp)
                    .border(1.dp, Color.Gray)
                    .wrapContentSize()

            )
            Button(
                onClick = {number++},
                modifier = Modifier.size(30.dp),
                contentPadding = PaddingValues(2.dp),
                enabled = if (number > 0) true else false
            ) {
                Text(text = "+")
            }
        }
    }

    @Composable
    fun MyClickButton() {

        var countState: MutableState<Int> = remember { mutableStateOf(0) }
        var count: Int by remember { mutableStateOf(0) }
        Column() {
            Button(onClick = {countState.value++ }) {
                Text(text = "Click count state ${countState.value}")
            }
            Button(onClick = {count++ }) {
                Text(text = "Click count state $count")
            }
        }
    }


    @Composable
    fun MyLayoutWeight() {
        Row() {
            Text(
                text = "Text",
                modifier = Modifier
                    .background(Color.Green)
                    .weight(2f)
            )
            Text(
                text = "Text",
                modifier = Modifier
                    .background(Color.Yellow)
                    .weight(1f)
            )
            Text(
                text = "Text",
                modifier = Modifier
                    .background(Color.Red)
                    .weight(1f)
            )
        }
    }

    @Composable
    fun MyBox() {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.LightGray),
            contentAlignment = Alignment.TopCenter
        ) {
            Text(text = "Box1")
        }
    }

    @Composable
    fun MyCard() {
        Card(
            modifier = Modifier.padding(20.dp),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors()
        ) {
            Text(
            text = "Card",
            modifier = Modifier.padding(10.dp)
            )

        }
    }

    @Composable
    fun MySurface() {
        Surface(
            contentColor = Color.Blue,
            color = Color.Green,
            border = BorderStroke(2.dp, Color.Red),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text(
                text = "surface",
                modifier = Modifier.padding(10.dp)
            )

        }
    }

    @Composable
    fun MyButton() {
        Column() {
            Button(onClick = {} ) {
                Text(text = "Button")
            }
            Button(
                onClick = {},
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.LightGray,
                    contentColor = Color.DarkGray
                ),
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, Color.DarkGray),
                contentPadding = PaddingValues(30.dp),
                enabled = false
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    Text(text = "Add")
                }
            }

            OutlinedButton(onClick = {}) {
                Text(text = "Outlined button")
            }
        }
    }

    @Composable
    fun MyTextDividerProgresserExercise() {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Witaj!",
                color = Color.White,
                fontSize = 50.sp,
                fontWeight = FontWeight.Thin,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .background(Color.Gray)
                    .fillMaxWidth()
                )

            Divider()
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Divider()
            Text(text = "Fajny ten Twój progres! ;)")
        }
    }

    @Composable
    fun MyProgress() {
        Column(modifier = Modifier.padding(10.dp)) {
            CircularProgressIndicator()
            CircularProgressIndicator(progress = 0.8f)
            LinearProgressIndicator()
        }
    }

    @Composable
    fun MyDivider() {
        Column() {
            Text(text = "wyzej")
            Divider()
            Text(text = "nizej")
        }
    }

    @Composable
    fun MySpacer() {
        Column() {
            Text(
                text = "jestem wyzej",
                //modifier = Modifier.padding(bottom = 20.dp)
            )
            Spacer(
                modifier = Modifier
                    .height(20.dp)
                    .fillMaxWidth()
                    .background(Color.Gray)
            )
            Text(text = "a ja nizej")
        }
    }

    @Composable
    fun MyIcon() {
        Column() {
            Icon(imageVector = Icons.Default.Email, contentDescription = "Email icon")
            Icon(imageVector = Icons.Outlined.Email, contentDescription = "Email icon")
            Icon(imageVector = Icons.Default.Done, contentDescription = "Email icon", tint = Color.Green)
            Icon(
                imageVector = Icons.Default.Done,
                contentDescription = "done icon",
                tint = Color.White,
                modifier = Modifier
                    .background(Color.Green, CircleShape)
                    .border(1.dp, Color.LightGray, CircleShape)
                    .padding(4.dp)
            )
        }
    }

    @Composable
    fun MyTextAlign() {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "Text1",
                modifier = Modifier
                    .background(Color.Green)
                    .align(Alignment.End)
                    .fillMaxWidth(),
                textAlign = TextAlign.End
            )
            Text(
                text = "Text2",
                modifier = Modifier
                    .background(Color.Yellow)
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                text = "Text3",
                modifier = Modifier
                    .background(Color.Red)
                    .align(Alignment.Start)
                    .fillMaxWidth()
                    .height(50.dp)
                    .wrapContentSize()
            )
        }
    }

    @Composable
    fun MyText() {
        Text(
            text = "Pisze sobie wlasny tekst w Text i bede go stylowac!",
            fontSize = 20.sp,
            color = Color.Red,
            fontWeight = FontWeight.Bold,
            fontStyle = FontStyle.Italic,
            textDecoration = TextDecoration.Underline,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }

    @Composable
    fun MyModifier() {
            Column(
                modifier = Modifier
                    .background(Color.LightGray)
                    .fillMaxSize()
                    .padding(vertical = 32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "testujemy opcje dostepne w modifier",
                    modifier = Modifier
                        .width(75.dp)
                       // .background(Color.Cyan, CircleShape)
                       // .clip(CircleShape)
                        .background(Color.Cyan)
                        .padding(8.dp)
                        .rotate(45f)
                        .border(2.dp, Color.Blue, RoundedCornerShape(5.dp))
                )
                Text(
                    text = "Android",
                    modifier = Modifier
                        .rotate(45f)
                        .background(Color.Red)
                        .padding(20.dp)
                        .background(Color.Green)
                        .padding(5.dp)
                        .background(Color.White)
                        .border(1.dp, Color.Black)
                )
            }
    }


    @Composable
    fun MyColumnExcersise() {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment. CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Witaj Androidzie")
            Text(text = "Witaj ponownie!")
            Text(text = "Hej, co u ciebie?")
        }
    }


    @Composable
    fun MyColumn() {
        Column(
            modifier = Modifier.background(Color.LightGray).fillMaxSize(),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Witaj Androidzie")
            Text(text = "Witaj ponownie!")
            Text(text = "Hej, co u ciebie?")
        }
    }

    @Composable
    fun MyRow() {
        Row() {
            Text(text = "Witaj Androidzie")
            Text(text = "Witaj ponownie!")
            Text(text = "Hej, co u ciebie?")
        }
    }

    @Composable
    fun MyElement() {
        Text(text = "Witaj Androidzie")
        Text(text = "Witaj ponownie!")
        Text(text = "Hej, co u ciebie?")
    }
}

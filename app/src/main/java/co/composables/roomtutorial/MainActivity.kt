package co.composables.roomtutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import co.composables.roomtutorial.ui.theme.RoomTutorialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RoomTutorialTheme {
                val viewModel = viewModel<UserViewModel>()

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.background),
                ) {

                    val users by viewModel.users

                    LazyColumn {
                        item {
                            Row(Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceEvenly
                            ) {
                                Button(onClick = { viewModel.addUser() }) {
                                    Text("Add Random Person")
                                }
                                Button(onClick = { viewModel.refresh() }) {
                                    Text("Refresh")
                                }
                            }
                        }
                        items(users) {
                            PersonRow(it)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun PersonRow(person: Person) {
        Surface(Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp, horizontal = 16.dp)) {
            Text("${person.firstName} ${person.lastName}")
        }
    }
}

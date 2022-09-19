package co.composables.roomtutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
                            }
                        }
                        items(users) {
                            PersonRow(it, onClick = {
                                viewModel.updateName(it)
                            }, onDeleteClick = {
                                viewModel.delete(it)
                            }
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun PersonRow(person: Person, onClick: () -> Unit, onDeleteClick: () -> Unit) {
        Surface(Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 10.dp)
            .padding(start = 16.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("${person.firstName} ${person.lastName}")
                Spacer(Modifier.weight(1f))
                IconButton(onDeleteClick) {
                    Icon(painterResource(R.drawable.ic_round_delete_24), contentDescription = null)
                }
            }
        }
    }
}

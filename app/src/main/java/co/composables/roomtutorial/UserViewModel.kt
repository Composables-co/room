package co.composables.roomtutorial

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserViewModel(application: Application) : AndroidViewModel(application) {

    var users = mutableStateOf<List<Person>>(emptyList())
        private set

    private var dao: PersonDao? = null

    init {
        viewModelScope.launch(Dispatchers.IO) {
            dao = MyDatabase.getInstance(application).personDao()
        }
    }

    fun addUser() {
        viewModelScope.launch(Dispatchers.IO) {
            val person = Person(firstName = firstNames.random(), lastName = lastNames.random())
            requireNotNull(dao).insertPeople(person)
        }
    }

    fun refresh() {
        viewModelScope.launch(Dispatchers.IO) {
            val people = requireNotNull(dao).getAllPeople()
            withContext(Dispatchers.Main) {
                users.value = people
            }
        }
    }
}

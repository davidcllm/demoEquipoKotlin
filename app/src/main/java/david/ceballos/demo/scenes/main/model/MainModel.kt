package david.ceballos.demo.scenes.main.model

import androidx.lifecycle.MutableLiveData
import david.ceballos.demo.dataClasses.User

data class MainModel (
    var user: User = User(),
    val isValidForm: MutableLiveData<Boolean> = MutableLiveData<Boolean>(),

    //TODO: Agregar observers únicos de usuario y contraseña
    //Ponemos las cosas que queremos que se observen en el modelo
    val isUserNameValid: MutableLiveData<Boolean> = MutableLiveData<Boolean>(),
    val isPasswordValid: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
)
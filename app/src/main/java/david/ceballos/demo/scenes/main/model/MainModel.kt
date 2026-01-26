package david.ceballos.demo.scenes.main.model

import androidx.lifecycle.MutableLiveData
import david.ceballos.demo.dataClasses.User

data class MainModel (
    var user: User = User(),
    val isValidForm: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    //TODO: Agregar observers únicos de usuario y contraseña
)
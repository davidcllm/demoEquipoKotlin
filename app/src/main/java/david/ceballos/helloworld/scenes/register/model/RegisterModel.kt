package david.ceballos.helloworld.scenes.register.model

import androidx.lifecycle.MutableLiveData
import david.ceballos.helloworld.dataClasses.User

data class RegisterModel (
    var user: User = User(),
    val isValidForm: MutableLiveData<Boolean> = MutableLiveData<Boolean>(),
    val isNameValid: MutableLiveData<Boolean> = MutableLiveData<Boolean>(),
    val isLastNameValid: MutableLiveData<Boolean> = MutableLiveData<Boolean>(),
    val isUserNameValid: MutableLiveData<Boolean> = MutableLiveData<Boolean>(),
    val isPasswordValid: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
)
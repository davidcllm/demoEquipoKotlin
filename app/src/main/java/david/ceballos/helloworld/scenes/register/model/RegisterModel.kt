package david.ceballos.helloworld.scenes.register.model

import androidx.lifecycle.MutableLiveData
import david.ceballos.helloworld.dataClasses.User

data class RegisterModel (
    var user: User = User(),
    val isValidForm: MutableLiveData<Boolean> = MutableLiveData<Boolean>(),
    val isNameValid: MutableLiveData<String?> = MutableLiveData(),
    val isLastNameValid: MutableLiveData<String?> = MutableLiveData(),
    val isUserNameValid: MutableLiveData<String?> = MutableLiveData(),
    val isPasswordValid: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
)
package david.ceballos.helloworld.scenes.profile.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {
    private val _isFaceIdEnabled = MutableLiveData(true)
    val isFaceIdEnabled: LiveData<Boolean> = _isFaceIdEnabled

    fun setFaceIdEnabled(enabled: Boolean) {
        _isFaceIdEnabled.value = enabled
    }
}
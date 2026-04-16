package david.ceballos.helloworld.scenes.profile.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import david.ceballos.helloworld.sharedPreference.SharedPreferenceConstants

class ProfileViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = application.getSharedPreferences(
        SharedPreferenceConstants.NAME_SHARE_PREFERENCE,
        Context.MODE_PRIVATE
    )

    // Lee el valor guardado; si no existe, el default es true
    private val _isFaceIdEnabled = MutableLiveData(
        prefs.getBoolean(SharedPreferenceConstants.IS_BIOMETRIC_ENABLED_KEY, true)
    )
    val isFaceIdEnabled: LiveData<Boolean> = _isFaceIdEnabled

    fun setFaceIdEnabled(enabled: Boolean) {
        _isFaceIdEnabled.value = enabled
        // Persiste el cambio
        prefs.edit()
            .putBoolean(SharedPreferenceConstants.IS_BIOMETRIC_ENABLED_KEY, enabled)
            .apply()
    }
}
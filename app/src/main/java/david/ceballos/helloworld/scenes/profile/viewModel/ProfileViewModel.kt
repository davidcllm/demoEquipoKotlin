package david.ceballos.helloworld.scenes.profile.viewModel

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import david.ceballos.helloworld.sharedPreference.SharedPreferenceConstants
import java.io.File

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

    // carga la URI guardada al iniciar, null si no hay ninguna
    private val _profileImageUri = MutableLiveData<Uri?>(
        prefs.getString(SharedPreferenceConstants.PROFILE_IMAGE_URI_KEY, null)
            ?.let { Uri.parse(it) }
    )
    val profileImageUri: LiveData<Uri?> = _profileImageUri

    // guarda la URI como string
    fun saveProfileImage(uri: Uri) {
        val context = getApplication<Application>()

        // Copia la imagen a almacenamiento interno para tener acceso permanente
        val permanentUri = copyImageToInternalStorage(context, uri)
        val uriToSave = permanentUri ?: uri  // si falla la copia, guarda la original

        _profileImageUri.value = uriToSave
        prefs.edit()
            .putString(SharedPreferenceConstants.PROFILE_IMAGE_URI_KEY, uriToSave.toString())
            .apply()
    }

    private fun copyImageToInternalStorage(context: Context, sourceUri: Uri): Uri? {
        return try {
            val inputStream = context.contentResolver.openInputStream(sourceUri) ?: return null
            val file = File(context.filesDir, "profile_image.jpg")
            file.outputStream().use { output ->
                inputStream.use { input ->
                    input.copyTo(output)
                }
            }
            Uri.fromFile(file)
        } catch (e: Exception) {
            null
        }
    }

    fun clearProfileImage() {
        _profileImageUri.value = null
        prefs.edit().remove(SharedPreferenceConstants.PROFILE_IMAGE_URI_KEY).apply()
    }

    fun setFaceIdEnabled(enabled: Boolean) {
        _isFaceIdEnabled.value = enabled
        // Persiste el cambio
        prefs.edit()
            .putBoolean(SharedPreferenceConstants.IS_BIOMETRIC_ENABLED_KEY, enabled)
            .apply()
    }
}
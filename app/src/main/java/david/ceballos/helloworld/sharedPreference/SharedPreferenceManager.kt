package david.ceballos.helloworld.sharedPreference

import android.content.Context
import android.content.SharedPreferences

class SharedPreferenceManager(context: Context) {

    private val sharedPreference: SharedPreferences = context.getSharedPreferences(
        SharedPreferenceConstants.NAME_SHARE_PREFERENCE,
        Context.MODE_PRIVATE
    )

    fun clearInformation(): Boolean {
        return try {
            sharedPreference.edit().clear().commit()
        } catch (e: Exception) {
            false
        }
    }

    fun setString(key: String, value: String) {
        sharedPreference.edit().putString(key, value).apply()
    }

    fun getString(key: String): String {
        return sharedPreference.getString(key, "") ?: ""
    }

    fun setBoolean(key: String, value: Boolean) {
        sharedPreference.edit().putBoolean(key, value).apply()
    }

    fun getBoolean(key: String): Boolean {
        return sharedPreference.getBoolean(key, false)
    }
}
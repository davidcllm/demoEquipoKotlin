package Utilities.Biometric

import androidx.biometric.BiometricPrompt

/**
 * Interfaz
 */
interface BiometricAuthListener {
    fun onBiometricAuthenticationSuccess(result: BiometricPrompt.AuthenticationResult)
    fun onBiometricAuthenticationError(errorCode: Int, errorMessage: String)
}
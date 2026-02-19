package Utilities

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.sqrt

class ShakeDetector(
    context: Context, // Contexto
    private val onShake: (Int) -> Unit // Callback provee conteo de shakes
) : SensorEventListener {

    // Sensor Manager
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    // Especificamos el sensor a utilizar
    private val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    // Constantes estáticas
    companion object {
        // Umbral para detectar una vibración (2,7f - vibración suficientemente fuerte)
        private const val SHAKE_THRESHOLD_GRAVITY = 2.7f

        // Ignora las sacudidas múltiples si ocurren demasiado rápido (con una diferencia de menos de 500 ms)
        private const val SHAKE_SLOP_TIME = 500

        // Restablecer el recuento de movimientos si no se detectan movimientos durante 3 segundos
        private const val SHAKE_COUNT_RESET_TIME = 3000
    }

    private var shakeTimestamp: Long = 0
    private var shakesCount: Int = 0

    // Iniciamos el Listener
    fun start() {
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI)
    }

    // Detenemos el Listener
    fun stop() {
        sensorManager.unregisterListener(this)
    }

    // Manejador de eventos del sensor
    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) return

        // Normalizar los valores de aceleración por la gravedad de la Tierra (g = 9,8 m/s²)
        val gX = event.values[0] / SensorManager.GRAVITY_EARTH
        val gY = event.values[1] / SensorManager.GRAVITY_EARTH
        val gZ = event.values[2] / SensorManager.GRAVITY_EARTH

        // Calcular la fuerza g total
        val gForce = sqrt(gX * gX + gY * gY + gZ * gZ)

        // Verificar si la fuerza excede el umbral -> posible vibración
        if (gForce > SHAKE_THRESHOLD_GRAVITY) {
            val now = System.currentTimeMillis()

            // Ignore shakes que estén demasiado juntos
            if (shakeTimestamp + SHAKE_SLOP_TIME > now) {
                return
            }

            // Restablecer el conteo si ha transcurrido suficiente tiempo
            if (shakeTimestamp + SHAKE_COUNT_RESET_TIME < now) {
                shakesCount = 0
            }

            shakeTimestamp = now
            shakesCount++

            // Notificar al callback con el recuento de sacudidas actual
            onShake(shakesCount)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        // Not used in this case
    }
}

package nombre.apellido.helloworld.Networking.Models

import com.crepalatchi.mx.Networking.Models.Encoding
import com.crepalatchi.mx.Networking.Models.HTTPMethod
import com.crepalatchi.mx.Networking.Models.TargetType
import org.json.JSONObject

/**
 * Modelo para las peticiones
 * @property url: URL de la petición
 * @property method: Method de la petición
 * @property encoding: Codificación de la petición
 * @property parameters: Parámetros de la petición, pueden no existir en peditiones GET
 */
open class AnahuacAPI(
    override var url: String,
    override var method: HTTPMethod,
    override var encoding: Encoding,
    override var parameters: JSONObject?
) : TargetType
# NewsFlash — App de Noticias (Android / Kotlin)

Aplicación Android nativa desarrollada en Kotlin que consume noticias en tiempo real desde la API de [newsapi.ai](https://newsapi.ai) (Event Registry). Incluye registro e inicio de sesión local, autenticación biométrica, búsqueda de noticias por categoría o palabra clave, y gestión de perfil de usuario.

## Características

- **Splash animado** con navegación automática hacia el login
- **Registro de usuarios** con validación de campos en tiempo real (nombre, apellido, usuario, contraseña y confirmación)
- **Inicio de sesión** con credenciales locales persistidas en `SharedPreferences`
- **Autenticación biométrica** (huella / rostro) mediante AndroidX Biometric, con opción de usar credenciales del dispositivo
- **Listado de noticias** en `RecyclerView`, con carga de imágenes vía Glide
- **Búsqueda** por palabra clave libre o por categorías predefinidas (tendencias, deportes, entretenimiento, política)
- **Perfil de usuario** con foto seleccionable desde galería, copiada a almacenamiento interno para acceso permanente
- **Navegación por pestañas** con `BottomNavigationView`, reutilizando fragmentos en lugar de recrearlos

## Arquitectura

El proyecto sigue un patrón **MVVM con capa de Router**, donde cada pantalla ("scene") se organiza en carpetas independientes:

```
scenes/<pantalla>/
├── view/          # Activity o Fragment — solo UI y listeners
├── viewModel/     # Lógica de presentación y exposición de LiveData
├── model/         # Estado observable de la pantalla
├── router/        # Navegación entre pantallas (Intents)
└── worker/        # Llamadas a la API (solo en 'list')
```


### Capa de red

`RequestManager` encapsula Volley detrás de una interfaz basada en `TargetType`, de modo que cada petición se describe declarativamente (URL, método, encoding, parámetros) en lugar de construirse a mano. Incluye:

- Política de reintentos con timeout de 30 s
- Inyección opcional de un JWT en el header `Authorization`
- Traducción de errores de Volley (`NetworkError`, `TimeoutError`, `ServerError`…) a mensajes legibles

## Stack técnico

| Categoría | Tecnologías |
|---|---|
| Lenguaje | Kotlin 2.0.21 |
| Build | Gradle (Kotlin DSL) + AGP 8.13.2, version catalog |
| Arquitectura | MVVM + Router, ViewBinding, LiveData |
| Red | Volley 1.2.1, Gson 2.13.2 |
| UI | Material Components, ConstraintLayout, RecyclerView, Fragments |
| Imágenes | Glide 4.16.0 |
| Animación | Lottie 6.4.1 |
| Seguridad | AndroidX Biometric |
| Media | Media3 / ExoPlayer 1.5.1 |

**SDK mínimo:** 24 (Android 7.0) · **Target:** 36 · **Java:** 11

## Configuración

El proyecto requiere una API Key de [newsapi.ai](https://newsapi.ai) para funcionar.

1. Crea una cuenta en newsapi.ai y obtén tu API Key
2. Dentro de Android Studio, crea el archivo `local.properties` en la raíz del proyecto
3. Agrega la siguiente línea:

   ```properties
   API_KEY=tu_api_key_aqui
   ```

4. Sincroniza el proyecto en Android Studio (**Sync Now**)

La llave se inyecta en tiempo de compilación como `BuildConfig.API_KEY` desde `app/build.gradle.kts`, y `local.properties` está incluido en `.gitignore` para que nunca llegue al repositorio.

## Ejecución

```bash
git clone https://github.com/davidcllm/demoEquipoKotlin.git
cd demoEquipoKotlin
# Configura local.properties como se indica arriba
./gradlew assembleDebug
```

O bien abre el proyecto en Android Studio y ejecuta sobre un emulador o dispositivo físico. Para probar el login biométrico se requiere un dispositivo (o emulador) con huella o reconocimiento facial configurado.

## Flujo de la aplicación

```
SplashActivity (5 s)
      ↓
MainActivity (login)  ←→  RegisterActivity
      ↓
HomeActivity
      ├── ListFragment     (noticias + búsqueda)
      └── ProfileFragment  (perfil + ajustes)
```

El acceso a `HomeActivity` puede darse por credenciales o por autenticación biométrica, siempre que el usuario haya activado esa opción en su perfil.

## Permisos

- `INTERNET` — consumo de la API de noticias
- `ACCESS_NETWORK_STATE` — detección del estado de conexión

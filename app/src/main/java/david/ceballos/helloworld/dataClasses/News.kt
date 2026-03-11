package david.ceballos.helloworld.dataClasses

/*
Estos datos serán cambiados de acuerdo a la información del API de noticias.
Por el momento, esta información es provisional únicamente para visualizar el
recyclerview.
 */
data class News (
    val sourceName: String,
    val date: String,
    val title: String,
    val subtitle: String,
    val description: String
)
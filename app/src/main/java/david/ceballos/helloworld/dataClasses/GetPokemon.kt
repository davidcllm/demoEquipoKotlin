package david.ceballos.helloworld.dataClasses

data class GetPokemon(
    val count: Double?,
    val next: String?,
    val previous: String?,
    val results: List<Pokemon>?,
)

data class Pokemon(
    val name: String?,
    val url: String?,
)
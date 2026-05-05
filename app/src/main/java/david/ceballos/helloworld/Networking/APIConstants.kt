package david.ceballos.helloworld.Networking

object APIConstants {

    // Base URL del servidor de noticias
    const val MAIN_SERVER = "https://eventregistry.org/api/v1/"

    // Valores por defecto para las peticiones
    const val DEFAULT_LANGUAGE = "spa"
    const val DEFAULT_PAGE = 1
    const val DEFAULT_PAGE_SIZE = 20
    const val DEFAULT_SORT_BY = "date"

    object EndPoints {
        const val GET_ARTICLES = "article/getArticles"
    }

    object BodyKeys {
        const val ACTION = "action"
        const val KEYWORD = "keyword"
        const val LANG = "lang"
        const val ARTICLES_PAGE = "articlesPage"
        const val ARTICLES_COUNT = "articlesCount"
        const val ARTICLES_SORT_BY = "articlesSortBy"
        const val API_KEY = "apiKey"
    }

    object Actions {
        const val GET_ARTICLES = "getArticles"
    }
}
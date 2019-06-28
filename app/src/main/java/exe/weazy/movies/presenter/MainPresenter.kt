package exe.weazy.movies.presenter

import exe.weazy.movies.Tools
import exe.weazy.movies.arch.MainContract
import exe.weazy.movies.entity.Movie
import exe.weazy.movies.model.MainModel
import java.io.File

class MainPresenter : MainContract.Presenter, MainContract.LoadingListener {

    private lateinit var view: MainContract.View
    private lateinit var model : MainModel

    private val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    private var allMovies : ArrayList<Movie> = ArrayList()
    private var currentMovies : ArrayList<Movie> = ArrayList()

    private lateinit var likes : ArrayList<Int>

    private var isSearch = false
    private var isUpdate = false



    /**
     * Событие привязки presenter к view
     * @param view - view, к которой привязывается presenter
     * @param file - путь к директории internal storage приложения для model
     */
    override fun attach(view: MainContract.View, file : File) {
        this.view = view
        model = MainModel(this, file)
    }

    /**
     * Обновление списка фильмов
     * @param page - страница, которую требуется загрузить (1 по умолчанию)
     */
    override fun getMovieList(page : Int, isUpdate : Boolean) {

        when {
            // Если мы загружаем данные впервые или если хотим их обновить
            !isUpdate && allMovies.isEmpty() || isUpdate -> {
                this.isUpdate = isUpdate

                allMovies = ArrayList()

                view.showCircleLoading()

                model.loadMovies(page)
                likes = model.getLikes()
            }

            // Если данные уже загружены, работает при смене конфигурации
            !isUpdate && allMovies.isNotEmpty() -> {
                currentMovies = allMovies
                view.updateList(allMovies)
                view.showList()
            }

            // Если мы только что искали по запросу и вышли из режима поиска
            isSearch -> {
                isSearch = false
                currentMovies = allMovies
                view.updateList(allMovies)
            }
        }
    }

    /**
     * Поиск фильмов
     * @param query - запрос, по которому будет производится поиск
     */
    override fun searchMovie(query: String) {
        isSearch = true

        view.showHorizontalLoading()
        model.loadMovies(query = query)

        val result = ArrayList<Movie>()
        val editedQuery = Tools.getCyrillicString(query)

        allMovies.forEach { movie ->

            val title = Tools.getCyrillicString(movie.title)

            if (title.contains(editedQuery)) {
                result.add(movie)
            }
        }

        currentMovies = result
        view.updateList(currentMovies)
    }

    /**
     * Добавление фильма в избранное и его удаление оттуда
     * @param id - id фильма
     */
    override fun likeMovie(id : Int) {
        var index = 0
        for (i in 0 until currentMovies.size - 1) {
            if (currentMovies[i].id == id) {
                index = i
                break
            }
        }

        currentMovies[index].like = !currentMovies[index].like

        if (!likes.contains(id)) {
            likes.add(id)
        } else {
            likes.remove(id)
        }

        model.writeLikes(likes)
    }



    /**
     * Обработка события удачной загрузки фильмов
     * @param movies - список загруженных фильмов
     */
    override fun onMoviesLoadedFinished(movies: ArrayList<Movie>?) {
        if (movies != null) {

            val result = ArrayList<Movie>()

            movies.forEach { movie ->
                movie.posterPath = BASE_IMAGE_URL + movie.posterPath
                if (!allMovies.contains(movie)) {
                    result.add(movie)
                }
            }

            result.forEach {
                if (likes.contains(it.id)) {
                    it.like = true
                }
            }

            if (isUpdate) {
                isUpdate = false
                allMovies = result
                currentMovies = allMovies
                view.updateList(allMovies)
                view.showList()
            } else {
                allMovies.addAll(result)

                if (isSearch) {
                    currentMovies.addAll(result)

                    if (currentMovies.isEmpty()) {
                        view.showNotFound()
                    } else {
                        view.updateList(currentMovies)
                        view.showList()
                    }
                } else {
                    currentMovies = allMovies
                    view.updateList(allMovies)
                    view.showList()
                }
            }
        } else {
            view.showError()
        }
    }

    /**
     * Обработка события, когда при загрузке произошла ошибка
     * @param t - ошибка
     */
    override fun onMoviesLoadedFailure(t: Throwable) {
        if (currentMovies.isEmpty()) {
            view.showError()
        } else {
            view.showList()
            view.showSnackbarError()
        }
    }



    /**
     * Берёт текущий фильм по индексу
     * @param index - индекс
     */
    fun getMovie(index : Int) = currentMovies[index]
}
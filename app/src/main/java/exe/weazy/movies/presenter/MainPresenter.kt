package exe.weazy.movies.presenter

import exe.weazy.movies.arch.LoadingListener
import exe.weazy.movies.arch.MainContract
import exe.weazy.movies.entity.Movie
import exe.weazy.movies.model.MainModel

class MainPresenter : MainContract.Presenter, LoadingListener {

    private lateinit var view: MainContract.View
    private val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    private var model : MainModel = MainModel(this)
    private var allMovies : ArrayList<Movie> = ArrayList()
    private var currentMovies : ArrayList<Movie> = ArrayList()

    private var isSearch = false
    private var isUpdate = false


    override fun attach(view: MainContract.View) {
        this.view = view
    }

    override fun detach() {

    }

    override fun updateMovieList(page : Int) {
        if (isSearch) {
            isSearch = false
            view.updateList(allMovies)
        } else {
            isUpdate = true
            view.showCircleLoading()
            model.loadMovies(page)
        }
    }

    override fun searchMovie(query: String) {
        isSearch = true

        view.showHorizontalLoading()
        model.loadMovies(query = query)

        val result = ArrayList<Movie>()
        val editedQuery = getCyrillicString(query)

        allMovies.forEach { movie ->

            val title = getCyrillicString(movie.title)

            if (title.contains(editedQuery)) {
                result.add(movie)
            }
        }

        currentMovies = result
        view.updateList(currentMovies)
    }


    override fun onFinished(movies: ArrayList<Movie>?) {
        if (movies != null) {

            val result = ArrayList<Movie>()

            movies.forEach { movie ->
                movie.posterPath = BASE_IMAGE_URL + movie.posterPath
                if (!allMovies.contains(movie)) {
                    result.add(movie)
                }
            }

            if (isUpdate) {
                isUpdate = false
                allMovies = result
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
                    view.updateList(allMovies)
                    view.showList()
                }
            }
        } else {
            view.showError()
        }
    }

    override fun onFailure(t: Throwable) {
        view.showError()
    }

    override fun onProgressUpdate(percentage: Int) {
        // TODO: implement it
    }

    fun getMovie(index : Int) = allMovies[index]

    private fun getCyrillicString(str : String) : String {
        val lowerCaseString = str.toLowerCase()
        val result = StringBuilder()

        lowerCaseString.forEach {
            if (it == 'ё') result.append('е')
            else result.append(it)
        }

        return result.toString()
    }
}
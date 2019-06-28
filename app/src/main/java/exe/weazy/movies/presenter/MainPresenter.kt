package exe.weazy.movies.presenter

import exe.weazy.movies.arch.LoadingListener
import exe.weazy.movies.arch.MainContract
import exe.weazy.movies.entity.Movie
import exe.weazy.movies.model.MainModel
import java.io.File

class MainPresenter : MainContract.Presenter, LoadingListener {

    private lateinit var view: MainContract.View
    private lateinit var model : MainModel

    private val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    private var allMovies : ArrayList<Movie> = ArrayList()
    private var currentMovies : ArrayList<Movie> = ArrayList()

    private lateinit var likes : ArrayList<Int>

    private var isSearch = false
    private var isUpdate = false


    override fun attach(view: MainContract.View, file : File) {
        this.view = view
        model = MainModel(this, file)
    }

    override fun detach() {

    }

    override fun updateMovieList(page : Int) {
        if (isSearch) {
            isSearch = false
            currentMovies = allMovies
            view.updateList(allMovies)
        } else {
            isUpdate = true
            view.showCircleLoading()
            model.loadMovies(page)
            likes = model.getLikes()
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


    override fun onFinished(movies: ArrayList<Movie>?) {
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

    override fun onFailure(t: Throwable) {
        view.showError()
    }

    fun getMovie(index : Int) = currentMovies[index]

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
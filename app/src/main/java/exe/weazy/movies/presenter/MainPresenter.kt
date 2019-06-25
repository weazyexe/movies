package exe.weazy.movies.presenter

import exe.weazy.movies.arch.LoadingListener
import exe.weazy.movies.arch.MainContract
import exe.weazy.movies.entity.Movie
import exe.weazy.movies.model.MainModel

class MainPresenter : MainContract.Presenter, LoadingListener {

    private lateinit var view: MainContract.View
    private val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    private var model : MainModel = MainModel(this)
    private var movies : ArrayList<Movie> = ArrayList()


    override fun attach(view: MainContract.View) {
        this.view = view
    }

    override fun detach() {

    }

    override fun updateMovieList(page : Int) {
        view.showLoading()
        model.loadMovies(page)
    }

    override fun searchMovie(query: String) {
        // TODO: implement it
    }


    override fun onFinished(movies: ArrayList<Movie>?) {
        if (movies != null) {
            movies.forEach { movie ->
                movie.posterPath = BASE_IMAGE_URL + movie.posterPath

                if (!this.movies.contains(movie))
                    this.movies.add(movie)
            }

            view.showList(this.movies)
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
}
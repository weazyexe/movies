package exe.weazy.movies.presenter

import exe.weazy.movies.arch.LoadingListener
import exe.weazy.movies.arch.MainContract
import exe.weazy.movies.entity.Movie
import exe.weazy.movies.model.MainModel

class MainPresenter(private var view: MainContract.View) : MainContract.Presenter, LoadingListener {

    private val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    private var model : MainModel = MainModel(this)
    private var movies : ArrayList<Movie> = ArrayList()

    override fun updateMovieList(page : Int) {
        model.loadMovies(page)
    }

    override fun searchMovie(query: String) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroy() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun onFinished(movies: ArrayList<Movie>?) {
        if (movies != null) {
            movies.forEach {
                it.posterPath = BASE_IMAGE_URL + it.posterPath
                this.movies.add(it)
            }

            view.showList(this.movies)
        }
    }

    override fun onFailure(t: Throwable) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProgressUpdate(percentage: Int) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
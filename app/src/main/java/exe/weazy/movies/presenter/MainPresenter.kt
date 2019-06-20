package exe.weazy.movies.presenter

import exe.weazy.movies.arch.LoadingListener
import exe.weazy.movies.arch.MainContract
import exe.weazy.movies.entity.Movie

class MainPresenter : MainContract.Presenter, LoadingListener {


    private lateinit var view: MainContract.View


    constructor()

    constructor(view : MainContract.View) {
        this.view = view
    }

    override fun updateMovieList() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun searchMovie(query: String) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onDestroy() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun onFinished(movies: List<Movie>) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onFailure(t: Throwable) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onProgressUpdate(percentage: Int) {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
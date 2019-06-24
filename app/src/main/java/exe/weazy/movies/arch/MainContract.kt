package exe.weazy.movies.arch

import exe.weazy.movies.entity.Movie

interface MainContract {
    interface View {
        fun showList(movies : ArrayList<Movie>)
        fun showLoading()
        fun showNotFound()
        fun showError()
    }

    interface Presenter {
        fun updateMovieList(page : Int)
        fun searchMovie(query : String)
        fun onDestroy()
    }

    interface Model {
        fun loadMovies(page : Int, query : String = "")
    }
}
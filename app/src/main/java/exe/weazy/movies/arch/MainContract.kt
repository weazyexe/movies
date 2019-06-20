package exe.weazy.movies.arch

interface MainContract {
    interface View {
        fun showList()
        fun showLoading()
        fun showNotFound()
        fun showError()
    }

    interface Presenter {
        fun updateMovieList()
        fun searchMovie(query : String)
        fun onDestroy()
    }

    interface Model {
        fun loadMovies(query : String = "")
    }
}
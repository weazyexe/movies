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
        fun attach(view : View)
        fun detach()
        fun updateMovieList(page : Int)
        fun searchMovie(query : String)
    }

    interface Model {
        fun loadMovies(page : Int, query : String = "")
    }
}
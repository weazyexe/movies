package exe.weazy.movies.arch

import exe.weazy.movies.entity.Movie

interface MainContract {
    interface View {
        fun showList()
        fun showCircleLoading()
        fun showHorizontalLoading()
        fun showNotFound()
        fun showError()
        fun updateList(movies : ArrayList<Movie>)
    }

    interface Presenter {
        fun attach(view : View)
        fun detach()
        fun updateMovieList(page : Int = 1)
        fun searchMovie(query : String)
    }

    interface Model {
        fun loadMovies(page : Int = 1, query : String = "")
    }
}
package exe.weazy.movies.arch

import exe.weazy.movies.entity.Movie
import java.io.File

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
        fun attach(view : View, file : File)
        fun detach()
        fun updateMovieList(page : Int = 1)
        fun searchMovie(query : String)
        fun likeMovie(id : Int)
    }

    interface Model {
        fun loadMovies(page : Int = 1, query : String = "")
        fun writeLikes(likes : ArrayList<Int>)
        fun getLikes() : ArrayList<Int>
    }
}
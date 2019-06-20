package exe.weazy.movies.arch

import exe.weazy.movies.entity.Movie

interface LoadingListener {
    fun onFinished(movies : List<Movie>)
    fun onFailure(t : Throwable)
    fun onProgressUpdate(percentage : Int)
}
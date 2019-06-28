package exe.weazy.movies.arch

import exe.weazy.movies.entity.Movie

interface LoadingListener {
    fun onFinished(movies : ArrayList<Movie>?)
    fun onFailure(t : Throwable)
}
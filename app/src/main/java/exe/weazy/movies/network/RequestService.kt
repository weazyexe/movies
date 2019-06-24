package exe.weazy.movies.network

import exe.weazy.movies.entity.Movie
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RequestService {

    @GET("/discover/movie?api_key=6ccd72a2a8fc239b13f209408fc31c33&language=ru&region=ru&sort_by=popularity.desc&include_video=false&page={page}")
    fun getMovies(@Path("page") page : Int) : Call<ArrayList<Movie>>

    @GET("/search/movie?api_key=6ccd72a2a8fc239b13f209408fc31c33&language=ru-RU&query={query}")
    fun getMoviesByQuery(@Path("query") query : String) : Call<ArrayList<Movie>>
}
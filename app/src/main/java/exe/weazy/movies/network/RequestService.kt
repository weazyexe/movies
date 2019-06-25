package exe.weazy.movies.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RequestService {

    @GET("discover/movie?api_key=6ccd72a2a8fc239b13f209408fc31c33&language=ru&region=ru&sort_by=popularity.desc&include_video=false")
    fun getMovies(@Query("page") page : Int) : Call<MovieResponse>

    @GET("search/movie?api_key=6ccd72a2a8fc239b13f209408fc31c33&language=ru-RU")
    fun getMoviesByQuery(@Query("query") query : String) : Call<MovieResponse>
}
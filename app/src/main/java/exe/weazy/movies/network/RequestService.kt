package exe.weazy.movies.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RequestService {

    private val API_KEY: String
        get() = "6ccd72a2a8fc239b13f209408fc31c33"

    @GET("discover/movie?language=ru&region=ru&sort_by=popularity.desc&include_video=false")
    fun getMovies(@Query("page") page : Int, @Query("api_key") apiKey : String = API_KEY) : Call<MovieResponse>

    @GET("search/movie?language=ru-RU")
    fun getMoviesByQuery(@Query("query") query : String, @Query("api_key") apiKey : String = API_KEY) : Call<MovieResponse>
}
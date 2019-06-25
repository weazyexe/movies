package exe.weazy.movies.network

import com.google.gson.annotations.SerializedName
import exe.weazy.movies.entity.Movie

data class MovieResponse (
    @SerializedName("results")
    val movies : ArrayList<Movie>
)
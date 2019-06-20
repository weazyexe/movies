package exe.weazy.movies.entity

import com.google.gson.annotations.SerializedName

class Movie (
    @SerializedName("title")
    var title : String,

    @SerializedName("overview")
    var overview : String,

    @SerializedName("poster_path")
    var posterPath : String,

    @SerializedName("release_date")
    var releaseDate : String,

    @SerializedName("adult")
    var adult : Boolean
)
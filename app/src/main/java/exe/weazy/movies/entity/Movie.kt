package exe.weazy.movies.entity

import com.google.gson.annotations.SerializedName

class Movie (

    @SerializedName("id")
    var id : Int,

    @SerializedName("title")
    var title : String,

    @SerializedName("overview")
    var overview : String,

    @SerializedName("poster_path")
    var posterPath : String,

    @SerializedName("release_date")
    var releaseDate : String,

    @SerializedName("adult")
    var adult : Boolean,

    var like : Boolean
) {

    override fun equals(other: Any?): Boolean {
        if (other !is Movie) return false

        return id == other.id && title == other.title && overview == other.overview &&
                posterPath == other.posterPath && releaseDate == other.releaseDate &&
                adult == other.adult
    }

    override fun toString(): String {
        return "$id | $title"
    }
}
package exe.weazy.movies.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import exe.weazy.movies.R
import exe.weazy.movies.Tools
import exe.weazy.movies.entity.Movie

class MoviesAdapter(private var movies : List<Movie>, private var onItemClickListener : View.OnClickListener)
    : RecyclerView.Adapter<MoviesAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_movie, parent, false)
        view.setOnClickListener(onItemClickListener)
        return Holder(view)
    }

    override fun getItemCount() = movies.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val movie = movies[position]

        holder.title.text = movie.title
        holder.overview.text = movie.overview
        holder.date.text = Tools.convertDate(movie.releaseDate)

        if (movie.like) {
            holder.liked.visibility = View.VISIBLE
            holder.notLiked.visibility = View.GONE
        } else {
            holder.liked.visibility = View.GONE
            holder.notLiked.visibility = View.VISIBLE
        }

        Glide.with(holder.cardMovie)
            .load(movie.posterPath)
            .into(holder.poster)
    }

    fun setMovies(movies : ArrayList<Movie>) {
        this.movies = movies
        notifyDataSetChanged()
    }

    inner class Holder(view : View) : RecyclerView.ViewHolder(view) {

        var cardMovie : CardView
        var title : TextView
        var overview : TextView
        var poster : ImageView
        var date : TextView
        var liked : ImageView
        var notLiked : ImageView

        init {
            super.itemView

            cardMovie = itemView.findViewById(R.id.card_movie)
            title = itemView.findViewById(R.id.text_title)
            overview = itemView.findViewById(R.id.text_overview)
            poster = itemView.findViewById(R.id.image_poster)
            date = itemView.findViewById(R.id.text_date)
            liked = itemView.findViewById(R.id.image_liked)
            notLiked = itemView.findViewById(R.id.image_not_liked)
        }
    }
}
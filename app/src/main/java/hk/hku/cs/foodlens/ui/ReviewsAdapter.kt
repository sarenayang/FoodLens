package hk.hku.cs.foodlens.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hk.hku.cs.foodlens.R
import hk.hku.cs.foodlens.ui.friends.ReviewData

data class Review(val username: String, val dishName: String, val restaurant: String, val reviewText: String, val hearts: Int)

class ReviewsAdapter(private var reviews: List<ReviewData>) : RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder>() {

    class ReviewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userHadDishAtRestaurant: TextView = itemView.findViewById(R.id.userHadDishAtRestaurant)
        val review: TextView = itemView.findViewById(R.id.review)
        val heart1: ImageView = itemView.findViewById(R.id.heart1)
        val heart2: ImageView = itemView.findViewById(R.id.heart2)
        val heart3: ImageView = itemView.findViewById(R.id.heart3)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.review_cards, parent, false)
        return ReviewsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReviewsViewHolder, position: Int) {
        val review = reviews[position]
        holder.userHadDishAtRestaurant.text = "${review.friendName} reviewed ${review.dishName} at ${review.restaurantName}"

        // Set hearts visibility based on the grade
        holder.heart1.visibility = if (review.grade >= 1) View.VISIBLE else View.INVISIBLE
        holder.heart2.visibility = if (review.grade >= 2) View.VISIBLE else View.INVISIBLE
        holder.heart3.visibility = if (review.grade >= 3) View.VISIBLE else View.INVISIBLE
    }

    fun updateReviews(newReviews: List<ReviewData>) {
        reviews = newReviews
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = reviews.size
}
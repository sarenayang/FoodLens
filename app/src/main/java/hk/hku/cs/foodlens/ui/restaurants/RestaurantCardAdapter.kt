package hk.hku.cs.foodlens.ui.restaurants

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hk.hku.cs.foodlens.R

class RestaurantCardAdapter(
    private var restaurants: List<RestaurantCardData>,
    private val onClick: (RestaurantCardData) -> Unit
) : RecyclerView.Adapter<RestaurantCardAdapter.RestaurantViewHolder>() {

    class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.restaurant_carditem, parent, false)
        return RestaurantViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val restaurant = restaurants[position]
        holder.titleTextView.text = restaurant.title
        holder.itemView.setOnClickListener { onClick(restaurant) }
    }

    override fun getItemCount(): Int {
        return restaurants.size
    }

    fun updateRestaurants(newRestaurants: List<RestaurantCardData>) {
        restaurants = newRestaurants
        notifyDataSetChanged()
    }
}
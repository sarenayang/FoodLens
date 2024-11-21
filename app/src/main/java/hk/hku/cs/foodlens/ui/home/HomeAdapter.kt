package hk.hku.cs.foodlens.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hk.hku.cs.foodlens.R
import hk.hku.cs.foodlens.ui.model.Restaurant
import org.w3c.dom.Text


class HomeAdapter(
    private val context: Context,
    private val restaurants: List<Restaurant>,
    private val onItemClick: (Restaurant) -> Unit
) : RecyclerView.Adapter<HomeAdapter.RestaurantViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.restaurant_list_item, parent, false)
        return RestaurantViewHolder(view)
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        val restaurant = restaurants[position]
        holder.bind(restaurant, onItemClick)
    }

    override fun getItemCount(): Int = restaurants.size

    class RestaurantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.findViewById(R.id.restaurant_name)
        private val cuisineTextView: TextView = itemView.findViewById(R.id.cuisine)

        fun bind(restaurant: Restaurant, onItemClick: (Restaurant) -> Unit) {
            nameTextView.text = restaurant.name
            cuisineTextView.text = restaurant.cuisine
            itemView.setOnClickListener { onItemClick(restaurant) }
        }
    }
}


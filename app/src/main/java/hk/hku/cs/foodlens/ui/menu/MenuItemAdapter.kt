package hk.hku.cs.foodlens.ui.menu

import android.content.Context
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hk.hku.cs.foodlens.R
import hk.hku.cs.foodlens.ui.model.MenuItem


class MenuItemAdapter(
    private val context: Context,
    private val restaurantName: String,
    private val menuItems: List<MenuItem>,
    private val onItemClick: (MenuItem) -> Unit
) : RecyclerView.Adapter<MenuItemAdapter.MenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.menu_item, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val item = menuItems[position]
        holder.bind(item, onItemClick)

    }

    override fun getItemCount(): Int = menuItems.size

    class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemNameTextView: TextView = itemView.findViewById(R.id.item_name)

        fun bind(menuItem: MenuItem, onItemClick: (MenuItem) -> Unit) {
            itemNameTextView.text = menuItem.dishName
            itemView.setOnClickListener { onItemClick(menuItem) }
        }
    }
}

//    fun updateRestaurants(newRestaurants: List<RestaurantCardData>) {
//        restaurants = newRestaurants
//        notifyDataSetChanged()
//    }

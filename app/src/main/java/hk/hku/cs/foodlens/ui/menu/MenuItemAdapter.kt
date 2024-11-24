package hk.hku.cs.foodlens.ui.menu

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hk.hku.cs.foodlens.R
import hk.hku.cs.foodlens.ui.model.MenuItem


class MenuItemAdapter(
    private val context: Context,
    private val restaurantName: String,
    private val menuItems: List<MenuItem>,
    private val onItemClick: (MenuItem) -> Unit,
    private val onButtonClick: (MenuItem) -> Unit // Add a new parameter for button click
) : RecyclerView.Adapter<MenuItemAdapter.MenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menu_item, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val item = menuItems[position]
        holder.bind(item, onButtonClick, onItemClick)

    }

    override fun getItemCount(): Int = menuItems.size

    class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemNameTextView: TextView = itemView.findViewById(R.id.item_name)
        private val button: ImageButton = itemView.findViewById(R.id.star_button)

        fun bind(menuItem: MenuItem, onButtonClick: (MenuItem) -> Unit, onItemClick: (MenuItem) -> Unit) {
            button.setOnClickListener {
                onButtonClick(menuItem)
            }

            itemNameTextView.text = menuItem.dishName
            Log.v("BRUH", menuItem.dishName)
            itemView.setOnClickListener { onItemClick(menuItem) }
        }
    }
}

//    fun updateRestaurants(newRestaurants: List<RestaurantCardData>) {
//        restaurants = newRestaurants
//        notifyDataSetChanged()
//    }

package hk.hku.cs.foodlens.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import hk.hku.cs.foodlens.R

class RestaurantCardAdapter(
    private val cardList: List<RestaurantCardData>,
    private val onItemClick: (RestaurantCardData) -> Unit
) : RecyclerView.Adapter<RestaurantCardAdapter.CardViewHolder>() {

    inner class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        private val descriptionTextView: TextView = itemView.findViewById(R.id.descriptionTextView)

        fun bind(cardData: RestaurantCardData) {
            titleTextView.text = cardData.title
            descriptionTextView.text = cardData.description
            itemView.setOnClickListener { onItemClick(cardData) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.restaurant_carditem, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(cardList[position])
    }

    override fun getItemCount() = cardList.size
}
package hk.hku.cs.foodlens.ui.model

data class Restaurant(
    val name: String,
    val location: String,
    val cuisine: String,
    val menuItems: List<String> // maybe create MenuItem data class?
)

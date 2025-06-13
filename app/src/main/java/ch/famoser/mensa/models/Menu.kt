package ch.famoser.mensa.models

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
class Menu(
  val title: String,
  val description: String,
  val price: Array<String>,
  val allergens: String?,
) {
  companion object {
    val dummyMenu = Menu(
      title = "vitality",
      description = "Quornwürfel mit Tomatensauce mit Gnocchi",
      price = arrayOf("14.50", "20.50", "21.30"),
      allergens = "Sellerie, Nüsse, Schweinefleisch",
    )
  }

  override fun toString(): String = title + description

  @Transient
  var mensa: Mensa? = null

  public fun isSomeEmpty(): Boolean {
    return this.title.isEmpty() || this.description.isEmpty();
  }

  public fun mergeWithFallback(other: Menu): Menu {
    val title = this.title.ifEmpty { other.title };
    val description = this.description.ifEmpty { other.description };
    val price = this.price.ifEmpty { other.price }
    val allergens =
      if (this.allergens !== null && this.allergens.isNotEmpty()) this.allergens else other.allergens;

    return Menu(title, description, price, allergens);
  }
}

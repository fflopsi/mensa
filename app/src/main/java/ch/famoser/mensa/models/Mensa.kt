package ch.famoser.mensa.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import ch.famoser.mensa.adapters.MensaAdapter
import ch.famoser.mensa.services.URISerializer
import ch.famoser.mensa.services.UUIDSerializer
import kotlinx.serialization.Serializable
import java.net.URI
import java.util.UUID

@Serializable
class Mensa(
  @Serializable(with = UUIDSerializer::class) val id: UUID,
  val title: String,
  val mealTime: String,
  @Serializable(with = URISerializer::class) val url: URI,
  val imagePath: String? = null,
) {
  var location: Location? by mutableStateOf(null)

  private val _menus: MutableList<Menu> = mutableStateListOf()

  val menus: List<Menu>
    get() = _menus

  var state by mutableStateOf(MensaAdapter.ViewState.Initial)

  fun replaceMenus(menus: List<Menu>) {
    this._menus.clear()

    for (menu in menus) {
      menu.mensa = this
      this._menus.add(menu)
    }
  }

  override fun toString(): String = title
}

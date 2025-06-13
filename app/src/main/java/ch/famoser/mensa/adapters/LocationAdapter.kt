package ch.famoser.mensa.adapters

import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.platform.ComposeView
import androidx.recyclerview.widget.RecyclerView
import ch.famoser.mensa.activities.MainActivity
import ch.famoser.mensa.compose.MensaTheme
import ch.famoser.mensa.compose.RowLocation
import ch.famoser.mensa.models.Location
import ch.famoser.mensa.models.Mensa

class LocationAdapter(
  private val parentActivity: MainActivity,
  private val twoPane: Boolean,
  private val values: List<Location>,
  initializeFully: Boolean,
) : RecyclerView.Adapter<LocationAdapter.ComposeViewHolder>() {

  private val mensaAdapterByLocation: MutableMap<Location, MensaAdapter> = HashMap()
  val displayedLocations: MutableList<Location> = mutableStateListOf()

  init {
    reset(initializeFully)
  }

  fun reset(initializeFully: Boolean = true) {
    mensaAdapterByLocation.clear()
    displayedLocations.clear()

    for (location in values) {
      val adapter = MensaAdapter(parentActivity, location.mensas, twoPane, initializeFully)
      if (adapter.itemCount > 0) {
        mensaAdapterByLocation[location] = adapter
        displayedLocations.add(location)
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    ComposeViewHolder(ComposeView(parent.context).apply { id = View.generateViewId() })

  override fun onBindViewHolder(viewHolder: ComposeViewHolder, position: Int) {
    val item = displayedLocations[position]
    viewHolder.composeView.setContent {
      MensaTheme {
        RowLocation(location = item)
      }
    }
  }

  override fun getItemCount() = displayedLocations.size

  fun getFavoriteMensaCount() = values.sumOf { location ->
    location.mensas.count {
      MensaAdapter.isFavoriteMensa(
        parentActivity, it
      )
    }
  }

  fun mensaUpdated(mensa: Mensa) {
    val mensaAdapter = mensaAdapterByLocation[mensa.location]
    mensaAdapter?.mensaMenusRefreshed(mensa.id)
  }

  fun mensasUpdated(mensas: List<Mensa>) {
    val groupByLocation = mensas.groupBy { it.location }
    for (sameLocationMensas in groupByLocation) {
      val mensaAdapter = mensaAdapterByLocation[sameLocationMensas.key]
      for (sameLocationMensa in sameLocationMensas.value) {
        mensaAdapter?.mensaMenusRefreshed(sameLocationMensa.id)
      }
    }
  }

  inner class ComposeViewHolder(val composeView: ComposeView) : RecyclerView.ViewHolder(composeView)
}

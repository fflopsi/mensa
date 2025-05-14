package ch.famoser.mensa.adapters

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.recyclerview.widget.RecyclerView
import ch.famoser.mensa.activities.MainActivity
import ch.famoser.mensa.compose.MensaTheme
import ch.famoser.mensa.compose.RowMensa
import ch.famoser.mensa.models.Mensa
import java.util.UUID


class MensaAdapter(
  private val parentActivity: MainActivity,
  values: List<Mensa>,
  private val twoPane: Boolean, // TODO
  private val initializeFully: Boolean,
) : RecyclerView.Adapter<MensaAdapter.ComposeViewHolder>() {
  companion object {
    private const val MensaIsFavoriteSettingPrefix = "MensaMenusVisibility"
    private const val ShowOnlyFavoriteMensasSetting = "ShowOnlyFavoriteMensas"

    fun saveIsFavoriteMensa(context: Activity, mensa: Mensa, value: Boolean) {
      val sharedPreferences = context.getPreferences(Context.MODE_PRIVATE) ?: return
      sharedPreferences.edit().putBoolean(MensaIsFavoriteSettingPrefix + "." + mensa.id, value)
        .apply()
    }

    fun isFavoriteMensa(context: Activity, mensa: Mensa): Boolean {
      val sharedPreferences = context.getPreferences(Context.MODE_PRIVATE) ?: return false
      return sharedPreferences.getBoolean(MensaIsFavoriteSettingPrefix + "." + mensa.id, false)
    }

    fun showOnlyFavoriteMensas(context: Activity): Boolean {
      val sharedPreferences = context.getPreferences(Context.MODE_PRIVATE) ?: return false
      return sharedPreferences.getBoolean(ShowOnlyFavoriteMensasSetting, false)
    }

    fun saveOnlyFavoriteMensasSetting(context: Activity, value: Boolean) {
      val sharedPreferences = context.getPreferences(Context.MODE_PRIVATE) ?: return
      sharedPreferences.edit().putBoolean(ShowOnlyFavoriteMensasSetting, value).apply()
    }
  }

  private val displayedMensas: List<MensaViewModel>

  init {
    displayedMensas = if (showOnlyFavoriteMensas()) {
      values.filter { isFavoriteMensa(it) }
    } else {
      values
    }.map { MensaViewModel(it) }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComposeViewHolder {
    return ComposeViewHolder(ComposeView(parent.context).apply {
      id = View.generateViewId()
    })
  }

  private val statefulViewHoldersByMensaId: MutableMap<UUID, StatefulViewHolder> = HashMap()

  override fun onBindViewHolder(viewHolder: ComposeViewHolder, position: Int) {
    val mensaViewModel = displayedMensas[position]
    val holder = StatefulViewHolder(mensaViewModel)
    statefulViewHoldersByMensaId[mensaViewModel.mensa.id] = holder
    print(mensaViewModel.mensa.menus)
    viewHolder.composeView.setContent {
      MensaTheme {
        RowMensa(
          mensa = mensaViewModel.mensa,
          saveIsFavoriteMensa = ::saveIsFavoriteMensa,
        )
      }
    }
  }

  private fun saveIsFavoriteMensa(mensa: Mensa, value: Boolean) =
    Companion.saveIsFavoriteMensa(parentActivity, mensa, value)

  private fun isFavoriteMensa(mensa: Mensa) = Companion.isFavoriteMensa(parentActivity, mensa)

  private fun showOnlyFavoriteMensas() = Companion.showOnlyFavoriteMensas(parentActivity)

  override fun getItemCount() = displayedMensas.size

  fun mensaMenusRefreshed(mensaId: UUID) {
    val holder = statefulViewHoldersByMensaId[mensaId] ?: return

    val currentState = holder.mensaViewModel.viewState
    val recommendedState = getRecommendedState(holder.mensaViewModel.mensa)
    if (recommendedState == ViewState.Closed) {
      if (currentState != ViewState.Closed) {
        holder.mensaViewModel.mensa.state = ViewState.Closed
      }
    } else {
      if (currentState == ViewState.Expanded) {
        // I have no idea what this does and if I need it
        // holder.viewHolder.getMenuRecyclerView().adapter?.notifyDataSetChanged()
      } else if (currentState == ViewState.Closed || currentState == ViewState.Initial) {
        holder.mensaViewModel.mensa.state = recommendedState
      }
    }
  }

  private fun getRecommendedState(mensa: Mensa): ViewState {
    return if (mensa.menus.isNotEmpty()) {
      if (isFavoriteMensa(mensa)) ViewState.Expanded else ViewState.Available
    } else {
      ViewState.Closed
    }
  }

  inner class MensaViewModel(val mensa: Mensa, var viewState: ViewState = ViewState.Initial)

  inner class StatefulViewHolder(val mensaViewModel: MensaViewModel)

  inner class ComposeViewHolder(val composeView: ComposeView) : RecyclerView.ViewHolder(composeView)

  enum class ViewState { Initial, Closed, Available, Expanded }
}

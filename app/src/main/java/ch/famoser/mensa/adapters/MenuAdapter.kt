package ch.famoser.mensa.adapters

import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.recyclerview.widget.RecyclerView
import ch.famoser.mensa.activities.MainActivity
import ch.famoser.mensa.compose.MensaTheme
import ch.famoser.mensa.compose.RowMenu
import ch.famoser.mensa.models.Menu


class MenuAdapter(
  private val parentActivity: MainActivity,
  private val values: List<Menu>,
  private val twoPane: Boolean,
) : RecyclerView.Adapter<MenuAdapter.ComposeViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComposeViewHolder {
    return ComposeViewHolder(ComposeView(parent.context).apply {
      id = View.generateViewId()
    })
  }

  override fun onBindViewHolder(holder: ComposeViewHolder, position: Int) {
    val item = values[position]
    holder.composeView.setContent {
      MensaTheme {
        RowMenu(menu = item, twoPane = twoPane, parentActivity = parentActivity)
      }
    }
  }

  override fun getItemCount() = values.size

  inner class ComposeViewHolder(val composeView: ComposeView) : RecyclerView.ViewHolder(composeView)
}

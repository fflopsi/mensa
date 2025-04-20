package ch.famoser.mensa.adapters

import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.recyclerview.widget.RecyclerView
import ch.famoser.mensa.compose.RowMenuDetails
import ch.famoser.mensa.models.Menu


class MenuDetailsAdapter(
  private val values: List<Menu>
) : RecyclerView.Adapter<MenuDetailsAdapter.ComposeViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComposeViewHolder {
    return ComposeViewHolder(ComposeView(parent.context).apply {
      id = View.generateViewId()
    })
  }

  override fun onBindViewHolder(holder: ComposeViewHolder, position: Int) {
    val item = values[position]
    holder.composeView.setContent {
      MaterialTheme {
        RowMenuDetails(menu = item)
      }
    }
  }

  override fun getItemCount() = values.size

  inner class ComposeViewHolder(val composeView: ComposeView) : RecyclerView.ViewHolder(composeView)
}

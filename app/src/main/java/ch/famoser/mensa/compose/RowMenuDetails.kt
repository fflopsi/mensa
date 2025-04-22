package ch.famoser.mensa.compose

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ch.famoser.mensa.R
import ch.famoser.mensa.models.Menu
import ch.famoser.mensa.models.dummyMenu

fun onMenuClick(menu: Menu, context: Context) {
  val clip = ClipData.newPlainText("meals content", "${menu.title}: ${menu.description}")
  val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
  clipboardManager.setPrimaryClip(clip)
  Toast.makeText(context, "Copied to clipboard", Toast.LENGTH_SHORT).show()
}

fun onShareClick(menu: Menu, context: Context) {
  val sendIntent: Intent = Intent().apply {
    action = Intent.ACTION_SEND
    putExtra(Intent.EXTRA_TEXT, "${menu.title}: ${menu.description}")
    type = "text/plain"
  }
  val shareIntent = Intent.createChooser(sendIntent, null)
  context.startActivity(shareIntent)
}

@Composable
fun RowMenuDetails(menu: Menu, modifier: Modifier = Modifier) {
  val context = LocalContext.current

  ElevatedCard(
    onClick = { onMenuClick(menu, context) },
    elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
    colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surface),
    modifier = modifier
      .padding(
        horizontal = dimensionResource(R.dimen.card_spacing),
        vertical = dimensionResource(R.dimen.card_spacing_half),
      )
      .focusable(true)
      .fillMaxWidth(),
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(dimensionResource(R.dimen.text_margin)),
    ) {
      Column(modifier = Modifier.weight(1f)) {
        if (menu.title.isNotEmpty()) Text(
          text = menu.title,
          fontWeight = FontWeight.Bold,
          style = MaterialTheme.typography.bodyLarge,
        )
        if (menu.price.isNotEmpty()) Text(
          text = menu.price.joinToString(" / "),
          style = MaterialTheme.typography.bodyMedium,
        )
        if (menu.description.isNotEmpty()) Text(
          text = menu.description,
          style = MaterialTheme.typography.bodyMedium,
          modifier = Modifier
            .padding(top = dimensionResource(R.dimen.text_margin))
            .fillMaxWidth(),
        )
        if (!menu.allergens.isNullOrEmpty()) Text(
          text = menu.allergens,
          style = MaterialTheme.typography.bodySmall,
          modifier = Modifier
            .padding(top = dimensionResource(R.dimen.text_margin))
            .fillMaxWidth(),
        )
      }
      FilledIconButton(
        onClick = { onShareClick(menu, context) },
        modifier = Modifier.align(Alignment.Bottom),
      ) {
        Icon(Icons.Filled.Share, stringResource(R.string.share))
      }
    }
  }
}

@Preview(widthDp = 400)
@Composable
fun PreviewRowMenuDetails() = RowMenuDetails(dummyMenu)

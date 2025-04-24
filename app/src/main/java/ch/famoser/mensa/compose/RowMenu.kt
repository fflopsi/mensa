package ch.famoser.mensa.compose

import android.content.Intent
import android.os.Bundle
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import ch.famoser.mensa.R
import ch.famoser.mensa.activities.MainActivity
import ch.famoser.mensa.activities.MensaActivity
import ch.famoser.mensa.fragments.MensaDetailFragment
import ch.famoser.mensa.models.Menu
import ch.famoser.mensa.models.dummyMenu

@Composable
fun RowMenu(
  menu: Menu,
  twoPane: Boolean = false,
  parentActivity: MainActivity? = null,
  modifier: Modifier = Modifier,
) {
  val context = LocalContext.current

  Row(
    modifier = modifier
      .clickable(onClick = {
        if (twoPane) {
          val fragment = MensaDetailFragment().apply {
            arguments = Bundle().apply {
              putString(MensaDetailFragment.MENSA_ID, menu.mensa!!.id.toString())
            }
          }
          parentActivity!!.supportFragmentManager
            .beginTransaction()
            .replace(R.id.details_container, fragment)
            .commit()
        } else {
          val intent = Intent(context, MensaActivity::class.java).apply {
            putExtra(MensaDetailFragment.MENSA_ID, menu.mensa!!.id.toString())
          }

          context.startActivity(intent)
        }
      })
      .focusable(true)
      .fillMaxWidth(),
  ) {
    Text(
      text = menu.description,
      maxLines = 2,
      modifier = Modifier
        .padding(dimensionResource(R.dimen.text_margin))
        .weight(1f),
      color = MaterialTheme.colorScheme.onSurface, // This should not be required manually
    )
    Column(
      horizontalAlignment = Alignment.End,
      modifier = Modifier.padding(dimensionResource(R.dimen.text_margin)),
    ) {
      Text(
        text = menu.title,
        textAlign = TextAlign.End,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface, // This should not be required manually
      )
      if (menu.price.isNotEmpty()) Text(
        text = stringResource(R.string.price, menu.price.first()),
        textAlign = TextAlign.End,
        color = MaterialTheme.colorScheme.onSurface, // This should not be required manually
      )
    }
  }
}

@Preview(widthDp = 400)
@Composable
fun PreviewRowMenu() = ElevatedCard { RowMenu(dummyMenu) }

package ch.famoser.mensa.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import ch.famoser.mensa.R
import ch.famoser.mensa.models.Mensa
import ch.famoser.mensa.models.dummyMenu
import java.net.URI
import java.util.UUID

@Composable
fun RowMensa(
  mensa: Mensa,
  modifier: Modifier = Modifier,
) {
  ElevatedCard(
    modifier = modifier
      .padding(
        horizontal = dimensionResource(R.dimen.card_spacing),
        vertical = dimensionResource(R.dimen.card_spacing_half),
      )
      .clickable { }
      .focusable()
      .fillMaxWidth(),
  ) {
    Column(
      modifier = Modifier.fillMaxWidth(),
    ) {
      Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth(),
      ) {
        Text(
          text = mensa.title,
          fontWeight = FontWeight.Bold,
          overflow = TextOverflow.Ellipsis,
          maxLines = 1,
          modifier = Modifier
            .weight(1f)
            .padding(dimensionResource(R.dimen.text_margin)),
        )
        Text(
          text = mensa.mealTime,
          modifier = Modifier.padding(dimensionResource(R.dimen.text_margin)),
        )
      }
      LazyColumn(
        modifier = Modifier.fillMaxWidth(),
      ) {
        items(mensa.menus) {
          RowMenu(it)
        }
      }
    }
  }
}

@Preview(widthDp = 400)
@Composable
fun PreviewRowMensa() = RowMensa(Mensa(
  id = UUID.randomUUID(),
  title = "Mensa Polyterasse with very long text which should ellipsize",
  mealTime = "immer",
  url = URI(""),
).apply { replaceMenus(listOf(dummyMenu, dummyMenu, dummyMenu, dummyMenu)) })

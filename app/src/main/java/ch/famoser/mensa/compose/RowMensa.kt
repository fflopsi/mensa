package ch.famoser.mensa.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import ch.famoser.mensa.R
import ch.famoser.mensa.adapters.MensaAdapter.ViewState
import ch.famoser.mensa.models.Mensa
import ch.famoser.mensa.models.dummyMenu
import java.net.URI
import java.util.UUID

@Composable
fun RowMensa(
  mensa: Mensa,
  saveIsFavoriteMensa: (mensa: Mensa, value: Boolean) -> Unit = { _, _ -> },
  modifier: Modifier = Modifier,
) {
  ElevatedCard(
    modifier = modifier
      .padding(
        horizontal = dimensionResource(R.dimen.card_spacing),
        vertical = dimensionResource(R.dimen.card_spacing_half),
      )
      .clickable {
        if (mensa.state == ViewState.Available) {
          saveIsFavoriteMensa(mensa, true)
          mensa.state = ViewState.Expanded
        } else if (mensa.state == ViewState.Expanded) {
          saveIsFavoriteMensa(mensa, false)
          mensa.state = ViewState.Available
        }
      }
      .focusable()
      .fillMaxWidth(),
  ) {
    Column(
      modifier = Modifier.fillMaxWidth(),
    ) {
      Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
          .background(
            when (mensa.state) {
              ViewState.Closed, ViewState.Initial -> MaterialTheme.colorScheme.primaryContainer
              else -> MaterialTheme.colorScheme.primary
            }
          )
          .fillMaxWidth(),
      ) {
        Text(
          text = mensa.title,
          fontWeight = FontWeight.Bold,
          overflow = TextOverflow.Ellipsis,
          maxLines = 1,
          color = when (mensa.state) {
            ViewState.Closed, ViewState.Initial -> MaterialTheme.colorScheme.onPrimaryContainer
            else -> MaterialTheme.colorScheme.onPrimary
          },
          modifier = Modifier
            .weight(1f)
            .padding(dimensionResource(R.dimen.text_margin)),
        )
        Text(
          text = when (mensa.state) {
            ViewState.Closed -> stringResource(R.string.closed)
            else -> mensa.mealTime
          },
          color = when (mensa.state) {
            ViewState.Closed, ViewState.Initial -> MaterialTheme.colorScheme.onPrimaryContainer
            else -> MaterialTheme.colorScheme.onPrimary
          },
          modifier = Modifier.padding(dimensionResource(R.dimen.text_margin)),
        )
      }
      if (mensa.state == ViewState.Expanded) {
        Column(modifier = Modifier.fillMaxWidth()) {
          mensa.menus.forEach {
            RowMenu(it)
            HorizontalDivider()
          }
        }
      }
    }
  }
}

@Preview(widthDp = 400)
@Composable
fun PreviewRowMensa() = RowMensa(
  Mensa(
    id = UUID.randomUUID(),
    title = "Mensa Polyterasse with very long text which should ellipsize",
    mealTime = "11:00 - 14:00",
    url = URI(""),
  ).apply { replaceMenus(listOf(dummyMenu, dummyMenu, dummyMenu, dummyMenu)) },
)

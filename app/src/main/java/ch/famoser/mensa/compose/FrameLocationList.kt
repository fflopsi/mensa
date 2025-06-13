package ch.famoser.mensa.compose

import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ch.famoser.mensa.R
import ch.famoser.mensa.adapters.MensaAdapter
import ch.famoser.mensa.models.Location
import ch.famoser.mensa.models.Mensa
import ch.famoser.mensa.models.Menu.Companion.dummyMenu
import java.net.URI
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FrameLocationList(
  isRefreshing: Boolean,
  onRefresh: () -> Unit,
  modifier: Modifier = Modifier,
  onSettingsClick: () -> Unit = {},
  locations: List<Location>,
) {
  PullToRefreshBox(
    isRefreshing = isRefreshing,
    onRefresh = onRefresh,
    modifier = modifier.fillMaxWidth(),
  ) {
    Column(
      modifier = Modifier
        .padding(top = dimensionResource(R.dimen.card_spacing))
        .fillMaxWidth(),
    ) {
      if (locations.isEmpty()) {
        Text(
          text = stringResource(R.string.no_expanded_canteens),
          modifier = Modifier.align(Alignment.CenterHorizontally),
        )
      } else {
        LazyColumn(
          modifier = Modifier
            .padding(top = dimensionResource(R.dimen.card_spacing))
            .fillMaxWidth(),
        ) {
          items(locations) {
            RowLocation(it)
          }
        }
      }
      Text(
        text = stringResource(R.string.source),
        modifier = Modifier
          .padding(
            end = dimensionResource(R.dimen.text_margin),
            top = dimensionResource(R.dimen.card_spacing_four),
            bottom = dimensionResource(R.dimen.text_margin),
          )
          .align(Alignment.End),
      )
    }
    IconButton(
      onClick = onSettingsClick,
      modifier = Modifier
        .align(Alignment.TopEnd)
        .focusable(),
    ) {
      Icon(Icons.Default.MoreHoriz, stringResource(R.string.settings))
    }
  }
}

@Preview(widthDp = 400)
@Composable
fun PreviewFrameLocationList() = FrameLocationList(
  isRefreshing = false,
  onRefresh = {},
  locations = listOf(
    Location(
      title = "Zentrum",
      mensas = listOf(
        Mensa(
          id = UUID.randomUUID(),
          title = "Mensa Polyterasse with very long text which should ellipsize",
          mealTime = "11:00 - 14:00",
          url = URI(""),
        ).apply { replaceMenus(listOf(dummyMenu, dummyMenu, dummyMenu, dummyMenu)) },
        Mensa(
          id = UUID.randomUUID(),
          title = "Mensa Polyterasse with very long text which should ellipsize",
          mealTime = "11:00 - 14:00",
          url = URI(""),
        ).apply {
          replaceMenus(listOf(dummyMenu, dummyMenu, dummyMenu, dummyMenu))
          state = MensaAdapter.ViewState.Expanded
        },
        Mensa(
          id = UUID.randomUUID(),
          title = "Mensa Polyterasse with very long text which should ellipsize",
          mealTime = "11:00 - 14:00",
          url = URI(""),
        ).apply { replaceMenus(listOf(dummyMenu, dummyMenu, dummyMenu, dummyMenu)) },
      ),
    ),
  ),
)

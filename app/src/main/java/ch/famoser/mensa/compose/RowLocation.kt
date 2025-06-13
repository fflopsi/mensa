package ch.famoser.mensa.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import ch.famoser.mensa.R
import ch.famoser.mensa.adapters.MensaAdapter
import ch.famoser.mensa.models.Location
import ch.famoser.mensa.models.Mensa
import ch.famoser.mensa.models.Menu.Companion.dummyMenu
import java.net.URI
import java.util.UUID

@Composable
fun RowLocation(
  location: Location,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier
//      .padding(bottom = dimensionResource(R.dimen.card_spacing_four))
      .fillMaxWidth(),
  ) {
    Text(
      text = location.title,
      color = MaterialTheme.colorScheme.onBackground, // TODO: This should not be required manually
      modifier = Modifier.padding(
        start = dimensionResource(R.dimen.card_spacing),
        end = dimensionResource(R.dimen.card_spacing),
        bottom = dimensionResource(R.dimen.card_spacing_half),
      ),
    )
    Column(modifier = Modifier.fillMaxWidth()) {
      location.mensas.forEach {
        RowMensa(it)
      }
    }
  }
}

@Preview(widthDp = 400)
@Composable
fun PreviewRowLocation() = RowLocation(
  location = Location(
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
  )
)

package ch.famoser.mensa.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import ch.famoser.mensa.adapters.MensaAdapter
import ch.famoser.mensa.models.Location
import ch.famoser.mensa.models.Mensa
import ch.famoser.mensa.models.Menu.Companion.dummyMenu
import ch.famoser.mensa.repositories.LocationRepository
import java.net.URI
import java.util.UUID

@Composable
fun MainScreen(
  locations: List<Location>,
  modifier: Modifier = Modifier,
  onRefresh: () -> Unit = {},
) {
  Scaffold { innerPadding ->
    Box(
      modifier = modifier
        .consumeWindowInsets(innerPadding)
        .padding(innerPadding),
    ) {
      val context = LocalContext.current
      LinearProgressIndicator(
        progress = { 0.5f },
        modifier = Modifier.fillMaxWidth(),
      )
      FrameLocationList(
        isRefreshing = LocationRepository.getInstance(context).refreshActive(),
        onRefresh = onRefresh,
        locations = locations,
      )
    }
  }
}

@Preview(widthDp = 400)
@Composable
fun PreviewMainActivity() = MainScreen(
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

package ch.famoser.mensa.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ch.famoser.mensa.models.Menu
import ch.famoser.mensa.models.dummyMenu

@Composable
fun MensaDetail(
  menus: List<Menu>,
  modifier: Modifier = Modifier,
) {
  // LazyColumn not possible right now because of integration with xml views
  Column(
    modifier = modifier
      .padding(vertical = 4.dp)
      .fillMaxWidth()
  ) {
    menus.forEach {
      RowMenuDetails(it)
    }
  }
}

@Preview(widthDp = 400)
@Composable
fun PreviewMensaDetail() = MensaDetail(listOf(dummyMenu, dummyMenu, dummyMenu))

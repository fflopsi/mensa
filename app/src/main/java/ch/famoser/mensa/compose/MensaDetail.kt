package ch.famoser.mensa.compose

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ch.famoser.mensa.models.Menu
import ch.famoser.mensa.models.Menu.Companion.dummyMenu

@Composable
fun MensaDetail(
  menus: List<Menu>,
  modifier: Modifier = Modifier,
  listState: LazyListState = rememberLazyListState(),
  endSpacer: Boolean = false,
) {
  LazyColumn(
    state = listState,
    modifier = modifier
      .padding(vertical = 4.dp)
      .fillMaxWidth(),
  ) {
    items(menus) { RowMenuDetails(it) }
    if (endSpacer) item { Spacer(Modifier.height(76.dp)) }
  }
}

@Preview(widthDp = 400)
@Composable
fun PreviewMensaDetail() = MensaDetail(listOf(dummyMenu, dummyMenu, dummyMenu))

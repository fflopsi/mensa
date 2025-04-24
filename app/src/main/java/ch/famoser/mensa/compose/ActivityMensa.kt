package ch.famoser.mensa.compose

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.lerp
import ch.famoser.mensa.R
import ch.famoser.mensa.activities.MainActivity
import ch.famoser.mensa.models.Mensa
import ch.famoser.mensa.models.dummyMenu
import java.io.FileNotFoundException
import java.net.URI
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityMensa(mensa: Mensa, modifier: Modifier = Modifier) {
  val context = LocalContext.current
  val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
  val barHeight = lerp(
    TopAppBarDefaults.LargeAppBarExpandedHeight,
    TopAppBarDefaults.LargeAppBarCollapsedHeight,
    scrollBehavior.state.collapsedFraction,
  )
  val lazyListState = rememberLazyListState()

  Scaffold(
    topBar = {
      if (mensa.imagePath != null) {
        var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
        try {
          imageBitmap =
            BitmapFactory.decodeStream(context.assets.open(mensa.imagePath)).asImageBitmap()
        } catch (_: FileNotFoundException) {
        }
        if (imageBitmap != null) {
          Image(
            bitmap = imageBitmap!!,
            contentDescription = mensa.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
              .height(barHeight - 1.dp) // Avoid weird shimmering of some random 1px line
              .fillMaxWidth(),
          )
        }
      }
      LargeTopAppBar(
        title = { Text(mensa.title) },
        navigationIcon = {
          IconButton(
            onClick = {
              (context as Activity).navigateUpTo(Intent(context, MainActivity::class.java))
            },
          ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, stringResource(R.string.back))
          }
        },
        colors = TopAppBarDefaults.topAppBarColors(
          containerColor = MaterialTheme.colorScheme.background.copy(alpha = 0.75f)
        ),
        scrollBehavior = scrollBehavior,
      )
    },
    floatingActionButton = {
      ExtendedFloatingActionButton(
        text = { Text(text = stringResource(R.string.open_in_browser)) },
        icon = { Icon(Icons.Default.OpenInBrowser, stringResource(R.string.open_in_browser)) },
        expanded = scrollBehavior.state.collapsedFraction != 1f || !lazyListState.canScrollForward,
        onClick = {
          val i = Intent(Intent.ACTION_VIEW)
          i.data = Uri.parse(mensa.url.toString())
          context.startActivity(i)
        },
      )
    },
    floatingActionButtonPosition = FabPosition.Center,
    modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .consumeWindowInsets(innerPadding)
        .padding(innerPadding),
    ) {
      MensaDetail(
        menus = mensa.menus,
        endSpacer = true,
        listState = lazyListState,
      )
    }
  }
}

@Preview(heightDp = 500)
@Composable
fun PreviewActivityMensa() = MensaTheme {
  ActivityMensa(Mensa(
    id = UUID.randomUUID(),
    title = "Mensa Polyterasse",
    mealTime = "immer",
    url = URI("a"),
    imagePath = "eth/images/mensa-polyterrasse.jpg",
  ).apply { replaceMenus(listOf(dummyMenu, dummyMenu, dummyMenu, dummyMenu)) })
}

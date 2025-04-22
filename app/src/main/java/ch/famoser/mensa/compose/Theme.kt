package ch.famoser.mensa.compose

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import ch.famoser.mensa.R

@Composable
fun MensaTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = true,
  content: @Composable () -> Unit,
) {
  val context = LocalContext.current
  val colorScheme = when {
    dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
      if (darkTheme) dynamicDarkColorScheme(context)
      else dynamicLightColorScheme(context)
    }

    darkTheme -> mensaDarkColorScheme()
    else -> mensaLightColorScheme()
  }

  MaterialTheme(
    colorScheme = colorScheme,
    content = content,
  )
}

@Composable
fun mensaLightColorScheme(): ColorScheme {
  return lightColorScheme(
    primary = colorResource(R.color.md_theme_light_primary),
    onPrimary = colorResource(R.color.md_theme_light_onPrimary),
    primaryContainer = colorResource(R.color.md_theme_light_primaryContainer),
    onPrimaryContainer = colorResource(R.color.md_theme_light_onPrimaryContainer),
    secondary = colorResource(R.color.md_theme_light_secondary),
    onSecondary = colorResource(R.color.md_theme_light_onSecondary),
    secondaryContainer = colorResource(R.color.md_theme_light_secondaryContainer),
    onSecondaryContainer = colorResource(R.color.md_theme_light_onSecondaryContainer),
    tertiary = colorResource(R.color.md_theme_light_tertiary),
    onTertiary = colorResource(R.color.md_theme_light_onTertiary),
    tertiaryContainer = colorResource(R.color.md_theme_light_tertiaryContainer),
    onTertiaryContainer = colorResource(R.color.md_theme_light_onTertiaryContainer),
    error = colorResource(R.color.md_theme_light_error),
    onError = colorResource(R.color.md_theme_light_onError),
    errorContainer = colorResource(R.color.md_theme_light_errorContainer),
    onErrorContainer = colorResource(R.color.md_theme_light_onErrorContainer),
    background = colorResource(R.color.md_theme_light_background),
    onBackground = colorResource(R.color.md_theme_light_onBackground),
    surface = colorResource(R.color.md_theme_light_surface),
    onSurface = colorResource(R.color.md_theme_light_onSurface),
    surfaceVariant = colorResource(R.color.md_theme_light_surfaceVariant),
    onSurfaceVariant = colorResource(R.color.md_theme_light_onSurfaceVariant),
    outline = colorResource(R.color.md_theme_light_outline),
    inverseSurface = colorResource(R.color.md_theme_light_inverseSurface),
    inverseOnSurface = colorResource(R.color.md_theme_light_inverseOnSurface),
    inversePrimary = colorResource(R.color.md_theme_light_inversePrimary),
    surfaceTint = colorResource(R.color.md_theme_light_surfaceTint),
    outlineVariant = colorResource(R.color.md_theme_light_outlineVariant),
    scrim = colorResource(R.color.md_theme_light_scrim),
  )
}

@Composable
fun mensaDarkColorScheme(): ColorScheme {
  return darkColorScheme(
    primary = colorResource(R.color.md_theme_dark_primary),
    onPrimary = colorResource(R.color.md_theme_dark_onPrimary),
    primaryContainer = colorResource(R.color.md_theme_dark_primaryContainer),
    onPrimaryContainer = colorResource(R.color.md_theme_dark_onPrimaryContainer),
    secondary = colorResource(R.color.md_theme_dark_secondary),
    onSecondary = colorResource(R.color.md_theme_dark_onSecondary),
    secondaryContainer = colorResource(R.color.md_theme_dark_secondaryContainer),
    onSecondaryContainer = colorResource(R.color.md_theme_dark_onSecondaryContainer),
    tertiary = colorResource(R.color.md_theme_dark_tertiary),
    onTertiary = colorResource(R.color.md_theme_dark_onTertiary),
    tertiaryContainer = colorResource(R.color.md_theme_dark_tertiaryContainer),
    onTertiaryContainer = colorResource(R.color.md_theme_dark_onTertiaryContainer),
    error = colorResource(R.color.md_theme_dark_error),
    onError = colorResource(R.color.md_theme_dark_onError),
    errorContainer = colorResource(R.color.md_theme_dark_errorContainer),
    onErrorContainer = colorResource(R.color.md_theme_dark_onErrorContainer),
    background = colorResource(R.color.md_theme_dark_background),
    onBackground = colorResource(R.color.md_theme_dark_onBackground),
    surface = colorResource(R.color.md_theme_dark_surface),
    onSurface = colorResource(R.color.md_theme_dark_onSurface),
    surfaceVariant = colorResource(R.color.md_theme_dark_surfaceVariant),
    onSurfaceVariant = colorResource(R.color.md_theme_dark_onSurfaceVariant),
    outline = colorResource(R.color.md_theme_dark_outline),
    inverseSurface = colorResource(R.color.md_theme_dark_inverseSurface),
    inverseOnSurface = colorResource(R.color.md_theme_dark_inverseOnSurface),
    inversePrimary = colorResource(R.color.md_theme_dark_inversePrimary),
    surfaceTint = colorResource(R.color.md_theme_dark_surfaceTint),
    outlineVariant = colorResource(R.color.md_theme_dark_outlineVariant),
    scrim = colorResource(R.color.md_theme_dark_scrim),
  )
}

package ch.famoser.mensa.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import ch.famoser.mensa.compose.ActivityMensa
import ch.famoser.mensa.compose.MensaTheme
import ch.famoser.mensa.fragments.MensaDetailFragment
import ch.famoser.mensa.models.Mensa
import ch.famoser.mensa.repositories.LocationRepository
import java.util.UUID

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a [MainActivity].
 */
class MensaActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    val mensa = loadMensaFromIntent() ?: return

    setContent { MensaTheme { ActivityMensa(mensa) } }
  }

  private fun loadMensaFromIntent(): Mensa? {
    val mensaId = UUID.fromString(intent.getStringExtra(MensaDetailFragment.MENSA_ID))

    val locationRepository = LocationRepository.getInstance(this)
    return locationRepository.getMensa(mensaId)
  }
}

package ch.famoser.mensa.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import ch.famoser.mensa.compose.MensaDetail
import ch.famoser.mensa.compose.MensaTheme
import ch.famoser.mensa.models.Mensa
import ch.famoser.mensa.repositories.LocationRepository
import kotlinx.android.synthetic.main.activity_mensa.toolbar_layout
import java.util.UUID

class MensaDetailFragment : Fragment() {

    /**
     * The dummy content this fragment is presenting.
     */
    private var item: Mensa? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(MENSA_ID)) {
                val mensaId = UUID.fromString(it.getString(MENSA_ID))

                val locationRepository = LocationRepository.getInstance(requireContext())
                item = locationRepository.getMensa(mensaId)

                activity?.toolbar_layout?.title = item?.title
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
      return ComposeView(requireContext()).apply {
        setContent {
          MensaTheme {
            if (item != null) {
              MensaDetail(item!!.menus)
            }
          }
        }
      }
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val MENSA_ID = "item_id"
    }
}

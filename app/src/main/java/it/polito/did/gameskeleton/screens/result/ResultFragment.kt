/*package it.polito.did.gameskeleton.screens.result


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import it.polito.did.gameskeleton.ui.theme.GameSkeletonTheme
import com.example.did_app.R

class ResultFragment : Fragment() {

    private val args: ResultFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                GameSkeletonTheme(team = "" ){
                    Surface(color = MaterialTheme.colors.background) {
                        Column(
                            Modifier
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .padding(16.dp)
                        ) {
                            Text(
                                text = getString(args.rightAnswers, args.totalQuestions),
                                style = MaterialTheme.typography.h4,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 24.dp, horizontal = 16.dp)
                            )

                            // todo: local scoreboard - timesstamp - x/y (ranking by percentage)

                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Button(
                                    onClick = { findNavController().navigate(ResultFragmentDirections.goToWelcome()) }) {
                                    Text(text = stringResource(id = R.string.default_web_client_id))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}*/
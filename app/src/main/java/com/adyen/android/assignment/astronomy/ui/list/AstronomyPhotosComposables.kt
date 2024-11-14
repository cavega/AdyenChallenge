package com.adyen.android.assignment.astronomy.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.adyen.android.assignment.R
import com.adyen.android.assignment.astronomy.model.AstronomyItemUiModel
import com.adyen.android.assignment.astronomy.ui.common.AdyenAppBar
import com.adyen.android.assignment.astronomy.ui.common.ErrorComposable
import com.adyen.android.assignment.astronomy.ui.common.LoadingComposable
import com.adyen.android.assignment.astronomy.uistate.AstronomyPhotosListUiState
import java.time.format.DateTimeFormatter

private const val IMAGE_SIZE: Int = 64
private const val IMAGE_RADIUS = IMAGE_SIZE / 2

@Composable
fun AstronomyPhotosScreen(
    navController: NavController,
    viewModel: AstronomyPhotosViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.observeAsState(initial = AstronomyPhotosListUiState.Loading)

    Scaffold { innerPadding ->
        Box {
            when (uiState) {
                is AstronomyPhotosListUiState.Loading -> LoadingComposable()

                is AstronomyPhotosListUiState.DataLoaded -> AstronomyPhotosComposable(
                    itemList = (uiState as AstronomyPhotosListUiState.DataLoaded).items,
                    modifier = Modifier.padding(innerPadding),
                    onClick = { itemId ->
                        // TODO: Create destination for item detail screen
                    }
                )

                is AstronomyPhotosListUiState.Error -> {
                    ErrorComposable(
                        modifier = Modifier.padding(innerPadding),
                        stringResource(R.string.astronomy_photos_error_title),
                        stringResource(R.string.astronomy_photos_error_subtitle)
                    )
                }
            }
        }
    }
}

@Composable
fun ItemCard(
    modifier: Modifier = Modifier,
    item: AstronomyItemUiModel,
    onClick: () -> Unit = {}
) {

    val dateString = DateTimeFormatter.ofPattern("MM/dd/yyyy").format(item.date)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                // TODO: Navigate to detail screen
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            modifier = Modifier
                .width(IMAGE_SIZE.dp)
                .height(IMAGE_SIZE.dp)
                .clip(RoundedCornerShape(IMAGE_RADIUS.dp)),
            model = item.url,
            contentDescription = "Astronomy photo",
            placeholder = painterResource(R.drawable.photo_placeholder_24),
            error = painterResource(R.drawable.photo_placeholder_24),
            contentScale = ContentScale.FillBounds
        )
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = item.title,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Start),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(top = 4.dp),
                text = dateString,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AstronomyPhotosComposable(
    itemList: List<AstronomyItemUiModel>,
    modifier: Modifier,
    onClick: (String) -> Unit = {}
) {
    val appBarTitle = stringResource(R.string.astronomy_photos_title)
    //val contentPadding = PaddingValues(8.dp)

    Column {
        AdyenAppBar(
            title = appBarTitle,
        )
        LazyColumn {
            items(itemList) { item ->
                ItemCard(
                    item = item,
                    onClick = {
                        // TODO: bubble up event
                    }
                )
            }
        }
    }
}

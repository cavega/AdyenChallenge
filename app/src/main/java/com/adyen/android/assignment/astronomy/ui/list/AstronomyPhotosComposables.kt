package com.adyen.android.assignment.astronomy.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
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
import com.adyen.android.assignment.astronomy.ui.list.AstronomyPhotosViewModel.SortType
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
                    onItemClick = { itemId ->
                        // TODO: Create destination for item detail screen
                    },
                    onSortChange = { sortType ->
                        viewModel.sortPhotos(sortType)
                    }
                )

                is AstronomyPhotosListUiState.Error -> {
                    ErrorComposable(
                        modifier = Modifier.padding(innerPadding),
                        title = stringResource(R.string.astronomy_photos_error_title),
                        subtitle = stringResource(R.string.astronomy_photos_error_subtitle),
                        onRefreshClick = {
                            viewModel.loadCollection()
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ItemPhotoCard(
    modifier: Modifier = Modifier,
    item: AstronomyItemUiModel,
    onClick: () -> Unit = {}
) {

    val dateString = DateTimeFormatter.ofPattern("MM/dd/yyyy").format(item.date)
    val photoCardTextColor = MaterialTheme.colorScheme.onSurface
    val photoCardPlaceholder = painterResource(R.drawable.photo_placeholder_24)
    val photoCardRowPadding = PaddingValues(start = 24.dp, top = 8.dp, bottom = 8.dp, end = 24.dp)
    val textStartPadding = PaddingValues(start = 4.dp)

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(photoCardRowPadding)
            .clickable { onClick },
        verticalAlignment = Alignment.CenterVertically,
    ) {

        AsyncImage(
            modifier = Modifier
                .width(IMAGE_SIZE.dp)
                .height(IMAGE_SIZE.dp)
                .clip(RoundedCornerShape(IMAGE_RADIUS.dp)),
            model = item.url,
            contentDescription = "Astronomy photo",
            placeholder = photoCardPlaceholder,
            error = photoCardPlaceholder,
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
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(textStartPadding),
                fontSize = 16.sp,
                color = photoCardTextColor,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Text(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(textStartPadding),
                text = dateString,
                fontSize = 14.sp,
                color = photoCardTextColor,
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
    onItemClick: (String) -> Unit = {},
    onSortChange: (SortType) -> Unit = {}
) {
    val appBarTitle = stringResource(R.string.astronomy_photos_title)
    val reorderFabTitle =
        stringResource(R.string.astronomy_photos_reoder_list)

    var showDialog by remember { mutableStateOf(false) }

    Column {
        AdyenAppBar(
            title = appBarTitle,
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            text = stringResource(R.string.astronomy_photos_sort_latest),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
        Box {
            LazyColumn {
                items(itemList) { item ->
                    ItemPhotoCard(
                        item = item,
                        onClick = {
                            // TODO: Propagate click to parent (i.e. show detail screen)
                            //   Must determine what property to use since item has no id
                        }
                    )
                }
            }
            // TODO 1: We could add a scroll listener so that the FAB is hidden when scrolling
            // TODO 2: Abstract this away in a resuable composable wrapper function
            //      and move to SharedUiComposable file
            ExtendedFloatingActionButton(
                text = { reorderFabTitle },
                icon = {
                    Icon(
                        imageVector = ImageVector.vectorResource(R.drawable.ic_reorder),
                        contentDescription = null
                    )
                },
                onClick = { showDialog = true },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(
                        paddingValues = PaddingValues(
                            end = 16.dp,
                            bottom = 32.dp
                        )
                    )
            )

            // TODO: Review why the dialog composable is getting dismissed upon rotation
            PhotoOrderDialogComposable(
                showDialog = showDialog,
                onConfirm = { sortType ->
                    onSortChange(sortType)
                    showDialog = false
                },
                onDismiss = { showDialog = false }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PhotoOrderDialogComposable(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: (SortType) -> Unit
) {
    var selectedSortOrder by remember { mutableStateOf<SortType>(SortType.Latest) }

    if (!showDialog) return

    // TODO: The Text for each sorting options should have the typography defined at the theme level
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = stringResource(R.string.astronomy_photos_reoder_list)) },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(R.string.astronomy_photos_order_by_title)
                    )
                    RadioButton(
                        selected = selectedSortOrder == SortType.Title,
                        onClick = {
                            selectedSortOrder =
                                SortType.Title
                        }
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = stringResource(R.string.astronomy_photos_order_by_date))
                    RadioButton(
                        selected = selectedSortOrder == SortType.Latest,
                        onClick = {
                            selectedSortOrder =
                                SortType.Latest
                        }
                    )
                }
            }
        },
        /**
         * AlertDialog doesn't support stack buttons. As a workaround, we
         * can use a Column and group the buttons under the confirmButton
         */
        confirmButton = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = {
                    onConfirm(selectedSortOrder)
                    onDismiss()
                }) {
                    Text(text = stringResource(R.string.astronomy_photos_order_apply))
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = {
                    onDismiss()
                }) {
                    Text(text = stringResource(R.string.astronomy_photos_order_reset))
                }
            }
        },
    )
}
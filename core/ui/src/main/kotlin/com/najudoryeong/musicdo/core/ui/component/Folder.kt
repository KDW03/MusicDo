package com.najudoryeong.musicdo.core.ui.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.pluralStringResource
import com.najudoryeong.musicdo.core.designsystem.componenet.DoCard
import com.najudoryeong.musicdo.core.designsystem.componenet.SingleLineText
import com.najudoryeong.musicdo.core.designsystem.theme.spacing
import com.najudoryeong.musicdo.core.model.Folder
import com.najudoryeong.musicdo.core.ui.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun Folders(
    folders: List<Folder>,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    if (folders.isNotEmpty()) {
        LazyColumn(modifier = modifier.fillMaxSize()) {
            // items : folders , key : Folder.name
            items(items = folders, key = Folder::name) { folder ->
                FolderItem(
                    modifier = Modifier.animateItemPlacement(),
                    folder = folder,
                    onClick = { onClick(folder.name) }
                )
            }
        }
    } else {
        EmptyContent(textResource = R.string.no_folders)
    }
}

@Composable
private fun FolderItem(
    folder: Folder,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    DoCard(modifier = modifier, onClick = onClick) {
        Column(
            modifier = Modifier
                .padding(MaterialTheme.spacing.small)
                .fillMaxWidth()
        ) {

            // Folder Name Text
            SingleLineText(
                text = folder.name,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            // Folder Songs size
            SingleLineText(
                text = pluralStringResource(
                    id = R.plurals.number_of_songs,
                    count = folder.songs.size,
                    folder.songs.size
                ),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

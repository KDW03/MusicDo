package com.najudoryeong.musicdo.core.domain.repository

import com.najudoryeong.musicdo.core.model.Album
import com.najudoryeong.musicdo.core.model.Artist
import com.najudoryeong.musicdo.core.model.Folder
import com.najudoryeong.musicdo.core.model.Song
import kotlinx.coroutines.flow.Flow

/**
 * interface for MediaRepository
 */
interface MediaRepository {
    val songs: Flow<List<Song>>
    val artists: Flow<List<Artist>>
    val albums: Flow<List<Album>>
    val folders: Flow<List<Folder>>
}

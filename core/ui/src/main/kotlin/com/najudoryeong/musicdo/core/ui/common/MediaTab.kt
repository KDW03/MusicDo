package com.najudoryeong.musicdo.core.ui.common

import androidx.annotation.StringRes
import com.najudoryeong.musicdo.core.ui.R

/**
 *  음악 앱의 주요 카테고리를 나타내는 탭 enum class
 */
internal enum class MediaTab(@StringRes val titleResource: Int) {
    Songs(titleResource = R.string.songs),
    Artists(titleResource = R.string.artists),
    Albums(titleResource = R.string.albums),
    Folders(titleResource = R.string.folders)
}

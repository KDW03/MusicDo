package com.najudoryeong.musicdo.feature.library.model

enum class LibraryType(val value: String) {
    Artist(LIBRARY_TYPE_ARTIST),
    Album(LIBRARY_TYPE_ALBUM),
    Folder(LIBRARY_TYPE_FOLDER);

    companion object {
        private val libraryTypes = values().associateBy(LibraryType::value)
        operator fun get(value: String) =
            checkNotNull(libraryTypes[value]) { "Invalid library type $value." }
    }
}

private const val LIBRARY_TYPE_ARTIST = "artist"
private const val LIBRARY_TYPE_ALBUM = "album"
private const val LIBRARY_TYPE_FOLDER = "folder"
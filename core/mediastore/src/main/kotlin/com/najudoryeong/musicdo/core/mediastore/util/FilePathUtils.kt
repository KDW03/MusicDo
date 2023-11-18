package com.najudoryeong.musicdo.core.mediastore.util

import java.io.File

/**
 * FilePath를 받아 해당 폴더 이름 반환 함수
 */
internal fun String.asFolder() = File(this).parentFile?.name.orEmpty()

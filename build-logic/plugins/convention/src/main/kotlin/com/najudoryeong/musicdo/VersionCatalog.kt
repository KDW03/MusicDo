package com.najudoryeong.musicdo

import org.gradle.accessors.dm.LibrariesForLibs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.the


/**
 * 버전 카탈로그 접근을 위한 확장함수
 */
internal val Project.libs get() = the<LibrariesForLibs>()

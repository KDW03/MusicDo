/*
 * Copyright 2023 KDW03
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.najudoryeong.musicdo.core.designsystem.componenet

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow

/**
 * 단일 줄 텍스트를 표시하는 @Composable
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SingleLineText(
    text: String,
    modifier: Modifier = Modifier,
    shouldUseMarquee: Boolean = false,
    color: Color = Color.Unspecified,
    style: TextStyle = LocalTextStyle.current,
) {
    Text(
        // shouldUseMarquee가 true이면 if Text가 넘칠 때 스크롤 효과(Marquee) 적용
        modifier = modifier.then(if (shouldUseMarquee) Modifier.basicMarquee() else Modifier),
        text = text,
        color = color,
        // 넘칠 경우 ... 처리
        overflow = TextOverflow.Ellipsis,
        // 한줄
        maxLines = 1,
        style = style,
    )
}

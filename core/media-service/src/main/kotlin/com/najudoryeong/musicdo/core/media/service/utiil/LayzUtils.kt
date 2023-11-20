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

package com.najudoryeong.musicdo.core.media.service.utiil

/**
 *  쓰레드 안전성을 보장하지 않는 lazy 구현
 *  MusicService에서 사용되는 CoroutineScope는 주로 메인 스레드에서만 사용
 *
 *  따라서, CoroutineScope의 지연 초기화를 위해 unsafeLazy를 사용
 *  동기화 오버헤드가 없기 때문에 성능상의 이점을 제공
 */
fun <T> unsafeLazy(initializer: () -> T) =
    lazy(mode = LazyThreadSafetyMode.NONE, initializer = initializer)

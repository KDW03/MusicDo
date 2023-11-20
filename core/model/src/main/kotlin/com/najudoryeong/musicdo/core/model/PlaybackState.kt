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

package com.najudoryeong.musicdo.core.model

/**
 * 재생 상태
 *
 * IDLE : 초기 및 정지
 * BUFFERING : 버퍼링
 * READY : 재생 준비 완료
 * ENDED : 재생 종료
 *
 */
enum class PlaybackState { IDLE, BUFFERING, READY, ENDED }
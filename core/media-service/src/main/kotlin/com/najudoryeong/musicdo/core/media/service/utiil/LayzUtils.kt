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

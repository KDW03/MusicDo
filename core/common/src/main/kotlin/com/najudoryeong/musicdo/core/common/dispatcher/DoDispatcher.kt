package com.najudoryeong.musicdo.core.common.dispatcher

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val doDispatcher: DoDispatchers)

enum class DoDispatchers { DEFAULT, MAIN, UNCONFINED, IO }

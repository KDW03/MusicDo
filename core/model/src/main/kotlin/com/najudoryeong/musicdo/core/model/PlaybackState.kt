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

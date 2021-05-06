@file:Suppress("unused")

package com.nandaprasetio.core.domain.entity.video

class DetailVideoWrapper<T: BaseVideo>(
    val video: BaseVideo,
    val isFavourited: Boolean
)
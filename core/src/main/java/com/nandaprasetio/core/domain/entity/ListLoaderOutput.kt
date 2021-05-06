package com.nandaprasetio.core.domain.entity

class ListLoaderOutput<out T> (
    val resultList: List<T>,
    val prevKey: Int?,
    val nextKey: Int?
)
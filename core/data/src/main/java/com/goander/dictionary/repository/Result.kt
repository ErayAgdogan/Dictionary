package com.goander.dictionary.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

sealed interface Result<out T> {
    class Success<T>(val result: T): Result<T>
    object Loading: Result<Nothing>
}

public fun <T> Flow<T>.asResult():Flow<Result<T>> =
    map { Result.Success(it) as Result<T> }
    .onStart { emit(Result.Loading) }

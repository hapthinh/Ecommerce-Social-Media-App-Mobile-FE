package com.example.utilities

sealed class ResourceState <T> {
    class Loading<T> : ResourceState<T>()
    data class Success<T> (val data : T) : ResourceState<T>()
    data class Failure<T> (val data : T) : ResourceState<T>()
}
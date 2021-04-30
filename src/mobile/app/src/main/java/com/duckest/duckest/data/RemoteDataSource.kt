package com.duckest.duckest.data

import com.duckest.duckest.data.network.DuckestApi
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val duckestApi: DuckestApi
) {

}
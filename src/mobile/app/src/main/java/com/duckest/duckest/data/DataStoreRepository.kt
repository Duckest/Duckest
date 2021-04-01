package com.duckest.duckest.data

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import javax.inject.Inject

@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

}
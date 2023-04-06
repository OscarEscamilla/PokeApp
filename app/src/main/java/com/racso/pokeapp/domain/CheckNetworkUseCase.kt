package com.racso.pokeapp.domain

import com.racso.pokeapp.core.InternetCheck
import javax.inject.Inject

class CheckNetworkUseCase{
    suspend operator fun invoke(): Boolean {
        return InternetCheck.isNetworkAvailable()
    }
}
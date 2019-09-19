package com.moustafa.countrypicker.repository

import com.moustafa.countrypicker.repository.Repository
import com.moustafa.countrypicker.repository.network.RestCountriesService
import retrofit2.Response

/**
 * This repository is our source of truth, we fetch all the data using this repository
 * It may fetch the required data from the network layer or from the local db layer
 *
 * @author moustafasamhoury
 * created on Thursday, 19 Sep, 2019
 */
class RepositoryImpl(private val service: RestCountriesService) : Repository
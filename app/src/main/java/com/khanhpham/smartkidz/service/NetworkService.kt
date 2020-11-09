package com.khanhpham.smartkidz.service

import com.khanhpham.smartkidz.api_endpoint.HistoryApi
import javax.inject.Inject

class NetworkService {

    @Inject
    lateinit var historyApi: HistoryApi

}
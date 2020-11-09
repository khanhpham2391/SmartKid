package com.khanhpham.smartkidz.repository

import com.khanhpham.smartkidz.data.models.AppUser
import com.khanhpham.smartkidz.data.models.GameDetails
import com.khanhpham.smartkidz.data.models.Games
import com.khanhpham.smartkidz.data.models.History
import io.reactivex.Single

interface SmartKidRepository {

    fun getAppUsers(): Single<List<AppUser>>

    fun getAppUser(id: Int): Single<AppUser>

    fun getGames(): Single<List<Games>>

    fun createUser(appUser: AppUser): Single<AppUser>

    fun getGamePlay(gameId: Int, topicId: Int, difficultyId: Int): Single<List<GameDetails>>

    fun createHistory(history: History): Single<History>

    fun getHistory(): Single<List<History>>
}
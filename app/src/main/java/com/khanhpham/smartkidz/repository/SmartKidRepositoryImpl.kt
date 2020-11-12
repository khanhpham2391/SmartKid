package com.khanhpham.smartkidz.repository

import android.util.Log
import com.khanhpham.smartkidz.api_endpoint.AppUserServiceApi
import com.khanhpham.smartkidz.data.GamesData
import com.khanhpham.smartkidz.data.models.*
import io.reactivex.Single

class SmartKidRepositoryImpl(private val serviceApi: AppUserServiceApi) : SmartKidRepository {
    override fun getAppUsers(): Single<List<AppUser>> {
        return serviceApi.getAppUsers()
    }

    override fun getAppUser(id: Int): Single<AppUser> {
        return serviceApi.getAppUser(id)
    }

    override fun getGames(): Single<List<Games>> {
        return serviceApi.getGames()
    }

    override fun createUser(appUser: AppUser): Single<AppUser> {
        return serviceApi.createUser(appUser)
    }

    override fun getGamePlay(gameId: Int, topicId: Int, difficultyId: Int): Single<List<GameDetails>> {
        return serviceApi.getGamePlay(gameId,topicId,difficultyId)
    }

    override fun createHistory(history: History): Single<AppUser> {
        return serviceApi.createHistory(history)
    }

    override fun getHistory(): Single<List<History>> {
        return serviceApi.getHistory(GamesData.user.id!!)
    }

    override fun getIcon(): Single<List<IconImage>> {
        return serviceApi.getIcon()
    }

    override fun getPosition(userId: Int): Single<Map<Int, AppUser>> {
        return serviceApi.getPosition(userId)
    }


}
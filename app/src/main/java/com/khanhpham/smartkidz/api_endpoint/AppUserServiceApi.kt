package com.khanhpham.smartkidz.api_endpoint

import com.khanhpham.smartkidz.data.GamesData
import com.khanhpham.smartkidz.data.models.AppUser
import com.khanhpham.smartkidz.data.models.GameDetails
import com.khanhpham.smartkidz.data.models.Games
import com.khanhpham.smartkidz.data.models.History
import io.reactivex.Single
import retrofit2.http.*

interface AppUserServiceApi {
    @GET("users")
    fun getAppUsers(): Single<List<AppUser>>

    @GET("users/{id}")
    fun getAppUser(@Path("id") id: Int): Single<AppUser>

    @GET("games")
    fun getGames(): Single<List<Games>>

    @POST("users/create")
    fun createUser(@Body appUser: AppUser): Single<AppUser>

    @GET("games/plays")
    fun getGamePlay(@Query("gameId") gameId: Int, @Query("topicId") topicId: Int, @Query("difficultyId") difficultyId: Int): Single<List<GameDetails>>

    @POST("histories")
    fun createHistory(@Body history: History): Single<History>

    @GET("histories")
    fun getHistory(@Query("userId") userId: Int): Single<List<History>>

    companion object{
        val END_POINT_API = GamesData.urlConvert("http://localhost:8080/api/v1/")
    }
}
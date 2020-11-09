package com.khanhpham.smartkidz.data.response

import java.util.*

data class AppUserResponse(
    val createdAt: String?,
    val email: String?,
    val fullname: String?,
    val gender: Boolean,
    val id: Int,
    val isActive: Boolean,
    val isAdmin: Boolean,
    val password: String?,
    val photo: String?,
    val scoreCollection: List<ScoreResponse>,
    val updatedAt: String?,
    val username: String,
    val levelId: LevelResponse
)

data class ScoreResponse(
    val id: Int,
    val score: Int,
    val userId: AppUserResponse,
    val gameId: GamesResponse
)

data class LevelResponse (
    val id: Int,
    val name: String,
    val requiredLevelScore: Int,
    val icon: String,
    val isActive: Boolean,
    val createdAt: String?,
    val updatedAt: String?,
    val usersCollection: Collection<AppUserResponse>
)
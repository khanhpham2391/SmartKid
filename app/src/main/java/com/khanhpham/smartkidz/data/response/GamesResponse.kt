package com.khanhpham.smartkidz.data.response

import java.util.*

data class GamesResponse(
    val bgm: Any,
    val description: String,
    val difficultiesCollection: List<DifficultiesResponse>,
    val id: Int,
    val image: Any,
    val isActive: Boolean,
    val name: String,
    val scoreCollection: List<ScoreResponse>,
    val topicsCollection: List<TopicsResponse>
)

data class DifficultiesResponse(
    val id: Int,
    val isActive: Boolean,
    val name: String,
    val requiredScore: Int,
    val timeLimit: Int,
    val totalQuestion: Int,
    val totalTimePlay: Int
)

data class TopicsResponse(
    val id: Int,
    val name: String,
    val image: String,
    val isActive: Boolean,
    val createdAt: Date,
    val updatedAt: Date,
    val gameId: GamesResponse
)
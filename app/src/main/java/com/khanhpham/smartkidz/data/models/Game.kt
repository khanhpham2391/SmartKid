package com.khanhpham.smartkidz.data.models

import android.os.Parcel
import android.os.Parcelable

data class GameDetails(val id: Int, val gameId: Int, val difficultyId: Int, val topicId: Int, val question: String, val image: String, val correctAnswer: String, val answerOne: String, val answerTwo: String, val answerThree: String, val answerFour: String)

class GameData(var game: Int, var diff: Int, var topic: Int, var gamePosition: Int) : Parcelable {
    constructor(parcel: Parcel) : this (
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    ){}

    override fun writeToParcel(parcel: Parcel, p1: Int) {
            parcel.writeInt(game)
            parcel.writeInt(diff)
            parcel.writeInt(topic)
            parcel.writeInt(gamePosition)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GameData> {
        override fun createFromParcel(parcel: Parcel): GameData {
            return GameData(parcel)
        }

        override fun newArray(size: Int): Array<GameData?> {
            return arrayOfNulls(size)
        }
    }
}

data class Games (val id: Int, val name: String, val description: String, val image: String, val BGM: String, val active: Boolean, val difficultiesCollection: Collection<Difficulty>, val topicsCollection: Collection<Topic>)

data class Difficulty(val id: Int, val name: String, val requiredScore: Int, val timeLimit: Int, val totalTimePlay: Int, val totalQuestion: Int, val active: Boolean)

data class Topic(val id: Int, val name: String, val image: String, val active: Boolean)
package com.khanhpham.smartkidz.data.models

data class AppUser (val id: Int ? = null, val username: String, val fullname: String ? = null, val gender: String?="female", val email: String?=null, val photo: String?=null, val scoreCollection: Collection<Score>?=null, val levelId: Level?=null) {
}

data class Level (val id: Int, val name: String, val requiredLevelScore: Int, val icon: String)

data class Score(val id: Int, val score: Int, val appUser: AppUser, val game: Games)

enum class Gender(val value: Boolean) {
    Male(true),
    Female(false);

    companion object {
        fun find(value: Boolean): Gender {
            return if (value) Male else Female
        }
    }
}

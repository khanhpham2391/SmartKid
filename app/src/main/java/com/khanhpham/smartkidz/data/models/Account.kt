package com.khanhpham.smartkidz.data.models

data class AppUser (val id: Int ? = null, var username: String, var password:String?=null, var fullName: String ? = null, var gender: String?="female", var email: String?=null, var photo: String?=null, val scoreCollection: Collection<UserScore>?=null, val levelId: Level?=null) {
}

data class Level (val id: Int, val name: String, val requiredLevelScore: Int, val icon: String)

data class UserScore(val id: Int, val score: Int, val gameId: Int)

data class IconImage(val id:Int, val url: String)

enum class Gender(val value: Boolean) {
    Male(true),
    Female(false);

    companion object {
        fun find(value: Boolean): Gender {
            return if (value) Male else Female
        }
    }
}

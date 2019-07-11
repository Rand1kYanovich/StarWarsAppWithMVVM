package com.example.starwarsappwithmvvm.model.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "favoriteList")
class FullInfoCard : Serializable {

    @PrimaryKey
    var name: String = ""

    var height: String = ""
    var mass: String = ""

    @SerializedName("hair_color")
    @Expose
    var hairColor: String = ""
    @SerializedName("skin_color")
    @Expose
    var skinColor: String = ""
    @SerializedName("eye_color")
    @Expose
    var eyeColor: String = ""
    @SerializedName("birth_year")
    @Expose
    var birthYear: String = ""

    var gender: String = ""
    var color: String = ""
    var isFavorite = false


}
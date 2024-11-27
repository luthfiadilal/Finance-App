package com.my.dailycashflow.data


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "category")
@Parcelize
data class Category(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "limit")
    val limit: Int = 0,

    @ColumnInfo(name = "type")
    val type: String,
) : Parcelable{
    override fun toString(): String {
        return name
    }
}
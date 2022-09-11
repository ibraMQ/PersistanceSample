package com.example.persistencia

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = User.table_name)
data class User (
    @PrimaryKey
    @NotNull
    @ColumnInfo(name="name") val name: String,
    @ColumnInfo(name="address") val address: String,
    @ColumnInfo(name="sex") val sex: String){

    companion object{
        const val table_name = "user"
    }

}

//Todo: add primary key and migration
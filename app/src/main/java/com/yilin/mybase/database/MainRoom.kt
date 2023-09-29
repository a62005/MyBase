package com.yilin.mybase.database

import android.content.Context
import androidx.databinding.adapters.Converters
import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.yilin.mybase.MyApp

@Database(
    entities = [Test::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RoomConverters::class)
abstract class MainRoom : RoomDatabase() {

    companion object {
        @Volatile
        private var instance: MainRoom? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context,
            MainRoom::class.java, "zshl_main_room.db"
        ).build()
    }

    abstract fun getRoomDao(): MainRoomDao
}

@Entity(tableName = "test")
data class Test(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String
)

object RoomConverters : Converters() {

    private val gson: Gson by lazy { MyApp.instance.gson }

    @TypeConverter
    fun stringToList(data: String?): List<String> {
        if (data == null) {
            return emptyList()
        }
        val listType = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun listToString(list: List<String>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun stringToIntList(data: String?): List<Int> {
        if (data == null) {
            return emptyList()
        }
        val listType = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun intListToString(list: List<Int>): String {
        return gson.toJson(list)
    }

}
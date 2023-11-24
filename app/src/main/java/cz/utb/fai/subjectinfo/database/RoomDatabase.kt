package cz.utb.fai.subjectinfo.database

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectInfoDao {
    @Query("select * from subject_info WHERE zkratka = :zkratka")
    fun getSubjectByZkratka(zkratka: String): Flow<SubjectEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert( subject: SubjectEntity)
}


@Database(entities = [SubjectEntity::class], version = 1)
abstract class SubjectInfoDatabase: RoomDatabase() {
    abstract val subjectInfoDao: SubjectInfoDao
}

private lateinit var INSTANCE: SubjectInfoDatabase

fun getDatabase(context: Context): SubjectInfoDatabase {
    synchronized(SubjectInfoDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                SubjectInfoDatabase::class.java,
                "subject_info_database").build()
        }
    }
    return INSTANCE
}
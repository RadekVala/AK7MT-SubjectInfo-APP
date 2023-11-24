package cz.utb.fai.subjectinfo

import android.util.Log
import cz.utb.fai.subjectinfo.api.StagApiService
import cz.utb.fai.subjectinfo.database.SubjectEntity
import cz.utb.fai.subjectinfo.database.SubjectInfoDatabase
import cz.utb.fai.subjectinfo.model.SubjectInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class Repository (private val apiService: StagApiService, private val database: SubjectInfoDatabase) {


    // Fetch subject from Room database
    suspend fun getSubjectInfo(katedra: String, zkratka: String): Flow<SubjectInfo> {
        refreshSubjects(katedra, zkratka)
        return database.subjectInfoDao.getSubjectByZkratka(zkratka)
            .map { it.asDomainModel() }
    }

    // Refresh data from API and save to Room database
    suspend fun refreshSubjects(katedra: String, zkratka: String) {
        try {
            val apiResponse = apiService.getSubjectInfo(katedra, zkratka)
            val subjectInfo: SubjectInfo? = apiResponse.body()

            if(subjectInfo != null) {
                // convert domain model from REST API to database entity
                val subjectEntity = SubjectEntity(
                    0,
                    subjectInfo.nazev,
                    subjectInfo.zkratka,
                    subjectInfo.kreditu,
                    subjectInfo.typZkousky
                    )

                // Save to Room database
                withContext(Dispatchers.IO) {
                    database.subjectInfoDao.insert(subjectEntity)
                }
            }

        } catch (e: Exception) {
            // Handle API call errors
            Log.e("SubjectRepository", "Error refreshing subjects" + e.localizedMessage)
        }
    }

}
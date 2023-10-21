package cz.utb.fai.subjectinfo

import cz.utb.fai.subjectinfo.api.StagApiService
import cz.utb.fai.subjectinfo.model.SubjectInfo

class Repository (private val apiService: StagApiService) {

    suspend fun getSubjectInfo (katedra: String, zkratka: String) : SubjectInfo? {
        val response = apiService.getSubjectInfo(katedra, zkratka)

        if(response.isSuccessful) {
            return response.body()
        } else {
            return null
        }
    }
}
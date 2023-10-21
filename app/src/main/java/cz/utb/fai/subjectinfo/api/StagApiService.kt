package cz.utb.fai.subjectinfo.api

import cz.utb.fai.subjectinfo.model.SubjectInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface StagApiService {

    companion object {
        const val SUBJECT_INFO_ENDPOINT = "predmety/getPredmetInfo"
    }

    @GET(SUBJECT_INFO_ENDPOINT)
    suspend fun getSubjectInfo (
        @Query("katedra") katedra: String,
        @Query("zkratka") zkratka: String,
        @Query("outputFormat") outputFormat: String = "json"
    ) : Response<SubjectInfo>
}
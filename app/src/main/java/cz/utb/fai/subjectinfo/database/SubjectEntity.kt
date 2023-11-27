package cz.utb.fai.subjectinfo.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import cz.utb.fai.subjectinfo.model.SubjectInfo

/**
 * SubjectEntity represents a Subject entity in the database.
 */
@Entity(tableName = "subject_info")
data class SubjectEntity constructor(
    //@PrimaryKey(autoGenerate = true) val id: Int = 0,
    @PrimaryKey
    val nazev: String,
    val zkratka: String,
    val kreditu: Int,
    val typZkousky: String
) {

    /**
     * Map Subject info object to domain entity
     */
    fun asDomainModel(): SubjectInfo {
        return SubjectInfo(
            this.nazev,
            this.zkratka,
            this.kreditu,
            this.typZkousky
        )
    }
}
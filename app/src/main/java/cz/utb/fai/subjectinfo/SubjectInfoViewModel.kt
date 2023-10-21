package cz.utb.fai.subjectinfo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.utb.fai.subjectinfo.model.SubjectInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SubjectInfoViewModel(
    private val repository: Repository
) : ViewModel() {

    private val _subjectInfoValue = MutableLiveData<SubjectInfo>()
    val subjectInfoValue: LiveData<SubjectInfo> = _subjectInfoValue

    val zkratkaMutable = MutableLiveData<String>()

    fun getSubjectInfo(katedra: String, zkratka: String){
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getSubjectInfo(katedra, zkratka)
            _subjectInfoValue.postValue(result)
        }
    }

    fun search () {
        val zkratka = zkratkaMutable.value.toString()

        getSubjectInfo("AUIUI", zkratka )
    }
}
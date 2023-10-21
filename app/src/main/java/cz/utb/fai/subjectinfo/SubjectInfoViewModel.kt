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

    val showHint = MutableLiveData<Boolean>()
    val showNotFound = MutableLiveData<Boolean>()
    val zkratkaMutable = MutableLiveData<String?>()



    fun getSubjectInfo(katedra: String, zkratka: String){
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getSubjectInfo(katedra, zkratka)
            if(result is SubjectInfo) {
                _subjectInfoValue.postValue(result)
                showNotFound.postValue(false)
            } else {
                showNotFound.postValue(true)
            }
        }
    }

    fun search () {
        if (zkratkaMutable.value != null && !zkratkaMutable.value!!.isEmpty()) {
            // zkratka was provided by the user
            getSubjectInfo("AUIUI", zkratkaMutable.value!!)
        } else {
            // zkratka was not provided, show hint text view
            showHint.value = true
        }
    }

    fun hideHintAndNotFound () {
        showHint.value = false
        showNotFound.value = false
    }
}
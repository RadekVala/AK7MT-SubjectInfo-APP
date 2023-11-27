package cz.utb.fai.subjectinfo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cz.utb.fai.subjectinfo.model.SubjectInfo
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch


class SubjectInfoViewModel(
    private val repository: Repository
) : ViewModel() {

    // here is the Live data object subjectInfoValue which is showed on UI
    private val _subjectInfoValue = MutableLiveData<SubjectInfo>()
    val subjectInfoValue: LiveData<SubjectInfo> = _subjectInfoValue

    // Live Data for providing the "Zkratka" value for searching for subject
    val zkratkaMutable = MutableLiveData<String?>()

    // Live Data for showing Hint on UI
    val showHint = MutableLiveData<Boolean>()
    // Live Data for showing Not Found message on UI
    val showNotFound = MutableLiveData<Boolean>()
    // Live Data for showing loading progress dialog on UI
    val progress = MutableLiveData<Boolean>()

    init {
        // initial subjectInfoValue object is empty
        _subjectInfoValue.value = null
    }

    fun getSubjectInfo(katedra: String, zkratka: String){
        // repository returns Flow object, need to call repository in viewModelScope.launch
        viewModelScope.launch {
            try {
                // get subjectInfoValue object from repository using firstOrNull (from Flow obj) and pass it to VMs LiveData using .value
                _subjectInfoValue.value = repository.getSubjectInfo(katedra, zkratka).firstOrNull()
                // hide progress dialog
                progress.value = false
            } catch (e: Exception) {
                // in case of exception log it
                Log.v("MYAPP", "Not found: " + e.message)

                // show not found message
                showNotFound.value = true
                // hide progressbar
                progress.value = false
            }
        }

    }

    // Button click event handler
    fun search () {

        if (zkratkaMutable.value != null && !zkratkaMutable.value!!.isEmpty()) {
            // if user provided some "zkratka"
            // show progressbar
            progress.value = true

            // run VMs method subject info ("katedra" is hardcoded here for simplicity)
            getSubjectInfo("AUIUI", zkratkaMutable.value!!)

            // log to console, what we are searching for
            Log.v("MYAPP", "search for zkratka: "+ zkratkaMutable.value!!) // zkratka mutable

        } else {
            // zkratka was not provided, show hint text view
            showHint.value = true
        }
    }

    // hides both hint and not found text
    fun hideHintAndNotFound () {
        showHint.value = false
        showNotFound.value = false
    }
}
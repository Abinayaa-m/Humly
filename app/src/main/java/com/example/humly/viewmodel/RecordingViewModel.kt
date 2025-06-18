package com.example.humly.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.humly.data.db.HumlyDatabase
import com.example.humly.data.model.Recording
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class RecordingViewModel(application: Application) : AndroidViewModel(application) {
    private val recordingDao = HumlyDatabase.getDatabase(application).recordingDao()
    private val _recordings = MutableStateFlow<List<Recording>>(emptyList())
    val recordings: StateFlow<List<Recording>> = _recordings.asStateFlow()

    init {
        loadRecordings()
    }

    private fun loadRecordings() {
        val userId = "guest_user" // Temporary for guest mode
        viewModelScope.launch {
            recordingDao.getRecordingsByUser(userId).collect { recordingList ->
                _recordings.value = recordingList
            }
        }
    }

    fun insertRecording(recording: Recording) {
        viewModelScope.launch {
            recordingDao.insert(recording)
        }
    }

    fun deleteRecording(id: Int) {
        viewModelScope.launch {
            recordingDao.delete(id)
        }
    }
}
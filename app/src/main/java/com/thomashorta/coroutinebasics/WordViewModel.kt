package com.thomashorta.coroutinebasics

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thomashorta.coroutinebasics.data.WordException
import com.thomashorta.coroutinebasics.domain.GetWordListUseCase
import com.thomashorta.coroutinebasics.model.Word
import kotlinx.coroutines.launch

class WordViewModel(
    private val getWordList: GetWordListUseCase
): ViewModel() {
    private val _viewStateLiveData = MutableLiveData<ViewState>()
    val viewStateLiveData: LiveData<ViewState> = _viewStateLiveData

    fun fetchWords() {
        _viewStateLiveData.value = ViewState.Loading
        viewModelScope.launch {
            try {
                _viewStateLiveData.postValue(ViewState.Success(getWordList()))
            } catch (e: WordException) {
                _viewStateLiveData.postValue(ViewState.Error(e.message))
            }
        }
    }

    sealed class ViewState {
        object Loading : ViewState()
        class Success(val words: List<Word>): ViewState()
        class Error(val message: String): ViewState()
    }
}
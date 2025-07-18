package br.com.seucaio.cryptoexchanges.ui.screen.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.seucaio.cryptoexchanges.domain.usecase.GetExchangesUseCase
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ExchangeListViewModel(
    private val getExchangesUseCase: GetExchangesUseCase
) : ViewModel() {
    fun getExchanges() {
        viewModelScope.launch {
            getExchangesUseCase()
                .onStart { }
                .catch { }
                .onCompletion {  }
                .collect { }
        }
    }
}
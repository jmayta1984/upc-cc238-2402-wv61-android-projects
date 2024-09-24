package pe.edu.upc.appturismo.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import pe.edu.upc.appturismo.common.Resource
import pe.edu.upc.appturismo.common.UIState
import pe.edu.upc.appturismo.data.repository.PackageRepository
import pe.edu.upc.appturismo.domain.TourPackage

class PackageListViewModel(private val repository: PackageRepository) : ViewModel() {
    private val _state = mutableStateOf(UIState<List<TourPackage>>())
    val state: State<UIState<List<TourPackage>>> get() = _state

    private val _place = mutableStateOf("")
    val place: State<String> get() = _place

    private val _type = mutableStateOf("")
    val type: State<String> get() = _type

    private fun getPackages() {
        _state.value = UIState(isLoading = true)

        viewModelScope.launch {
            val result = repository.getPackages(_place.value, _type.value)

            if (result is Resource.Success) {
                _state.value = UIState(data = result.data)
            } else {
                _state.value = UIState(message = result.message ?: "An error occurred.")
            }

        }
    }

    fun onPlaceChanged(place: String) {
        _place.value = place
        getPackages()
    }

    fun onTypeChange(type: String) {
        _type.value = type
        getPackages()
    }


}
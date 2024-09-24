package pe.edu.upc.appturismo.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pe.edu.upc.appturismo.common.Constants
import pe.edu.upc.appturismo.data.remote.PackageService
import pe.edu.upc.appturismo.data.repository.PackageRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun PackageListScreen(viewModel: PackageListViewModel) {

    val state = viewModel.state.value
    val placeState = viewModel.place.value
    val typeState = viewModel.type.value

    val placeList = listOf(
        Place("S001", "Machu Picchu"),
        Place("S002", "Ayacucho"),
        Place("S003", "Chichen Itza")
    )

    val typeList = listOf(
        Type("1", "Viajes"),
        Type("2", "Hospedajes")
    )


    Scaffold { paddingValues: PaddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {

            LazyRow {
                items(placeList) { place ->
                    FilterChip(
                        modifier = Modifier.padding(4.dp),
                        selected = (place.id == placeState),
                        label = {
                            Text(place.name)
                        },
                        onClick = {
                            viewModel.onPlaceChanged(place.id)

                        })
                }

            }
            Row {
                typeList.forEach { type ->
                    FilterChip(
                        modifier = Modifier.padding(4.dp),
                        selected = (type.id == typeState ),
                        label = {
                            Text(type.name)
                        },
                        onClick = {
                            viewModel.onTypeChange(type.id)

                        }
                    )
                }
            }
            if (state.isLoading) {
                CircularProgressIndicator()
            }
            if (state.message.isNotEmpty()) {
                Text(state.message)
            }
            state.data?.let { packages ->
                LazyColumn {
                    items(packages) {
                        Text(it.name)
                    }
                }
            }

        }
    }
}

@Preview
@Composable
fun PackageListScreenPreview() {
    val service = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PackageService::class.java)
    val repository = PackageRepository(service)
    val viewModel = PackageListViewModel(repository)
    PackageListScreen(viewModel)
}

data class Place(val id: String, val name: String)
data class Type(val id: String, val name: String)
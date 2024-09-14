package pe.edu.upc.superherocompose.presentation.hero_list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HeroListScreen(viewModel: HeroListViewModel) {
    val state = viewModel.heroListState.value
    val name = viewModel.name.value

    Scaffold { paddingValues: PaddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            OutlinedTextField(value = name, onValueChange = {
                viewModel.onNameChanged(it)
            })
            OutlinedButton(onClick = {
                viewModel.searchHero()
            }) {
                Text(text = "Search hero")
            }
            if (state.isLoading) {
                CircularProgressIndicator()
            }
            if (state.message.isNotEmpty()) {
                Text(text = state.message)
            }
            state.heroes?.let { heroes ->
                LazyColumn {
                    items(heroes) { hero ->
                        Card {
                            Column {
                                Text(text = hero.name)
                                Text(text = hero.fullName)
                                Text(text = hero.publisher)
                            }
                        }
                    }
                }
            }

        }
    }
}
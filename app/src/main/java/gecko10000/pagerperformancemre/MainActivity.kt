package gecko10000.pagerperformancemre

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Example()
        }
    }
}

class ExampleViewModel : ViewModel() {
    private val _list = MutableLiveData<List<Int>>()
    val list: LiveData<List<Int>> = _list
    fun addItems() {
        _list.value = _list.value.orEmpty().plus(IntRange(1, 10).toList())
    }
}

@Composable
fun Example(exampleViewModel: ExampleViewModel = viewModel()) {
            val items by exampleViewModel.list.observeAsState(listOf())
            LaunchedEffect(Unit) {
                exampleViewModel.addItems()
            }
            ListOfItems(items)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListOfItems(items: List<Int>) {
    println("Rebuilt")
    val pagerState = rememberPagerState(pageCount = {
        items.size
    })

    HorizontalPager(state = pagerState, beyondBoundsPageCount = 1) {
        Text(items[it].toString())
    }
    Text(modifier = Modifier.padding(top = 50.dp), text = pagerState.pageCount.toString())
    Text(modifier = Modifier.padding(top = 100.dp), text = pagerState.currentPage.toString())
    Text(modifier = Modifier.padding(top = 150.dp), text = items.size.toString())
}

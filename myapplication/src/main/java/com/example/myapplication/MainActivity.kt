package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.RetrofitrootTheme
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.coroutine.viewModel.MainViewModel


class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      RetrofitrootTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
          jsonplaceholderArticle()
        }
      }
    }
  }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  Text(
      text = "Hello $name!",
      modifier = modifier,
  )
}


@Composable
fun jsonplaceholderArticle(
  modifier: Modifier = Modifier,
  viewModel: PoemBookmarksReadViewModel = viewModel()
) {
  Column {
    Button(onClick = {
       viewModel.getArticle()
    }) {
      Text(text = "get Article")
    }

    Button(onClick = {
      viewModel.addArticle()
    }) {
      Text(text = "add Article")
    }

    Button(onClick = {
      viewModel.rxJavaContributorsOwner()
    }) {
      Text(text = "rxJavaContributorsOwner")
    }
    Button(onClick = {
      viewModel.coroutineContributorsOwner()
    }) {
      Text(text = "coroutineContributorsOwner")
    }
    Button(onClick = {
      MainViewModel().getArticle()
    }) {
      Text(text = "wan coroutine")
    }
  }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
  RetrofitrootTheme {
    Greeting("Android")
  }
}

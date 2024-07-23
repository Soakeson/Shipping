// Copyright 2000-2021 JetBrains s.r.o. and contributors. Use of this source code is governed by the Apache 2.0 license that can be found in the LICENSE file.
import Server.ShippingTrackerServer
import Server.Views.TrackerView
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


@Composable
@Preview
fun App() {
    TrackerView().render()
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        LaunchedEffect(true) {
            withContext(Dispatchers.IO) {
                ShippingTrackerServer
            }
        }
        App()
    }
}


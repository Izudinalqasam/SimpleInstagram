package ibnu.sina.simpleinstagram.utils

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout


fun SwipeRefreshLayout.start() {
    isRefreshing = true
}

fun SwipeRefreshLayout.stop(){
    isRefreshing = false
}
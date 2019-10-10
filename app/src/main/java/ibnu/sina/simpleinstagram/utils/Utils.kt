package ibnu.sina.simpleinstagram.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

object Utils {

    fun hideKeyboard(view: View, context: Context?){
        val keyboard = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        keyboard.hideSoftInputFromWindow(view.windowToken, 0)
    }
}
package exe.weazy.movies

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.text.DateFormatSymbols

class Tools {
    companion object {
        fun convertDate(sDate: String): String {

            var month = ""
            var day = ""
            var year = ""

            month += sDate.substring(5, 7)
            day += sDate.substring(8)
            year += sDate.substring(0, 4)

            return day.toInt().toString() +
                    " " + DateFormatSymbols().months[month.toInt() - 1] +
                    " " + year.toInt().toString()
        }

        fun hideKeyboard(view: View?, context: Context?) {
            val inputManager = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(
                view?.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }

        fun getCyrillicString(str : String) : String {
            val lowerCaseString = str.toLowerCase()
            val result = StringBuilder()

            lowerCaseString.forEach {
                if (it == 'ё') result.append('е')
                else result.append(it)
            }

            return result.toString()
        }
    }
}
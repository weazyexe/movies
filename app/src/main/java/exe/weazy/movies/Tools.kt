package exe.weazy.movies

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
    }
}
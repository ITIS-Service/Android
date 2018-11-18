package com.itis.itisservice.tools.validation

import java.util.regex.Pattern
import javax.inject.Inject
import javax.inject.Singleton

class EmailValidator {

    private val validEmailAddressRegex = Pattern.compile("[A-Z0-9a-z._%+-]+@stud.kpfu.ru", Pattern.CASE_INSENSITIVE)

    fun isValid(emailStr: String): Boolean {
        val matcher = validEmailAddressRegex.matcher(emailStr)
        return matcher.find()
    }
}

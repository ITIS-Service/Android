package com.itis.itisservice.tools.validation

import java.util.regex.Pattern
import javax.inject.Inject
import javax.inject.Singleton

class PasswordValidator {

    private val atLeastOneNumberPattern = Pattern.compile(
            "^(?=.*[a-z])(?=.*\\d)[a-zA-Z\\d]{4,25}\$")

    fun isValid(password: String): Boolean {
        if (password.length > 20) {
            return false
        }
        if (!atLeastOneNumberPattern.matcher(password).find()) {
            return false
        }
        return true
    }
}

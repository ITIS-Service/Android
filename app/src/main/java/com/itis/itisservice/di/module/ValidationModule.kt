package com.itis.itisservice.di.module

import com.itis.itisservice.tools.validation.EmailValidator
import com.itis.itisservice.tools.validation.PasswordValidator
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ValidationModule {

    @Singleton
    @Provides
    internal fun provideEmailValidator(): EmailValidator {
        return EmailValidator()
    }

    @Singleton
    @Provides
    internal fun providePasswordValidator(): PasswordValidator {
        return PasswordValidator()
    }
}
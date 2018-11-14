package com.itis.itisservice.di.component

import com.itis.itisservice.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton
import com.itis.itisservice.tools.view.PasswordValidationTextInputEditText
import com.itis.itisservice.tools.view.EmailValidationEditText
import com.itis.itisservice.App
import com.itis.itisservice.di.module.ValidationModule


@Singleton
@Component(modules = [(ApplicationModule::class), (ValidationModule::class)])
interface ApplicationComponent {

    fun inject(app: App)

    fun inject(emailValidationEditText: EmailValidationEditText)

    fun inject(passwordValidationTextInputEditText: PasswordValidationTextInputEditText)
}
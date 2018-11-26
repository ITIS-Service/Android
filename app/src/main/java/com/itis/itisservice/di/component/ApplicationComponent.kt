package com.itis.itisservice.di.component

import com.itis.itisservice.di.module.ApplicationModule
import dagger.Component
import javax.inject.Singleton
import com.itis.itisservice.tools.view.PasswordValidationTextInputEditText
import com.itis.itisservice.tools.view.EmailValidationEditText
import com.itis.itisservice.App
import com.itis.itisservice.di.module.ApiModule
import com.itis.itisservice.di.module.PreferencesModule
import com.itis.itisservice.di.module.ValidationModule
import com.itis.itisservice.mvp.presenter.MainPresenter
import com.itis.itisservice.mvp.presenter.QuizPresenter
import com.itis.itisservice.mvp.presenter.SignInPresenter
import com.itis.itisservice.mvp.presenter.SignUpPresenter
import com.itis.itisservice.ui.activity.MainActivity
import com.itis.itisservice.ui.fragment.StartQuizFragment


@Singleton
@Component(modules = [ApplicationModule::class, ValidationModule::class, ApiModule::class, PreferencesModule::class])
interface ApplicationComponent {

    fun inject(app: App)

    fun inject(emailValidationEditText: EmailValidationEditText)

    fun inject(passwordValidationTextInputEditText: PasswordValidationTextInputEditText)

    fun inject(presenter: SignInPresenter)

    fun inject(presenter: SignUpPresenter)

    fun inject(presenter: QuizPresenter)

    fun inject(presenter: MainPresenter)

    fun inject(activity: MainActivity)

    fun inject(fragment: StartQuizFragment)
}
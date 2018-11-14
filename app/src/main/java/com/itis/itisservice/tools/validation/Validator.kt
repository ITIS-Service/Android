package com.itis.itisservice.tools.validation

import android.support.design.widget.TextInputLayout
import android.widget.TextView
import com.jakewharton.rxbinding2.view.RxView
import com.jakewharton.rxbinding2.widget.RxTextView
import io.reactivex.disposables.Disposable

inline fun validateInput(inputLayout: TextInputLayout, inputView: TextView, crossinline body: () -> Unit): Disposable {
    return RxView.focusChanges(inputView)
            .skipInitialValue() // Listen for focus events.
            .map {
                if (!it) { // If view lost focus, lambda (our check logic) should be applied.
                    body()
                }
                return@map it
            }
            .flatMap { hasFocus ->
                return@flatMap RxTextView.textChanges(inputView)
                        .skipInitialValue()
                        .map {
                            if (hasFocus && inputLayout.isErrorEnabled) inputLayout.isErrorEnabled = false
                        } // Disable error when user typing.
                        .skipWhile { hasFocus } // Don't react on text change events when we have a focus.
                        .doOnEach { body() }
            }
            .subscribe { }
}
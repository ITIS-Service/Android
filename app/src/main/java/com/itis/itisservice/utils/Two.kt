package com.itis.itisservice.utils

import io.reactivex.functions.BiFunction

data class Two<A, B>(
        var first: A? = null,
        var second: B? = null) {

    companion object {
        fun <A, B> create(a: A, b: B): Two<A, B> = Two(a, b)

        fun <A, B> zipFunc(): BiFunction<A, B, Two<A, B>> = BiFunction { a, b ->
            create(a, b)
        }
    }
}
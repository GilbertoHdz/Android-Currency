package com.gilbertohdz.currency.lib.interactors

import io.reactivex.ObservableTransformer

abstract class BaseInteractor<U, D> {
    abstract fun getTransformer(): ObservableTransformer<U, D>
}

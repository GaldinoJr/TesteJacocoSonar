package com.example.testejacocosonar

class MainPresenter(val view: MainContract.View) {
    fun onButtonOneClicked() {
        view.setLabelOne()
    }
}
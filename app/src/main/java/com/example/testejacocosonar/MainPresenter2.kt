package com.example.testejacocosonar

class MainPresenter2(val view: MainContract.View) {
    fun onButtonTwoClicked() {
        view.setLabelTwo()
    }
}
package com.example.testejacocosonar

import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MainPresenter2Test {
    @Mock
    lateinit var view: MainContract.View

    lateinit var presenter: MainPresenter2

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        presenter = MainPresenter2(view)
    }

    @Test
    fun test1(){
        presenter.onButtonTwoClicked()
        verify(view).setLabelTwo()
    }
}
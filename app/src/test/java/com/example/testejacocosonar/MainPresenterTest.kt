package com.example.testejacocosonar

import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class MainPresenterTest {
    @Mock
    lateinit var view: MainContract.View

    lateinit var presenter: MainPresenter

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
        presenter = MainPresenter(view)
    }

    @Test
    fun test1(){
        presenter.onButtonOneClicked()
        verify(view).setLabelOne()
    }
}
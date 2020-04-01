package com.example.testejacocosonar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener, MainContract.View {
    val mPresenter = MainPresenter(this)
    val mPresenter2 = MainPresenter2(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btTwo.setOnClickListener(this)
        btOne.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            btOne.id->{
                mPresenter.onButtonOneClicked()
            }
            btTwo.id->{
                mPresenter2.onButtonTwoClicked()
            }
        }
    }

    override fun setLabelOne() {
        tvMessage.text = "One"
    }

    override fun setLabelTwo() {
        tvMessage.text = "Two"
    }
}

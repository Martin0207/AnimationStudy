package com.martin.animationstudy.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.martin.animationstudy.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        btn_move_view.setOnClickListener {
            MoveViewActivity.start(this)
        }

        btn_animation.setOnClickListener {
            AnimationActivity.start(this)
        }
    }
}

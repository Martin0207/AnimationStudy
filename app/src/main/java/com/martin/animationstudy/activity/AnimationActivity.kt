package com.martin.animationstudy.activity

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationSet
import com.martin.animationstudy.R
import com.martin.animationstudy.util.AnimationUtil
import kotlinx.android.synthetic.main.activity_animation.*

class AnimationActivity : AppCompatActivity() {

    companion object {

        fun start(context: Context) {
            context.startActivity(Intent(context, AnimationActivity::class.java))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animation)

        click()
    }

    private fun click() {
        btn_translation_x.setOnClickListener {
            ObjectAnimator.ofFloat(mv, AnimationUtil.TRANSLATION_X, mv.x, mv.x + 100)
                    .run()
        }

        btn_translation_y.setOnClickListener {
            ObjectAnimator.ofFloat(mv, AnimationUtil.TRANSLATION_Y, mv.y, mv.y + 100)
                    .run()
        }

        btn_rotate_x.setOnClickListener {
            ObjectAnimator.ofFloat(mv, AnimationUtil.ROTATION_X, 0F, 150F)
                    .run()
        }

        btn_rotate_y.setOnClickListener {
            ObjectAnimator.ofFloat(mv, AnimationUtil.ROTATION_Y, 0F, 150F)
                    .run()
        }

        btn_scale_x.setOnClickListener {
            ObjectAnimator.ofFloat(mv, AnimationUtil.SCALE_X, 0F, 1F, 0.5F)
                    .run()
        }

        btn_scale_y.setOnClickListener {
            ObjectAnimator.ofFloat(mv, AnimationUtil.SCALE_Y, 0F, 1F, 0.5F)
                    .run()
        }

        btn_alpha.setOnClickListener {
            ObjectAnimator.ofFloat(mv, AnimationUtil.ALPHA, 1F, 0.3F, 0.8F)
                    .run()
        }

    }

    private fun ObjectAnimator.run() {
        this.setDuration(800)
                .start()
    }

}

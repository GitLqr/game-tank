package com.charylin.game.business

import com.charylin.game.model.View

/**
 * @author LQR
 * @time 2020/11/19
 * @desc 销毁的能力
 */
interface Destroyable : View {
    fun isDestroy(): Boolean
}
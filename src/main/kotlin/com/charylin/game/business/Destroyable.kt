package com.charylin.game.business

import com.charylin.game.model.View

/**
 * @author LQR
 * @time 2020/11/19
 * @desc 销毁的能力
 */
interface Destroyable : View {
    /**
     * 是否已销毁
     */
    fun isDestroy(): Boolean

    /**
     * 显示销毁效果
     */
    fun showDestroy(): Array<View>? = null
}
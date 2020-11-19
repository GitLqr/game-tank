package com.charylin.game.business

import com.charylin.game.enums.Direction
import com.charylin.game.model.View

/**
 * @author LQR
 * @time 2020/11/19
 * @desc 移动的能力
 */
interface AutoMovable : View {

    val currentDirection: Direction

    val speed: Int

    fun autoMove()
}
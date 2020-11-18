package com.charylin.game.model

/**
 * @author LQR
 * @time 2020/11/18
 * @desc 显示的视力，定义显示规范
 */
interface View {
    // 位置
    val x: Int
    val y: Int

    // 宽高
    val width: Int
    val height: Int

    // 显示
    fun draw()
}
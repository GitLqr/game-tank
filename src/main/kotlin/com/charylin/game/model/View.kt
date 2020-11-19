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

    /**
     * 检查碰撞
     */
    fun checkCollision(
        x1: Int, y1: Int, w1: Int, h1: Int,
        x2: Int, y2: Int, w2: Int, h2: Int
    ): Boolean {
        return when {
            y2 + h2 <= y1 -> {
                // 如果 阻挡物 在 运动物 的上方时，不碰撞
                false
            }
            y1 + h1 <= y2 -> {
                // 如果 阻挡物 在 运动物 的下方时，不碰撞
                false
            }
            x2 + w2 <= x1 -> {
                // 如果 阻挡物 在 运动物 的左方时，不碰撞
                false
            }
            else -> x1 + w1 > x2
        }
    }
}
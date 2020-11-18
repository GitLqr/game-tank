package com.charylin.game.model

import com.charylin.game.enums.Direction
import org.itheima.kotlin.game.core.Painter

/**
 * @author LQR
 * @time 2020/11/18
 * @desc 子弹
 */
class Bullet(val direction: Direction, create: (width: Int, height: Int) -> Pair<Int, Int>) : View {

    override val width: Int
    override val height: Int

    override val x: Int
    override val y: Int

    private val imagePath: String = when (direction) {
        Direction.UP -> "/img/shot_top.gif"
        Direction.DOWN -> "/img/shot_bottom.gif"
        Direction.LEFT -> "/img/shot_left.gif"
        Direction.RIGHT -> "/img/shot_right.gif"
    }

    init {
        val size = Painter.size(imagePath)
        width = size[0]
        height = size[1]
        val pair = create.invoke(width, height)
        x = pair.first
        y = pair.second
    }

    override fun draw() {
        val imagePath = when (direction) {
            Direction.UP -> "/img/shot_top.gif"
            Direction.DOWN -> "/img/shot_bottom.gif"
            Direction.LEFT -> "/img/shot_left.gif"
            Direction.RIGHT -> "/img/shot_right.gif"
        }
        Painter.drawImage(imagePath, x, y)
    }
}
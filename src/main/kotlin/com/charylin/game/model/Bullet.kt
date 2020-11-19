package com.charylin.game.model

import com.charylin.game.Config
import com.charylin.game.business.Attackable
import com.charylin.game.business.AutoMovable
import com.charylin.game.business.Destroyable
import com.charylin.game.business.Sufferable
import com.charylin.game.enums.Direction
import com.charylin.game.ext.checkCollision
import org.itheima.kotlin.game.core.Painter

/**
 * @author LQR
 * @time 2020/11/18
 * @desc 子弹
 */
class Bullet(
    override val owner: View,
    override val currentDirection: Direction,
    create: (width: Int, height: Int) -> Pair<Int, Int>
) :
    AutoMovable, Destroyable, Attackable, Sufferable {

    override val width: Int
    override val height: Int

    override var x: Int = 0
    override var y: Int = 0

    override val speed: Int = 8
    private var isDestroyed = false

    override val attackPower: Int = 1
    override val blood: Int = 1

    private val imagePath: String = when (currentDirection) {
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
        val imagePath = when (currentDirection) {
            Direction.UP -> "/img/shot_top.gif"
            Direction.DOWN -> "/img/shot_bottom.gif"
            Direction.LEFT -> "/img/shot_left.gif"
            Direction.RIGHT -> "/img/shot_right.gif"
        }
        Painter.drawImage(imagePath, x, y)
    }

    override fun autoMove() {
        // 根据自己的方向,来改变自己的坐标
        when (currentDirection) {
            Direction.UP -> y -= speed
            Direction.DOWN -> y += speed
            Direction.LEFT -> x -= speed
            Direction.RIGHT -> x += speed
        }
    }

    override fun isDestroy(): Boolean {
        if (isDestroyed) return true

        // 子弹在脱离屏幕后,需要被销毁
        if (x < -width) return true
        if (x > Config.gameWidth) return true
        if (y < -height) return true
        if (y > Config.gameHeight) return true
        return false
    }

    override fun isCollision(sufferable: Sufferable): Boolean {
        return checkCollision(sufferable)
    }

    override fun notifyAttack(sufferable: Sufferable) {
        isDestroyed = true
    }

    override fun notifySuffer(attackable: Attackable): Array<View>? {
        return arrayOf(Blast(x, y))
    }

}
package com.charylin.game.model

import com.charylin.game.Config
import com.charylin.game.business.*
import com.charylin.game.enums.Direction
import org.itheima.kotlin.game.core.Painter
import java.util.*

/**
 * @author LQR
 * @time 2020/11/19
 * @desc 敌方坦克
 */
class Enemy(override var x: Int, override var y: Int) : Movable, AutoMovable, Blockable, AutoShot, Sufferable,
    Destroyable {

    override var currentDirection: Direction = Direction.DOWN
    override val speed: Int = 8

    override val width: Int = Config.block
    override val height: Int = Config.block

    private var badDirection: Direction? = null

    private var lastShotTime = 0L
    private var shotFrequency = 800

    private var lastMoveTime = 0L
    private var moveFrequency = 50

    override var blood: Int = 2

    override fun draw() {
        val imagePath = when (currentDirection) {
            Direction.UP -> "img/enemy_1_u.gif"
            Direction.DOWN -> "img/enemy_1_d.gif"
            Direction.LEFT -> "img/enemy_1_l.gif"
            Direction.RIGHT -> "img/enemy_1_r.gif"
        }
        Painter.drawImage(imagePath, x, y)
    }

    override fun notifyCollision(direction: Direction?, block: Blockable?) {
        badDirection = direction
    }

    override fun autoMove() {
        val current = System.currentTimeMillis()
        if (current - lastMoveTime < moveFrequency) return
        lastMoveTime = current

        if (currentDirection == badDirection) {
            currentDirection = rdmDirection(badDirection)
            return
        }

        // 坦克坐标需要发生变化
        // 根据不同的方向，改变对应的坐标
        when (currentDirection) {
            Direction.UP -> y -= speed
            Direction.DOWN -> y += speed
            Direction.LEFT -> x -= speed
            Direction.RIGHT -> x += speed
        }
        // 越界判断
        if (x < 0) x = 0
        if (x > Config.gameWidth - width) x = Config.gameWidth - width
        if (y < 0) y = 0
        if (y > Config.gameHeight - height) y = Config.gameHeight - height
    }

    private fun rdmDirection(bad: Direction?): Direction {
        val i = Random().nextInt(4)
        val direction = when (i) {
            0 -> Direction.UP
            1 -> Direction.DOWN
            2 -> Direction.LEFT
            3 -> Direction.RIGHT
            else -> Direction.UP
        }
        if (direction == bad) {
            return rdmDirection(bad)
        }
        return direction
    }

    override fun autoShot(): View? {
        val current = System.currentTimeMillis()
        if (current - lastShotTime < shotFrequency) return null
        lastShotTime = current

        return Bullet(this, currentDirection) { bulletWidth, bulletHeight ->
            val tankX = x
            val tankY = y
            val tankWidth = width
            val tankHeight = height

            var bulletX = 0
            var bulletY = 0

            // 计算子弹真实的坐标
            when (currentDirection) {
                Direction.UP -> {
                    bulletX = tankX + (tankWidth - bulletWidth) / 2
                    bulletY = tankY - bulletHeight / 2
                }
                Direction.DOWN -> {
                    bulletX = tankX + (tankWidth - bulletWidth) / 2
                    bulletY = tankY + tankHeight - bulletHeight / 2
                }
                Direction.LEFT -> {
                    bulletX = tankX - bulletWidth / 2
                    bulletY = tankY + (tankHeight - bulletHeight) / 2
                }
                Direction.RIGHT -> {
                    bulletX = tankX + tankWidth - bulletWidth / 2
                    bulletY = tankY + (tankHeight - bulletHeight) / 2
                }
            }

            Pair(bulletX, bulletY)
        }
    }

    override fun notifySuffer(attackable: Attackable): Array<View>? {
        if (attackable.owner is Enemy) {
            // （敌）友军伤害关闭
            return null
        }

        blood -= attackable.attackPower
        return arrayOf(Blast(x, y))
    }

    override fun isDestroy(): Boolean = blood <= 0
}
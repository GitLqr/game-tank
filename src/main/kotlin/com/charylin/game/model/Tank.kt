package com.charylin.game.model

import com.charylin.game.Config
import com.charylin.game.business.Attackable
import com.charylin.game.business.Blockable
import com.charylin.game.business.Movable
import com.charylin.game.business.Sufferable
import com.charylin.game.enums.Direction
import org.itheima.kotlin.game.core.Painter

/**
 * @author LQR
 * @time 2020/11/18
 * @desc 坦克
 */
class Tank(override var x: Int, override var y: Int) : Movable, Blockable, Sufferable {

    override val width: Int = Config.block
    override val height: Int = Config.block

    override var currentDirection: Direction = Direction.UP
    override val speed: Int = 8

    // 坦克不可以走的方向
    private var badDirection: Direction? = null

    // 血量
    override var blood: Int = 20

    override fun draw() {
        // 根据坦克的方向进行绘制

        // 方式一：
        // when (currentDirection) {
        //     Direction.UP -> Painter.drawImage("img/tank_u.gif", x, y)
        //     Direction.DOWN -> Painter.drawImage("img/tank_d.gif", x, y)
        //     Direction.LEFT -> Painter.drawImage("img/tank_l.gif", x, y)
        //     Direction.RIGHT -> Painter.drawImage("img/tank_r.gif", x, y)
        // }

        // 方式二：
        val imagePath = when (currentDirection) {
            Direction.UP -> "img/tank_u.gif"
            Direction.DOWN -> "img/tank_d.gif"
            Direction.LEFT -> "img/tank_l.gif"
            Direction.RIGHT -> "img/tank_r.gif"
        }
        Painter.drawImage(imagePath, x, y)
    }

    fun move(direction: Direction) {
        // 判断是否是往要碰撞的方向走
        if (this.badDirection == direction) {
            return
        }

        // 当前方向，和希望移动的方向不一致时，只做方向改变
        if (this.currentDirection != direction) {
            this.currentDirection = direction
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

    override fun notifyCollision(direction: Direction?, block: Blockable?) {
        this.badDirection = direction
    }

    /**
     * 发射子弹
     */
    fun shot(): Bullet {
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
        blood -= attackable.attackPower
        return arrayOf(Blast(x, y))
    }
}
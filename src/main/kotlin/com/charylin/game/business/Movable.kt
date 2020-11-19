package com.charylin.game.business

import com.charylin.game.Config
import com.charylin.game.enums.Direction
import com.charylin.game.model.View

/**
 * @author LQR
 * @time 2020/11/18
 * @desc 移动运动的能力
 */
interface Movable : View {

    // 可移动物体存在方向
    val currentDirection: Direction

    // 可移动物体存在速度
    val speed: Int

    /**
     * 判断移动物体是否和阻塞物体发生碰撞
     * @return 要碰撞的方向，如果为null，说明没有碰撞
     */
    fun willCollision(block: Blockable): Direction? {
        // 未来的坐标
        var x = this.x
        var y = this.y
        when (currentDirection) {
            Direction.UP -> y -= speed
            Direction.DOWN -> y += speed
            Direction.LEFT -> x -= speed
            Direction.RIGHT -> x += speed
        }

        // 和边界进行检测
        // 越界判断
        if (x < 0) return Direction.LEFT
        if (x > Config.gameWidth - width) return Direction.RIGHT
        if (y < 0) return Direction.UP
        if (y > Config.gameHeight - height) return Direction.DOWN

        // 检测碰撞 下一步是否碰撞
        var collision = checkCollision(x, y, width, height, block.x, block.y, block.width, block.height)

        return if (collision) currentDirection else null
    }

    /**
     * 通知碰撞
     */
    fun notifyCollision(direction: Direction?, block: Blockable?)
}
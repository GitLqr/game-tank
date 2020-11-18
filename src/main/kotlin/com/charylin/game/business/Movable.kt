package com.charylin.game.business

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
    fun willCollision(block: Blockable): Direction?

    /**
     * 通知碰撞
     */
    fun notifyCollision(direction: Direction?, block: Blockable?)
}
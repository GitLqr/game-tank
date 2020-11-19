package com.charylin.game.business

import com.charylin.game.model.View

/**
 * @author LQR
 * @time 2020/11/19
 * @desc 具备攻击的能力
 */
interface Attackable : View {

    // 所有者
    val owner: View

    // 攻击力
    val attackPower: Int

    /**
     * 判断是否碰撞
     */
    fun isCollision(sufferable: Sufferable): Boolean

    /**
     * 通知攻击者,攻击到目标了
     */
    fun notifyAttack(sufferable: Sufferable)

}
package com.charylin.game.business

import com.charylin.game.model.View

/**
 * @author LQR
 * @time 2020/11/19
 * @desc 遭受攻击的能力
 */
interface Sufferable : View {

    // 生命值
    val blood: Int

    /**
     * 通知受攻击者,遭受到攻击了
     */
    fun notifySuffer(attackable: Attackable)
}
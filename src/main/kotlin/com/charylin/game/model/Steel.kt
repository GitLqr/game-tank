package com.charylin.game.model

import com.charylin.game.Config
import com.charylin.game.business.Attackable
import com.charylin.game.business.Blockable
import com.charylin.game.business.Sufferable
import org.itheima.kotlin.game.core.Painter

/**
 * @author LQR
 * @time 2020/11/18
 * @desc 铁墙
 */
class Steel(override val x: Int, override val y: Int) : Blockable, Sufferable {

    override val width: Int = Config.block
    override val height: Int = Config.block

    override val blood: Int = 1

    override fun draw() {
        Painter.drawImage("img/steel.gif", x, y)
    }

    override fun notifySuffer(attackable: Attackable): Array<View>? {
        return null
    }
}
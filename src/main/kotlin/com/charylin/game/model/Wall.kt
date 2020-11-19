package com.charylin.game.model

import com.charylin.game.Config
import com.charylin.game.business.Attackable
import com.charylin.game.business.Blockable
import com.charylin.game.business.Destroyable
import com.charylin.game.business.Sufferable
import org.itheima.kotlin.game.core.Painter

/**
 * @author LQR
 * @time 2020/11/18
 * @desc 砖墙
 */
class Wall(override val x: Int, override val y: Int) : Blockable, Sufferable, Destroyable {

    override val width: Int = Config.block
    override val height: Int = Config.block

    override var blood: Int = 3

    override fun draw() {
        Painter.drawImage("img/wall.gif", x, y)
    }

    override fun notifySuffer(attackable: Attackable) {
        blood -= attackable.attackPower
    }

    override fun isDestroy(): Boolean = blood <= 0
}
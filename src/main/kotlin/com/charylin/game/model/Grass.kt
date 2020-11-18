package com.charylin.game.model

import com.charylin.game.Config
import org.itheima.kotlin.game.core.Painter

/**
 * @author LQR
 * @time 2020/11/18
 * @desc 草地
 */
class Grass(override val x: Int, override val y: Int) : View {

    // 宽高
    override val width: Int = Config.block
    override val height: Int = Config.block

    // 显示行为
    override fun draw() {
        Painter.drawImage("img/grass.gif", x, y)
    }
}
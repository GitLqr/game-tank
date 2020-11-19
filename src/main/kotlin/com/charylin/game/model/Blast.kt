package com.charylin.game.model

import com.charylin.game.Config
import com.charylin.game.business.Destroyable
import org.itheima.kotlin.game.core.Painter

/**
 * @author LQR
 * @time 2020/11/19
 * @desc 爆炸物
 */
class Blast(override val x: Int, override val y: Int) : View, Destroyable {

    override val width: Int = Config.block
    override val height: Int = Config.block
    private val imagePaths = arrayListOf<String>()
    private var index: Int = 0

    init {
        (1..32).forEach {
            imagePaths.add("img/blast_${it}.png")
        }
    }

    override fun draw() {
        val i = index % imagePaths.size
        Painter.drawImage(imagePaths[i], x, y)
        index++
    }

    override fun isDestroy(): Boolean {
        return index >= imagePaths.size
    }

}
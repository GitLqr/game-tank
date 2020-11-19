package com.charylin.game

import com.charylin.game.business.*
import com.charylin.game.enums.Direction
import com.charylin.game.model.*
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import org.itheima.kotlin.game.core.Window
import java.io.File
import java.util.concurrent.CopyOnWriteArrayList

/**
 * @author LQR
 * @time 2020/11/18
 * @desc 游戏主窗口
 */
class GameWindow : Window(
    title = "坦克大战",
    icon = "img/blast_1.png",
    width = Config.gameWidth,
    height = Config.gameHeight
) {

    // private val views = arrayListOf<View>()
    private val views = CopyOnWriteArrayList<View>() // 线程安全的集合

    private lateinit var tank: Tank

    override fun onCreate() {
        // 地图：通过读文件的方式创建
        val file = File(javaClass.getResource("/map/1.map").path)
        val readLines = file.readLines()
        var lineNum = 0
        readLines.forEach { line ->
            var columnNum = 0
            line.toCharArray().forEach { column ->
                when (column) {
                    '砖' -> views.add(Wall(columnNum * Config.block, lineNum * Config.block))
                    '铁' -> views.add(Steel(columnNum * Config.block, lineNum * Config.block))
                    '水' -> views.add(Water(columnNum * Config.block, lineNum * Config.block))
                    '草' -> views.add(Grass(columnNum * Config.block, lineNum * Config.block))
                }
                columnNum++
            }
            lineNum++
        }

        // 添加我方坦克
        tank = Tank(Config.block * 10, Config.block * 12)
        views.add(tank)
    }

    override fun onDisplay() {
        views.forEach {
            it.draw()
        }
    }

    override fun onKeyPressed(event: KeyEvent) {
        // 用户操作
        when (event.code) {
            KeyCode.UP,
            KeyCode.W -> {
                tank.move(Direction.UP)
            }
            KeyCode.DOWN,
            KeyCode.S -> {
                tank.move(Direction.DOWN)
            }
            KeyCode.LEFT,
            KeyCode.A -> {
                tank.move(Direction.LEFT)
            }
            KeyCode.RIGHT,
            KeyCode.D -> {
                tank.move(Direction.RIGHT)
            }
            KeyCode.ENTER -> {
                val bullet = tank.shot()
                views.add(bullet)
            }
        }
    }

    override fun onRefresh() {
        // 业务逻辑
        // 判断运动物体和阻塞物体是否发生碰撞
        // 1）找到运动物体
        views.filter { it is Movable }.forEach { move ->
            move as Movable
            var badDirection: Direction? = null
            var badBlock: Blockable? = null

            // 2）找到阻塞物体
            views.filter { it is Blockable }.forEach blockTag@{ block ->
                block as Blockable

                // 3）遍历集合，判断是否发生碰撞
                // 获得碰撞的方向
                val direction = move.willCollision(block)
                direction?.let {
                    // 移动物体发生碰撞，跳出当前循环
                    badDirection = direction
                    badBlock = block
                    return@blockTag
                }
            }

            // 找到和move碰撞的block，找到会碰撞的方向
            move.notifyCollision(badDirection, badBlock)

            // 检测自动移动能力的物体,让他们自己动起来
            views.filter { it is AutoMovable }.forEach {
                (it as AutoMovable).autoMove()
            }

            // 检测销毁
            views.filter { it is Destroyable }.forEach {
                // 判断具备销毁能力的物体,是否被销毁了
                if ((it as Destroyable).isDestroy()) {
                    views.remove(it)
                }
            }

            // 检测具备攻击能力的和被攻击能力的物体间是否产生碰撞
            // 1)过滤 具备攻击能力的
            views.filter { it is Attackable }.forEach { attack ->
                attack as Attackable
                // 2)过滤 受攻击能力的
                views.filter { it is Sufferable }.forEach sufferTag@{ suffer ->
                    suffer as Sufferable
                    // 3)判断是否产生碰撞
                    if (attack.isCollision(suffer)) {
                        // 产生碰撞,找到碰撞者
                        // 通知攻击者 产生碰撞
                        attack.notifyAttack(suffer)
                        // 通知受攻击者 产生碰撞
                        suffer.notifySuffer(attack)
                        return@sufferTag
                    }
                }
            }
        }
    }
}
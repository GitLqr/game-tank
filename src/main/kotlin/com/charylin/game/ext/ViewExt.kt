package com.charylin.game.ext

import com.charylin.game.model.View

/**
 * View的扩展方法
 */
fun View.checkCollision(view: View): Boolean {
    return checkCollision(x, y, width, height, view.x, view.y, view.width, view.height)
}
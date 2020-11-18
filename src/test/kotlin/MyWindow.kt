import javafx.application.Application
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import org.itheima.kotlin.game.core.Window

/**
 * 窗体
 */
class MyWindow : Window() {
    override fun onCreate() {
    }

    override fun onDisplay() {
    }

    override fun onKeyPressed(event: KeyEvent) {
        when(event.code){
            KeyCode.ENTER -> println("点击了回车")
        }
    }

    override fun onRefresh() {
    }
}

fun main(args: Array<String>) {
    Application.launch(MyWindow::class.java)
}
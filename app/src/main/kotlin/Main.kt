import javafx.application.Application
import javafx.scene.Scene
import javafx.stage.Stage
import model.Model
import javafx.scene.image.Image
import view.ViewController

class Main : Application() {
    override fun start(primaryStage: Stage?) {

        with (primaryStage!!) {
            val model = Model(primaryStage)

            val root = ViewController(model)
            scene = Scene(root)

            primaryStage.scene = scene
            primaryStage.isResizable = true
            primaryStage.minHeight = 400.0
            primaryStage.minWidth = 600.0
            primaryStage.height = 600.0
            primaryStage.width = 800.0
            title = "Lightbox - Victor Astinov (vastinov)"
            icons.add(Image("bg.png"))
            show()
        }
    }
}
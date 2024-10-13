package ch.makery.address.view
import scalafx.scene.control.Label


// abstract controller for other controllers to inherit from
abstract class GeneralController(private var msg: Label) {

  // general function to show message to user based on their actions
  def showMessage(str: String): Unit = {
    msg.setText(str)
  }
}

package ch.makery.address.view

import ch.makery.address.MainApp
import scalafx.scene.control.{Alert, Button, Label}
import scalafx.scene.image.{Image, ImageView}
import scalafxml.core.macros.sfxml
import scalafx.scene.control.Alert.AlertType
import scalafx.beans.property.IntegerProperty

@sfxml
class MinigameController(
                          private val miniGame: Label,
                          private val point: Label,
                          private val lovemeButton: Button,
                          private val feedmeButton: Button,
                          private val petmeButton: Button,
                          private val image: ImageView
                        ) extends GeneralController(miniGame) {

  // Property to track the cat's feeling
  private val catFeeling = IntegerProperty(0)

  // Initialize the point label to display the current points
  point.text <== MainApp.userPoints.asString()

  // Deduct points, update the image, and show the success message
  private def handleButtonPress(successMessage: String, imagePath: String): Unit = {
    if (MainApp.userPoints.value >= 10) {
      MainApp.userPoints.value -= 10
      catFeeling.value += 1 // Increment the cat's feeling

      // Check if catFeeling reaches 10
      if (catFeeling.value >= 10) {
        image.setImage(new Image("image/special.gif")) // Update to special image
        showMessage("The cat is very happy!")
        // Reset or handle reaching max feeling as needed
      } else {
        image.setImage(new Image(imagePath)) // Update the image
        showMessage(successMessage) // Show the success message
      }

    } else {
      showMessage("Not enough points!") // if the point is less than 10
      new Alert(AlertType.Warning) {
        initOwner(MainApp.stage)
        title = "Insufficient Points"
        headerText = "Not Enough Points"
        contentText = "You need at least 10 points to press this button."
      }.showAndWait()
    }
  }

  // Event handlers for buttons
  def handleLoveme(): Unit = {
    handleButtonPress("LOVE YOU!", "image/loveme.gif")
  }

  def handleFeedme(): Unit = {
    handleButtonPress("YUMMY!", "image/yummy2.gif")
  }

  def handlePetme(): Unit = {
    handleButtonPress("PURRRRRR", "image/petme2.gif")
  }

  // Return back to Todolist
  def handleTodolist(): Unit = {
    MainApp.showTodolist()
  }
}

package ch.makery.address.view

import ch.makery.address.model.{Task, Due, Develop, Upcoming}
import ch.makery.address.MainApp
import scalafx.scene.control.{TextField, Alert, ChoiceBox}
import scalafxml.core.macros.sfxml
import scalafx.stage.Stage
import scalafx.Includes._
import ch.makery.address.util.DateUtil._
import scalafx.event.ActionEvent
import scalafx.collections.ObservableBuffer

@sfxml
class editlistController(
                          private val taskField: TextField,
                          private val dueField: TextField,
                          private val commentField: TextField,
                          private val myChoiceBox: ChoiceBox[String]
                        ) {
  var dialogStage: Stage = null
  private var _task: Task = null
  var okClicked = false
  def task = _task

  def task_=(x: Task) {
    _task = x

    taskField.text = _task.taskName.value
    commentField.text = _task.comment.value
    dueField.text = _task.date.value.asString
    dueField.setPromptText("dd.mm.yyyy")


    // Convert TaskStatus to String
    myChoiceBox.value = _task.choice.value.status
  }

  // Initialize the controller
  def dropdown(): Unit = {
    // Add choices to the ChoiceBox
    myChoiceBox.items = ObservableBuffer("Develop", "Due", "Upcoming")
  }

  //Handle ok function
  def handleOk(action: ActionEvent) {
    if (isInputValid()) {
      _task.taskName <== taskField.text
      _task.comment <== commentField.text
      _task.date.value = dueField.text.value.parseLocalDate


      // Convert String to TaskStatus
      _task.choice.value = myChoiceBox.value.value match {
        case "Develop" => Develop
        case "Due" => Due
        case "Upcoming" => Upcoming
      }
      okClicked = true
      dialogStage.close()
    }
  }

  //Handle cancel function
  def handleCancel(action: ActionEvent) {
    dialogStage.close();
  }
  def nullChecking(x: String) = x == null || x.length == 0
  def isInputValid(): Boolean = {
    var errorMessage = ""


    if (nullChecking(taskField.text.value))
      errorMessage += "No valid task!\n"
    if (nullChecking(commentField.text.value))
      errorMessage += "No valid comment!\n"

    if (nullChecking(myChoiceBox.value.value))
      errorMessage += "Must select a status!\n"


    if (nullChecking(dueField.text.value))
      errorMessage += "No valid due date!\n"
    else {
      if (!dueField.text.value.isValid) {
        errorMessage += "No valid due date. Use the format dd/mm/yyyy!\n"
      }
    }


    if (errorMessage.isEmpty) {
      true
    } else {
      val alert = new Alert(Alert.AlertType.Error) {
        initOwner(dialogStage)
        title = "Invalid Fields"
        headerText = "Please correct the empty fields"
        contentText = errorMessage
      }.showAndWait()

      false
    }
  }
}

package ch.makery.address.view

import ch.makery.address.model.{Completed, Develop, Due, Task, TaskStatus, Upcoming}
import ch.makery.address.MainApp
import scalafx.scene.control.{Alert, Label, TableColumn, TableView}
import scalafxml.core.macros.sfxml
import scalafx.beans.property.StringProperty
import scalafx.event.ActionEvent
import scalafx.scene.control.Alert.AlertType
import scalafx.beans.property.IntegerProperty
import scalafx.Includes._

import scala.util.{Failure, Success}

@sfxml
class TodolistController(
                          private val taskTable: TableView[Task],
                          private val taskColumn: TableColumn[Task, String],
                          private val progressColumn: TableColumn[Task, String],
                          private val taskLabel: Label,
                          private val dueLabel: Label,
                          private val progressLabel: Label,
                          private val commentLabel: Label,
                          private val regMesg: Label
                        ) extends GeneralController(regMesg) {

  // Initialize TableView display contents model
  taskTable.items = MainApp.taskData

  // Initialize columns' cell values
  taskColumn.cellValueFactory = { _.value.taskName }
  progressColumn.cellValueFactory = { cellData =>
    new StringProperty(cellData.value.choice.value.status)
  }

  // Make userPoints accessible globally
  MainApp.userPoints = IntegerProperty(0)

  // To show the task details at the right side of the AnchorPane
  private def showTaskDetails(task: Option[Task]): Unit = {
    task match {
      case Some(task) =>
        // Fill the labels with info from the Task object.
        taskLabel.text <== task.taskName
        progressLabel.text <== new StringProperty(task.choice.value.status)
        commentLabel.text <== task.comment
        dueLabel.text = task.date.value.toString

      case None =>
        // Task is null, remove all the text.
        taskLabel.text.unbind()
        commentLabel.text.unbind()
        taskLabel.text = ""
        dueLabel.text = ""
        progressLabel.text = ""
        commentLabel.text = ""
    }
  }

  // Clear task details initially
  showTaskDetails(None)

  // Update task details when a different task is selected in the table
  taskTable.selectionModel().selectedItem.onChange { (_, _, newValue) =>
    showTaskDetails(Option(newValue))
  }

  // Delete Function
  def handleDeleteTask(action: ActionEvent): Unit = {
    val selectedIndex = taskTable.selectionModel().selectedIndex.value
    if (selectedIndex >= 0) {
      MainApp.taskData.remove(selectedIndex).delete() match {
        case Success(_) => // Task deleted successfully
        case Failure(e) =>
          new Alert(AlertType.Information) {
            initOwner(MainApp.stage)
            title = "Fail to remove"
            headerText = "Failure to remove Task"
            contentText = "Something went wrong with the database"
          }.showAndWait()
      }

    } else {
      // Nothing selected.
      new Alert(AlertType.Warning) {
        initOwner(MainApp.stage)
        title = "No Selection"
        headerText = "No Task Selected"
        contentText = "Please select a task in the table."
      }.showAndWait()
    }
  }

  // New Task Function
  def handleNewTask(action: ActionEvent): Unit = {
    val task = new Task("", Develop)
    val okClicked = MainApp.showeditlist(task)
    if (okClicked) {
      MainApp.taskData += task
      task.save() match {
        case Success(_) => // Task saved successfully
        case Failure(e) =>
          new Alert(AlertType.Information) {
            initOwner(MainApp.stage)
            title = "Fail to save"
            headerText = "Failure to save Task"
            contentText = "Something went wrong with the database"
          }.showAndWait()
      }
    }
  }

  // Edit Task Function
  def handledittask(action: ActionEvent): Unit = {
    val selectedTask = taskTable.selectionModel().selectedItem.value
    if (selectedTask != null) {
      val okClicked = MainApp.showeditlist(selectedTask)
      if (okClicked) {
        showTaskDetails(Some(selectedTask))
        selectedTask.save() match {
          case Success(_) =>
            taskTable.refresh()  // Refresh the TableView after saving
          case Failure(e) =>
            new Alert(AlertType.Information) {
              initOwner(MainApp.stage)
              title = "Fail to edit"
              headerText = "Failure to edit Task"
              contentText = "Something went wrong with the database"
            }.showAndWait()
        }
      }
    } else {
      new Alert(AlertType.Warning) {
        initOwner(MainApp.stage)
        title = "No Selection"
        headerText = "No Task Selected"
        contentText = "Please select a task in the table."
      }.showAndWait()
    }
  }


  // Single method to update task status using polymorphism
  private def updateTaskStatus(newStatus: TaskStatus): Unit = {
    val selectedTask = taskTable.selectionModel().selectedItem.value
    if (selectedTask != null) {
      selectedTask.choice.value = newStatus
      selectedTask.save() match { // Save changes to the database
        case Success(_) =>
          taskTable.refresh()
          showTaskDetails(Some(selectedTask)) // Update the displayed details

          // Check if the task was marked as completed
          if (newStatus == Completed) {
            MainApp.userPoints.value += 10 // Add 10 points
            regMesg.text = s"Earned 10 points. Total points: ${MainApp.userPoints.value}"
          }

        case Failure(e) =>
          new Alert(AlertType.Information) {
            initOwner(MainApp.stage)
            title = "Fail to save"
            headerText = "Failure to save Task"
            contentText = "Something went wrong with the database"
          }.showAndWait()
      }
    } else {
      new Alert(AlertType.Warning) {
        initOwner(MainApp.stage)
        title = "No Selection"
        headerText = "No Task Selected"
        contentText = "Please select a task in the table."
      }.showAndWait()
    }
  }

  // Handlers for the status update buttons
  def handleComplete(action: ActionEvent): Unit = {
    updateTaskStatus(Completed)
  }

  def handleDue(action: ActionEvent): Unit = {
    updateTaskStatus(Due)
  }

  def handleDevelop(action: ActionEvent): Unit = {
    updateTaskStatus(Develop)
  }

  def handleUpcoming(action: ActionEvent): Unit = {
    updateTaskStatus(Upcoming)
  }

  def handleMinigame(): Unit = {
    MainApp.showMinigame()
  }
}

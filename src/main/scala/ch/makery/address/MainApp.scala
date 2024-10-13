package ch.makery.address

import ch.makery.address.model.Task
import ch.makery.address.util.Database
import ch.makery.address.view.editlistController
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.Includes._
import scalafxml.core.{FXMLLoader, NoDependencyResolver}
import javafx.{scene => jfxs}
import scalafx.beans.property.{IntegerProperty, StringProperty}
import scalafx.collections.ObservableBuffer
import scalafx.stage.{Modality, Stage}

object MainApp extends JFXApp {
  // Points accumulated by the user
  var userPoints = IntegerProperty(0)
   // The data as an observable list of tasks.
   //Constructor
  Database.setupDB()
  val taskData = new ObservableBuffer[Task]()
  taskData ++= Task.getAllTasks


  // Transform path of RootLayout.fxml to URI for resource location.
  val rootResource = getClass.getResource("view/RootLayout.fxml") // Relative value
  // Initialize the loader object.
  val loader = new FXMLLoader(rootResource, NoDependencyResolver)
  // Load root layout from fxml file.
  loader.load()
  // Retrieve the root component BorderPane from the FXML
  val roots = loader.getRoot[jfxs.layout.BorderPane]
  // Initialize stage
  stage = new PrimaryStage {
    title = "Meow To Do List"
    scene = new Scene {
      root = roots
    }
  }

// show login page
  def showLogin(): Unit = {
    val resource = getClass.getResource("view/Login.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  // show Signup page
  def showSignup(): Unit = {
    val resource = getClass.getResource("view/Signup.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  //show Login function
  def showloginfunction(): Unit = {
    val resource = getClass.getResource("view/loginfunction.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  //show Signup Function
  def showSignupFunction(): Unit = {
    val resource = getClass.getResource("view/SignUpFunction.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  //show Welcome page
  def showWelcome(): Unit = {
    val resource = getClass.getResource("view/Welcome.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  //show the main page
  def showTodolist(): Unit = {
    val resource = getClass.getResource("view/Todolist.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }

  //show Mini Game page
  def showMinigame(): Unit = {
    val resource = getClass.getResource("view/Minigame.fxml")
    val loader = new FXMLLoader(resource, NoDependencyResolver)
    loader.load()
    val roots = loader.getRoot[jfxs.layout.AnchorPane]
    this.roots.setCenter(roots)
  }


//show Edit List in the Main page
  def showeditlist(task: Task): Boolean = {
    val resource = getClass.getResourceAsStream("view/editlist.fxml")
    val loader = new FXMLLoader(null, NoDependencyResolver)
    loader.load(resource);
    val roots2  = loader.getRoot[jfxs.Parent]
    val control = loader.getController[editlistController#Controller]

    val dialog = new Stage() {
      initModality(Modality.ApplicationModal)
      initOwner(stage)
      scene = new Scene {
        stylesheets += getClass.getResource("view/DarkTheme.css").toString
        root = roots2
      }
    }
    control.dialogStage = dialog
    control.task = task
    dialog.showAndWait()
    control.okClicked
  }





  showWelcome()
}

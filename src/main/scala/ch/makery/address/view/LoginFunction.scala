package ch.makery.address.view

import ch.makery.address.MainApp
import scalafx.scene.control.{Label, TextField}
import scalafxml.core.macros.sfxml


@sfxml
class LoginFunction  (private var firstNameField: TextField,
                      private var lastNameField: TextField,
                      private var pwfield: TextField,
                      regMesg: Label)  extends GeneralController(regMesg){

  def gotomainpage(): Unit = {
    if (firstNameField.getText.nonEmpty && pwfield.getText.nonEmpty && lastNameField.getText.nonEmpty) {
      MainApp.showTodolist()
    }

    // Ensure all fields are filled
    else {
      showMessage("Please fill in all fields.")
    }
  }

//back to Login page
  def backtologin(): Unit = {
    MainApp.showLogin()
  }
}

package ch.makery.address.view

import ch.makery.address.MainApp
import scalafx.scene.control.{Label, PasswordField, TextField}
import scalafxml.core.macros.sfxml


@sfxml
class SignupFunction(private var firstNameField: TextField,
                       private var lastNameField: TextField,
                       private var pwfield: PasswordField,
                       private var cpwfield: PasswordField,
                       regMesg: Label) extends GeneralController(regMesg) {

  def confirm(): Unit = {
    // Ensure all fields are filled
    if (firstNameField.getText.nonEmpty && lastNameField.getText.nonEmpty &&
      pwfield.getText.nonEmpty && cpwfield.getText.nonEmpty) {

      // Check if passwords match
      if (pwfield.getText == cpwfield.getText) {
        showMessage("Successfully created account!")
      } else {
        showMessage("Passwords do not match")
      }

    } else {
      // If not all fields are filled
      showMessage("Please fill in all fields.")
    }
  }

  //back to Signup page
  def gobacktosignup(): Unit = {
    MainApp.showSignup()
  }

  //back to Login pageS
  def backtologin(): Unit = {
    MainApp.showloginfunction()
  }
}


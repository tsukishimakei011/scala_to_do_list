package ch.makery.address.view

import ch.makery.address.MainApp
import scalafxml.core.macros.sfxml


@sfxml
class SignupController {

  //Go to Sign Up Page
  def gotoSignup(): Unit = {
    MainApp.showSignupFunction()
  }



}
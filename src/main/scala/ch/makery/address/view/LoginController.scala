package ch.makery.address.view

import ch.makery.address.MainApp
import scalafxml.core.macros.sfxml


@sfxml
class LoginController  {

//Go to Sign Up Page
  def getSignin(): Unit = {
    MainApp.showSignup()
  }

  //Show login function
  def getlogin(): Unit = {
    MainApp.showloginfunction()
  }



}
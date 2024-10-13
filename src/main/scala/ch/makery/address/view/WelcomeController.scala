package ch.makery.address.view

import ch.makery.address.MainApp
import scalafxml.core.macros.sfxml


@sfxml
class WelcomeController {

  //go to Login Page
  def getStart(): Unit = {
    MainApp.showLogin()
  }
}
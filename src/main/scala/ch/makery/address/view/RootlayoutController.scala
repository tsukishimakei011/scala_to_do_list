package ch.makery.address.view

import ch.makery.address.MainApp
import scalafxml.core.macros.sfxml

@sfxml
class RootlayoutController {

  //Back to Login page
  def handleLogout(): Unit = {
    MainApp.showLogin()
  }

}
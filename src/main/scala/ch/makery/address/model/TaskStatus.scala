package ch.makery.address.model

import scalafx.beans.property.StringProperty

// Define a sealed trait TaskStatus, which will be the base type for all task statuses.
sealed trait TaskStatus {
  def status: String
}

// Case object representing the "Develop" task status.
case object Develop extends TaskStatus {
  val status = "Develop"
}

// Case object representing the "Due" task status.
case object Due extends TaskStatus {
  val status = "Due"
}

// Case object representing the "Upcoming" task status.
case object Upcoming extends TaskStatus {
  val status = "Upcoming"
}

// Case object representing the "Completed" task status.
case object Completed extends TaskStatus {
  val status = "Completed"
}

// Companion object for the TaskStatus trait.
object TaskStatus {
  // Method to convert a string into the corresponding TaskStatus case object.
  def withName(name: String): TaskStatus = name match {
    case "Develop" => Develop
    case "Due" => Due
    case "Upcoming" => Upcoming
    case "Completed" => Completed
    case _ => throw new IllegalArgumentException(s"Unknown status: $name") // Handle unknown strings by throwing an exception.
  }
}

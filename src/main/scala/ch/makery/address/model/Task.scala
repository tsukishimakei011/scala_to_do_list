package ch.makery.address.model

import scalafx.beans.property.{StringProperty, ObjectProperty}
import java.time.LocalDate
import ch.makery.address.util.Database
import ch.makery.address.util.DateUtil._
import scalikejdbc._
import scala.util.{Try, Success, Failure}

// The Task class represents a task with a name, due date, comment, and status.
// It includes methods to save, delete, and check the existence of the task in the database.
class Task(val taskNameS: String, val choiceS: TaskStatus = Develop) extends Database {

  // Auxiliary constructor for creating an empty Task
  def this() = this(null, Develop)

  // StringProperty to hold the task name
  var taskName = new StringProperty(taskNameS)

  // ObjectProperty to hold the due date, initialized to a specific date
  var date = ObjectProperty[LocalDate](LocalDate.of(1999, 2, 21))

  // StringProperty to hold the comment associated with the task
  var comment = new StringProperty("Let's Jia You")

  // ObjectProperty to hold the task's status (e.g., Develop, Due)
  var choice = ObjectProperty[TaskStatus](choiceS)

  // Method to save the task to the database, returning a Try[Int] indicating success or failure
  def save(): Try[Int] = {
    if (!isExist) {
      Try(DB autoCommit { implicit session =>
        sql"""
          INSERT INTO task (taskName, date, comment, choice) VALUES
          (${taskName.value}, ${date.value.asString}, ${comment.value}, ${choice.value.status})
        """.update.apply()
      }).recover({
        case e: Exception =>
          e.printStackTrace()
          0
      })
    } else {
      Try(DB autoCommit { implicit session =>
        sql"""
          UPDATE task
          SET
            date = ${date.value.asString},
            comment = ${comment.value},
            choice = ${choice.value.status}
          WHERE taskName = ${taskName.value}
        """.update.apply()
      })
    }
  }

  // Method to delete the task from the database if it exists
  def delete(): Try[Int] = {
    if (isExist) {
      Try(DB autoCommit { implicit session =>
        sql"""
          DELETE FROM task WHERE taskName = ${taskName.value}
        """.update.apply()
      })
    } else {
      throw new Exception("Task not exists in Database")
    }
  }

  // Method to check if the task already exists in the database
  def isExist: Boolean = {
    DB readOnly { implicit session =>
      sql"""
        SELECT * FROM task WHERE taskName = ${taskName.value}
      """.map(rs => rs.string("taskName")).single.apply()
    } match {
      case Some(_) => true
      case None => false
    }
  }
}

// Companion object for the Task class, providing additional methods
object Task extends Database {

  // Method to create a new Task with specific properties
  def apply(taskNameS: String, dateS: LocalDate, commentS: String, choiceS: TaskStatus): Task = {
    new Task(taskNameS, choiceS) {
      date.value = dateS
      comment.value = commentS
    }
  }

  // Method to initialize the task table in the database
  def initializeTable(): Unit = {
    DB autoCommit { implicit session =>
      sql"""
        CREATE TABLE task (
          id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
          taskName VARCHAR(64),
          date VARCHAR(64),
          comment VARCHAR(200),
          choice VARCHAR(64)
        )
      """.execute.apply()
    }
  }

  // Method to retrieve all tasks from the database
  def getAllTasks: List[Task] = {
    DB readOnly { implicit session =>
      sql"SELECT * FROM task".map(rs => Task(
        rs.string("taskName"),
        rs.string("date").parseLocalDate,
        rs.string("comment"),
        TaskStatus.withName(rs.string("choice"))
      )).list.apply()
    }
  }
}

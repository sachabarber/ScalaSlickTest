package org.com.barbers.slicktest

import com.typesafe.slick.driver.ms.SQLServerDriver.api._
import scala.concurrent.Future
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.async.Async.{async, await}

object TableResultHelper {

  def selectTwoItems(db: Database) : Unit = {

    implicit val session: Session = db.createSession()
    val q =  Items.items.take(2)
    val futureDB : Future[Seq[DBItem]] = db.run(q.result)

    async {
      val sqlData = await(futureDB)
      val item = sqlData.head
      println(s"TableResultRunner.selectTwoItems()[0] " +
        s"Id: ${item.id}, Description: ${item.description}, " +
        s"Cost: ${item.cost}, WarehouseLocationId: ${item.warehouseLocationId}")
    } onFailure {
      case e => {
        println(s"ERROR : $e")
      }
    }
  }

  def insertSeveralItems(db: Database, items : List[DBItem]) : Unit = {

    implicit val session: Session = db.createSession()
    val insertActions = DBIO.seq(
      (Items.items ++= items.toSeq).transactionally
    )
    val sql = Items.items.insertStatement
    val futureDB : Future[Unit] = db.run(insertActions)

    async {
      await(futureDB)
      println(s"TableResultRunner.insertSeveralItems() DONE")
    } onFailure {
      case e => {
        println(s"ERROR : $e")
      }
    }
  }

  def saveItem(db: Database, item: DBItem) = {

    val action =(Items.items returning Items.items.map(_.id)) +=
      DBItem(-1, item.description, item.cost, item.warehouseLocationId)
    val futureDB : Future[Int] = db.run(action)

    async {
      val savedItemId = await(futureDB)
      println(s"TableResultRunner.saveItem() savedItem.Id ${savedItemId}")
    } onFailure {
      case e => {
        println(s"ERROR : $e")
      }
    }
  }

  def deleteRandomItem(db: Database) = {

    async {
      val q =  Items.items.take(1)
      val futureDB : Future[Seq[DBItem]] = db.run(q.result)
      val sqlData = await(futureDB)
      val item = sqlData.head
      val deleteFuture : Future[Unit] = db.run(
        Items.items.filter(_.id === item.id).delete).map(_ => ())
      await(deleteFuture)
      println(s"TableResultRunner.deleteRandomItem() deleted item.Id ${item.id}")
    } onFailure {
      case e => {
        println(s"ERROR : $e")
      }
    }
  }


  def updateItemCost(db: Database, description : String, cost : Double) = {

    async {
      val q = Items.items
        .filter(_.description === description)
        .map(_.cost)
        .update(cost)

      val futureDB = db.run(q)
      val done = await(futureDB)
      println(s"Update cost of ${description}, to ${cost}")

      val q2 = for { p <- Items.items if p.description === description } yield p
      val futureDBQuery : Future[Seq[DBItem]] = db.run(q2.result)
      val items = await(futureDBQuery)
      items.map(item => println(s"TableResultRunner.updateItemCost The item is now $item") )
    } onFailure {
      case e => {
        println(s"ERROR : $e")
      }
    }
  }


  def findItemById(db: Database,id : Int) = {

    async {
      val q = for { p <- Items.items if p.id === id } yield p
      val futureDBQuery : Future[Option[DBItem]] = db.run(q.result.headOption)
      val item : Option[DBItem] = await(futureDBQuery)
      println(s"OPTION ${item}")
      item match {
        case Some(x) =>  println(s"TableResultRunner.findItemById The item is $x")
        case _ => ()
      }
    } onFailure {
      case e => {
        println(s"ERROR : $e")
      }
    }
  }

}





package org.com.barbers.slicktest

import com.typesafe.slick.driver.ms.SQLServerDriver.api._
import scala.collection.immutable
import scala.concurrent.Future
import scala.util.{Failure, Success}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.async.Async.{async, await}

object PlainSQLHelper {

  def selectScalarObject(db:Database) : Unit = {

    val action = sql"""Select count(*) as 'sysobjectsCount'  from sysobjects""".as[Int]
    val futureDB : Future[Vector[Int]] = db.run(action)

    async {
      val sqlData = await(futureDB)
      val count = sqlData.head
      println(s"PlainSQLHelper.selectScalarObject() sysobjectsCount: $count")
    } onFailure {
      case e => {
        println(s"ERROR : $e")
      }
    }
  }


  def selectTupleObject(db: Database) : Unit = {

    val action = sql"""Select count(*)  as 'sysobjectsCount', count(*)/10  as 'sysobjectsCountDiv10' from sysobjects""".as[(Int,Int)]
    val futureDB : Future[Vector[(Int,Int)]] = db.run(action)

    async {
      val sqlData = await(futureDB)
      val (x,y) = sqlData.head
      println(s"PlainSQLHelper.selectTupleObject() sysobjectsCount: $x, sysobjectsCountDiv10: $y")
    } onFailure {
      case e => {
        println(s"ERROR : $e")
      }
    }
  }


  def selectRawTableObject(db: Database) : Unit = {

    val action = sql"""Select * from Items""".as[(Int,String, Double, Int)]
    val futureDB : Future[Vector[(Int,String, Double, Int)]] = db.run(action)

    async {
      val sqlData = await(futureDB)
      val (id,desc, cost, location) = sqlData.head
      val item = RawSQLItem(id,desc, cost, location)
      println(s"PlainSQLHelper.selectRawTableObject() Id: ${item.id}, Description: ${item.description}, Cost: ${item.cost}, WarehouseLocation: ${item.warehouseLocationId}")
    } onFailure {
      case e => {
        println(s"ERROR : $e")
      }
    }
  }


  case class RawSQLItem(id: Int, description: String, cost: Double,  warehouseLocationId: Int)

}

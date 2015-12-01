package org.com.barbers.slicktest

import com.typesafe.slick.driver.ms.SQLServerDriver.api._

object Items {
  val items = TableQuery[Items]
}

case class DBItem(id: Int, description: String, cost: Double,  warehouseLocationId: Int)

class Items(tag: Tag) extends Table[DBItem](tag, "Products") {
  def id = column[Int]("Id", O.PrimaryKey, O.AutoInc)
  def description = column[String]("Description")
  def cost = column[Double]("Cost")
  def warehouseLocationId = column[Int]("WarehouseLocationId")
  def * = (id, description, cost, warehouseLocationId) <> (DBItem.tupled, DBItem.unapply)
}





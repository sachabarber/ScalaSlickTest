//package org.com.barbers.slicktest
//
//import com.typesafe.slick.driver.ms.SQLServerDriver.api._
//
//
//object Products {
//  val products = TableQuery[Products]
//}
//
//case class DBProduct(id: Int, description: String, cost: Double,  warehouseLocationId: Int)
//
//class Products(tag: Tag) extends Table[DBProduct](tag, "Products") {
//  def id = column[Int]("Id", O.PrimaryKey, O.AutoInc)
//  def description = column[String]("Description")
//  def cost = column[Double]("Cost")
//  def warehouseLocationId = column[Int]("WarehouseLocationId")
//  def * = (id, description, cost, warehouseLocationId) <> (DBProduct.tupled, DBProduct.unapply)
//}
//
//
//
//

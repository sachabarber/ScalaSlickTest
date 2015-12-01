package org.com.barbers.slicktest

import com.typesafe.slick.driver.ms.SQLServerDriver.api._

object SlickORMDemoApp extends App {


  val counter = 0
  val db = Database.forConfig("dms")

  // PLAIN SQL QUERIES
  PlainSQLHelper.selectScalarObject(db)
  PlainSQLHelper.selectTupleObject(db)
  PlainSQLHelper.selectRawTableObject(db)

  // INSERT multiple
  val itemsToInsert = List(
    DBItem(-1, "coffee", 7.99, 1),
    DBItem(-1, "baked beans", 1.99, 1))
  TableResultHelper.insertSeveralItems(db, itemsToInsert)

  //INSERT single
  TableResultHelper.saveItem(db, DBItem(-1, "milk", 0.99, 1))

  // SELECT
  TableResultHelper.selectTwoItems(db)

  // DELETE RANDOM
  TableResultHelper.deleteRandomItem(db)

  // UPDATE
  TableResultHelper.updateItemCost(db, "milk", 1.09)

  // GET BY ID
  TableResultHelper.findItemById(db, 5)


  //STORE PROC TEST
  StoredProcedureHelper.selectItems(db, "milk")

  System.in.read()
  ()

}


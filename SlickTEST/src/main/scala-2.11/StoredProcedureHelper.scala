package org.com.barbers.slicktest

import java.sql.ResultSet
import com.typesafe.slick.driver.ms.SQLServerDriver.api._

object StoredProcedureHelper {

  def selectItems(db: Database, description: String): Unit = {

    val sqlStatement = db.source.createConnection().prepareCall(
      "{ call [dbo].[sp_SelectItemsByDescription](?) }",
      ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY)

    sqlStatement.setFetchDirection(ResultSet.FETCH_FORWARD);
    sqlStatement.setString("@desc", description)

    val rs = sqlStatement.executeQuery()

    while (rs.next()) {
      val item = new DBItem(
        rs.getInt("Id"),
        rs.getString("Description"),
        rs.getDouble("Cost"),
        rs.getInt("WarehouseLocationId"))

      println(s"StoredProcedureHelper.selectProducts " +
        "using description set to ${desc} got this result : " +
        s"Id: ${item.id}, Description: ${item.description}, " +
        s"Cost: ${item.cost}, WarehouseLocationId: ${item.warehouseLocationId}")
    }

    rs.close()
    sqlStatement.close()
  }
}


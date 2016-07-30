/*
 * Copyright (c) 2016. Elder Research Inc. All rights reserved.
 */

package net.metasim.sdf

import utest._
import kantan.csv.ops._
import kantan.csv._

object io {
  def readCSV() = getClass.getResourceAsStream("/autompg.csv").asCsvReader[List[String]](',', true)
}

case class DataFrame()
object DataFrame {

}

object Scratch extends TestSuite {
  val tests = this {
    "do something" - {
      val rows = io.readCSV()
      assert(rows.nonEmpty)
    }
  }
}

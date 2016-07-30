/*
 * Copyright (c) 2016. Simeon H.K. Fitch. All rights reserved.
 */

package net.metasim

package object ggscala {
  trait DataMap[T] {

  }

  object ggplot {
    def apply[T: DataMap](data: T) = new PlotDef
  }

  case class PlotDef() {

  }
}

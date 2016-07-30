package net.metasim

/*
 * Copyright (C) 2016 Simeon H.K. Fitch. All rights reserved.
 */
package object sdf {
  type Tagged[U] = { type Tag = U }
  type @@[T, U] = T with Tagged[U]

}

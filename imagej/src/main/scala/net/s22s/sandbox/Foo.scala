package net.s22s.sandbox

import net.imagej.ops.OpService
import org.scijava.Context

object Foo {
  def main(args: Array[String]): Unit = {
    val context: Context = new Context(classOf[OpService])
    val ops: OpService = context.getService(classOf[OpService])



    val blank = ops.create().img(Array(150, 100))

    val formula = "10 * (Math.cos(0.3*p[0]) + Math.sin(0.3*p[1]))"
    val sinusoid = ops.image().equation(blank, formula)
    println(ops.image().ascii(sinusoid))

  }
}

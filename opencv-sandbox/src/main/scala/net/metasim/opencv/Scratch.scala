package net.metasim.opencv
import org.bytedeco.javacpp.opencv_core.CvType
import org.bytedeco.javacpp.opencv_core.Mat

object Scratch {
  def main(args: Array[String]): Unit = {
    val mat = Mat.eye(3, 3, CvType.CV_8UC1)
    println(mat.dump())
  }
}

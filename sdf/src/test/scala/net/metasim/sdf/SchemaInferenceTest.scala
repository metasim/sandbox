/*
 * Copyright (C) 2016 Simeon H.K. Fitch. All rights reserved.
 */

package net.metasim.sdf

import java.text.DateFormat
import java.util.Date

import org.apache.commons.lang3.math.NumberUtils
import org.scalacheck._
import org.scalacheck.Prop._
import org.scalacheck.Arbitrary._

import scala.collection.immutable.IndexedSeq
import scala.util.Try

object SchemaInferenceTest extends Properties("SchemaInference") {
  sealed trait StringTarget
  trait BS extends StringTarget
  trait IS extends StringTarget
  trait DS extends StringTarget
  trait DTS extends StringTarget
  trait SS extends StringTarget
  trait CS extends StringTarget

  type BooleanString = String @@ BS
  type IntegerString = String @@ IS
  type DoubleString =  String @@ DS
  type DateString = String @@ DTS
  type TextString = String @@ SS
  type CategoryMember = String @@ CS
  type Category = Set[CategoryMember]


  val boolStrGen = arbitrary[Boolean].map(_.toString.asInstanceOf[BooleanString])
  val intStrGen = arbitrary[Int].map(_.toString.asInstanceOf[IntegerString])
  val doubleStrGen = arbitrary[Double].map(_.toString.asInstanceOf[DoubleString])
  val dateStrGen = arbitrary[Date].map(DateFormat.getDateInstance().format(_).asInstanceOf[DateString])

  /** Free form text generator. */
  val textStrGen = for {
    s <- Gen.alphaStr
    // Only allow strings that don't parse as numbers
    if !NumberUtils.isNumber(s)
  } yield s.asInstanceOf[TextString]

  /** Creates strings of length 3 to 12. */
  val catMemberGen: Gen[CategoryMember] = Gen.choose(3, 12).flatMap(s ⇒
    Gen.listOfN(s, Gen.alphaChar).map(s ⇒ ("@" + s.mkString).asInstanceOf[CategoryMember])
  )

  /** Creates a bounded category. */
  val categoryGen: Gen[Category] = Gen.nonEmptyContainerOf[Set, CategoryMember](catMemberGen)

  def categoryMemberGen(category: Category): Gen[CategoryMember] = Gen.oneOf(category.toSeq)

  def chooseGen(category: Category): Gen[String] = {
    Gen.frequency(
      3 -> intStrGen,
      3 -> doubleStrGen,
      2 -> boolStrGen,
      1 -> dateStrGen,
      2 -> textStrGen,
      2 -> categoryMemberGen(category)
    )
  }

  /** Creates 1 to 20 column generators. */
  val schemaGen: Gen[Seq[Gen[String]]] = for {
    numCols ← Gen.choose(1, 20)
    category ← categoryGen
    schema ← Gen.containerOfN[Seq, Gen[String]](numCols, chooseGen(category))
  } yield schema


  val rowGen = for {
    schema ← schemaGen
    rowGen ← schema
    cell ← rowGen.sample
  } yield cell

//  val datasetGen = Gen.sized { size ⇒
//    Gen.list
//    for {
//      rows ← Gen.choose(10, 1000)
//    } yield for {
//      range ← Gen.const(0 until rows)
//      _ ← range
//      row ← rowGen
//    } yield row
//
////      for {
//    //rows ← Gen.choose(10, 1000)
////      _ ← (0 until rows).toSeq
////      row ← rowGen
////      s ← row
////    } yield s
//  }

  //-------------------------------------------------

  property("genInts") = forAll(intStrGen) { is: IntegerString ⇒
    is.toInt <= Int.MaxValue && is.toInt >= Int.MinValue
  }

  property("genDoubles") = forAll(doubleStrGen) { ds: DoubleString ⇒
    !ds.toDouble.isNaN
  }

  property("genDates") = forAll(dateStrGen) { ds: DateString ⇒
    Try(DateFormat.getDateInstance.parse(ds)).recover {
      case t: Throwable ⇒ println(t.getMessage); t
    }.isSuccess
  }

  property("genStrings") = forAll(textStrGen) { ss: TextString ⇒
    !NumberUtils.isNumber(ss)
  }

  property("genCategories") = forAll(categoryGen) { cs: Category ⇒
    cs.nonEmpty && cs.forall(_.nonEmpty)
  }

  property("genSchema") = forAll(schemaGen) { schema: Seq[Gen[String]] ⇒
    println("VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV")
    println("schema size: " + schema.size)
    val dataset = for(_ ← 0 until 3) yield for {
      colGen ← schema
      cell ← colGen.sample
    } yield cell

    pprint.pprintln(dataset)
    println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^")

    schema.nonEmpty
  }


  property("genDataset") = forAll(rowGen) { (ds) ⇒
    pprint.pprintln(ds)
    true
  }
}


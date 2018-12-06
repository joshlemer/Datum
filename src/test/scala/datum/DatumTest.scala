package datum

import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.Assertions._

class DatumTest extends FlatSpec with Matchers {

  case object A extends Field.valueType[Int]
  case object B extends Field.valueType[String]

  behavior of "toString"

  it should "display properly" in {

    def check(datum: Datum[Field])(expected: (String, String)*): Unit = {
      val result = datum.toString
      result should startWith ("Datum(")
      result should endWith (")")

      expected.foreach(tuple => assert(result contains(tuple._1 + " -> " + tuple._2)))
    }


    check(Datum())()
    check(Datum(A -> 1))("A" -> "1")
    check(Datum(A -> 1, B -> "hi"))("A" -> "1", "B" -> "hi")
  }

  behavior of "basic sealed trait fields"

  it should "basic example" in {
    sealed trait UserField extends Field

    object User extends FieldCompanion[UserField] {
      case object Age extends UserField with valueType[Int]
      case object Name extends UserField with valueType[String]
      case object Id extends UserField with valueType[Long]
      case object Address extends UserField with valueType[Datum[AddressField]]
    }
    sealed trait AddressField extends Field

    object AddressField extends FieldCompanion[AddressField] {
      case object IsApartment extends AddressField with valueType[Boolean]
      case object City extends AddressField with valueType[String]
      case object State extends AddressField with valueType[String]
      case object Province extends AddressField with valueType[String]
      case object Country extends AddressField with valueType[String]
      case object StreetNumber extends AddressField with valueType[Int]
    }

    val user = Datum(

    )


//    check(Datum())()
//    check(Datum(A -> 1))("A" -> "1")
//    check(Datum(A -> 1, B -> "hi"))("A" -> "1", "B" -> "hi")
  }


}

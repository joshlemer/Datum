package datum

import io.circe.JsonObject
import org.scalatest.{FlatSpec, Matchers}
import org.scalatest.Assertions._

class DatumTest extends FlatSpec with Matchers {


  val A = Field[Int]("a")
  val B = Field[String]("b")
  val C = Field[Char]("c")

  val x = Datum(
    A -> 1,
    B -> "hey"
  )

  val y = Datum(
    A -> 4,
    C -> 'x'
  )

  val z = x ++ y

  println(z) // Datum(a -> 4, b -> hey, c -> x)






//  case object A extends Field.valueType[Int]
//  case object B extends Field.valueType[String]
//
//  behavior of "toString"
//
//  it should "display properly" in {
//
//    def check(datum: Datum)(expected: (String, String)*): Unit = {
//      val result = datum.toString
//      result should startWith("Datum(")
//      result should endWith(")")
//
//      expected.foreach(tuple => assert(result contains (tuple._1 + " -> " + tuple._2)))
//    }
//
//    check(Datum())()
//    check(Datum(A -> 1))("A" -> "1")
//    check(Datum(A -> 1, B -> "hi"))("A" -> "1", "B" -> "hi")
//  }
//
//  behavior of "basic sealed trait fields"
//
//  it should "basic example" in {
//    sealed trait UserField extends Field
//
//    object User extends FieldCompanion[UserField] {
//      case object Age extends UserField with valueType[Int]
//      case object Name extends UserField with valueType[String]
//      case object Id extends UserField with valueType[Long]
//      case object Address extends UserField with valueType[Datum.ofField[AddressField]]
//    }
//    sealed trait AddressField extends Field
//
//    object AddressField extends FieldCompanion[AddressField] {
//      case object IsApartment extends AddressField with valueType[Boolean]
//      case object City extends AddressField with valueType[String]
//      case object State extends AddressField with valueType[String]
//      case object Province extends AddressField with valueType[String]
//      case object Country extends AddressField with valueType[String]
//      case object StreetNumber extends AddressField with valueType[Int]
//    }
//
//  }
//
//  {
//    import cats.syntax.functor._
//    import io.circe.{Decoder, Encoder}, io.circe.generic.auto._
//    import io.circe.syntax._
//    import io.circe.Json
//    import io.circe.Encoder._
//    import io.circe.Encoder.encodeInt
//
//    object DatumEncoder {
//
//      class FieldValueEncoder[F <: Field](field: F)(valueEncoder: Encoder[field.Value]) extends Encoder[FieldValue[]]
//
//      implicit def encoderForField[F <: Field](field: F)(implicit ev: Encoder[field.Value]): (F, Encoder[FieldValue[F]]) =
//        (field, ev.asInstanceOf[Encoder[FieldValue[F]]])
//
//
//      def forFields[F <: Field](fieldEncoders: (F, Encoder[FieldValue[F]])*): Encoder[Datum.ofField[F]] =
//        Encoder.instance(
//          datum =>
//            Json.fromFields(datum.flatMap { fv =>
//              fieldEncoders.find(_._1 == fv.field).map(enc => fv.field.toString -> enc._2.apply(fv))
//            })
//        )
//    }
//
//    sealed trait UserField extends Field
//
//    object User extends FieldCompanion[UserField] {
//      case object Age extends UserField with valueType[Int] {
//        def desc = "The age in years of the user"
//      }
//      case object Name extends UserField with valueType[String]
//      case object Id extends UserField with valueType[Long]
//      case object Address extends UserField with valueType[Datum.ofField[AddressField]]
//    }
//    sealed trait AddressField extends Field
//
//    object AddressField extends FieldCompanion[AddressField] {
//      case object IsApartment extends AddressField with valueType[Boolean]
//      case object City extends AddressField with valueType[String]
//      case object State extends AddressField with valueType[String]
//      case object Province extends AddressField with valueType[String]
//      case object Country extends AddressField with valueType[String]
//      case object StreetNumber extends AddressField with valueType[Int]
//    }
//
//
//    case class dyn[V](name: String) extends Field.valueType[V]
//
//    dyn[Int]("name") -> 1
//
//    d.get(dyn[String]("name"))
//
//
//    import User._
//    import DatumEncoder._
//
//    val userEncoder =
//      DatumEncoder.forFields[UserField](
//        Age,
////        Name,
//        Id,
//        Address
//      )
//
//    val d: Datum = ???
//
//    d.get(dyn("asdf"))
//
//
//    val R = Field.ofType[Char]
//
//    Datum(
//      Age -> 1,
//      Name -> "Josh"
//    ) ++ Datum(
//      Id -> 55,
//      Name -> "asdf",
//      R -> 'r'
//    )
//
//
//    trait X {
//
//      def foo: Int
//      def bar: Int
//
//      //
//    }
//
//    // Datum(Age -> 1, Name -> Josh, Id -> 55)
//
//    Iterable[Datum]()
//      .filter(col(Name).isEqualTo("Josh"))
//      .join(List(Datum()))(Name -> Name, Age -> Age )
//
//
//
//
//    def takesUser(u: Datum)(implicit ev0: Contains[u.type, Name.type]) = {
//      val s: Option[String] = u.get(Name)
//      s(Name)(...)
//    }
//
////    println(e(Datum()))
////    println(e(Datum(Name -> "josh")))
//
//
//    /*
//
//    DatumEncoder.forFields(
//    )
//
//     */
////    implicit def encodeField[F <: Field](field: F)(implicit ev: Encoder[field.Value]): Encoder[Datum]
////    object GenericDerivation {
////      implicit val encodeEvent: Encoder[Field] = Encoder.instance {
////        case foo @ Foo(_) => foo.asJson
////        case bar @ Bar(_) => bar.asJson
////        case baz @ Baz(_) => baz.asJson
////        case qux @ Qux(_) => qux.asJson
////      }
////
////      implicit val decodeEvent: Decoder[Event] =
////        List[Decoder[Event]](
////          Decoder[Foo].widen,
////          Decoder[Bar].widen,
////          Decoder[Baz].widen,
////          Decoder[Qux].widen
////        ).reduceLeft(_ or _)
////    }
//  }

}

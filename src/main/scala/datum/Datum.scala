package datum

trait Datum[+F <: Field] {

  def fields: Iterable[FieldValue[F]]

  def get[F0 >: F <: Field](field: F0): Option[field.Value]

  def +[F0 >: F <: Field](that: F0)(value: that.Value): Datum[F0]

  def +[F0 >: F <: Field](that: FieldValue[F0]): Datum[F0]

  def -[F0 >: F <: Field](that: F0): Datum[F0]

  override def toString: String =
    fields.iterator
      .map(kv => kv.field.toString + " -> " + kv.value.toString)
      .mkString("Datum(", ", ", ")")
}

object Datum {

  def apply[F <: Field](fieldValues: FieldValue[F]*): Datum[F] =
    new Impl(fieldValues.map(_.toTuple).toMap)

  class Impl[+F <: Field](_fields: Map[Field, Any]) extends Datum[F] {

    override def fields: Iterable[FieldValue[F]] =
      _fields.map {
        case (k, v) =>
          val k0 = k.asInstanceOf[F]
          k0 -> v.asInstanceOf[k0.Value]
      }

    override def get[FF >: F <: Field](field: FF): Option[field.Value] =
      _fields.get(field).map(_.asInstanceOf[field.Value])
    override def +[FF >: F <: Field](that: FF)(value: that.Value) =
      new Impl(_fields + (that -> value).toTuple)
    override def +[FF >: F <: Field](that: FieldValue[FF]) =
      new Impl[F](_fields + that.toTuple)
    override def -[FF >: F <: Field](that: FF) = new Impl(_fields - that)
  }
}

trait Field { fieldSelf =>
  type Value
  def ->(value: Value): FieldValue[this.type] = {
    val v = value
    new FieldValue[this.type] {
      override val field: fieldSelf.type = fieldSelf
      override val value: this.field.Value = v
    }
  }
}

object Field extends FieldCompanion[Field] {
  def ofType[V]: valueType[V] = new valueType[V] {}
}


trait FieldCompanion[F <: Field] {
  trait valueType[V] extends Field { this: F =>
    type Value = V
  }
}

trait FieldValue[+F <: Field] {
  val field: F

  val value: field.Value

  def toTuple: (F, field.Value) = (field, value)
}

object FieldValue {
//  implicit def fromTuple[F <: Field, V](fieldValue: (F, V))(implicit ev: V <:< fieldValue._1.Value): FieldValue[F] =
//    new FieldValue[fieldValue._1.type] {
//      override val field: fieldValue._1.type = fieldValue._1
//      override val value: fieldValue._1.Value = ev(fieldValue._2)
//    }
}

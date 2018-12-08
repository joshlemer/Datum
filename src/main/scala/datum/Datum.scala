package datum

trait Datum extends Iterable[Field#FieldValue] {

  def get(field: Field): Option[field.Value]

  def +(that: Field#FieldValue): Datum

  def ++(that: Iterable[Field#FieldValue]): Datum = that.foldLeft(this)(_ + _)

  def -(that: Field): Datum

  override def toString: String =
    iterator
      .map(kv => kv.field.toString + " -> " + kv.value.toString)
      .mkString("Datum(", ", ", ")")
}

object Datum {
  def apply(fieldValues: Field#FieldValue*): Datum = new Impl(fieldValues.map(_.toTuple).toMap)

  private class Impl[+F <: Field](_fields: Map[Field, Field#FieldValue]) extends Datum {
    override def iterator: Iterator[Field#FieldValue] = _fields.valuesIterator
    override def get(field: Field): Option[field.Value] = _fields.get(field).map(_.value.asInstanceOf[field.Value])
    override def +(that: Field#FieldValue) = {
      val newFields = _fields + ((that.field, that))
      if (newFields eq _fields) this else new Impl(newFields)
    }
    override def -(that: Field) = {
      val newFields = _fields - that
      if (newFields eq _fields) this else new Impl(newFields)
    }
  }
}

trait Field { fieldSelf =>
  type Value

  def name: String

  trait FieldValue {
    val field: fieldSelf.type = fieldSelf

    val value: field.Value

    def toTuple: (fieldSelf.type, FieldValue) = (field, this)
  }

  def ->(value: Value): this.FieldValue = {
    val v = value
    new this.FieldValue {
      override val field: fieldSelf.type = fieldSelf
      override val value: this.field.Value = v
    }
  }

  override final def equals(obj: Any): Boolean = this eq obj.asInstanceOf[AnyRef]

  override final def toString: String = name
}

object Field {
  private final class Impl[V](val name: String) extends Field {
    type Value = V
  }

  final def apply[V](name: String): Field { type Value = V } = new Impl[V](name)
}

trait FieldValue[+F <: Field] {
  val field: F

  val value: field.Value

  def toTuple: (F, field.Value) = (field, value)
}

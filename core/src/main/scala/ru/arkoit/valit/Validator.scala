package ru.arkoit.valit

/**
  * (c) Artyom Kozhemyakin <xenus.t@gmail.com>
  */
trait Validator[A] { self =>
  def validate(o: A): ValidationResult

  def &[C <: ValidationResult](v: => Validator[A]) = new Validator[A] {
    def validate(o: A): ValidationResult = self.validate(o) match {
      case ValidationSuccess => v.validate(o)
      case a: ValidationFailure => a
    }
  }

  def |[C <: ValidationResult](v: => Validator[A]) = new Validator[A] {
    def validate(o: A): ValidationResult = self.validate(o) match {
      case a: ValidationFailure => v.validate(o)
      case _ => ValidationSuccess
    }
  }

  def unary_!(): Validator[A] = new Validator[A] {
    override def validate(o: A): ValidationResult = self.validate(o) match {
      case ValidationSuccess => vfailure
      case _: ValidationFailure => vsuccess
    }
  }
}

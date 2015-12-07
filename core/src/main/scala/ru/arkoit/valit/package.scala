package ru.arkoit

/**
  * (c) Artyom Kozhemyakin <xenus.t@gmail.com>
  */
package object valit {
  val vsuccess = ValidationSuccess

  val vfailure = EmptyValidationFailure

  def v[A, R](f: A => R)(implicit c: R => ValidationResult) = new Validator[A] {
    override def validate(o: A): ValidationResult = c(f(o))
  }

  implicit def resultIdentity[R <: ValidationResult](r: R): R = r

  implicit def resultFromBoolean(r: Boolean): ValidationResult = if(r) vsuccess else vfailure

  implicit def anyToValidationContext[A](o: A): ValidationContext[A] = ValidationContext(o)

  implicit def functionToValidator[A](f: A => ValidationResult): Validator[A] = v(f)

  implicit def functionToValidator2[A](f: A => Boolean): Validator[A] = v(f)
}

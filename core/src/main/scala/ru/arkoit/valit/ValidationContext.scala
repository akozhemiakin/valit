package ru.arkoit.valit

/**
  * (c) Artyom Kozhemyakin <xenus.t@gmail.com>
  */
final case class ValidationContext[A] (o: A) {
  def validateWith(v: Validator[A]): ValidationResult = v.validate(o)
}

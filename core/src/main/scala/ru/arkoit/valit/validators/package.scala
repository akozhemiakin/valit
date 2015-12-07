package ru.arkoit.valit

/**
  * (c) Artyom Kozhemyakin <xenus.t@gmail.com>
  */
package object validators {
  def lessThen[T: Ordering](k: T)(implicit ev: Ordering[T]): Validator[T] = v((a: T) => ev.compare(a, k) < 0)

  def notLessThen[T: Ordering](k: T)(implicit ev: Ordering[T]): Validator[T] = v((a: T) => ev.compare(a, k) >= 0)

  def moreThen[T: Ordering](k: T)(implicit ev: Ordering[T]): Validator[T] = v((a: T) => ev.compare(a, k) > 0)

  def notMoreThen[T: Ordering](k: T)(implicit ev: Ordering[T]): Validator[T] = v((a: T) => ev.compare(a, k) <= 0)

  def eq[T, K](o: T): Validator[K] = v((a: K) => a == o)

  def regex(pattern: String): Validator[String] = v((s: String) => s.matches(pattern))

  def notEmpty: Validator[String] = v((s: String) => !s.isEmpty)

  def empty: Validator[String] = v((s: String) => s.isEmpty)
}

package ru.arkoit.valit

/**
  * (c) Artyom Kozhemyakin <xenus.t@gmail.com>
  */
sealed trait ValidationResult {
  def isSuccess = this == ValidationSuccess

  def isFailure = isInstanceOf[ValidationFailure]
}

object ValidationSuccess extends ValidationResult

trait ValidationFailure extends ValidationResult

object EmptyValidationFailure extends ValidationFailure

trait ValidationFailureWithMessage extends ValidationFailure {
  def message: String
}

case class DefaultValidationFailureWithMessage(message: String) extends ValidationFailureWithMessage
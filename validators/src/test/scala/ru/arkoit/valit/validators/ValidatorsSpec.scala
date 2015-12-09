package ru.arkoit.valit.validators

import org.scalatest.FlatSpec
import ru.arkoit.valit._

/**
  * (c) Artyom Kozhemyakin <xenus.t@gmail.com>
  */
class ValidatorsSpec extends FlatSpec {
  "lessThen validator" should "succeed in validating value less then the specified for comparison and fail otherwise" in {
    assert((10 validateWith lessThen(20)).isSuccess)
    assert((30 validateWith lessThen(20)).isFailure)
  }

  "moreThen validator" should "succeed in validating value more then the specified for comparison and fail otherwise" in {
    assert((30 validateWith moreThen(20)).isSuccess)
    assert((10 validateWith moreThen(20)).isFailure)
  }

  "notLessThen validator" should "succeed in validating value not less then the specified for comparison and fail otherwise" in {
    assert((30 validateWith notLessThen(20)).isSuccess)
    assert((20 validateWith notLessThen(20)).isSuccess)
    assert((10 validateWith notLessThen(20)).isFailure)
  }

  "notMoreThen validator" should "succeed in validating value not more then the specified for comparison and fail otherwise" in {
    assert((30 validateWith notMoreThen(20)).isFailure)
    assert((20 validateWith notMoreThen(20)).isSuccess)
    assert((10 validateWith notMoreThen(20)).isSuccess)
  }

  "equalTo validator" should "compare validating value with some other value using and return true if they are equal (in terms of Any.equals)" in {
    assert((10 validateWith equalTo(10)).isSuccess)

    val i = 10; val j = 10
    assert((i validateWith equalTo(j)).isSuccess)

    case class Bar(a: Int, b: String)
    assert((Bar(7, "foo") validateWith equalTo(Bar(7, "foo"))).isSuccess)
  }

  "regex validator" should "check if the validating value matches the provided regex" in {
    assert(("wazzuuuuuuuuuuuup" validateWith regex("|wazz[u]*p|")).isSuccess)
    assert(("wazzuuuuuuuuuuuup" validateWith regex("|woz[u]*p|")).isFailure)
  }

  "notEmpty validator" should "check that the string is not empty" in {
    assert(("foo" validateWith notEmpty).isSuccess)
    assert(("" validateWith notEmpty).isFailure)
  }

  "empty validator" should "check that the string is empty" in {
    assert(("foo" validateWith empty).isFailure)
    assert(("" validateWith empty).isSuccess)
  }
}

package ru.arkoit.valit

import org.scalatest.FlatSpec

/**
  * (c) Artyom Kozhemyakin <xenus.t@gmail.com>
  */
class ValitSpec extends FlatSpec {
  val vIsPositive = v((a: Int) => a > 0) // Validating that 'a' is positive
  val vIsOdd = v((a: Int) => a % 2 == 0) // Validating if 'a' is odd
  val vMoreThan100Abs = v((a: Int) => math.abs(a) > 100)

  "Any value" should "be able to be correctly validated with the appropriate function" in {
    val r = 10 validateWith vIsPositive
    assert(r.isSuccess)

    val r2 = -10 validateWith vIsPositive
    assert(r2.isFailure)
  }

  it should "be able to be validated with multiple functions combined with AND logic" in {
    val r = 10 validateWith (vIsPositive & vIsOdd)
    assert(r.isSuccess)

    val r2 = -10 validateWith (vIsPositive & vIsOdd)
    assert(r2.isFailure)

    val r3 = 9 validateWith (vIsPositive & vIsOdd)
    assert(r3.isFailure)
  }

  it should "be able to be validated with multiple functions combined with OR logic" in {
    val r = 10 validateWith (vIsPositive | vIsOdd)
    assert(r.isSuccess)

    val r2 = -10 validateWith (vIsPositive | vIsOdd)
    assert(r2.isSuccess)

    val r3 = 9 validateWith (vIsPositive | vIsOdd)
    assert(r3.isSuccess)

    val r4 = -5 validateWith (vIsPositive | vIsOdd)
    assert(r4.isFailure)
  }

  it should "be able to be validated by simple, not-wrapped A => Boolean function" in {
    val r = -10 validateWith ((a: Int) => a < 0)
    assert(r.isSuccess)
  }

  it should "be able to be validated by simple, not-wrapped A => ValidationResult function" in {
    val r = -10 validateWith ((a: Int) => if(a < 0) vsuccess else vfailure)
    assert(r.isSuccess)
  }

  it should "be able to be validated by multiple combined unwrapped A => Boolean or A => ValidationResult functions" in {
    val r = -10 validateWith (
      ((a: Int) => if(a < 0) vsuccess else vfailure) &
      ((a: Int) => a % 2 == 0)
    )
    assert(r.isSuccess)
  }

  "Validators combination logic" should "preserve logical operators precedence (& - first, | - second)" in {
    // T|T&T=T
    val r = 110 validateWith vIsPositive | vIsOdd & vMoreThan100Abs
    assert(r.isSuccess)

    // F|F&F=F
    val r2 = -9 validateWith vIsPositive | vIsOdd & vMoreThan100Abs
    assert(r2.isFailure)

    // T|F&F=T
    val r3 = 9 validateWith vIsPositive | vIsOdd & vMoreThan100Abs
    assert(r3.isSuccess)

    // F|T&F=F
    val r4 = -10 validateWith vIsPositive | vIsOdd & vMoreThan100Abs
    assert(r4.isFailure)

    // T|T&F=T
    val r5 = 10 validateWith vIsPositive | vIsOdd & vMoreThan100Abs
    assert(r5.isSuccess)

    // F|T&T=T
    val r6 = -110 validateWith vIsPositive | vIsOdd & vMoreThan100Abs
    assert(r6.isSuccess)
  }

  "Validators" should "be able to be negated with ! unary operator" in {
    val vIsNotPositive = !vIsPositive

    val r = -10 validateWith vIsNotPositive
    assert(r.isSuccess)

    val r2 = 0 validateWith vIsNotPositive
    assert(r2.isSuccess)

    val r3 = 10 validateWith vIsNotPositive
    assert(r3.isFailure)
  }
}

package fi.kapsi.kosmik.sfti.ch12

import org.scalatest.{FunSpec, Matchers}

class Chapter12Spec extends FunSpec with Matchers {
  describe("Exercise 01") {
    it("should produce elements for given range and function") {
      Ex01.values(x => x * x, -5, 5) shouldEqual
        List((-5, 25), (-4, 16), (-3, 9), (-2, 4), (-1, 1), (0, 0), (1, 1), (2, 4), (3, 9), (4, 16), (5, 25))
    }
  }

  describe("Exercise 02") {
    it("should find largest value") {
      Ex02.largest(Array(5, 2, -4, 7, 2, 7, 5)) shouldEqual 7
    }
  }

  describe("Exercise 03") {
    it("should produce factorial with reduce") {
      Ex03.factReduce(0) shouldEqual 1
      Ex03.factReduce(1) shouldEqual 1
      Ex03.factReduce(2) shouldEqual 2
      Ex03.factReduce(3) shouldEqual 6
      Ex03.factReduce(10) shouldEqual 3628800
    }
  }

  describe("Exercise 04") {
    it("should produce factorial with fold") {
      Ex04.factFold(0) shouldEqual 1
      Ex04.factFold(1) shouldEqual 1
      Ex04.factFold(2) shouldEqual 2
      Ex04.factFold(3) shouldEqual 6
      Ex04.factFold(10) shouldEqual 3628800
    }
  }

  describe("Exercise 05") {
    it("should find largest output") {
      Ex05.largest(x => 10 * x - x * x, 1 to 10) shouldEqual 25
    }
  }

  describe("Exercise 06") {
    it("should find the input at which the output is largest") {
      Ex06.largestAt(x => 10 * x - x * x, 1 to 10) shouldEqual 5
    }
  }

  describe("Exercise 07") {
    import fi.kapsi.kosmik.sfti.ch12.Ex07.adjustToPair

    it("should invoke a two parameter function") {
      (1 to 3) zip (3 to 5) map adjustToPair(_ + _) shouldEqual Vector(4, 6, 8)
    }
  }

  describe("Exercise 08") {
    import fi.kapsi.kosmik.sfti.ch12.Ex08.correspondingLengths

    it("should determine if given lengths correspond to string lengths") {
      correspondingLengths(Array(""), Array(0)) shouldBe true
      correspondingLengths(Array(""), Array(1)) shouldBe false
      correspondingLengths(Array(""), Array(0, 1)) shouldBe false
      correspondingLengths(Array("a"), Array(1)) shouldBe true
      correspondingLengths(Array("a"), Array(2)) shouldBe false
      correspondingLengths(Array("a"), Array(1, 1)) shouldBe false
      correspondingLengths(Array("a", "qwer", "zx"), Array(1, 4, 2)) shouldBe true
      correspondingLengths(Array("a", "qwer", "zx"), Array(1, 4, 3)) shouldBe false
    }
  }

  describe("Exercise 09") {
    import fi.kapsi.kosmik.sfti.ch12.Ex09.Corresponder

    it("should call non-currying corresponds with predicate function") {
      // This would not work because of "missing parameter type for expanded function..."
      // new Corresponder(Array("a", "qwer", "zx"))
      //   .corresponds(Array(1, 4, 2), _.length == _) shouldBe true


      // Neither would this. This fails due to "missing parameter type..."
      // new Corresponder(Array("a", "qwer", "zx"))
      //   .corresponds(Array(1, 4, 2), (a, b) => a.length == b) shouldBe true

      // So you need to explicitly declare the parameter types for the predicate function. This is because otherwise
      // the type inferencer does not have enough information to figure out the types. The stock version of
      // corresponds, that utilizes currying, does not suffer from this problem.
      new Corresponder(Array("a", "qwer", "zx"))
        .corresponds(Array(1, 4, 2), (a: String, b: Int) => a.length == b) shouldBe true
      new Corresponder(Array("a", "qwer", "zx"))
        .corresponds(Array(1, 4, 345), (a: String, b: Int) => a.length == b) shouldBe false
    }
  }

  describe("Exercise 10") {
    it("should do unless with call-by-name") {
      import fi.kapsi.kosmik.sfti.ch12.Ex10.{callByNameUnless => unless}

      var lessThan5Count = 0
      for (i <- 1 to 7) {
        unless(i >= 5) {
          lessThan5Count += 1
        }
      }

      lessThan5Count shouldEqual 4
    }

    it("should do unless with call-by-value") {
      import fi.kapsi.kosmik.sfti.ch12.Ex10.{callByValueUnless => unless}

      var lessThan5Count = 0
      for (i <- 1 to 7) {
        // Syntax is a bit uglier but otherwise call-by-value works in this case just as well as call-by-name.
        // This is because the condition function value is evaluated only once per "unless" block evaluation.
        unless(() => i >= 5) {
          lessThan5Count += 1
        }
      }

      lessThan5Count shouldEqual 4
    }

    it("should do unless with non-currying implementation") {
      import fi.kapsi.kosmik.sfti.ch12.Ex10.{nonCurryingUnless => unless}

      var lessThan5Count = 0
      for (i <- 1 to 7) {
        // Note that the invocation syntax is different when "unless" does not utilize currying.
        unless(i >= 5, {
          lessThan5Count += 1
        })
      }

      lessThan5Count shouldEqual 4
    }
  }
}
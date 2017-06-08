package calc

import org.scalacheck.Arbitrary
import org.scalacheck.Arbitrary._
import org.scalacheck.Prop._
import org.scalatest.{Matchers, WordSpec}
import org.scalatest.prop.Checkers



class ExpressionSpec extends WordSpec with Matchers with Checkers {

  implicit val arbitraryConstant = Arbitrary( 
    for {
      num <- arbitrary[Int]
    } yield Constant(num)
  )

  "Expression" should {

    "evaluate Add(1, 1) as 2" in {
      Add(Constant(1), Constant(1)).eval should be(2)
    }

    "evaluate Mult(2, 0) as 0" in {
      Mult(Constant(2), Constant(0)).eval should be(0)
    }

  }

  "Addition" should {
    "be commutative" in {
      check{ (c1: Constant, c2: Constant) => 
        Add(c1,c2).eval == Add(c2, c1).eval
      }
    }

    "have 0 as neutral element" in {
      check{ (c1: Constant) =>
        Add(c1, Constant(0)).eval == c1.eval
      }
    }
  }

  "Multiplication" should {
    "be commutative" in {
      check { (c1: Constant, c2: Constant) =>
        Mult(c1, c2).eval == Mult(c2, c1).eval
      }
    }

    "have 1 as neutral element" in {
      check { (c1: Constant) =>
        Mult(c1, Constant(1)).eval == c1.eval
      }
    }

    "have 0 as nil element" in {
      check { (c1: Constant) =>
        Mult(c1, Constant(0)).eval == 0 
      }
    }
  }
}

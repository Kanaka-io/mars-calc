package calc 

import org.scalatest.{ EitherValues, Matchers, WordSpec }


class ParserSpec extends WordSpec with Matchers with EitherValues {

  "Parser" should {

    "accept single constants" in {
      val res = Parser.parse(List(Literal("0")))
      res should be ('right)
      res.right.get should be(Constant(0))
    }

    "accept simple addition" in {
      val res = Parser.parse(List(Literal("1"), Plus, Literal("2")))
      res should be ('right)
      res.right.get should be(Add(Constant(1), Constant(2)))
    }

    "accept simple multiplication" in {
      val res = Parser.parse(List(Literal("1"), Times, Literal("2")))
      res should be ('right)
      res.right.get should be(Mult(Constant(1), Constant(2)))
    }

    "work with nested operations" in {
      val res = Parser.parse(List(Literal("1"), Plus, Literal("4"), Times, Literal("2")))
      res should be('right)
      res.right.get should be(Add(Constant(1), Mult(Constant(4), Constant(2))))
    }
    
  }

}

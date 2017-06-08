package calc

import org.scalatest.{ FlatSpec, Matchers }



class TokenizerSpec extends FlatSpec with Matchers {

  it should "tokenize '1 + 4'" in {
    Tokenizer.tokenize("1 + 4") should be(Right(List(Literal("1"), Plus, Literal("4"))))
  }

  it should "tokenize '2 * 4 + 3'" in {
    Tokenizer.tokenize("2 * 4 + 3") should be(Right(List(Literal("2"), Times, Literal("4"), Plus, Literal("3"))))
  }

  it should "return an error on '1 + toto'" in {
    Tokenizer.tokenize("1 + toto") should be(Left("Unexpected character t"))
  }

  "tokens" should "be duplicated if repeated in the input string" in {
    Tokenizer.tokenize("1 ++ 2") should be(Right(List(Literal("1"), Plus, Plus, Literal("2"))))
  }
}

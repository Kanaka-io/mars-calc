package calc 

import scala.annotation.tailrec

/*
 "1 + 1" -> List[Token]
 */
sealed trait Token 
case object Plus extends Token
case object Times extends Token
final case class Literal(str: String) extends Token


object Tokenizer {


  @tailrec
  private def helper(str: String, acc: List[Token]): Either[String, List[Token]] = {
    if(str.isEmpty()) Right(acc.reverse)
    else str.head match {
      case c if c.isDigit =>
        helper(str.dropWhile(_.isDigit), Literal(str.takeWhile(_.isDigit)) :: acc)
      case c if c.isWhitespace =>
        helper(str.dropWhile(_.isWhitespace), acc)
      case '+' => 
        helper(str.tail , Plus :: acc)
      case '*' => 
        helper(str.tail, Times :: acc)
      case x => Left(s"Unexpected character $x")
    }
  }

  // "1 + 1" -> List(Literal("1"), Plus, Literal("1"))
  def tokenize(input: String): Either[String, List[Token]] = {

    helper(input, Nil)
  }

}

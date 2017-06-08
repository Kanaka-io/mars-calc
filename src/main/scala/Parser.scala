package calc

import scala.annotation.tailrec



sealed trait HalfExpr {
  def toExpr(rhs: Expr): Expr
}

final case class HalfAdd(lhs: Expr) extends HalfExpr {
  def toExpr(rhs: Expr) = Add(lhs, rhs)
}
final case class HalfMult(lhs: Expr) extends HalfExpr {
  def toExpr(rhs: Expr) = Mult(lhs, rhs)
}




object Parser {

  @tailrec
  private def helper(tokens: List[Token], stack: List[HalfExpr], last: Option[Expr]): Either[String, Expr] = {
    if(tokens.isEmpty && stack.isEmpty && last.nonEmpty) Right(last.get)
    else {
      (tokens, last) match {
        case (Literal(x) :: Nil, None)           => helper(Nil, stack, Some(Constant(x.toInt)))
        case (Literal(x) :: Plus :: tail, None)  => helper(tail, HalfAdd(Constant(x.toInt)) :: stack, None)
        case (Literal(x) :: Times :: tail, None) => helper(tail, HalfMult(Constant(x.toInt)) :: stack, None)
        case (Nil, Some(expr))                   => helper(Nil, stack.tail, Some(stack.head.toExpr(expr)))
        case (x :: _, _)                         => Left(s"Unexpected token $x")
        case (Nil, None)                         => Left("Unexpected end of input")
      }
    }
  }

  def parse(tokens: List[Token]): Either[String, Expr] = 
    helper(tokens, Nil, None)
}

object Calculator {

  def apply(input: String): Either[String, Int] = 
    for {
      tokens <- Tokenizer.tokenize(input).right
      expr   <- Parser.parse(tokens).right
    } yield expr.eval

}

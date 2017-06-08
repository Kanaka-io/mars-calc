package calc



object Parser {

  type Result = Option[Either[String, Expr]]

  def parse(tokens: List[Token]): Either[String, Expr] = 
    tokens              // 1 + 4 * 2
      .sliding(2, 2)    // Iterator(List(1, +), List(4, *), List(2))
      .foldRight[Result](None){ (p, e) => (p, e) match {
          case (_, err @ Some(Left(_))) => err
          case (Literal(x) :: Nil, None) => Some(Right(Constant(x.toInt)))
          case (Literal(x) :: Plus :: _, Some(Right(expr))) => Some(Right(Add(Constant(x.toInt), expr)))    
          case (Literal(x) :: Times :: _, Some(Right(expr))) => Some(Right(Mult(Constant(x.toInt), expr)))
          case ( x, _ ) => Some(Left(s"Unexpected token ${x.last}"))
        }
      }
      .getOrElse(Left("Unexpected end of input"))

}

object Calculator {

  def apply(input: String): Either[String, Int] = 
    for {
      tokens <- Tokenizer.tokenize(input).right
      expr   <- Parser.parse(tokens).right
    } yield expr.eval

}

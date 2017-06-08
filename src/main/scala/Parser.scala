package calc

object Parser {

  def parse(tokens: List[Token]): Either[String, Expr] = ???

}

object Calculator {

  def apply(input: String): Either[String, Int] = 
    for {
      tokens <- Tokenizer.tokenize(input).right
      expr   <- Parser.parse(tokens).right
    } yield expr.eval

}

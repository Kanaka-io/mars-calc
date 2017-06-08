package calc

sealed trait Expr {
  def eval: Int
}

final case class Constant(value: Int) extends Expr {
  def eval = value
}

final case class Add(lhs: Expr, rhs: Expr) extends Expr {
  def eval = lhs.eval + rhs.eval
}

final case class Mult(lhs: Expr, rhs: Expr) extends Expr {
  def eval = lhs.eval * rhs.eval
}

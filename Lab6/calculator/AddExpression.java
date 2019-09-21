class AddExpression
   extends BinaryExpression
{

   public AddExpression(final Expression lft, final Expression rht)
   {
      super(lft, rht, "+");
   }

   public double _applyOperator(double x, double y)
   {
      return x +y;
   }
}

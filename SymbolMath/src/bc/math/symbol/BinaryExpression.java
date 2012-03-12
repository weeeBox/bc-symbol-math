package bc.math.symbol;

public class BinaryExpression
{
	public static Operand evaluate(String a, String b, Operation operation)
	{
		return evaluate(new Operand(a), new Operand(b), operation);
	}
	
	public static Operand evaluate(Operand a, Operand b, Operation operation)
	{
		boolean zeroA = a.isZero();
		boolean zeroB = b.isZero();
		
		if (zeroA && zeroB)
		{
			return Operand.ZERO;
		}
		
		if (operation == Operation.MUL)
		{
			if (zeroA || zeroB)
			{
				return Operand.ZERO;
			}
			
			if (a.isOne())
			{
				if (a.isNegative())
				{
					return b.negateClone();
				}
				return b;
			}
			
			if (b.isOne())
			{
				if (b.isNegative())
				{
					return a.negateClone();
				}
				
				return a;
			}
			
			ExpressionOperand c = new ExpressionOperand(toUnsignedString(a, true) + " * " + toUnsignedString(b, true), operation);
			if (a.isNegative() ^ b.isNegative())
			{
				c.negate();
			}
			
			return c;
		}
		
		if (zeroB)
		{
			return a;
		}
		
		if (operation == Operation.ADD)
		{
			if (zeroA)
			{
				return b;
			}
			
			if (b.isNegative())
			{
				return new ExpressionOperand(toString(a) + " - " + toUnsignedString(b, true), operation);
			}
			
			return new ExpressionOperand(toString(a) + " + " + toString(b), operation);
		}
		
		if (operation == Operation.SUB)
		{
			if (zeroA)
			{
				return b.negateClone();
			}
			
			if (b.isNegative())
			{
				return new ExpressionOperand(toString(a) + " + " + toUnsignedString(b), operation);
			}
			
			return new ExpressionOperand(toString(a) + " - " + toUnsignedString(b, true), operation);
		}
		
		assert false : operation;
		return null;
	}

	private static String toString(Operand o)
	{
		return toString(o, false);
	}
	
	private static String toString(Operand o, boolean useParenthesis)
	{
		if (o instanceof ExpressionOperand && useParenthesis)
		{
			ExpressionOperand exp = (ExpressionOperand) o;
			if (exp.getOperation() != Operation.MUL)
			{
				if (exp.isNegative())
				{
					return String.format("-(%s)", exp.getValue());
				}
				
				return String.format("(%s)", exp.getValue());
			}
		}
		
		if (o.isNegative())
		{
			return String.format("-%s", o.getValue());
		}
		
		return o.getValue();
	}
	
	private static String toUnsignedString(Operand o)
	{
		return toUnsignedString(o, false);
	}
	
	private static String toUnsignedString(Operand o, boolean useParenthesis)
	{
		if (o instanceof ExpressionOperand && useParenthesis)
		{
			ExpressionOperand exp = (ExpressionOperand) o;
			if (exp.getOperation() != Operation.MUL)
			{
				return String.format("(%s)", exp.getValue());
			}
		}
		
		return o.getValue();
	}
}

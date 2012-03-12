package bc.math.symbol;
public class ExpressionOperand extends Operand
{
	private Operation operation;
	
	public ExpressionOperand(String value, Operation operation)
	{
		super(value, false);
		this.operation = operation;
	}
	
	public Operation getOperation()
	{
		return operation;
	}
	
	@Override
	public String toString()
	{
		if (isNegative())
		{
			if (operation != Operation.MUL)
			{
				return String.format("-(%s)", getValue());
			}
			return "-" + getValue();
		}
		
		return getValue();
	}
}

package bc.math.symbol;

public class Operand
{
	private String value;
	private boolean negative;

	public static final Operand ZERO = new Operand("0");
	public static final Operand ONE = new Operand("1");
	public static final Operand NEGATIVE_ONE = new Operand("1", true);
	
	public Operand(String value)
	{
		this(value, false);
	}
	
	public Operand(String value, boolean negative)
	{
		this.value = value;
		this.negative = negative;
	}
	
	public String getValue()
	{
		return value;
	}
	
	public void setValue(String value)
	{
		this.value = value;
	}
	
	public boolean isZero()
	{
		return value.equals("0");
	}
	
	public boolean isOne()
	{
		return value.equals("1");
	}
	
	public boolean isNegative()
	{
		return negative && !isZero();
	}
	
	public void negate()
	{
		negative = !negative;
	}
	
	public void setNegative(boolean negative)
	{
		this.negative = negative;
	}
	
	public Operand clone()
	{
		return new Operand(value, negative);
	}
	
	public Operand negateClone()
	{
		return new Operand(value, !negative);
	}
	
	@Override
	public String toString()
	{
		if (isNegative())
		{
			return "-" + getValue();
		}
		
		return getValue();
	}
}

package bc.math.symbol;
import bc.utils.code.ListWriteDestination;

public class Matrix
{
	private String[][] elements;

	public Matrix(String[][] elements)
	{
		this.elements = elements;
	}

	public Matrix(int rows, int cols, String elementBaseName)
	{
		this(rows, cols);

		for (int i = 0; i < rows; i++)
		{
			for (int j = 0; j < cols; j++)
			{
				elements[i][j] = String.format("%s%d%d", elementBaseName, i + 1, j + 1);
			}
		}
	}
	
	public Matrix(int rows, int cols)
	{
		this(new String[rows][cols]);
	}

	public static Matrix appendPrefixTo(Matrix m, String prefix)
	{
		Matrix result = m.clone();
		for (int i = 0; i < m.rowsCount(); ++i)
		{
			for (int j = 0; j < m.colsCount(); ++j)
			{
				Operand operand = m.getOperand(i, j);
				if (operand.isZero() || operand.isOne())
				{
					continue;
				}
				
				result.set(i, j, prefix + m.get(i, j));
			}
		}
		
		return result;
	}
	
	public String get(int i, int j)
	{
		checkRange(i, j);
		return elements[i][j];
	}

	public Operand getOperand(int i, int j)
	{
		String value = get(i, j);
		return new Operand(value);
	}

	public void set(int i, int j, String value)
	{
		checkRange(i, j);
		elements[i][j] = value;
	}

	private void checkRange(int i, int j)
	{
		if (i < 0 || i >= rowsCount())
		{
			throw new IllegalArgumentException("Row out of range: row=" + i + " rowsCount=" + rowsCount());
		}

		if (j < 0 || j >= colsCount())
		{
			throw new IllegalArgumentException("Col out of range: col=" + j + " colsCount=" + colsCount());
		}
	}

	public int rowsCount()
	{
		return elements.length;
	}

	public int colsCount()
	{
		return elements[0].length;
	}

	public static Matrix multiply(Matrix a, Matrix b)
	{
		int rowsCount = a.rowsCount();
		int colsCount = b.colsCount();
		int mulCount = a.colsCount();

		String[][] elements = new String[rowsCount][colsCount];
		for (int i = 0; i < rowsCount; ++i)
		{
			for (int j = 0; j < colsCount; ++j)
			{
				StringBuilder elementBuf = new StringBuilder();

				int operandIndex = 0;
				for (int k = 0; k < mulCount; ++k)
				{
					Operand ma = a.getOperand(i, k);
					if (ma.isZero())
					{
						continue;
					}

					Operand mb = b.getOperand(k, j);
					if (mb.isZero())
					{
						continue;
					}

					Operand c = BinaryExpression.evaluate(ma, mb, Operation.MUL);
					if (operandIndex++ > 0)
					{
						elementBuf.append(" + ");
					}
					elementBuf.append(c);
				}
				elements[i][j] = elementBuf.toString();
			}
		}

		return new Matrix(elements);
	}

	public static Matrix transpose(Matrix m)
	{
		Matrix result = new Matrix(m.colsCount(), m.rowsCount());
		for (int i = 0; i < result.rowsCount(); ++i)
		{
			for (int j = 0; j < result.colsCount(); ++j)
			{
				result.set(i, j, m.get(j, i));
			}
		}
		return result;
	}

	public Matrix clone()
	{
		String[][] elementsCopy = new String[rowsCount()][colsCount()];

		for (int i = 0; i < elements.length; ++i)
		{
			String[] row = elements[i];
			String[] rowCopy = new String[row.length];

			System.arraycopy(row, 0, rowCopy, 0, row.length);

			elementsCopy[i] = rowCopy;
		}

		return new Matrix(elementsCopy);
	}

	@Override
	public String toString()
	{
		ListWriteDestination dest = new ListWriteDestination();

		dest.writeBlockOpen();

		for (int i = 0; i < elements.length; ++i)
		{
			dest.write("{ ");
			for (int j = 0; j < elements[0].length; ++j)
			{
				if (j > 0)
				{
					dest.write(", ");
				}
				dest.write(elements[i][j]);
			}
			dest.writeln(" },");
		}

		dest.writeBlockClose();

		return dest.toString();
	}
}

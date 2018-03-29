package logic;

public enum InferenceRule implements BiDirectionalTransform
{	
	DE_MORGANS_OR("(NEG (OR P Q))", "(AND (NEG P) (NEG Q))", "DeMorgan's or"),
	DE_MORGANS_AND("(NEG (AND P Q))", "(OR (NEG P) (NEG Q))", "DeMorgan's and"),
	OR_DISTRIBUTION("(OR P (AND Q R))", "(AND (OR P Q) (OR P R))"),
	AND_DISTRIBUTION("(AND P (OR Q R))", "(OR (AND P Q) (AND P R))"),
	DOUBLE_NEGATION("(NEG (NEG P))", "P");

	private final Expression left;
	private final Expression right;
	private final String name;

	InferenceRule(String left, String right, String name)
	{
		this.left = Expression.create(left);
		this.right = Expression.create(right);
		this.name = name;
	}
	
	InferenceRule(String left, String right)
	{
		this.left = Expression.create(left);
		this.right = Expression.create(right);
		name = null;
	}
	
	/**
	 * 
	 * @param orig
	 * @return
	 */
	public Expression transform(Expression orig)
	{
		if (left.matches(orig))
			return Transform.transform(left.fillMatches(orig), right);
		else if (right.matches(orig))
			return Transform.transform(right.fillMatches(orig), left);
		System.out.println("An inference rule couldn't be successfully applied");
		return null; // TODO this could be a terrible idea
	}
	
	@Override
	public TransformSteps transformWithSteps(Expression orig)
	{
		TransformSteps ans = new TransformSteps(orig);
		ans.addStep(this);
		return ans;
	}
	
	@Override
	public String toString()
	{
		if (name != null)
			return name;
		return name().replaceAll("_", " ").toLowerCase();
	}

	@Override
	public Expression left()
	{
		return left;
	}

	@Override
	public Expression right()
	{
		return right;
	}
}
package logic;

import java.io.Serializable;
import java.util.Map;

/**
 * An interface representing arbitrary transformations that can be applied to
 * Expressions, along with the InferenceRule steps applied to get the desired result
 * @author Jallibad
 *
 */
public interface Transform extends Serializable
{
	/**
	 * A simple transform that returns only the final result
	 * @param orig the expression to be transformed
	 * @return the resultant Expression
	 */
	public Expression transform(Expression orig);
	
	/**
	 * Transforms the given Expression, saving the intermediate steps along the way
	 * @param orig the expression to be transformed
	 * @return a TransformSteps object containing each intermediate InferenceRule
	 */
	public TransformSteps transformWithSteps(Expression orig);
	
	public static Expression transform(Map<Literal,Expression> mapping, Expression e)
	{
		if (e instanceof Literal)
		{
			if (mapping.containsKey(e))
				return mapping.get(e);
			else
			{
				// TODO throw error and shit
				throw new Error();
			}
		}
		else if (e instanceof Function)
			return ((Function) e).mapTerms(x -> transform(mapping, x));
		else
		{
			// TODO throw error and shit
			return null;
		}
	}
}
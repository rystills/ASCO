package logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;

import logic.malformedexpression.InvalidArgumentsException;
import logic.malformedexpression.MalformedExpressionException;

public class FunctionTests
{
	@Test(expected = MalformedExpressionException.class)
	public void testConstructor() throws MalformedExpressionException
	{
		new Function(Operator.AND, new Literal("A"));
	}
	
	@Test(expected = MalformedExpressionException.class)
	public void testVariadicConstructor() throws MalformedExpressionException
	{
		new Function(Operator.AND, "A");
	}
	
	@Test
	public void testEquality() throws MalformedExpressionException
	{
		Function e = new Function(Operator.AND, "A", "B");
		assertEquals(e, new Function(Operator.AND, "A", "B"));
		assertEquals(ExpParser.parseUnsafe("(AND A B)"), e);
		assertNotEquals(e, new Function(Operator.AND, "A", "A"));
		assertNotEquals(e, new Function(Operator.OR, "A", "B"));
		assertNotEquals(e, new Literal("A"));
	}
	
	@Test
	public void testToString() throws MalformedExpressionException
	{
		Function e = new Function(Operator.AND, "A", "B");
		assertEquals("(AND A B)", e.toString());
	}
	
	@Test
	public void testGetVariables() throws MalformedExpressionException
	{
		Function e1 = new Function(Operator.NEG, "A");
		Set<Literal> testVariables = new HashSet<Literal>();
		testVariables.add(new Literal("A"));
		assertEquals(testVariables, e1.getVariables());
		Function e2 = new Function(Operator.AND, "A", "B");
		testVariables.add(new Literal("B"));
		assertEquals(testVariables, e2.getVariables());
		Function e3 = new Function(Operator.NEG, e2);
		assertEquals(testVariables, e3.getVariables());
	}
	
	@Test
	public void testMatches()
	{
		// TODO write test cases here
	}
	
	@Test
	public void testFillMatches() throws InvalidArgumentsException
	{
		Map<Literal, Expression> m1 = ExpParser.parseUnsafe("(AND A B)").fillMatches(ExpParser.parseUnsafe("(AND (NEG A) (NEG B))")).get();
		Map<Literal, Expression> m1Ans = new HashMap<>();
		m1Ans.put(new Literal("A"), ExpParser.parseUnsafe("(NEG A)"));
		m1Ans.put(new Literal("B"), ExpParser.parseUnsafe("(NEG B)"));
		assertEquals(m1Ans, m1);
		
		assertEquals(Optional.empty(), ExpParser.parseUnsafe("(AND (NEG A) (NEG B))").fillMatches(ExpParser.parseUnsafe("(AND A B)")));
	}
}

package package2;

import java.util.ArrayList;

public class BooleanExpression {
	
	private String operator;
	// &&, ||, ->, <->, XOR, !
	
	private ArrayList<BooleanExpression> upExpressions;
	private BooleanExpression down1, down2;
	
	public BooleanExpression(){
		upExpressions = new ArrayList<BooleanExpression>();
		operator = "&&";
	}

	public BooleanExpression(BooleanExpression member1, BooleanExpression member2, String operator){
		upExpressions = new ArrayList<BooleanExpression>();
		down1 = member1;
		down2 = member2;
		this.operator = operator;
	}
	
	public boolean evaluate(){
		if(operator == "&&"){
			return down1.evaluate() && down2.evaluate();
		}
		else if(operator == "||"){
			return down1.evaluate() || down2.evaluate();
		}
		else if(operator == "<->"){
			return down1.evaluate() == down2.evaluate();
		}
		else if(operator == "XOR"){
			return down1.evaluate() != down2.evaluate();
		}
		// if operator == !
		else{
			return !down1.evaluate();
		}
	}
	
	public void setDown1(BooleanExpression expression){
		down1 = expression;
	}
	
	public void setDown2(BooleanExpression expression){
		down2 = expression;
	}
	
	public BooleanExpression getDown1(){
		return down1;
	}
	
	public BooleanExpression getDown2(){
		return down2;
	}
	
	public void setOperator(String newOperator){
		operator = newOperator;
	}
	
	public String getOperator(){
		return operator;
	}
	
}

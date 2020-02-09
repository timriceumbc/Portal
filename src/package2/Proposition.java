package package2;

public class Proposition extends BooleanExpression {
	
	private DataSource dataSource1, dataSource2;
	
	private String operator;
	// <, >, ==, !=

	Proposition(DataSource dataSource1, DataSource dataSource2){
		this.dataSource1 = dataSource1;
		this.dataSource2 = dataSource2;
		operator = "==";
	}

	Proposition(DataSource dataSource1, DataSource dataSource2, String operator){
		this.dataSource1 = dataSource1;
		this.dataSource2 = dataSource2;
		this.operator = operator;
	}
	
	public boolean evaluate(){
		if(operator == "=="){
			return dataSource1.getData() == dataSource2.getData();
		}
		else if(operator == "!="){
			return dataSource1.getData() != dataSource2.getData();
		}
		else if(operator == "<"){
			return dataSource1.getData() < dataSource2.getData();
		}
		else{
			return dataSource1.getData() > dataSource2.getData();
		}
	}
	
	public void setDataSource1(DataSource dataSource){
		dataSource1 = dataSource;
	}
	
	public void setDataSource2(DataSource dataSource){
		dataSource2 = dataSource;
	}
	
	public DataSource getDataSource1(){
		return dataSource1;
	}
	
	public DataSource setDataSource2(){
		return dataSource2;
	}
	
	public void setDown1(BooleanExpression expression){
		
	}
	
	public void setDown2(BooleanExpression expression){
		
	}
	
	public BooleanExpression getDown1(){
		return null;
	}
	
	public BooleanExpression getDown2(){
		return null;
	}
	
	public void setOperator(String newOperator){
		operator = newOperator;
	}
	
	public String getOperator(){
		return operator;
	}
	
}

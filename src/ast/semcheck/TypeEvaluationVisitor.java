package ir.semcheck;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

import ir.ASTVisitor;
import ir.ast.*;
import ir.error.Error; // define class error
import ir.semcheck.SymbolTable;


// type checker, concrete visitor 
public class TypeEvaluationVisitor implements ASTVisitor {

	private SymbolTable stack;

	private Integer actualOffset;
	private Integer classMemory;

	private final static int VARSIZE=1;

	public TypeEvaluationVisitor(){
		this.stack = new SymbolTable();
		this.actualOffset = 0;
		this.classMemory  = 0;
	}

	// visit statements
	private void initClassMemory(){
		this.classMemory =1;
	}

	private Integer getClassMemory(){
		return this.classMemory;
	}

	private Integer incClassMemory(){
		Integer aux = this.classMemory;
		this.classMemory++;
		return aux;
	}

	private Integer incClassMemoryArray(Integer cant){
		this.classMemory = this.classMemory + cant;
		Integer aux = this.classMemory-1;
		return aux;
	}
	// visit statements
	private void initActualOffset(){
		this.actualOffset =1;
	}

	private Integer getActualOffset(){
		return this.actualOffset;
	}

	private Integer incActualOffset(){
		Integer aux = this.actualOffset;
		this.actualOffset++;
		return aux;
	}

	private Integer incActualOffsetArray(Integer cant){
		this.actualOffset = this.actualOffset + cant;
		Integer aux = this.actualOffset-1;
		return aux;
	}

	@Override
	public void visit(AST stmt){

	}

	@Override	
	public void visit(ArithmeticBinOp stmt){
		Expression exprLeft  = stmt.getLeftOperand();
		Expression exprRight = stmt.getRightOperand();
		if (exprLeft.getType().equalsIgnoreCase("UNDEFINED")){
			exprLeft.accept(this);
		}
		if(exprRight.getType().equalsIgnoreCase("UNDEFINED")){
			exprRight.accept(this);
		}
		if (!( ( exprLeft.getType().equalsIgnoreCase("INTEGER")&&(exprRight.getType().equalsIgnoreCase("INTEGER")) ) ||
			((exprLeft.getType().equalsIgnoreCase("FLOAT")&&(exprRight.getType().equalsIgnoreCase("FLOAT")) )))){
			this.errors.add(new Error(exprLeft.getLineNumber(),exprLeft.getColumnNumber(), "Invalid artihmetic operation, both expressions should be INTEGER or FLOAT"));
		}else{
			stmt.setType(exprLeft.getType());
		}
	}
	
	@Override
	public void visit(ArithmeticUnaryOp stmt){
		Expression expr = stmt.getOperand();
		expr.accept(this);
		if (!((expr.getType().equalsIgnoreCase("INTEGER"))||(expr.getType().equalsIgnoreCase("FLOAT")))){
			this.errors.add(new Error(expr.getLineNumber(),expr.getColumnNumber(), "Invalid aritmetic operation, "+expr.getType()+" expression expected"));
		}else{
			stmt.setType(expr.getType());
		}
	} 
	
	@Override
	public void visit(ArrayIdDecl stmt){

	}
	
	@Override
	public void visit(ArrayLocation loc){
		List<IdDecl> ids = loc.getIds();
		int last 		 = ids.size()-1;
		String type 	 = this.stack.getCurrentType(ids.get(last).getName());
		if (type!=null)
			loc.setType(type);
		Expression expr  = loc.getExpression();
		expr.accept(this);
		if(!expr.getType().equalsIgnoreCase("INTEGER"))
			this.errors.add(new Error(expr.getLineNumber(),expr.getColumnNumber(), "Only Integer index allowed"));
	}
	
	@Override
	public void visit(AssignStmt assignStmt){
		Location loc    = assignStmt.getLocation();
		loc.accept(this);
		Expression expr = assignStmt.getExpression();
		expr.accept(this);
		if (!loc.getType().equalsIgnoreCase(expr.getType()) && 
			!loc.getType().equalsIgnoreCase("UNDEFINED")) {
			this.errors.add(new Error(expr.getLineNumber(),expr.getColumnNumber(), loc.getType()+" expression expected"));
		}

	}
	
	@Override
	public void visit(AttributeArrayLocation loc){
		List<IdDecl> ids = loc.getIds();
		int last = ids.size()-1;
		String lastName = ids.get(last).getName();
		String type = this.stack.getCurrentType(lastName);
		if (type!=null)
			loc.setType(type);
	}
	
	@Override
	public void visit(AttributeLocation loc){
		List<IdDecl> ids = loc.getIds();
		int last = ids.size()-1;
		String lastName = ids.get(last).getName();
		if (last==0){
			String type = this.stack.getCurrentType(lastName);
			if(type!=null){
				loc.setType(ids.get(last).getType());
			}
		}else{
			String firstNameClass 	= ids.get(0).getName();
			String classD 			= this.stack.getCurrentType(firstNameClass);
			SymbolInfo classDecl 	= this.stack.getCurrentSymbolInfo(classD);
			List<IdDecl> atts 		= classDecl.getAttList();
			IdDecl aux 				= new IdDecl(lastName, loc.getLineNumber(), loc.getColumnNumber());
			if(this.stack.contains(atts,aux)){
				loc.setType(this.stack.currentId().getType());
			} else {
				this.errors.add(new Error(loc.getLineNumber(),loc.getColumnNumber(), "Not a valid attribute"));
			}
		}
	}
	
	@Override
	public void visit(Block block){
		this.stack.newLevel();
		List<FieldDecl> fieldDeclList 	= block.getVariable();
		List<Statement> stmtList 		= block.getStatements();

		for (FieldDecl fieldDecl : fieldDeclList){
			fieldDecl.accept(this);
		}
		for(Statement stmt : stmtList){
			stmt.accept(this);
		}
		this.stack.closeLevel();
	}
	
	@Override
	public void visit(BodyDecl body){
		if (!body.isExtern()){
			Block block = body.getBlock();
			block.accept(this);
		}
	}
	
	@Override
	public void visit(BooleanLiteral lit){

	}
	
	@Override
	public void visit(BreakStmt stmt){

	}
	
	@Override
	public void visit(ClassDecl classDecl){
		initClassMemory();
		List<FieldDecl> fieldDeclList 	= classDecl.getAttributes();
		List<MethodDecl> methodDeclList = classDecl.getMethods();
		this.stack.newLevel();
		
		for (FieldDecl fieldDecl : fieldDeclList){
			fieldDecl.accept(this);
		}

		for (MethodDecl methodDecl : methodDeclList){
			SymbolInfo a = new SymbolInfo(true, methodDecl.getType(), methodDecl);
			a.addParamList(methodDecl.getParams());
			this.stack.addDeclare(a);
		}

		for (MethodDecl methodDecl : methodDeclList){
			methodDecl.accept(this);
		}
		classDecl.setOff(getClassMemory());
		this.stack.closeLevel();
	}
	
	@Override
	public void visit(ContinueStmt stmt){

	}
	
	@Override
	public void visit(EqBinOp stmt){
		Expression exprLeft  = stmt.getLeftOperand();
		Expression exprRight = stmt.getRightOperand();
		exprLeft.accept(this);
		exprRight.accept(this);
		if (exprLeft.getType().equalsIgnoreCase("INTEGER") || exprLeft.getType().equalsIgnoreCase("FLOAT") || exprLeft.getType().equalsIgnoreCase("BOOLEAN")){
			if (exprLeft.getType().equalsIgnoreCase(exprRight.getType())){
				stmt.setType("BOOLEAN");
			}else{
				this.errors.add(new Error(exprLeft.getLineNumber(),exprLeft.getColumnNumber(), "Both expressions should be of the same type"));
			}
		}else{
			this.errors.add(new Error(exprLeft.getLineNumber(),exprLeft.getColumnNumber(), "Expressions of a basic type expected"));
		}
	}

	@Override
	public void visit(Expression stmt){
		
	}

	@Override
	public void visit(FieldDecl fieldDecl){
		String type = fieldDecl.getType();
		if (!Type.contains(type)){
			this.errors.add(new Error(fieldDecl.getLineNumber(),fieldDecl.getColumnNumber(), "Unexistent field declaration type"));
		}
		List<IdDecl> idDeclList 		= fieldDecl.getNames();
		List<SymbolInfo> symbolInfoList = new LinkedList<SymbolInfo>();
		for (IdDecl idDecl : idDeclList){
			if (idDecl.isAttribute()){
				if(idDecl instanceof ArrayIdDecl){
					idDecl.setOff(incClassMemoryArray (((ArrayIdDecl) idDecl).getNumber()));
				}else{
					idDecl.setOff(incClassMemory());
				}
			}else{
				if (!Type.isNativeType(type)){
					if(idDecl instanceof ArrayIdDecl){
						idDecl.setOff(incActualOffsetArray(((ArrayIdDecl) idDecl).getNumber())); //VER
					}else{
						idDecl.setOff(incActualOffsetArray(idDecl.getClassRef().getOff()));
					}
				}else{

					if(idDecl instanceof ArrayIdDecl){
						idDecl.setOff(incActualOffsetArray(((ArrayIdDecl) idDecl).getNumber()));
					}else{

						idDecl.setOff(incActualOffset());
					}
				}
			}
			this.stack.addDeclare(new SymbolInfo(fieldDecl.getType(), idDecl));

		}
	}

	@Override
	public void visit(FloatLiteral lit){

	}

	@Override
	public void visit(ForStmt stmt){
		this.stack.newLevel();
		IdDecl counterName   = stmt.getCounterName();
		counterName.setOff(incActualOffset());
		this.stack.addDeclare(new SymbolInfo(counterName.getType(), counterName));

		Expression initValue = stmt.getInit();
		Expression endValue  = stmt.getEnd();
		initValue.accept(this);
		endValue.accept(this);

		if (!((initValue.getType().equalsIgnoreCase("INTEGER"))&&(endValue.getType().equalsIgnoreCase("INTEGER")))){
			this.errors.add(new Error(initValue.getLineNumber(),initValue.getColumnNumber(), "Both init and end value should be Integers"));
		}

		Statement statement  = stmt.getBody();
		statement.accept(this);
		this.stack.closeLevel();
	}
	
	@Override
	public void visit(IdDecl loc){

	}
	
	@Override
	public void visit(IfThenElseStmt stmt){
		Expression condition = stmt.getCondition();
		condition.accept(this);
		if (!condition.getType().equalsIgnoreCase("BOOLEAN")){
			this.errors.add(new Error(condition.getLineNumber(),condition.getColumnNumber(), "Not a valid condition"));
		}
		Statement ifBlock 	= stmt.getIfBlock();
		ifBlock.accept(this);
		Statement elseBlock = stmt.getElseBlock();
		elseBlock.accept(this);
	}

	@Override
	public void visit(IfThenStmt stmt){
		Expression condition = stmt.getCondition();
		condition.accept(this);
		if (!condition.getType().equalsIgnoreCase("BOOLEAN")){
			this.errors.add(new Error(condition.getLineNumber(),condition.getColumnNumber(), "Not a valid condition"));
		}
		Statement ifBlock 	 = stmt.getIfBlock();
		ifBlock.accept(this);
	}
	
	@Override
	public void visit(IntLiteral lit){

	}
	
	@Override
	public void visit(LogicalBinOp stmt){
		Expression exprLeft  = stmt.getLeftOperand();
		Expression exprRight = stmt.getRightOperand();
		exprLeft.accept(this);
		exprRight.accept(this);
		if (!((exprLeft.getType().equalsIgnoreCase("BOOLEAN"))&&(exprRight.getType().equalsIgnoreCase("BOOLEAN")))){
			this.errors.add(new Error(exprLeft.getLineNumber(),exprLeft.getColumnNumber(), "Both expressions should be booleans"));
		}else{
			stmt.setType("BOOLEAN");
		}
	}
	
	@Override
	public void visit(LogicalUnaryOp stmt){
		Expression expr = stmt.getOperand();
		expr.accept(this);
		if (!expr.getType().equalsIgnoreCase("BOOLEAN")){
			this.errors.add(new Error(expr.getLineNumber(),expr.getColumnNumber(), "Boolean expression expected"));
		}else{
			stmt.setType("BOOLEAN");
		}
	}

	@Override
	public void visit(MethodCall methodCall){
		List<IdDecl> ids 			= methodCall.getIds();
		int last 		 			= ids.size()-1;
		String lastName  			= ids.get(last).getName();
		List<Expression> paramList  = methodCall.getParams();
		List<IdDecl> formal 		= new LinkedList<IdDecl>();
		if(last==0){
			String type = this.stack.getCurrentType(lastName);
			if (type!=null){
				methodCall.setType(type);
				formal = this.stack.getCurrentSymbolInfo(lastName).getAttList();
			} else {
				return;
			}
		}else{
			String firstName 		 = ids.get(0).getName();
			String type 		   	 = this.stack.getCurrentType(firstName);
			SymbolInfo instance 	 = this.stack.getCurrentSymbolInfo(type);
			List<SymbolInfo> methods = instance.getMethodList();
			if(this.stack.containsMeth(methods, new IdDecl(lastName, methodCall.getLineNumber(), methodCall.getColumnNumber()))){
				methodCall.setType(this.stack.currentS().getType());
				formal = this.stack.currentS().getAttList();				
			}else{
				return;
			}
		}
		if(formal.size()==paramList.size()){
			for (IdDecl param : formal) {
				Expression current = paramList.get(formal.indexOf(param));
				if (current.getType().equalsIgnoreCase("UNDEFINED"))
					current.accept(this);
				if (!param.getType().equalsIgnoreCase(current.getType()))
					this.errors.add(new Error(current.getLineNumber(),current.getColumnNumber(), "Parameter of type "+param.getType()+" expected"));
			}
		}else{
			this.errors.add(new Error(methodCall.getLineNumber(),methodCall.getColumnNumber(), "Actual and formal argument lists differ in length"));
		}
	}
	
	@Override
	public void visit(MethodCallStmt stmt){
		MethodCall methodCall = stmt.getMethodCall();
		methodCall.accept(this);
	}
	
	@Override
	public void visit(MethodDecl methodDecl){
		initActualOffset();
		String type = methodDecl.getType();
		if (!Type.contains(type)){
			this.errors.add(new Error(methodDecl.getLineNumber(),methodDecl.getColumnNumber(), "Not a valid method type"));
		}
		this.stack.newLevel();
		List<SymbolInfo> symbolInfoList = new LinkedList<SymbolInfo>();
		List<ParamDecl> paramDeclList 	= methodDecl.getParams();
		for (ParamDecl paramDecl : paramDeclList){
			symbolInfoList.add(new SymbolInfo(paramDecl.getType(), paramDecl));
		}

		this.stack.addDeclareList(symbolInfoList);
		for (ParamDecl paramDecl : paramDeclList){
			paramDecl.accept(this);
		}
		BodyDecl body = methodDecl.getBody();
		body.accept(this);
		if (!body.isExtern()){
			List<Statement> stmts = body.getBlock().getStatements();
			for (Statement stmt : stmts){
				if(stmt instanceof ReturnStmt){
					ReturnStmt ret = (ReturnStmt) stmt;
					if (!ret.getExpression().getType().equalsIgnoreCase(type)){
						this.errors.add(new Error(stmt.getLineNumber(),stmt.getColumnNumber(), "This method should return an expression of type "+type));
					}
				}
				if(stmt instanceof ReturnVoidStmt){
					if (type!="VOID"){
						this.errors.add(new Error(stmt.getLineNumber(),stmt.getColumnNumber(), "This method is declared as void"));
					}
				}
					
			}
		}
		methodDecl.setOff(getActualOffset());
		this.stack.closeLevel();
	}
	
	@Override
	public void visit(ParamDecl paramDecl){
		String type = paramDecl.getType();
		if (!Type.contains(type))
			this.errors.add(new Error(paramDecl.getLineNumber(),paramDecl.getColumnNumber(), "Not a valid type"));
		paramDecl.setOff(incActualOffset());
	}
	
	@Override
	public void visit(Program prog){
		this.stack.newLevel();
		List<ClassDecl> classDeclList = prog.getClassDeclare();
		for (ClassDecl classDecl : classDeclList){
			SymbolInfo classSymbol 	  = new SymbolInfo(classDecl.getName(),classDecl);
			classSymbol.addAttList(classDecl.getAttributes());
			classSymbol.addMethodList(classDecl.getMethods());
			this.stack.addDeclare(classSymbol);
		}
		for (ClassDecl classDecl : classDeclList){
			classDecl.accept(this);
		}
		this.stack.closeLevel();
	}
	
	@Override
	public void visit(RelationalBinOp stmt){
		Expression exprLeft  = stmt.getLeftOperand();
		Expression exprRight = stmt.getRightOperand();
		exprLeft.accept(this);
		exprRight.accept(this);
		if (!( (exprLeft.getType().equalsIgnoreCase("INTEGER")&&(exprRight.getType().equalsIgnoreCase("INTEGER")))||
			((exprLeft.getType().equalsIgnoreCase("FLOAT")&&(exprRight.getType().equalsIgnoreCase("FLOAT")) )))){
			this.errors.add(new Error(exprLeft.getLineNumber(),exprLeft.getColumnNumber(), "Both expressions should be of an INTEGER or FLOAT type"));
		}else{
			stmt.setType("BOOLEAN");
		}
	}
	
	@Override
	public void visit(ReturnStmt stmt){
		Expression expr = stmt.getExpression();
		if(expr.getType().equalsIgnoreCase("UNDEFINED"))
			expr.accept(this);
	}

	@Override
	public void visit(ReturnVoidStmt stmt){

	}
	
	@Override
	public void visit(Skip stmt){

	}
	
	@Override
	public void visit(Statement stmt){

	}
	
	@Override
	public void visit(VarLocation loc){
		List<IdDecl> ids = loc.getIds();
		int last 		 = ids.size()-1;
		String lastName  = ids.get(last).getName();
		String type 	 = this.stack.getCurrentType(lastName);
		if(type!=null)
			loc.setType(type);
	}
	
	@Override
	public void visit(WhileStmt stmt){
		Expression condition = stmt.getCondition();
		condition.accept(this);
		if (!condition.getType().equalsIgnoreCase("BOOLEAN")){
			this.errors.add(new Error(condition.getLineNumber(),condition.getColumnNumber(), "Condition should be a boolean"));
		}
		Statement body = stmt.getBody();
		body.accept(this);
	}

	private void addError(AST a, String desc) {
		this.errors.add(new Error(a.getLineNumber(), a.getColumnNumber(), desc));
	}

	@Override
	public List<Error> getErrors() {
		return this.errors;
	}

	@Override
	public List<Error> stackErrors() {
		return this.stack.getErrors();
	}

	public SymbolTable getStack(){
		return this.stack;
	}

}

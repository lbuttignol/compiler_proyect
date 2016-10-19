package ir.AsmGenerator;

import ir.intermediateCode.*;
import java.util.List;


class AsmGenerator {
	private List<StatementCode> intermediateCode;
	
	public AsmGenerator(List<StatementCode> program){
		this.intermediateCode = program;
	}

	public void translate(StatementCode stmt){
		switch (stmt.getOperationCode()) {
			// Declarations	
			case BEGINPROGRAM:
				System.out.println( "BEGINPROGRAM");			
			case ENDPROGRAM:
				System.out.println( "ENDPROGRAM");
			case BEGINCLASS:
				System.out.println( "BEGINCLASS");
			case ENDCLASS:
				System.out.println( "ENDCLASS");
			case FIELD:
				System.out.println( "FIELD");
			case BEGINMETHOD:
				System.out.println( "BEGINMETHOD");
			case ENDMETHOD:
				System.out.println( "ENDMETHOD");
			case PARAMDECL:
				System.out.println( "PARAMDECL");
			case LOADPARAM:
				System.out.println( "LOADPARAM");  
			case IDDECL:
				System.out.println( "IDDECL");				
			case ARRAYDECLI:
				System.out.println( "ARRAYDECLI");
			case ARRAYDECLF:
				System.out.println( "ARRAYDECLF");
			case ARRAYDECLB:
				System.out.println( "ARRAYDECLB");
			case INTDECL:
				System.out.println( "INTDECL");
			case FLOATDECL:
				System.out.println( "FLOATDECL");
			case BOOLDECL:
				System.out.println( "BOOLDECL");

		// Statements
			case BEGINIF:
				System.out.println( "BEGINIF");
			case ENDIF:
				System.out.println( "ENDIF");
			case ELSEIF:
				System.out.println( "ELSEIF");
			case BEGINFOR:
				System.out.println( "BEGINFOR");
			case INCFOR:
				System.out.println( "INCFOR");
			case ENDFOR:
				System.out.println( "ENDFOR");
			case BEGINWHILE:
				System.out.println( "BEGINWHILE");
			case ENDWHILE:
				System.out.println( "ENDWHILE");
			
		// Unary Aruthmetic
			case SUBUI:
				System.out.println( "SUBUI");
			case NOT:
				System.out.println( "NOT"); 	
			case ADDII:
				System.out.println( "ADDII");
			case ADDFF:
				System.out.println( "ADDFF");
			case ADDIF:
				System.out.println( "ADDIF");
			case ADDFI:
				System.out.println( "ADDFI");
			case SUBII:
				System.out.println( "SUBII");
			case SUBFF:
				System.out.println( "SUBFF");
			case SUBIF:
				System.out.println( "SUBIF");
			case SUBFI:
				System.out.println( "SUBFI");
			case MULII:
				System.out.println( "MULII");
			case MULFF:
				System.out.println( "MULFF");
			case MULIF:
				System.out.println( "MULIF");
			case MULFI:
				System.out.println( "MULFI");
			case DIVII:
				System.out.println( "DIVII");
			case DIVFF:
				System.out.println( "DIVFF");
			case DIVIF:
				System.out.println( "DIVIF");
			case DIVFI:
				System.out.println( "DIVFI");
			case MODII:
				System.out.println( "MODII");

		// Eq operations													
			case EQII:
				System.out.println( "EQII");
			case EQFF:
				System.out.println( "EQFF");
			case EQIF:
				System.out.println( "EQIF");
			case EQFI:
				System.out.println( "EQFI");
			case EQBB:
				System.out.println( "EQBB");
			case NEQII:
				System.out.println( "NEQII");
			case NEQFF:
				System.out.println( "NEQFF");
			case NEQIF:
				System.out.println( "NEQIF");
			case NEQFI:
				System.out.println( "NEQFI");
			case NEQBB:
				System.out.println( "NEQBB");

		// Relational operations
			case SMALLII:
				System.out.println( "SMALLII");
			case SMALLFF:
				System.out.println( "SMALLFF");
			case SMALLIF:
				System.out.println( "SMALLIF");
			case SMALLFI:
				System.out.println( "SMALLFI");
			case LTOEII:
				System.out.println( "LTOEII");
			case LTOEFF:
				System.out.println( "LTOEFF");
			case LTOEIF:
				System.out.println( "LTOEIF");
			case LTOEFI:
				System.out.println( "LTOEFI");
			case BIGGERII:
				System.out.println( "BIGGERII");
			case BIGGERFF:
				System.out.println( "BIGGERFF");
			case BIGGERIF:
				System.out.println( "BIGGERIF");
			case BIGGERFI:
				System.out.println( "BIGGERFI");
			case GTOEII:
				System.out.println( "GTOEII");
			case GTOEFF:
				System.out.println( "GTOEFF");
			case GTOEIF:
				System.out.println( "GTOEIF");
			case GTOEFI:
				System.out.println( "GTOEFI");

		// Logical operations			
			case ANDBB:
				System.out.println( "ANDBB");
			case ORBB:
				System.out.println( "ORBB");

		// Jump
			case JMPFALSE:
				System.out.println( "JMPFALSE");	
			case JMPTRUE:
				System.out.println( "JMPTRUE");
			case JMP:
				System.out.println( "JMP");

		// Assign
			case ASSIGNATION:
				System.out.println( "ASSIGNATION"); 		
			case ASSINCI:
				System.out.println( "ASSINCI");
			case ASSDECI:
				System.out.println( "ASSDECI");
			case ASSINCF:
				System.out.println( "ASSINCF");
			case ASSDECF:
				System.out.println( "ASSDECF");
			case INC:
				System.out.println( "INC");
				
		}

		System.out.println( "");
	}
}
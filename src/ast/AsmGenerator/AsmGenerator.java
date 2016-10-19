package ir.AsmGenerator;

import ir.intermediateCode.*;
import java.util.List;


public class AsmGenerator {
	private List<StatementCode> intermediateCode;
	
	public AsmGenerator(List<StatementCode> program){
		this.intermediateCode = program;
	}

	public void translate(StatementCode stmt){
		switch (stmt.getOperationCode()) {
			// Declarations	
			case BEGINPROGRAM:
				System.out.println( "BEGINPROGRAM");			
				break;
			case ENDPROGRAM:
				System.out.println( "ENDPROGRAM");
				break;
			case BEGINCLASS:
				System.out.println( "BEGINCLASS");
				break;
			case ENDCLASS:
				System.out.println( "ENDCLASS");
				break;
			case FIELD:
				System.out.println( "FIELD");
				break;
			case BEGINMETHOD:
				System.out.println( "BEGINMETHOD");
				break;
			case ENDMETHOD:
				System.out.println( "ENDMETHOD");
				break;
			case PARAMDECL:
				System.out.println( "PARAMDECL");
				break;
			case LOADPARAM:
				System.out.println( "LOADPARAM");  
				break;
			case IDDECL:
				System.out.println( "IDDECL");				
				break;
			case ARRAYDECLI:
				System.out.println( "ARRAYDECLI");
				break;
			case ARRAYDECLF:
				System.out.println( "ARRAYDECLF");
				break;
			case ARRAYDECLB:
				System.out.println( "ARRAYDECLB");
				break;
			case INTDECL:
				System.out.println( "INTDECL");
				break;
			case FLOATDECL:
				System.out.println( "FLOATDECL");
				break;
			case BOOLDECL:
				System.out.println( "BOOLDECL");
				break;

			//Locations
			case ARRAYLOCI:
				System.out.println( "ARRAYLOCI");
			case ARRAYLOCF:
				System.out.println( "ARRAYLOCF");
			case ARRAYLOCB:
				System.out.println( "ARRAYLOCB");
			case ATTARRAYLOCI:
				System.out.println( "ATTARRAYLOCI");
			case ATTARRAYLOCF:
				System.out.println( "ATTARRAYLOCF");
			case ATTARRAYLOCB:
				System.out.println( "ATTARRAYLOCB");
			case ATTLOCI:
				System.out.println( "ATTLOCI");
			case ATTLOCF:
				System.out.println( "ATTLOCF");
			case ATTLOCB:
				System.out.println( "ATTLOCB");
			case VARLOCI:
				System.out.println( "VARLOCI");
			case VARLOCF:
				System.out.println( "VARLOCF");
			case VARLOCB:
				System.out.println( "VARLOCB");

		// Statements
			case BEGINIF:
				System.out.println( "BEGINIF");
				break;
			case ENDIF:
				System.out.println( "ENDIF");
				break;
			case ELSEIF:
				System.out.println( "ELSEIF");
				break;
			case BEGINFOR:
				System.out.println( "BEGINFOR");
				break;
			case INCFOR:
				System.out.println( "INCFOR");
				break;
			case ENDFOR:
				System.out.println( "ENDFOR");
				break;
			case BEGINWHILE:
				System.out.println( "BEGINWHILE");
				break;
			case ENDWHILE:
				System.out.println( "ENDWHILE");
				break;
			
		// Unary Aruthmetic
			case SUBUI:
				System.out.println( "SUBUI");
				break;
			case NOT:
				System.out.println( "NOT"); 	
				break;
			case ADDII:
				System.out.println( "ADDII");
				break;
			case ADDFF:
				System.out.println( "ADDFF");
				break;
			case ADDIF:
				System.out.println( "ADDIF");
				break;
			case ADDFI:
				System.out.println( "ADDFI");
				break;
			case SUBII:
				System.out.println( "SUBII");
				break;
			case SUBFF:
				System.out.println( "SUBFF");
				break;
			case SUBIF:
				System.out.println( "SUBIF");
				break;
			case SUBFI:
				System.out.println( "SUBFI");
				break;
			case MULII:
				System.out.println( "MULII");
				break;
			case MULFF:
				System.out.println( "MULFF");
				break;
			case MULIF:
				System.out.println( "MULIF");
				break;
			case MULFI:
				System.out.println( "MULFI");
				break;
			case DIVII:
				System.out.println( "DIVII");
				break;
			case DIVFF:
				System.out.println( "DIVFF");
				break;
			case DIVIF:
				System.out.println( "DIVIF");
				break;
			case DIVFI:
				System.out.println( "DIVFI");
				break;
			case MODII:
				System.out.println( "MODII");
				break;

		// Eq operations													
			case EQII:
				System.out.println( "EQII");
				break;
			case EQFF:
				System.out.println( "EQFF");
				break;
			case EQIF:
				System.out.println( "EQIF");
				break;
			case EQFI:
				System.out.println( "EQFI");
				break;
			case EQBB:
				System.out.println( "EQBB");
				break;
			case NEQII:
				System.out.println( "NEQII");
				break;
			case NEQFF:
				System.out.println( "NEQFF");
				break;
			case NEQIF:
				System.out.println( "NEQIF");
				break;
			case NEQFI:
				System.out.println( "NEQFI");
				break;
			case NEQBB:
				System.out.println( "NEQBB");
				break;

		// Relational operations
			case SMALLII:
				System.out.println( "SMALLII");
				break;
			case SMALLFF:
				System.out.println( "SMALLFF");
				break;
			case SMALLIF:
				System.out.println( "SMALLIF");
				break;
			case SMALLFI:
				System.out.println( "SMALLFI");
				break;
			case LTOEII:
				System.out.println( "LTOEII");
				break;
			case LTOEFF:
				System.out.println( "LTOEFF");
				break;
			case LTOEIF:
				System.out.println( "LTOEIF");
				break;
			case LTOEFI:
				System.out.println( "LTOEFI");
				break;
			case BIGGERII:
				System.out.println( "BIGGERII");
				break;
			case BIGGERFF:
				System.out.println( "BIGGERFF");
				break;
			case BIGGERIF:
				System.out.println( "BIGGERIF");
				break;
			case BIGGERFI:
				System.out.println( "BIGGERFI");
				break;
			case GTOEII:
				System.out.println( "GTOEII");
				break;
			case GTOEFF:
				System.out.println( "GTOEFF");
				break;
			case GTOEIF:
				System.out.println( "GTOEIF");
				break;
			case GTOEFI:
				System.out.println( "GTOEFI");
				break;

		// Logical operations			
			case ANDBB:
				System.out.println( "ANDBB");
				break;
			case ORBB:
				System.out.println( "ORBB");
				break;

		// Jump
			case JMPFALSE:
				System.out.println( "JMPFALSE");	
				break;
			case JMPTRUE:
				System.out.println( "JMPTRUE");
				break;
			case JMP:
				System.out.println( "JMP");
				break;

		// Assign
			case ASSIGNATION:
				System.out.println( "ASSIGNATION"); 		
				break;
			case ASSINCI:
				System.out.println( "ASSINCI");
				break;
			case ASSDECI:
				System.out.println( "ASSDECI");
				break;
			case ASSINCF:
				System.out.println( "ASSINCF");
				break;
			case ASSDECF:
				System.out.println( "ASSDECF");
				break;
			case INC:
				System.out.println( "INC");
				break;
			default:
				System.out.println("");	
				break;			
		}

		System.out.println( "");
	}
}
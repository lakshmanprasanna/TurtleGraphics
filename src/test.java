import java.io.FileNotFoundException;
import java.util.List;

import Commands.Command;
import Helpers.Context;
import Helpers.Evaluator;
import Turtle.Turtle;
import Visitor.AbstractVisitor;
import Visitor.DistanceCalculationVisitor;
import Visitor.StepByStepExecutionVisitor;
import javafx.geometry.Point2D;


public class test {

public static void main(String[] args) throws FileNotFoundException {	
		
		Evaluator exp = new Evaluator("turtle.txt");
		
		exp.makeSyntaxTree();
		
		Context context = exp.getContext();	
	

		
	//	Turtle t = exp.interpret(context);
		
	//	System.out.println("X "+Math.round(t.location().getX())+" Y "+Math.round(t.location().getY()));
		
		context.setTurtle(new Turtle());
		
		AbstractVisitor<List<Command>> v = new StepByStepExecutionVisitor(context);
		exp.accept(v);		
		List<Command> commandList = v.getResult();	
	Turtle t1 = new Turtle();
	

	
	for(int i=0;i<commandList.size();i++){
		 t1 = commandList.get(i).execute();
	}
	
	
	System.out.println("Visitor"+" X " + t1.location().getX()+" Y "+ t1.location().getY());
	
	AbstractVisitor<Integer> distanceVisitor = new DistanceCalculationVisitor(context);
	exp.accept(distanceVisitor);
	
	System.out.println("Visitor 2 "+ distanceVisitor.getResult());
	
	}
}

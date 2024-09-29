import java.io.File;

public class Driver{
		public static void main(String[] args) {
			double [] c1 = {1, 1};
			int [] d1 = {0, 1};
			Polynomial p1 = new Polynomial(c1, d1);
			double [] c2 = {1, 1};
			int [] d2 = {0, 1};
			Polynomial p2 = new Polynomial(c2, d2);
			Polynomial s = p1.add(p2);
			Polynomial s2 = p1.multiply(p2);
			File file = new File("text.txt");
			Polynomial t = new Polynomial(file);
			p2.saveToFile("text2");
			
			System.out.println("Polynomial Representation(Buffer):");
			for (int i = 0; i < t.coefficients.length; i++) {
	            
		    	System.out.println("Coefficient: " + t.coefficients[i] + ", Exponential: " + t.exponentials[i]);
		    }
			
		   
		    System.out.println("Polynomial Representation(add):");
		    for (int i = 0; i < s.coefficients.length; i++) {
		            
		    	System.out.println("Coefficient: " + s.coefficients[i] + ", Exponential: " + s.exponentials[i]);
		    }
		    System.out.println("Polynomial Representation(multiply):");
		    for (int i = 0; i < s2.coefficients.length; i++) {
		            
		    	System.out.println("Coefficient: " + s2.coefficients[i] + ", Exponential: " + s2.exponentials[i]);
		    }
		    
			System.out.println("s(2) =" + s.evaluate(2));
			if(s.hasRoot(1))
				System.out.println("1 is a root of s");
			else
				System.out.println("1 is not a root of s");
         }
}
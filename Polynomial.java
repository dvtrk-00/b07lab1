import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;

public class Polynomial{
	double[] coefficients;
	int[] exponentials;
	
	public Polynomial() {
		 coefficients = new double[] {0};
		 exponentials = new int [] {0};
	}
	
	public Polynomial(double[] a, int[] b) {
		coefficients = a;
		exponentials = b;
		
	}
	
	public Polynomial(File file) {
	    String line = null;
	    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
	        line = reader.readLine(); 
	    } catch (IOException e) {
	        System.out.println("Error reading the file: " + e.getMessage());
	    }
	    
	    if (line != null) {
	        String[] terms = line.split("(?=[+-])");
	        coefficients = new double[terms.length];
	        exponentials = new int[terms.length];
	        int i = 0;
	        
	        for (String term : terms) {
	            term = term.trim();  
	            int xIndex = term.indexOf("x");
	            if(xIndex == -1) {
	                coefficients[i] = Double.parseDouble(term);
	                exponentials[i] = 0;
	            } else { 
	                if (xIndex > 0) { 
	                    coefficients[i] = Double.parseDouble(term.substring(0, xIndex));
	                } else { 
	                    coefficients[i] = term.startsWith("-") ? -1 : 1;
	                }

	                if (xIndex < term.length() - 1) {
	                    exponentials[i] = Integer.parseInt(term.substring(xIndex + 1));
	                } else {
	                    exponentials[i] = 1;
	                }
	            }
	            i++;
	        }   
	    }
	}

	public void saveToFile(String fileName) {
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
	        StringBuilder sb = new StringBuilder();
	        for (int i = 0; i < coefficients.length; i++) {
	            int intCoefficient = (int) Math.round(coefficients[i]);
	            boolean isPositive = intCoefficient >= 0;

	            
	            if (i > 0 && isPositive) {
	                sb.append("+");
	            }

	            
	            if (intCoefficient != 1 || exponentials[i] == 0) {  
	                sb.append(intCoefficient);
	                if (exponentials[i] != 0) {  
	                    sb.append("x");
	                }
	            } else {  
	                sb.append("x");
	            }

	            
	            if (exponentials[i] != 1 && exponentials[i] != 0) {
	                sb.append(exponentials[i]);
	            }
	        }
	        writer.write(sb.toString());
	    } catch (IOException e) {
	        System.out.println("Error writing to file: " + e.getMessage());
	    }
	}
	public int new_size(Polynomial a) {
		int last_index1 = a.exponentials.length - 1;
		int last_index2 = exponentials.length -1;
		
		if(a.exponentials[last_index1] > exponentials[last_index2]) {
			return a.exponentials[last_index1] + 1;
		}
		return exponentials[last_index2] + 1;
	}
	
	public int total_zero(double[] a) {
		int total = 0;
		
		for(int i = 0; i < a.length; i++) {
			if(a[i] == 0) {
				total++;
			}
		}
		return total;
	}
	
	public Polynomial reconstruction(Polynomial a, Polynomial p) {
		int index;
		
		for(int i = 0; i < a.exponentials.length; i++) {
			index = a.exponentials[i];
			p.coefficients[index] = a.coefficients[i];
		}
		return p;
	}
	
	public Polynomial extraction(double[] expanded_list) {
		int length = expanded_list.length - total_zero(expanded_list);
		double[] part1 = new double[length];
		int[] part2 = new int[length];
		int a = 0;
		
		for(int i = 0; i < expanded_list.length; i++) {
			if(expanded_list[i] != 0) {
				part1[i-a] = expanded_list[i];
				part2[i-a] = i;
			}else {
				a++;
			}
		}
		return new Polynomial(part1, part2);
	}
	
	public Polynomial add(Polynomial a) {
		int size = new_size(a);
		Polynomial pp1 = new Polynomial(new double[size], new int[size]);
		Polynomial pp2 = new Polynomial(new double[size], new int[size]);
		Polynomial p1 = reconstruction(a, pp1);
		Polynomial p2 = reconstruction(this, pp2);
		double[] expanded_list = new double[p1.coefficients.length];
		
		for(int i = 0; i < p1.coefficients.length; i++) {
			expanded_list[i] = p1.coefficients[i] + p2.coefficients[i];
		}
		return extraction(expanded_list);
	}
	
	public Polynomial multiply(Polynomial a) {
		int size = a.exponentials[a.exponentials.length - 1 ] + this.exponentials[this.exponentials.length - 1] + 1;
		Polynomial p = new Polynomial(new double [size], new int[size]);
		
		for(int i = 0; i < a.coefficients.length; i++) {
			for(int j = 0; j < a.coefficients.length; j++){
				p.coefficients[a.exponentials[i] + this.exponentials[j]] = p.coefficients[a.exponentials[i] + this.exponentials[j]] + a.coefficients[i]*this.coefficients[j];
			}
		}
		return extraction(p.coefficients);
	}
	
	public double evaluate(double x) {
		double total = 0;
		int coefficient;
		
		for (int i = 0; i < coefficients.length; i++){
			double power = 1;
			for(int j = 0; j < exponentials[i]; j++) {
				power = power * x;
			}
			total = total + power * coefficients[i];
		}
		return total;
	}
	
	public boolean hasRoot(double x) {
		double value = evaluate(x);
		if (value == 0){
			return true;
		}
		return false;
	}
}


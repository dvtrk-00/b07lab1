public class Polynomial{
	double[] coefficients;
	
	public Polynomial() {
		this.coefficients = new double[] {0};
	}
	
	public Polynomial(double[] a) {
		coefficients = a;
		
	}
	public Polynomial add(Polynomial a) {
		Polynomial p = new Polynomial();
		if (a.coefficients.length > coefficients.length) {
			p = a;
			for(int i = 0; i < coefficients.length; i++) {
				p.coefficients[i] = p.coefficients[i] + coefficients[i];
			}
		}else {
			p.coefficients = coefficients;
			for(int i = 0; i < a.coefficients.length; i++) {
				p.coefficients[i] = p.coefficients[i] + a.coefficients[i];
			}
		}
		return p;
	}
	public double evaluate(double x) {
		
		double total = coefficients[0];
		for (int i = 1; i < coefficients.length; i++){
			double power = 1;
			for (int j = 0; j < i; j++) {
				power = power * x;
			}
			total = total + coefficients[i] * power;
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


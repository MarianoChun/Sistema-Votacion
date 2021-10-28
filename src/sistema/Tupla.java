package sistema;

public class Tupla<X, Y> {
	final X x;
	final Y y;
	
	public Tupla(X x, Y y) {
		this.x = x;
		this.y = y;
	}
	
	public X getX() {
		return x;
	}
	
	public Y getY() {
		return y;
	}
	
	@Override
	public String toString() {
		StringBuilder cadena = new StringBuilder();
		cadena.append("(");
		cadena.append(getX());
		cadena.append(",");
		cadena.append(getY());
		cadena.append(")");
		return cadena.toString();
	}
}

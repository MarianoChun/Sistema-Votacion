package sistema;

import java.util.ArrayList;

public class Tupla<T1,T2> {
	private T1 valor1;
	private T2 valor2;
	public Tupla(T1 x, T2 y) { // Constructor ambos enteros
		// Si x e y son distintos de Integer, error
		if(x.getClass() != Integer.class|| y.getClass() != Integer.class) {
			throw new RuntimeException("Los valores ingresados no son enteros");
		}
		valor1 = x;
		valor2 = y;
		}
	
	public T1 getX() {
		return valor1;
	}
	
	public T2 getY() {
		return valor2;
	}
	
	public void setX(T1 x) {
		valor1 = x;
	}
	
	public void setY(T2 y) {
		valor2 = y;
	}
	
	public static ArrayList<Tupla<Integer,Integer>> listaCoordenadas(Tupla t){
		ArrayList<Tupla<Integer,Integer>> coordenadas = new ArrayList();
		coordenadas.add(t);
		return coordenadas;
	}
	
	@Override
	public String toString() {
		return "("+valor1.toString() +","+ valor2.toString()+")";
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Tupla n = new Tupla(2,2);
		Tupla n1 = new Tupla(4,1);
		ArrayList<Tupla<Integer, Integer>> coordenadas = listaCoordenadas(n);
		coordenadas.add(n1);
		System.out.println(coordenadas);
		
		
		
	}

}

/*
MIT License

Copyright (c) 2016 Michel Betancourt

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/
package tableroajedrez.caballo;


/**
 * Kernel hace todo el proceso de calculo creando un array ya terminado
 * @author Michel Betancourt
 */
public class Kernel {
	
	private final int numFilas;
	private final int numColumnas;
	private int[][] tablero;
 
 	/*
 		Constructor
 	 */
	public Kernel(int nf, int nc) {
		numFilas = nf;
		numColumnas = nc;
		tablero     = new int[nf][nc];
	}
  
	/*
		Inicia la solucion del problema
	 */
	public int[][] start(int  val1, int val2)
	{
		resolverProblema(val1, val2, 1);
		return tablero;
	}
	

	private  boolean resolverProblema(int f, int c, int num) {
	tablero[f][c] = num;
	if(num==numFilas*numColumnas) 
		return true;
	int[][] posibles = findPosibles(f, c);
	sortPosibles(posibles);   
	for (int[] posible : posibles)
	{
		if (resolverProblema(posible[0], posible[1], num+1))
		{
			return true;
		}
	}
	tablero[f][c]=0;
	return false;
}
 

	private void sortPosibles(int[][] posibles) {
		for(int i=0; i<posibles.length; i++)
		{
			for(int j=i+1; j<posibles.length; j++) 
			{
				int cuantosPosiblesI = findPosibles(posibles[i][0], posibles[i][1]).length;
				int cuantosPosiblesJ = findPosibles(posibles[j][0], posibles[j][1]).length;
				
				if(cuantosPosiblesJ<cuantosPosiblesI) 
				{
					int tmp0 = posibles[i][0];
					posibles[i][0] = posibles[j][0];
					posibles[j][0] = tmp0;
					int tmp1 = posibles[i][1];
					posibles[i][1] = posibles[j][1];
					posibles[j][1] = tmp1;
				}
			}
		}
	}
 
	private int[][] findPosibles(int f, int c) {
		int[][] resp = new int[8][2];
		int pos  = 0;
		
		if(isValido(f-2,c-1))
		{
			resp[pos][0]=f-2; 
			resp[pos++][1]=c-1; 
		}
		
		if(isValido(f-2,c+1))
		{ 
			resp[pos][0]=f-2; 
			resp[pos++][1]=c+1; 
		}
		
		if(isValido(f-1,c-2))
		{ 
			resp[pos][0]=f-1; 
			resp[pos++][1]=c-2; 
		}
		
		if(isValido(f-1,c+2))
		{ 
			resp[pos][0]=f-1;
			resp[pos++][1]=c+2; 
		}
		if(isValido(f+2,c-1))
		{
			resp[pos][0]=f+2; 
			resp[pos++][1]=c-1;
		}
		
		if(isValido(f+2,c+1))
		{
			resp[pos][0]=f+2; 
			resp[pos++][1]=c+1; 
		}
		
		if(isValido(f+1,c-2))
		{
			resp[pos][0]=f+1;
			resp[pos++][1]=c-2; 
		}
		
		if(isValido(f+1,c+2))
		{ 
			resp[pos][0]=f+1; 
			resp[pos++][1]=c+2; 
		}
		
		int[][] tmp = new int[pos][2];
		
		for(int i=0; i<pos; i++) 
		{ 
			tmp[i][0] = resp[i][0];
			tmp[i][1]=resp[i][1];
		}
		
		return tmp;
	}
 
	private boolean isValido(int f, int c) {
		
		if(f<0 || f>numFilas-1 || c<0 || c>numColumnas-1)
			return false;
		
		return tablero[f][c] == 0;
	}
}

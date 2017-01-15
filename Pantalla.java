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

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

/**
 * Clase Pantalla muestra el tablero llama a kernel y recoje la posicion inicial dada por el usuario
 * 
 * @author Michel Betancourt
 * @version 1.0
 * 
 */
public class Pantalla extends JFrame implements ActionListener{
	
	private int Tablero[][];
	private JButton cuadros[][] = new JButton[8][8];
	private JButton botonSiguiente, botonFinal, botonAtras, botonReiniciar;
	private Container contenedor1, contenedor2;
	private Border borde;
	private Kernel kernel;
	private GridBagConstraints posicion;
	private int contador, posicionH, posicionV;
	
	public Pantalla() {
		initComponents();
	}
	
	private void initComponents() {
		
		//-------------------- Instancias --------------------------//
		contenedor1= new Container();
		contenedor2= new Container();
		kernel = new Kernel(8,8);
		posicion = new GridBagConstraints();
		botonSiguiente = new JButton("Siguiente");
		botonAtras = new JButton("Atras");
		botonFinal = new JButton("Final");
		botonReiniciar = new JButton("Reiniciar");
		//----------------//------------------------//----------------//
		
		//---------------- Agrega los botones -----------------//
		contenedor2.add(botonReiniciar);
		contenedor2.add(botonSiguiente);
		contenedor2.add(botonAtras);
		contenedor2.add(botonFinal);
		//----------------//------------------------//----------------//
		
		// --------------- Sets Layouts -------------------------//
		this.getContentPane().setLayout(new GridBagLayout());
		contenedor2.setLayout(new FlowLayout());
		contenedor1.setLayout(new GridLayout(8,8));
		//----------------//------------------------//----------------//
		
		contenedor2.setSize(720, 300);
		
		//-------------------Crea las casillas--------------------//
		borde = BorderFactory.createLineBorder(Color.black, 1);
		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				cuadros[i][j] = new JButton("");
				if (i % 2 == 0)
				{
					if (j % 2 == 0 )
						cuadros[i][j].setBackground(Color.white);
					else
						cuadros[i][j].setBackground(Color.black);
				} else
				{
					if (j % 2 == 1 )
						cuadros[i][j].setBackground(Color.white);
					else
						cuadros[i][j].setBackground(Color.black);
				}
				cuadros[i][j].addActionListener(this);
				cuadros[i][j].setBorder(borde);	
				cuadros[i][j].setHorizontalAlignment(SwingConstants.CENTER);
				cuadros[i][j].setVerticalAlignment(SwingConstants.CENTER);
			}
		}
		for (int i = 0; i < 8; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				contenedor1.add(cuadros[i][j]);
			}
		}
		//----------------//------------------------//----------------//
		
		posicion.gridx = 0;
		posicion.gridy = 0;
		posicion.gridheight = 8;
		posicion.gridwidth = 8;
		posicion.weightx = 1.0;
		posicion.weighty = 1.0;
		posicion.fill = GridBagConstraints.BOTH;
		this.getContentPane().add(contenedor1, posicion);
		posicion.weightx = 1.0;
		posicion.weighty = 0;
		posicion.gridx = 0;
		posicion.gridy = 9;
		posicion.gridheight = 8;
		posicion.gridwidth = 2;
		posicion.fill = GridBagConstraints.HORIZONTAL;
		this.getContentPane().add(contenedor2, posicion);
		
		//------------------- ActionListener --------------------//
		botonSiguiente.addActionListener(this);
		botonReiniciar.addActionListener(this);
		botonAtras.addActionListener(this);
		botonFinal.addActionListener(this);
		//----------------//------------------------//----------------//
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == botonSiguiente && contador < 65)
		{
			contador++;
			muestraTablero();
		}else if(e.getSource() == botonReiniciar && contador != 0)
		{
			contador = 0;
			reiniciaTablero();
		} else if (e.getSource() == botonFinal && contador != 65)
		{
			contador = 65;
			muestraTablero();
		}else if (e.getSource() == botonAtras && contador > 0)
		{
			contador--;
			reiniciaTablero();
			muestraTablero();
		}else if (Tablero == null)
		{
			boolean aux = true;
			for (int i = 0; i < 8 && aux; i++)
			{
				for (int j = 0; j < 8 && aux; j++)
				{
					if (e.getSource() == cuadros[i][j])
					{
						posicionV = i;
						posicionH = j;
						aux = false;
						Tablero = kernel.start(posicionV, posicionH);
						contador++;
						muestraTablero();
					}
				}
			}
		}
	}
	
	
	private void muestraTablero() {
		boolean aux;
		if (Tablero == null)
			Tablero = kernel.start(0,0);
		for (int i = 1; i < contador + 1; i++)
		{
			aux = true;
			for (int j = 0; j < 8 && aux; j++)
			{
				for (int h = 0; h < 8 && aux; h++)
				{
					descolorea();
					if (Tablero[j][h] == i)
					{
						cuadros[j][h].setOpaque(true);
						cuadros[j][h].setBackground(Color.red);
						cuadros[j][h].setText(""+i);
						aux = false;
					}
				}
			}
		}
	}
	
	private void descolorea() {
		boolean aux;
		for (int i = 1; i < contador; i++)
		{
			aux = true;
			for (int j = 0; j < 8 && aux; j++)
			{
				for (int h = 0; h < 8 && aux; h++)
				{
					
					if (Tablero[j][h] == i)
					{
						cuadros[j][h].setBackground(Color.cyan);
						aux = false;
					}
				}
			}
		}
	}
	
	private void reiniciaTablero() {
		for (int i = 0; i < 8;i ++)
		{
			for (int j = 0; j < 8; j++)
			{
				if (i % 2 == 0)
				{
					if (j % 2 == 0 )
						cuadros[i][j].setBackground(Color.white);
					else
						cuadros[i][j].setBackground(Color.black);
				} else
				{
					if (j % 2 == 1 )
						cuadros[i][j].setBackground(Color.white);
					else
						cuadros[i][j].setBackground(Color.black);
				}
				cuadros[i][j].setText("");
			}
		}
	}

	public int getPosicionH() {
		return posicionH;
	}

	public int getPosicionV() {
		return posicionV;
	}
	
	
}

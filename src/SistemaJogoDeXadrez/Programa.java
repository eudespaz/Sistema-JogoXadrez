package SistemaJogoDeXadrez;

import xadrez.PartidaDeXadrez;

public class Programa {

	public static void main(String[] args) {
	
		
		PartidaDeXadrez partidaDeXadrez = new PartidaDeXadrez();
		UI.printBoard(partidaDeXadrez.getPecas());
	

	}

}

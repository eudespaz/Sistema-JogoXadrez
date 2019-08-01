package SistemaJogoDeXadrez;

import xadrez.PartidaDeXadrez;

public class Programa {

	public static void main(String[] args) {
	
		
		PartidaDeXadrez partidaDeXadrez = new PartidaDeXadrez();
		UI.printBoard(partidaDeXadrez.getPecas());
		UI.printBoard(partidaDeXadrez.getPecas());
		System.out.println("Sistem Eudes Paz");

	}

}

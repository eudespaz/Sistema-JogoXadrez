package SistemaJogoDeXadrez;

import java.util.Scanner;

import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;
import xadrez.XadrezPosicao;

public class Programa {

	public static void main(String[] args) {
	
		Scanner entrada = new Scanner(System.in);
		
		PartidaDeXadrez partidaDeXadrez = new PartidaDeXadrez();
		
		
		while (true) {
	
		UI.printBoard(partidaDeXadrez.getPecas());
		System.out.println();
		System.out.print("Origem: ");
		XadrezPosicao fonte = UI.LerXadrezPosicao(entrada);
		
		
		System.out.println();
		System.out.print("destino: ");
		XadrezPosicao alvo = UI.LerXadrezPosicao(entrada);
		
		PecaDeXadrez capturarPeca = partidaDeXadrez.performaceMoverXadrez(fonte, alvo);
		
		
		}
	

	}

}

package SistemaJogoDeXadrez;

import java.util.InputMismatchException;
import java.util.Scanner;

import xadrez.ExcecaoDoXadrez;
import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;
import xadrez.XadrezPosicao;

public class Programa {

	public static void main(String[] args) {
	
		Scanner entrada = new Scanner(System.in);
		
		PartidaDeXadrez partidaDeXadrez = new PartidaDeXadrez();
		
		
		while (true) { 
			try {
				UI.clearScreen();
				UI.printBoard(partidaDeXadrez.getPecas());
				System.out.println();
				System.out.print("Origem: ");
				XadrezPosicao fonte = UI.LerXadrezPosicao(entrada);
				
				boolean[][] possivelMovimentacao = partidaDeXadrez.possivelMovimentacao(fonte);
				UI.clearScreen();
				UI.printBoard(partidaDeXadrez.getPecas(), possivelMovimentacao);
				System.out.println();
				System.out.print("destino: ");
				XadrezPosicao alvo = UI.LerXadrezPosicao(entrada);
				
				PecaDeXadrez capturarPeca = partidaDeXadrez.performaceMoverXadrez(fonte, alvo);
				
			}
			catch (ExcecaoDoXadrez e){
				System.out.println(e.getMessage());
				entrada.nextLine();
			}
			catch (InputMismatchException e) {
				System.out.println(e.getMessage());
				entrada.nextLine();
				
			}
		}
	

	}

}

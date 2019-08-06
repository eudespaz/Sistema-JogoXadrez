package SistemaJogoDeXadrez;


import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


import xadrez.Cor;
import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;
import xadrez.XadrezPosicao;

public class UI {
	
	// https://stackoverflow.com/questions/5762491/how-to-print-color-in-console-using-system-out-println

		public static final String ANSI_RESET = "\u001B[0m";
		public static final String ANSI_BLACK = "\u001B[30m";
		public static final String ANSI_RED = "\u001B[31m";
		public static final String ANSI_GREEN = "\u001B[32m";
		public static final String ANSI_YELLOW = "\u001B[33m";
		public static final String ANSI_BLUE = "\u001B[34m";
		public static final String ANSI_PURPLE = "\u001B[35m";
		public static final String ANSI_CYAN = "\u001B[36m";
		public static final String ANSI_WHITE = "\u001B[37m";

		public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
		public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
		public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
		public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
		public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
		public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
		public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
		public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";
		
		// https://stackoverflow.com/questions/2979383/java-clear-the-console
		public static void clearScreen() {
			System.out.print("\033[H\033[2J");
			System.out.flush();
		}
		
		public static XadrezPosicao LerXadrezPosicao(Scanner entrada) {
		
		try {
			String s = entrada.nextLine();
			char coluna = s.charAt(0);
			int linha = Integer.parseInt(s.substring(1));
			return new XadrezPosicao(coluna, linha);
		}
		catch (RuntimeException e){
			throw new InputMismatchException("Erro a posição do xadrez esta invalida, valide os volumes a1 e h8");
		}
			
	}
	
	public static void printXadrez(PartidaDeXadrez partidaDeXadrez, List<PecaDeXadrez> capiturada) {
		printBoard(partidaDeXadrez.getPecas());
		System.out.println();
		printCapituraPecas(capiturada);
		System.out.println();
		System.out.println("Turno: " + partidaDeXadrez.getTurno());
		System.out.println("Aguardando jogador da Cor: " + partidaDeXadrez.getJogadores());
	}
		
	
	public static void printBoard(PecaDeXadrez[][] pecas) {
		for (int i=0 ; i<pecas.length ; i++) {
			System.out.print((8 - i) + " ");
			for (int j=0 ; j<pecas.length; j++) {
				printPeca(pecas[i][j], false);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}
	
	public static void printBoard(PecaDeXadrez[][] pecas, boolean[][] possivelMovimentacao) {
		for (int i=0 ; i<pecas.length ; i++) {
			System.out.print((8 - i) + " ");
			for (int j=0 ; j<pecas.length; j++) {
				printPeca(pecas[i][j], possivelMovimentacao[i][j]);
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}
	
	
	private static void printPeca(PecaDeXadrez peca, boolean background) {
		if (background) {
			System.out.print(ANSI_BLUE_BACKGROUND);
		}
	    	if (peca == null) {
	            System.out.print("-"  + ANSI_RESET);
	        }
	        else {
	            if (peca.getCor() == Cor.BRANCO) {
	                System.out.print(ANSI_WHITE + peca + ANSI_RESET);
	            }
	            else {
	                System.out.print(ANSI_YELLOW + peca + ANSI_RESET);
	            }
	        }
	        System.out.print(" ");
		}
	
	private static void printCapituraPecas(List<PecaDeXadrez> capiturar) {
		List<PecaDeXadrez> pretas = capiturar.stream().filter(x -> x.getCor() == Cor.PRETO).collect(Collectors.toList()); 
		List<PecaDeXadrez> brancas = capiturar.stream().filter(x -> x.getCor() == Cor.BRANCO).collect(Collectors.toList());
		System.out.println("Peças Capituradas: ");
		System.out.print("Branco: ");
		System.out.print(ANSI_WHITE);
		System.out.print(Arrays.toString(brancas.toArray()));
		System.out.print(ANSI_RESET);
		System.out.println();
		System.out.print("Preto: ");
		System.out.print(ANSI_YELLOW);
		System.out.print(Arrays.toString(pretas.toArray()));
		System.out.print(ANSI_RESET);
	}

}

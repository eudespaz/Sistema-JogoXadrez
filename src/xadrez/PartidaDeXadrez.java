package xadrez;

import gameborda.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaDeXadrez {
	
	private Tabuleiro tabuleiro;
	
	public PartidaDeXadrez() {
		tabuleiro = new Tabuleiro(8, 8);
		configuracaoInicial();
	}

	public PecaDeXadrez[][] getPecas(){
		PecaDeXadrez[][] mat = new PecaDeXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i=0 ; i<tabuleiro.getLinhas(); i++) {
			for (int j=0; j<tabuleiro.getColunas(); j++) {
				mat[i][j] = (PecaDeXadrez)tabuleiro.peca(i,j);
			}
		}
		return mat;
				
		
	}
	private void novaCordenadaXadrez(char coluna, int linha, PecaDeXadrez peca) {
		tabuleiro.lugarPeca(peca, new XadrezPosicao(coluna, linha).toPosicao());
	}
	
	private void configuracaoInicial() {
		
			novaCordenadaXadrez('c', 1, new Torre(tabuleiro, Cor.BRANCO));
			novaCordenadaXadrez('c', 2, new Torre(tabuleiro, Cor.BRANCO));
			novaCordenadaXadrez('d', 2, new Torre(tabuleiro, Cor.BRANCO));
			novaCordenadaXadrez('e', 2, new Torre(tabuleiro, Cor.BRANCO));
			novaCordenadaXadrez('e', 1, new Torre(tabuleiro, Cor.BRANCO));
			novaCordenadaXadrez('d', 1, new Rei(tabuleiro, Cor.BRANCO));
	  
			novaCordenadaXadrez('c', 7, new Torre(tabuleiro, Cor.PRETO));
			novaCordenadaXadrez('c', 8, new Torre(tabuleiro, Cor.PRETO));
			novaCordenadaXadrez('d', 7, new Torre(tabuleiro, Cor.PRETO));
			novaCordenadaXadrez('e', 7, new Torre(tabuleiro, Cor.PRETO));
			novaCordenadaXadrez('e', 8, new Torre(tabuleiro, Cor.PRETO));
			novaCordenadaXadrez('d', 8, new Rei(tabuleiro, Cor.PRETO));
	        
		}
}

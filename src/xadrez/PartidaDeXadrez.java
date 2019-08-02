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
		novaCordenadaXadrez('b', 6, new Torre(tabuleiro, Cor.BRANCO));
		novaCordenadaXadrez('e', 8, new Rei(tabuleiro, Cor.PRETO));
		novaCordenadaXadrez('e', 1,new Rei(tabuleiro, Cor.BRANCO));
	}
}









package xadrez.pecas;

import gameborda.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaDeXadrez;

public class Torre extends PecaDeXadrez{

	public Torre(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
		
	}

	@Override
	public String toString() {
		return "T";
	}
	@Override
	public boolean[][] possivelMovimentacao() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		return mat;
	
		}
	
}
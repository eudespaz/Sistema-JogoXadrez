package xadrez.pecas;

import gameborda.Posicao;
import gameborda.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaDeXadrez;

public class Bispo extends PecaDeXadrez{

	public Bispo(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);
		
	}

	@Override
	public String toString() {
		return "B";
	}
	@Override
	public boolean[][] possivelMovimentacao() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		
		Posicao p = new Posicao(0, 0);
		
		//Noroeste
		p.valores(posicao.getLinha() - 1, posicao.getColuna() - 1);
		while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().pecaPedaco(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.valores(p.getLinha() - 1, p.getColuna() - 1);
		}
		if (getTabuleiro().posicaoExistente(p) && pecaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		//Nordeste	
		p.valores(posicao.getLinha() - 1, posicao.getColuna() + 1);
		while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().pecaPedaco(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.valores(p.getLinha() - 1, p.getColuna() + 1);
		}
		if (getTabuleiro().posicaoExistente(p) && pecaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		
		//Sudeste	
		p.valores(posicao.getLinha() + 1, posicao.getColuna() + 1);
		while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().pecaPedaco(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.valores(p.getLinha() + 1, p.getColuna() + 1);
		}
		if (getTabuleiro().posicaoExistente(p) && pecaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		
		//Sudoeste
		p.valores(posicao.getLinha() + 1, posicao.getColuna() - 1);
		while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().pecaPedaco(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.valores(p.getLinha() + 1, p.getColuna() - 1);
		}
		if (getTabuleiro().posicaoExistente(p) && pecaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}
		
		return mat;
	
		}
	
}
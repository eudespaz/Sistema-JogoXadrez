package xadrez.pecas;

import gameborda.Posicao;
import gameborda.Tabuleiro;
import xadrez.Cor;
import xadrez.PecaDeXadrez;

public class Rainha extends PecaDeXadrez {

	public Rainha(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro, cor);

	}

	@Override
	public String toString() {
		return "Ŕ";
	}

	@Override
	public boolean[][] possivelMovimentacao() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];

		Posicao p = new Posicao(0, 0);

		// Cima
		p.valores(posicao.getLinha() - 1, posicao.getColuna());
		while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().pecaPedaco(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() - 1);
		}
		if (getTabuleiro().posicaoExistente(p) && pecaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Esquerda
		p.valores(posicao.getLinha(), posicao.getColuna() - 1);
		while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().pecaPedaco(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() - 1);
		}
		if (getTabuleiro().posicaoExistente(p) && pecaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Direita
		p.valores(posicao.getLinha(), posicao.getColuna() + 1);
		while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().pecaPedaco(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setColuna(p.getColuna() + 1);
		}
		if (getTabuleiro().posicaoExistente(p) && pecaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Baixo
		p.valores(posicao.getLinha() + 1, posicao.getColuna());
		while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().pecaPedaco(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.setLinha(p.getLinha() + 1);
		}
		if (getTabuleiro().posicaoExistente(p) && pecaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Noroeste
		p.valores(posicao.getLinha() - 1, posicao.getColuna() - 1);
		while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().pecaPedaco(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.valores(p.getLinha() - 1, p.getColuna() - 1);
		}
		if (getTabuleiro().posicaoExistente(p) && pecaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Nordeste
		p.valores(posicao.getLinha() - 1, posicao.getColuna() + 1);
		while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().pecaPedaco(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.valores(p.getLinha() - 1, p.getColuna() + 1);
		}
		if (getTabuleiro().posicaoExistente(p) && pecaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Sudeste
		p.valores(posicao.getLinha() + 1, posicao.getColuna() + 1);
		while (getTabuleiro().posicaoExistente(p) && !getTabuleiro().pecaPedaco(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
			p.valores(p.getLinha() + 1, p.getColuna() + 1);
		}
		if (getTabuleiro().posicaoExistente(p) && pecaOponente(p)) {
			mat[p.getLinha()][p.getColuna()] = true;
		}

		// Sudoeste
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
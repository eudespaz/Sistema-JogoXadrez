package xadrez.pecas;

import gameborda.Posicao;
import gameborda.Tabuleiro;
import xadrez.Cor;
import xadrez.PartidaDeXadrez;
import xadrez.PecaDeXadrez;

public class Peao extends PecaDeXadrez{
	
	private PartidaDeXadrez partidaDeXadrez;

	public Peao(Tabuleiro tabuleiro, Cor cor, PartidaDeXadrez partidaDeXadrez) {
		super(tabuleiro, cor);
		this.partidaDeXadrez = partidaDeXadrez;
		
	}

	@Override
	public boolean[][] possivelMovimentacao() {
		boolean[][] mat = new boolean[getTabuleiro().getLinhas()][getTabuleiro().getColunas()];
		Posicao p = new Posicao(0, 0);
		
		if (getCor() == Cor.BRANCO) {
			p.valores(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().pecaPedaco(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.valores(posicao.getLinha() - 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() - 1, posicao.getColuna());
			if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().pecaPedaco(p) && getTabuleiro().posicaoExistente(p2) && 
				!getTabuleiro().pecaPedaco(p2) && getContagemMovimento() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.valores(posicao.getLinha() - 1, posicao.getColuna() - 1);
			if (getTabuleiro().posicaoExistente(p) && pecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.valores(posicao.getLinha() - 1, posicao.getColuna() + 1);
			if (getTabuleiro().posicaoExistente(p) && pecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			
			if (posicao.getLinha() == 3 ) {
				Posicao eudes = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if (getTabuleiro().posicaoExistente(eudes) && pecaOponente(eudes) && getTabuleiro().peca(eudes) == partidaDeXadrez.getEmPassantVulneravel()) {
					mat[eudes.getLinha() - 1][eudes.getColuna()] = true;
				}
				Posicao paz = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if (getTabuleiro().posicaoExistente(paz) && pecaOponente(paz) && getTabuleiro().peca(paz) == partidaDeXadrez.getEmPassantVulneravel()) {
					mat[paz.getLinha() - 1][paz.getColuna()] = true;
				}
			}
			
			
		}
		else {
			p.valores(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().pecaPedaco(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.valores(posicao.getLinha() + 2, posicao.getColuna());
			Posicao p2 = new Posicao(posicao.getLinha() + 1, posicao.getColuna());
			if (getTabuleiro().posicaoExistente(p) && !getTabuleiro().pecaPedaco(p) && getTabuleiro().posicaoExistente(p2) && 
				!getTabuleiro().pecaPedaco(p2) && getContagemMovimento() == 0) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.valores(posicao.getLinha() + 1, posicao.getColuna() - 1);
			if (getTabuleiro().posicaoExistente(p) && pecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
			}
			p.valores(posicao.getLinha() + 1, posicao.getColuna() + 1);
			if (getTabuleiro().posicaoExistente(p) && pecaOponente(p)) {
				mat[p.getLinha()][p.getColuna()] = true;
				
			}
			
			
			if (posicao.getLinha() == 4 ) {
				Posicao eudes = new Posicao(posicao.getLinha(), posicao.getColuna() - 1);
				if (getTabuleiro().posicaoExistente(eudes) && pecaOponente(eudes) && getTabuleiro().peca(eudes) == partidaDeXadrez.getEmPassantVulneravel()) {
					mat[eudes.getLinha() + 1][eudes.getColuna()] = true;
				}
				Posicao paz = new Posicao(posicao.getLinha(), posicao.getColuna() + 1);
				if (getTabuleiro().posicaoExistente(paz) && pecaOponente(paz) && getTabuleiro().peca(paz) == partidaDeXadrez.getEmPassantVulneravel()) {
					mat[paz.getLinha() + 1][paz.getColuna()] = true;
				}
			}
			
			
		}
		
		return mat;
	}

	@Override
	public String toString() {
		return "P";
	}
	
	

}

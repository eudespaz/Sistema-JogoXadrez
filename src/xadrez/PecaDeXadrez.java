package xadrez;

import gameborda.Peca;
import gameborda.Posicao;
import gameborda.Tabuleiro;

public abstract class PecaDeXadrez extends Peca {
	
	private Cor cor;
	private int contagemMovimento;
	

	public PecaDeXadrez(Tabuleiro tabuleiro, Cor cor) {
		super(tabuleiro);
		this.cor = cor;
	}
	
	
	public Cor getCor() {
		return cor;
	}
	
	public int getContagemMovimento() {
		return contagemMovimento;
	}
	
	public void incremantarContagemMovimento() {
		contagemMovimento++;
	}
	
	public void descremantarContagemMovimento() {
		contagemMovimento--;
	}
	
	public XadrezPosicao getXadrezPosicao() {
		return XadrezPosicao.fromPosicao(posicao);
	}

	protected boolean pecaOponente(Posicao posicao) {
		PecaDeXadrez p = (PecaDeXadrez)getTabuleiro().peca(posicao);
		return p != null && p.getCor() !=cor;
		
	}
	

}

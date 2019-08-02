package gameborda;

public abstract class Peca {
	
	protected Posicao posicao; 
	private Tabuleiro tabuleiro;
	
	
	public Peca(Tabuleiro tabuleiro) {
		this.tabuleiro = tabuleiro;
		posicao = null;
	}


	protected Tabuleiro getTabuleiro() {
		return tabuleiro;
	}		
	
	public abstract boolean[][] possivelMovimentacao();
	
	public boolean possivelMovimentacao(Posicao posicao) {
		return possivelMovimentacao()[posicao.getLinha()][posicao.getColuna()];
	}

	public boolean ChamarNovamentePossivelMovimentacao() {
		boolean[][] mat = possivelMovimentacao();
		for(int i = 0; i<mat.length; i++) {
			for (int j = 0; j<mat.length; j++ ) {
				if (mat [i][j]) {
					return true;
				}
			}
		}
		return false;
	}
}

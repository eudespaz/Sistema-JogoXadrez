package gameborda;

public class Tabuleiro {
	
	private int linhas;
	private int colunas;
	private Peca[][] pecas;
	
	
	public Tabuleiro(int linhas, int colunas) {
		if (linhas < 1 || colunas < 1) {
			throw new ExcecaoTabuleiro("Erro criando tabuleiro, é necessario criar uma linha e uma coluna");
		}
		
		this.linhas = linhas;
		this.colunas = colunas;
		pecas = new Peca[linhas][colunas];
	}

	public int getLinhas() {
		return linhas;
	}

	public int getColunas() {
		return colunas;
	}
	
	public Peca peca (int linha, int coluna ) {
		if (!posicaoExistente(linha, coluna)) {
			throw new ExcecaoTabuleiro("Posição não esta no Tabuleiro");
		}
		return pecas[linha][coluna];
	}

	public Peca peca(Posicao posicao) {
		if (!posicaoExistente(posicao)) {
			throw new ExcecaoTabuleiro("Posição não esta no Tabuleiro");
		}
		return pecas[posicao.getLinha()][posicao.getColuna()]; 
	}
	
	public void lugarPeca(Peca peca, Posicao posicao) {
		if (pecaPedaco(posicao)) {
			throw new ExcecaoTabuleiro("Já existe a peça na posição: " + posicao);			
		}
		pecas[posicao.getLinha()][posicao.getColuna()] = peca;
		peca.posicao = posicao;
	}
	
	public Peca removePeca(Posicao posicao) {
		if (!posicaoExistente(posicao)) {
			throw new ExcecaoTabuleiro("Posição do tabuleiro não existe");
		}
		if (peca(posicao) == null){
			return null;
		}
		Peca aux = peca(posicao);
		aux.posicao = null;
		pecas[posicao.getLinha()][posicao.getColuna()] = null;
		return aux;
	}
	
	private boolean posicaoExistente(int linha, int colula) {
		return linha >= 0 && linha < linhas && colula >= 0 && colula < colunas;
		
	}
	
	public boolean posicaoExistente(Posicao posicao) {
		return posicaoExistente(posicao.getLinha(), posicao.getColuna());
		
	}
	
	public boolean pecaPedaco(Posicao posicao) {
		if (!posicaoExistente(posicao)) {
			throw new ExcecaoTabuleiro("Posição não esta no Tabuleiro");
		}
		return peca(posicao) != null;
	}
}


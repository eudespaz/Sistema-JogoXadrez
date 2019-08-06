package xadrez;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import gameborda.Peca;
import gameborda.Posicao;
import gameborda.Tabuleiro;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaDeXadrez {
	
	private int turno;
	private Tabuleiro tabuleiro;
	private Cor jogadores;
	private boolean cheque; 
	
	private List<Peca> pecaDoTabuleiro = new ArrayList<>();
	private List<Peca> capiturarPecas = new ArrayList<>();
	
	
	public PartidaDeXadrez() {
		
		tabuleiro = new Tabuleiro(8, 8);
		turno = 1;
		jogadores = Cor.BRANCO;
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
	
	public int getTurno() {
		return turno;
	}

	public Cor getJogadores() {
		return jogadores;
	}

	public boolean getCheque() {
		return cheque;
	}
	
	public boolean[][] possivelMovimentacao(XadrezPosicao posicaoFonte ) {
		Posicao posicao = posicaoFonte.toPosicao();
		validatePosicaoFonte(posicao);
		return tabuleiro.peca(posicao).possivelMovimentacao();
		
	}
			
			
	public PecaDeXadrez performaceMoverXadrez(XadrezPosicao posicaoFonte, XadrezPosicao posicionarAlvo) {
		Posicao fonte = posicaoFonte.toPosicao();
		Posicao alvo = posicionarAlvo.toPosicao();
		validatePosicaoFonte(fonte);
		ValidacaodestinoPosicao(fonte, alvo);
		Peca capturarPeca = realizarMovimento(fonte, alvo);
		
		if (testeCheque(jogadores)) {
			desfazerMovimento(fonte, alvo, capturarPeca);
			throw new ExcecaoDoXadrez("Você não pode se colocar em cheque");
		}
		
		cheque = (testeCheque(oponente(jogadores))) ? true : false;
		
		passarTurno();
		return (PecaDeXadrez)capturarPeca;
	}
	

	private Peca realizarMovimento(Posicao fonte, Posicao alvo) {
		Peca p = tabuleiro.removePeca(fonte);
		Peca capturarPeca = tabuleiro.removePeca(alvo);
		tabuleiro.lugarPeca(p, alvo);
		
		if (capturarPeca != null) {
			pecaDoTabuleiro.remove(capturarPeca);
			capiturarPecas.add(capturarPeca);
			
		}
		return capturarPeca;
	}
	
	private void desfazerMovimento(Posicao fonte, Posicao alvo, Peca capturarPeca) {
		Peca p = tabuleiro.removePeca(alvo);
		tabuleiro.lugarPeca(p, fonte);
		
		if (capturarPeca != null) {
			tabuleiro.lugarPeca(capturarPeca, fonte);
			capiturarPecas.remove(capturarPeca);
			pecaDoTabuleiro.add(capturarPeca);
		}
		
	}
	
	private void validatePosicaoFonte(Posicao posicao) {
		if (!tabuleiro.pecaPedaco(posicao)) {
			throw new ExcecaoDoXadrez("Não existe peça nesta posição");
		}
		if (jogadores != ((PecaDeXadrez)tabuleiro.peca(posicao)).getCor()) {
			throw new ExcecaoDoXadrez("A peça escolhida não é sua.");
			
		}
		if (!tabuleiro.peca(posicao).ChamarNovamentePossivelMovimentacao()) {
			throw new ExcecaoDoXadrez("Não é possivel mover a peça");
			
		}
	}
	

	private void ValidacaodestinoPosicao(Posicao fonte, Posicao alvo) {
		if (!tabuleiro.peca(fonte).possivelMovimentacao(alvo)) {
			throw new ExcecaoDoXadrez("A peça escolhida não pode mover para o destino");
		}
	}
	
	private void passarTurno() {
		turno++;
		jogadores = (jogadores == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
	}
	
	private Cor oponente(Cor cor) {
		return (cor == Cor.BRANCO) ? Cor.PRETO : Cor.BRANCO;
		
	}
	
	private PecaDeXadrez rei(Cor cor) {
		List<Peca> list = pecaDoTabuleiro.stream().filter(x -> ((PecaDeXadrez)x).getCor()== cor).collect(Collectors.toList()); 
		for (Peca p : list) {
			if (p instanceof Rei) {
				return (PecaDeXadrez)p;
			}
		}
		throw new IllegalStateException("Não existe " + cor + " Rei no tabuleiro");
	}
	
	private boolean testeCheque(Cor cor) {
		Posicao reiPosicao = rei(cor).getXadrezPosicao().toPosicao();
		List<Peca> oponentePecas = pecaDoTabuleiro.stream().filter(x -> ((PecaDeXadrez)x).getCor() == oponente(cor)).collect(Collectors.toList());
		for (Peca p : oponentePecas) {
			boolean[][] mat = p.possivelMovimentacao();
			if (mat[reiPosicao.getLinha()][reiPosicao.getColuna()]) {
				return true;
			}
		}
		return false;
	}
	

	private void novaCordenadaXadrez(char coluna, int linha, PecaDeXadrez peca) {
		tabuleiro.lugarPeca(peca, new XadrezPosicao(coluna, linha).toPosicao());
		pecaDoTabuleiro.add(peca);
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

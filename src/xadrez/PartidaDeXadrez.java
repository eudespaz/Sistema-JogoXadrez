package xadrez;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import gameborda.Peca;
import gameborda.Posicao;
import gameborda.Tabuleiro;
import xadrez.pecas.Bispo;
import xadrez.pecas.Cavalo;
import xadrez.pecas.Peao;
import xadrez.pecas.Rainha;
import xadrez.pecas.Rei;
import xadrez.pecas.Torre;

public class PartidaDeXadrez {

	private int turno;
	private Tabuleiro tabuleiro;
	private Cor jogadores;
	private boolean cheque;
	private boolean chequeMate;
	private PecaDeXadrez emPassantVulneravel;
	private PecaDeXadrez promocao;

	private List<Peca> pecaDoTabuleiro = new ArrayList<>();
	private List<Peca> capiturarPecas = new ArrayList<>();

	public PartidaDeXadrez() {

		tabuleiro = new Tabuleiro(8, 8);
		turno = 1;
		jogadores = Cor.BRANCO;
		configuracaoInicial();
	}

	public PecaDeXadrez[][] getPecas() {
		PecaDeXadrez[][] mat = new PecaDeXadrez[tabuleiro.getLinhas()][tabuleiro.getColunas()];
		for (int i = 0; i < tabuleiro.getLinhas(); i++) {
			for (int j = 0; j < tabuleiro.getColunas(); j++) {
				mat[i][j] = (PecaDeXadrez) tabuleiro.peca(i, j);
			}
		}
		return mat;

	}

	public boolean getChequeMate() {
		return chequeMate;
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

	public PecaDeXadrez getEmPassantVulneravel() {
		return emPassantVulneravel;
	}

	public PecaDeXadrez getPromocao() {
		return promocao;
	}
	public boolean[][] possivelMovimentacao(XadrezPosicao posicaoFonte) {
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

		PecaDeXadrez pecaQueMovel = (PecaDeXadrez) tabuleiro.peca(alvo);

		promocao = null;
		if (pecaQueMovel instanceof Peao) {
			if ((pecaQueMovel.getCor() == Cor.BRANCO && alvo.getLinha() == 0) || (pecaQueMovel.getCor() == Cor.PRETO && alvo.getLinha() == 7)) {
				promocao = (PecaDeXadrez)tabuleiro.peca(alvo);
				promocao = replacePromocaoPeca("Ŕ");
			}
		}
		
		cheque = (testeCheque(oponente(jogadores))) ? true : false;

		if (testeChequeMate(oponente(jogadores))) {
			chequeMate = true;
		} else {
			passarTurno();

		}

		if (pecaQueMovel instanceof Peao
				&& (alvo.getLinha() == fonte.getLinha() - 2 || alvo.getLinha() == fonte.getLinha() + 2)) {
			emPassantVulneravel = pecaQueMovel;
		} else {
			emPassantVulneravel = null;
		}

		return (PecaDeXadrez) capturarPeca;
	}
	
	public PecaDeXadrez replacePromocaoPeca(String type) {
		if (promocao == null) {
			throw new IllegalStateException("A peça não foi promovida");
		}
		if (!type.equals("B") && !type.equals("C") && !type.equals("T") && !type.equals("Ŕ")) {
			throw new InvalidParameterException("Tipo de Promação invalida");
		}
		
		Posicao pos = promocao.getXadrezPosicao().toPosicao();
		Peca p = tabuleiro.removePeca(pos);
		pecaDoTabuleiro.remove(p);
		
		PecaDeXadrez novaPeca = novaPeca(type, promocao.getCor());
		tabuleiro.lugarPeca(novaPeca, pos);
		pecaDoTabuleiro.add(novaPeca);
		
		return novaPeca;
		
		
	}
	
	private PecaDeXadrez novaPeca(String type, Cor cor) {
		if (type.equals("B")) return new Bispo(tabuleiro, cor);
		if (type.equals("C")) return new Cavalo(tabuleiro, cor);
		if (type.equals("Ŕ")) return new Rainha(tabuleiro, cor);
		 return new Torre(tabuleiro, cor);
	}
	

	private Peca realizarMovimento(Posicao fonte, Posicao alvo) {
		PecaDeXadrez p = (PecaDeXadrez) tabuleiro.removePeca(fonte);
		p.incremantarContagemMovimento();
		Peca capturarPeca = tabuleiro.removePeca(alvo);
		tabuleiro.lugarPeca(p, alvo);

		if (capturarPeca != null) {
			pecaDoTabuleiro.remove(capturarPeca);
			capiturarPecas.add(capturarPeca);

		}
		if (p instanceof Rei && alvo.getColuna() == fonte.getColuna() + 2) {
			Posicao origemT = new Posicao(fonte.getLinha(), fonte.getColuna() + 3);
			Posicao destinoT = new Posicao(fonte.getLinha(), fonte.getColuna() + 1);
			PecaDeXadrez Torre = (PecaDeXadrez) tabuleiro.removePeca(origemT);
			tabuleiro.lugarPeca(Torre, destinoT);
			Torre.incremantarContagemMovimento();
		}

		if (p instanceof Rei && alvo.getColuna() == fonte.getColuna() - 2) {
			Posicao origemT = new Posicao(fonte.getLinha(), fonte.getColuna() - 4);
			Posicao destinoT = new Posicao(fonte.getLinha(), fonte.getColuna() - 1);
			PecaDeXadrez Torre = (PecaDeXadrez) tabuleiro.removePeca(origemT);
			tabuleiro.lugarPeca(Torre, destinoT);
			Torre.incremantarContagemMovimento();
		}

		if (p instanceof Peao) {
			if (fonte.getColuna() != alvo.getColuna() && capturarPeca == null) {
				Posicao peaoPosicao;
				if (p.getCor() == Cor.BRANCO) {
					peaoPosicao = new Posicao(alvo.getLinha() + 1, alvo.getColuna());

				} else {
					peaoPosicao = new Posicao(alvo.getLinha() - 1, alvo.getColuna());
				}
				capturarPeca = tabuleiro.removePeca(peaoPosicao);
				capiturarPecas.add(capturarPeca);
				pecaDoTabuleiro.remove(capturarPeca);
			}

		}

		return capturarPeca;
	}

	private void desfazerMovimento(Posicao fonte, Posicao alvo, Peca capturarPeca) {
		PecaDeXadrez p = (PecaDeXadrez) tabuleiro.removePeca(alvo);
		tabuleiro.lugarPeca(p, fonte);
		p.descremantarContagemMovimento();
		if (capturarPeca != null) {
			tabuleiro.lugarPeca(capturarPeca, fonte);
			capiturarPecas.remove(capturarPeca);
			pecaDoTabuleiro.add(capturarPeca);
		}

		if (p instanceof Rei && alvo.getColuna() == fonte.getColuna() + 2) {
			Posicao origemT = new Posicao(fonte.getLinha(), fonte.getColuna() + 3);
			Posicao destinoT = new Posicao(fonte.getLinha(), fonte.getColuna() + 1);
			PecaDeXadrez Torre = (PecaDeXadrez) tabuleiro.removePeca(destinoT);
			tabuleiro.lugarPeca(Torre, origemT);
			Torre.descremantarContagemMovimento();
		}

		if (p instanceof Rei && alvo.getColuna() == fonte.getColuna() - 2) {
			Posicao origemT = new Posicao(fonte.getLinha(), fonte.getColuna() - 4);
			Posicao destinoT = new Posicao(fonte.getLinha(), fonte.getColuna() - 1);
			PecaDeXadrez Torre = (PecaDeXadrez) tabuleiro.removePeca(destinoT);
			tabuleiro.lugarPeca(Torre, origemT);
			Torre.descremantarContagemMovimento();
		}
		if (p instanceof Peao) {
			if (fonte.getColuna() != alvo.getColuna() && capturarPeca == emPassantVulneravel) {
				PecaDeXadrez peao = (PecaDeXadrez)tabuleiro.removePeca(alvo);
				Posicao peaoPosicao;
				if (p.getCor() == Cor.BRANCO) {
					peaoPosicao = new Posicao(3, alvo.getColuna());

				} else {
					peaoPosicao = new Posicao(4, alvo.getColuna());
				}
				tabuleiro.lugarPeca(peao, peaoPosicao);
			}

		}

	}

	private void validatePosicaoFonte(Posicao posicao) {
		if (!tabuleiro.pecaPedaco(posicao)) {
			throw new ExcecaoDoXadrez("Não existe peça nesta posição");
		}
		if (jogadores != ((PecaDeXadrez) tabuleiro.peca(posicao)).getCor()) {
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
		List<Peca> list = pecaDoTabuleiro.stream().filter(x -> ((PecaDeXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : list) {
			if (p instanceof Rei) {
				return (PecaDeXadrez) p;
			}
		}
		throw new IllegalStateException("Não existe " + cor + " Rei no tabuleiro");
	}

	private boolean testeCheque(Cor cor) {
		Posicao reiPosicao = rei(cor).getXadrezPosicao().toPosicao();
		List<Peca> oponentePecas = pecaDoTabuleiro.stream().filter(x -> ((PecaDeXadrez) x).getCor() == oponente(cor))
				.collect(Collectors.toList());
		for (Peca p : oponentePecas) {
			boolean[][] mat = p.possivelMovimentacao();
			if (mat[reiPosicao.getLinha()][reiPosicao.getColuna()]) {
				return true;
			}
		}
		return false;
	}

	private boolean testeChequeMate(Cor cor) {
		if (!testeCheque(cor)) {
			return false;
		}
		List<Peca> list = pecaDoTabuleiro.stream().filter(x -> ((PecaDeXadrez) x).getCor() == cor)
				.collect(Collectors.toList());
		for (Peca p : list) {
			boolean[][] mat = p.possivelMovimentacao();
			for (int i = 0; i < tabuleiro.getLinhas(); i++) {
				for (int j = 0; j < tabuleiro.getLinhas(); j++) {
					if (mat[i][j]) {
						Posicao fonte = ((PecaDeXadrez) p).getXadrezPosicao().toPosicao();
						Posicao alvo = new Posicao(i, j);
						Peca capturarPeca = realizarMovimento(fonte, alvo);
						boolean testeCheque = testeCheque(cor);
						desfazerMovimento(fonte, alvo, capturarPeca);
						if (!testeCheque) {
							return false;
						}
					}

				}
			}
		}
		return true;
	}

	private void novaCordenadaXadrez(char coluna, int linha, PecaDeXadrez peca) {
		tabuleiro.lugarPeca(peca, new XadrezPosicao(coluna, linha).toPosicao());
		pecaDoTabuleiro.add(peca);
	}

	private void configuracaoInicial() {

		novaCordenadaXadrez('a', 1, new Torre(tabuleiro, Cor.BRANCO));
		novaCordenadaXadrez('b', 1, new Cavalo(tabuleiro, Cor.BRANCO));
		novaCordenadaXadrez('c', 1, new Bispo(tabuleiro, Cor.BRANCO));
		novaCordenadaXadrez('d', 1, new Rainha(tabuleiro, Cor.BRANCO));
		novaCordenadaXadrez('e', 1, new Rei(tabuleiro, Cor.BRANCO, this));
		novaCordenadaXadrez('f', 1, new Bispo(tabuleiro, Cor.BRANCO));
		novaCordenadaXadrez('g', 1, new Cavalo(tabuleiro, Cor.BRANCO));
		novaCordenadaXadrez('h', 1, new Torre(tabuleiro, Cor.BRANCO));
		novaCordenadaXadrez('a', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		novaCordenadaXadrez('b', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		novaCordenadaXadrez('c', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		novaCordenadaXadrez('d', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		novaCordenadaXadrez('e', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		novaCordenadaXadrez('f', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		novaCordenadaXadrez('g', 2, new Peao(tabuleiro, Cor.BRANCO, this));
		novaCordenadaXadrez('h', 2, new Peao(tabuleiro, Cor.BRANCO, this));

		novaCordenadaXadrez('a', 8, new Torre(tabuleiro, Cor.PRETO));
		novaCordenadaXadrez('b', 8, new Cavalo(tabuleiro, Cor.PRETO));
		novaCordenadaXadrez('c', 8, new Bispo(tabuleiro, Cor.PRETO));
		novaCordenadaXadrez('d', 8, new Rainha(tabuleiro, Cor.PRETO));
		novaCordenadaXadrez('e', 8, new Rei(tabuleiro, Cor.PRETO, this));
		novaCordenadaXadrez('f', 8, new Bispo(tabuleiro, Cor.PRETO));
		novaCordenadaXadrez('g', 8, new Cavalo(tabuleiro, Cor.PRETO));
		novaCordenadaXadrez('h', 8, new Torre(tabuleiro, Cor.PRETO));
		novaCordenadaXadrez('a', 7, new Peao(tabuleiro, Cor.PRETO, this));
		novaCordenadaXadrez('b', 7, new Peao(tabuleiro, Cor.PRETO, this));
		novaCordenadaXadrez('c', 7, new Peao(tabuleiro, Cor.PRETO, this));
		novaCordenadaXadrez('d', 7, new Peao(tabuleiro, Cor.PRETO, this));
		novaCordenadaXadrez('e', 7, new Peao(tabuleiro, Cor.PRETO, this));
		novaCordenadaXadrez('f', 7, new Peao(tabuleiro, Cor.PRETO, this));
		novaCordenadaXadrez('g', 7, new Peao(tabuleiro, Cor.PRETO, this));
		novaCordenadaXadrez('h', 7, new Peao(tabuleiro, Cor.PRETO, this));
	}
}

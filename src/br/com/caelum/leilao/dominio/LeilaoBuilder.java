package br.com.caelum.leilao.dominio;

public class LeilaoBuilder {

	private Leilao leilao;
	
	public LeilaoBuilder para(String descricao){
		this.leilao = new Leilao(descricao);
		return this;
	}
	
	public LeilaoBuilder propoe(Lance lance) {
		leilao.propoe(lance);
		return this;
	}
	
	public Leilao build() {
		return this.leilao;
	}
	
}

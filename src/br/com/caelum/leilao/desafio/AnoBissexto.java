package br.com.caelum.leilao.desafio;

public class AnoBissexto {

	private Integer ano;

	public AnoBissexto(int ano) {
		this.setAno(ano);
	}

	public boolean ehBissexto() {
		return ehBissexto(ano);
	}

	public boolean ehBissexto(int ano) {

		boolean multiploDe4 = multiplosDe4Passam(ano);
		boolean naoEhMultiploDe100 = multiplosDe100NaoPassam(ano);
		
		boolean eh = multiploDe4 && naoEhMultiploDe100;
		
		if (multiplosDe400Passam(ano)) {
			eh = multiplosDe400Passam(ano);
		}
		return eh;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}
	
	private boolean multiplosDe400Passam(int ano) {
		if (ano % 400 == 0) {
			return true;
		}

		return false;
	}

	private boolean multiplosDe4Passam(int ano) {

		if (ano % 4 == 0) {
			return true;
		}

		return false;
	}

	private boolean multiplosDe100NaoPassam(int ano) {

		if (ano % 100 == 0) {
			return false;
		}

		return true;

	}

}

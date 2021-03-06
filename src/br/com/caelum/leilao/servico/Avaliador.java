package br.com.caelum.leilao.servico;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;

public class Avaliador {

	private double maiorDeTodos = Double.NEGATIVE_INFINITY;
	private double menorDeTodos = Double.POSITIVE_INFINITY;
	private List<Lance> maiores;

	public Avaliador() {

	}

	public void avalia(Leilao leilao) {

		if (leilao.getLances().size() == 0)
			throw new RuntimeException("Não é possível avaliar um leilão sem lances");

		for (Lance lance : leilao.getLances()) {

			if (lance.getValor() > getMaiorLance()) {
				setMaiorDeTodos(lance.getValor());
			}

			if (lance.getValor() < getMenorLance()) {
				setMenorLance(lance.getValor());
			}
		}

		pegaOsMaioresNo(leilao);

	}

	public double valorMedio(Leilao leilao) {

		double soma = 0;
		int qtd = 0;

		for (Lance lance : leilao.getLances()) {
			soma = soma + lance.getValor();
			qtd++;
		}

		double valorMedio = soma / qtd;
		return valorMedio;

	}

	private void pegaOsMaioresNo(Leilao leilao) {

		maiores = new ArrayList<Lance>(leilao.getLances());

		Collections.sort(maiores, new Comparator<Lance>() {

			public int compare(Lance o1, Lance o2) {

				if (o1.getValor() < o2.getValor())
					return 1;

				if (o1.getValor() > o2.getValor())
					return -1;

				return 0;
			}
		});

		maiores = maiores.subList(0, maiores.size() > 3 ? 3 : maiores.size());
	}

	public List<Lance> getTresMaiores() {
		return this.maiores;
	}

	public double getMaiorLance() {
		return maiorDeTodos;
	}

	public double getMaiorLance(Leilao leilao) {
		this.avalia(leilao);
		return getMaiorLance();
	}

	public void setMaiorDeTodos(double maiorDeTodos) {
		this.maiorDeTodos = maiorDeTodos;
	}

	public double getMenorLance() {
		return menorDeTodos;
	}

	public void setMenorLance(double menorDeTodos) {
		this.menorDeTodos = menorDeTodos;
	}

}

package br.com.caelum.leilao.servico;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;

public class AvaliadorTest {

	private List<Usuario> usuarios = new ArrayList<>();
	private Avaliador leiloeiro = new Avaliador();
	private Leilao leilao = new Leilao("Playstation 3");

	public AvaliadorTest() {

		Usuario joao = new Usuario("João");
		Usuario jose = new Usuario("José");
		Usuario maria = new Usuario("Maria");
		this.usuarios.add(joao);
		this.usuarios.add(jose);
		this.usuarios.add(maria);

	}

	@Test
	public void avaliadorAvaliaCorretamenteEmOrdemCrescente() {

		leilao.propoe(new Lance(usuarios.get(0), 250.00));
		leilao.propoe(new Lance(usuarios.get(1), 300.00));
		leilao.propoe(new Lance(usuarios.get(2), 400.00));
		leiloeiro.avalia(leilao);

		double esperaMaior = 400.00;
		double atualMaior = leiloeiro.getMaiorLance();
		double esperaMenor = 250.00;
		double atualMenor = leiloeiro.getMenorLance();

		assertEquals(esperaMaior, atualMaior, 0.00001);
		assertEquals(esperaMenor, atualMenor, 0.00001);

	}

	@Test
	public void AvalieSeValorMedioEstaSendoCalculadoCorretamenteParaUmaListaDeValoresAleatorios() {

		Random inteiro = new Random();
		int x = inteiro.nextInt(3);

		Random lance = new Random();
		double soma = 0;
		double qtd = 0;

		for (int i = 0; i < 10000; i++) {

			double valorLance = lance.nextDouble() * 1000;
			leilao.propoe(new Lance(usuarios.get(x), valorLance));
			soma += valorLance;
			qtd += 1;

		}

		double mediaEsperada = soma / qtd;
		double mediaAvaliada = leiloeiro.valorMedio(leilao);

		assertEquals(mediaEsperada, mediaAvaliada, 0.0000001);

	}

	@Test
	public void AvalieLeilaoComApenasUmLance() {
		Usuario joao = new Usuario("Joao");
		Leilao leilao = new Leilao("Playstation 3 Novo");

		leilao.propoe(new Lance(joao, 200.0));

		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);

		assertEquals(200, leiloeiro.getMaiorLance(), 0.0001);
		assertEquals(200, leiloeiro.getMenorLance(), 0.0001);
	}

	@Test
	public void encontreOsTresMaioresLances() {
		Usuario joao = new Usuario("João");
		Usuario maria = new Usuario("Maria");
		Leilao leilao = new Leilao("Playstation 3 Novo");

		leilao.propoe(new Lance(joao, 100.0));
		leilao.propoe(new Lance(maria, 200.0));
		leilao.propoe(new Lance(joao, 300.0));
		leilao.propoe(new Lance(maria, 400.0));

		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);

		List<Lance> maiores = leiloeiro.getTresMaiores();

		assertEquals(3, maiores.size());
		assertEquals(400, maiores.get(0).getValor(), 0.00001);
		assertEquals(300, maiores.get(1).getValor(), 0.00001);
		assertEquals(200, maiores.get(2).getValor(), 0.00001);
	}
	
	@Test
	public void encontreOsTresMaioresLancesQuandoNaoHouverNenhumLance() {

		Leilao leilao = new Leilao("Playstation 3 Novo");

		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);

		List<Lance> maiores = leiloeiro.getTresMaiores();

		assertEquals(0, maiores.size());
		assertTrue(maiores.isEmpty());
	}
	
	@Test
	public void avaliaOrdemAleatoriaDeValores() {

		Usuario joao = new Usuario("João");
		Usuario maria = new Usuario("Maria");
		Leilao leilao = new Leilao("Playstation 3 Novo");

		leilao.propoe(new Lance(joao, 500.0));
		leilao.propoe(new Lance(maria, 200.0));
		leilao.propoe(new Lance(joao, 800.0));
		leilao.propoe(new Lance(maria, 400.0));
		leilao.propoe(new Lance(joao, 500.5));
		leilao.propoe(new Lance(maria, 200.5));
		leilao.propoe(new Lance(joao, 800.5));
		leilao.propoe(new Lance(maria, 400.5));


		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);
		
		assertEquals(800.5, leiloeiro.getMaiorLance(), 0.00001);
		assertEquals(200, leiloeiro.getMenorLance(), 0.00001);

	}
	
	@Test
	public void avaliaOrdemDecrescente() {

		Usuario joao = new Usuario("João");
		Usuario maria = new Usuario("Maria");
		Leilao leilao = new Leilao("Playstation 3 Novo");

		leilao.propoe(new Lance(joao, 500.0));
		leilao.propoe(new Lance(maria, 400.0));
		leilao.propoe(new Lance(joao, 300.0));
		leilao.propoe(new Lance(maria, 200.0));
		leilao.propoe(new Lance(joao, 100.5));
		leilao.propoe(new Lance(maria, 20.5));
		leilao.propoe(new Lance(joao, 8.5));
		leilao.propoe(new Lance(maria, 4.5));


		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);
		
		assertEquals(500, leiloeiro.getMaiorLance(), 0.00001);
		assertEquals(4.500, leiloeiro.getMenorLance(), 0.00001);

	}
}

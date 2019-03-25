package br.com.caelum.leilao.servico;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.LeilaoBuilder;
import br.com.caelum.leilao.dominio.Usuario;

public class AvaliadorTest {

	private List<Usuario> usuarios = new ArrayList<>();
	private Avaliador leiloeiro;
	private Leilao leilaoValoresCrescentes;
	private Leilao leilaoValoresDecrescentes;
	private Usuario joao;
	private Usuario jose;
	private Usuario maria;
	private Usuario joaoAlfredo;
	private Usuario rosa;

	@After
	public void finaliza() {
		System.out.println("Finalizando");
	}

	@BeforeClass
	public static void testandoBeforeClass() {
		System.out.println("before class");
	}

	@AfterClass
	public static void testandoAfterClass() {
		System.out.println("after class");
	}

	@Before
	public void setUp() {

		this.leiloeiro = new Avaliador();
		this.joao = new Usuario("João");
		this.jose = new Usuario("José");
		this.maria = new Usuario("Maria");
		this.joaoAlfredo = new Usuario("João");
		this.rosa = new Usuario("Rosa");
		this.leilaoValoresCrescentes = new LeilaoBuilder().para("Playstation 4").propoe(new Lance(joao, 250))
				.propoe(new Lance(jose, 300)).propoe(new Lance(maria, 400)).build();
		this.leilaoValoresDecrescentes = new LeilaoBuilder().para("Playstation 4").propoe(new Lance(joao, 400))
				.propoe(new Lance(jose, 300)).propoe(new Lance(maria, 250)).build();

		this.usuarios.add(joao);
		this.usuarios.add(jose);
		this.usuarios.add(maria);
	}

	@Test(expected = RuntimeException.class)
	public void naoDeveAvaliarLeiloesSemNenhumLanceDado() {
		Leilao leilao = new LeilaoBuilder().para("Playstation 3 Novo").build();

		leiloeiro.avalia(leilao);
	}

	@Test
	public void avaliadorAvaliaCorretamenteEmOrdemCrescente() {

		leiloeiro.avalia(leilaoValoresCrescentes);
		assertThat(leiloeiro.getMenorLance(), equalTo(250.0));
		assertThat(leiloeiro.getMaiorLance(), equalTo(400.0));

	}

	@Test
	public void AvalieLeilaoComApenasUmLance() {

		Leilao leilao = new LeilaoBuilder().para("Playstation 4").propoe(new Lance(joao, 200)).build();

		leilao.propoe(new Lance(joao, 200.0));
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
		assertThat(maiores, hasItems(
                new Lance(maria, 400), 
                new Lance(joao, 300),
                new Lance(maria, 200)
        ));
	}

	@Test
	public void avaliaOrdemAleatoriaDeValores() {

		Leilao leilao = new LeilaoBuilder().para("Playstation 4").propoe(new Lance(joao, 500.0))
				.propoe(new Lance(maria, 200.0)).propoe(new Lance(joao, 800.0)).propoe(new Lance(maria, 400.0))
				.propoe(new Lance(joao, 500.5)).propoe(new Lance(maria, 200.5)).propoe(new Lance(joao, 800.5)).build();

		leilao.propoe(new Lance(maria, 400.5));

		Avaliador leiloeiro = new Avaliador();
		leiloeiro.avalia(leilao);

		assertEquals(800.5, leiloeiro.getMaiorLance(), 0.00001);
		assertEquals(200, leiloeiro.getMenorLance(), 0.00001);

	}

	@Test
	public void avaliaOrdemDecrescente() {

		leiloeiro.avalia(leilaoValoresDecrescentes);

		assertEquals(400, leiloeiro.getMaiorLance(), 0.00001);
		assertEquals(250, leiloeiro.getMenorLance(), 0.00001);

	}

	@Test
	public void dobraUltimoLance() {

		Leilao leilao = new LeilaoBuilder().para("Playstation 4").propoe(new Lance(joao, 500.0))
				.propoe(new Lance(maria, 200.0)).propoe(new Lance(joao, 800.0)).propoe(new Lance(maria, 400.0))
				.propoe(new Lance(joao, 500.5)).propoe(new Lance(maria, 200.5)).propoe(new Lance(joao, 800.5))
				.propoe(new Lance(joao, 500.0)).propoe(new Lance(maria, 400.0)).propoe(new Lance(joaoAlfredo, 300.0))
				.propoe(new Lance(rosa, 200.0)).propoe(new Lance(joao, 100.5)).propoe(new Lance(maria, 20.5))
				.propoe(new Lance(joaoAlfredo, 8.5)).propoe(new Lance(rosa, 4.5)).build();

		leilao.dobraLance(joaoAlfredo);

		List<Lance> lancesUsuario = new ArrayList<>();

		for (Lance lance : leilao.getLances()) {

			if (lance.getUsuario().equals(joaoAlfredo)) {
				lancesUsuario.add(lance);
			}
		}

		int ultimoLance = lancesUsuario.size() - 1;
		Lance ultimo = lancesUsuario.get(ultimoLance);

		assertEquals(17, ultimo.getValor(), 0.00001);
		assertEquals(joaoAlfredo, ultimo.getUsuario());
	}

	@Test
	public void naoDeveDobrarCasoNaoHajaLanceAnterior() {
		Leilao leilao = new Leilao("Macbook Pro 15");
		Usuario steveJobs = new Usuario("Steve Jobs");

		leilao.dobraLance(steveJobs);

		assertEquals(0, leilao.getLances().size());
	}
}

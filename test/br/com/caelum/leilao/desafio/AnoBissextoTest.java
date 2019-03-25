package br.com.caelum.leilao.desafio;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class AnoBissextoTest {

	@Test
	public void temQueSerBissextoSeMultiploDe400() {
		AnoBissexto ano = new AnoBissexto(2400);
		assertTrue(ano.ehBissexto());
	}
	
	@Test
	public void naoPodeSerBissextoSeMultiploDe100() {
		AnoBissexto ano = new AnoBissexto(2300);
		assertTrue(!ano.ehBissexto());
	}

	@Test
	public void temQueSerBissextoQuandoMultiploDe4() {
		
		AnoBissexto ano = new AnoBissexto(2104);
		assertTrue(ano.ehBissexto());
	}
	
}

package br.com.caelum.leilao.servico;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.LeilaoBuilder;
import br.com.caelum.leilao.dominio.Usuario;
import static br.com.caelum.leilao.servico.LeilaoMatcher.temUmLance;

public class LeilaoTest {
	@Test
	public void deveReceberUmLance() {
		Leilao leilao = new LeilaoBuilder().para("Macbook Pro 15").build();
		assertEquals(0, leilao.getLances().size());

		Lance lance = new Lance(new Usuario("Steve Jobs"), 2000);
		leilao.propoe(lance);

		assertThat(leilao.getLances().size(), equalTo(1));
		assertThat(leilao, temUmLance(lance));
	}
}

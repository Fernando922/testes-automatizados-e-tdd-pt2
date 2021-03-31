package br.com.alura.leilao.utils;

import org.junit.Test;

import java.math.BigDecimal;

import static br.com.alura.leilao.utils.MoedaUtil.formataParaBrasileiro;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class MoedaUtilTest {



    @Test
    public void deve_FormatarParaReal_quandoReceberValor(){
        String valorFormatadoParaReal = formataParaBrasileiro(new BigDecimal(20));

        assertThat(valorFormatadoParaReal, is(equalTo("R$  20,00")));
    }
}
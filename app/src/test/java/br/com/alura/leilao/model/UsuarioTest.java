package br.com.alura.leilao.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class UsuarioTest {


    private final Usuario usuario = new Usuario("Fernando");

    @Test
    public void deve_RetornarNome_QuandoSolicitado(){
        String nome = usuario.getNome();
        assertEquals("Fernando", nome);
    }

}
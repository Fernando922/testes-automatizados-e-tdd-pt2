package br.com.alura.leilao;

import org.junit.Test;

import br.com.alura.leilao.model.Usuario;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {

        //igual e diferente
        assertEquals("O resultado da soma não é o mesmo", 4, 2 + 2);
        assertNotEquals(3, 2+2);

        //boolean
        assertTrue(true);
        assertFalse(false);

        //null
        assertNull(null);
        assertNotNull(new Usuario("Fernando"));


        assertThat(2+2, equalTo(4));




    }
}
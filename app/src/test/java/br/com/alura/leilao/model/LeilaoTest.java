package br.com.alura.leilao.model;

import org.hamcrest.Matchers;
import org.junit.Test;

import java.util.List;

import br.com.alura.leilao.exception.LanceMenorQueUltimoLanceException;
import br.com.alura.leilao.exception.LanceSeguidoDoMesmoUsuarioException;
import br.com.alura.leilao.exception.UsuarioJaDeuCincoLancesException;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.CombinableMatcher.both;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class LeilaoTest {
    public static final double DELTA = 0.0001;   //evita o uso de magic numbers
    private final Leilao COMPUTADOR_USADO = new Leilao("Computador usado");  //é utilizado em todos os testes!
    private final Usuario FERNANDO = new Usuario("fernando");  // é usado na maioria dos casos
    private final Usuario ALEX = new Usuario("Alex");

    //só se voce quiser lidar com mensagens em cada exception!
    //@Rule
    //public ExpectedException exception = ExpectedException.none();

    //como nomear  de forma mais descritiva possivel!
    //[deve] [resultado esperado] [estado de teste]

    @Test
    public void deve_DevolvereDescricao_QuandoRecebeDescricao() {


        //executar ação esperada
        String descricaoDevolvida = COMPUTADOR_USADO.getDescricao();

        //testar resultado esperado
        // assertEquals("Computador usado", descricaoDevolvida);
        assertThat(descricaoDevolvida, is(equalTo("Computador usado"))); //forma mais fácil de ler o teste
    }


    @Test
    public void deve_DevolverMaiorLance_QuandoRecebeApenasUmLance() {
        //delta pega a diferença entre os valores com ponto flutuante e se ele for maior,
        // significa que os valores são equivalentes
        COMPUTADOR_USADO.propoe(new Lance(FERNANDO, 200.0));
        double maiorLanceDevolvido = COMPUTADOR_USADO.getMaiorLance();
        // assertEquals(200.0, maiorLanceDevolvido, DELTA);  //valor padrao de delta
        assertThat(maiorLanceDevolvido, closeTo(200.0, DELTA));
    }

    @Test
    public void deve_DevolverMaiorLance_QuandoRecebeMaisDeUmLanceEmOrdemCrescente() {
        COMPUTADOR_USADO.propoe(new Lance(FERNANDO, 100));
        COMPUTADOR_USADO.propoe(new Lance(ALEX, 200));
        double maiorLanceDevolvido = COMPUTADOR_USADO.getMaiorLance();
        assertEquals(200.0, maiorLanceDevolvido, DELTA);
    }


    @Test
    public void deve_DevolverMenorLance_QuandoRecebeApenasUmLance() {
        //delta pega a diferença entre os valores com ponto flutuante e se ele for maior,
        // significa que os valores são equivalentes
        COMPUTADOR_USADO.propoe(new Lance(FERNANDO, 200.0));
        double menorLanceDevolvido = COMPUTADOR_USADO.getMenorLance();
        assertEquals(200.0, menorLanceDevolvido, DELTA);  //valor padrao de delta
    }


    @Test
    public void deve_DevolverMenorLance_QuandoRecebeMaisDeUmLanceEmOrdemCrescente() {
        COMPUTADOR_USADO.propoe(new Lance(FERNANDO, 100));
        COMPUTADOR_USADO.propoe(new Lance(ALEX, 200));
        double menorLanceDevolvido = COMPUTADOR_USADO.getMenorLance();
        assertEquals(100.0, menorLanceDevolvido, DELTA);
    }

    @Test
    public void deve_DevolverTresMaioresLances_QuandoRecebeExatosTresLances() {
        COMPUTADOR_USADO.propoe(new Lance(FERNANDO, 100));
        COMPUTADOR_USADO.propoe(new Lance(ALEX, 400));  //mesmo nome, instancias diferentes!
        COMPUTADOR_USADO.propoe(new Lance(FERNANDO, 500));

        List<Lance> tresMaioresLancesDevolvidos = COMPUTADOR_USADO.tresMaioresLances();


        //  assertEquals(3, tresMaioresLancesDevolvidos.size());
        //  assertThat(tresMaioresLancesDevolvidos, hasSize(3));


        //assertEquals(500, tresMaioresLancesDevolvidos.get(0).getValor(), DELTA);
        // assertThat(tresMaioresLancesDevolvidos, hasItem(new Lance(FERNANDO, 500)));

        // assertEquals(400, tresMaioresLancesDevolvidos.get(1).getValor(), DELTA);
        //assertEquals(100, tresMaioresLancesDevolvidos.get(2).getValor(), DELTA);


        //a ordem é validada também, da posição 0 a 3 do array
//        assertThat(tresMaioresLancesDevolvidos, contains(
//                new Lance(FERNANDO, 500),
//                new Lance(ALEX, 400),
//                new Lance(FERNANDO, 100)
//        ));


        assertThat(tresMaioresLancesDevolvidos,
                both(Matchers.<Lance>hasSize(3))
                        .and(contains(
                                new Lance(FERNANDO, 500),
                                new Lance(ALEX, 400),
                                new Lance(FERNANDO, 100)
                        ))
        );
    }


    @Test
    public void deve_DevolverTresMaioresLances_QuandoNaoRecebeLances() {
        List<Lance> tresMaioresLancesDevolvidos = COMPUTADOR_USADO.tresMaioresLances();
        assertEquals(0, tresMaioresLancesDevolvidos.size());
    }


    @Test
    public void deve_DevolverTresMaioresLances_QuandoRecebeApenasUmLance() {
        COMPUTADOR_USADO.propoe(new Lance(FERNANDO, 100));

        List<Lance> tresMaioresLancesDevolvidos = COMPUTADOR_USADO.tresMaioresLances();


        assertEquals(1, tresMaioresLancesDevolvidos.size());
        assertEquals(100, tresMaioresLancesDevolvidos.get(0).getValor(), DELTA);
    }


    @Test
    public void deve_DevolverTresMaioresLances_QuandoRecebeApenasDoisLances() {
        COMPUTADOR_USADO.propoe(new Lance(FERNANDO, 100));
        COMPUTADOR_USADO.propoe(new Lance(new Usuario("Pedro"), 500));

        List<Lance> tresMaioresLancesDevolvidos = COMPUTADOR_USADO.tresMaioresLances();


        assertEquals(2, tresMaioresLancesDevolvidos.size());
        assertEquals(500, tresMaioresLancesDevolvidos.get(0).getValor(), DELTA);
        assertEquals(100, tresMaioresLancesDevolvidos.get(1).getValor(), DELTA);
    }


    @Test
    public void deve_DevolverTresMaioresLances_QuandoRecebeMaisDeTresLances() {
        COMPUTADOR_USADO.propoe(new Lance(FERNANDO, 100));
        COMPUTADOR_USADO.propoe(new Lance(new Usuario("Joaquim"), 400));
        COMPUTADOR_USADO.propoe(new Lance(new Usuario("Pedro"), 500));
        COMPUTADOR_USADO.propoe(new Lance(FERNANDO, 600));

        List<Lance> tresMaioresLancesDevolvidosParaQuatroLances = COMPUTADOR_USADO.tresMaioresLances();


        assertEquals(3, tresMaioresLancesDevolvidosParaQuatroLances.size());
        assertEquals(600, tresMaioresLancesDevolvidosParaQuatroLances.get(0).getValor(), DELTA);
        assertEquals(500, tresMaioresLancesDevolvidosParaQuatroLances.get(1).getValor(), DELTA);
        assertEquals(400, tresMaioresLancesDevolvidosParaQuatroLances.get(2).getValor(), DELTA);

        COMPUTADOR_USADO.propoe(new Lance(new Usuario("Pedro"), 800));
        List<Lance> tresMaioresLancesDevolvidosParaCincoLances = COMPUTADOR_USADO.tresMaioresLances();
        assertEquals(3, tresMaioresLancesDevolvidosParaCincoLances.size());
        assertEquals(800, tresMaioresLancesDevolvidosParaCincoLances.get(0).getValor(), DELTA);
        assertEquals(600, tresMaioresLancesDevolvidosParaCincoLances.get(1).getValor(), DELTA);
        assertEquals(500, tresMaioresLancesDevolvidosParaCincoLances.get(2).getValor(), DELTA);
    }

    @Test
    public void deve_DevolverValorZeroParaMaiorLance_QuandoNaoTiverLances() {
        double maiorLanceDevolvido = COMPUTADOR_USADO.getMaiorLance();

        assertEquals(0, maiorLanceDevolvido, DELTA);
    }


    @Test
    public void deve_DevolverValorZeroParaMenorLance_QuandoNaoTiverLances() {
        double menorLanceDevolvido = COMPUTADOR_USADO.getMenorLance();
        assertEquals(0, menorLanceDevolvido, DELTA);
    }

    @Test(expected = LanceMenorQueUltimoLanceException.class)
    public void naoDeve_AdicionarLance_QuandoForMenorQueOMaiorLance() {
        COMPUTADOR_USADO.propoe(new Lance(FERNANDO, 500));
        COMPUTADOR_USADO.propoe(new Lance(new Usuario("Rogério"), 400));
    }

    @Test(expected = LanceSeguidoDoMesmoUsuarioException.class)
    public void naoDeve_AdicionarLance_QuandoForOMesmoUsuarioDoUltimoLance() {  //ou deve_LancarException_QuandoForOMesmoUsuarioDoUltimoLance
        // exception.expect(LanceSeguidoDoMesmoUsuarioException.class);
        COMPUTADOR_USADO.propoe(new Lance(FERNANDO, 500));
        COMPUTADOR_USADO.propoe(new Lance(FERNANDO, 600));
    }

    @Test(expected = UsuarioJaDeuCincoLancesException.class)
    public void naoDeve_AdicionarLance_QuandoUsuarioDerCincoLances() {
        final Usuario FRAN = new Usuario("Fran");

        COMPUTADOR_USADO.propoe(new Lance(ALEX, 100.0));
        COMPUTADOR_USADO.propoe(new Lance(FRAN, 200.0));
        COMPUTADOR_USADO.propoe(new Lance(ALEX, 300.0));
        COMPUTADOR_USADO.propoe(new Lance(FRAN, 400.0));
        COMPUTADOR_USADO.propoe(new Lance(ALEX, 500.0));
        COMPUTADOR_USADO.propoe(new Lance(FRAN, 600.0));
        COMPUTADOR_USADO.propoe(new Lance(ALEX, 700.0));
        COMPUTADOR_USADO.propoe(new Lance(FRAN, 800.0));
        COMPUTADOR_USADO.propoe(new Lance(ALEX, 900.0));
        COMPUTADOR_USADO.propoe(new Lance(FRAN, 1000.0));
        COMPUTADOR_USADO.propoe(new Lance(ALEX, 1100.0));
    }


}
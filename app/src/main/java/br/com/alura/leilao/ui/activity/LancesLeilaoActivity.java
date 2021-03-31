package br.com.alura.leilao.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.Serializable;
import java.math.BigDecimal;

import br.com.alura.leilao.R;
import br.com.alura.leilao.model.Lance;
import br.com.alura.leilao.model.Leilao;
import br.com.alura.leilao.model.Usuario;

import static br.com.alura.leilao.utils.MoedaUtil.formataParaBrasileiro;

public class LancesLeilaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lances_leilao);
        Intent dadosRecebidos = getIntent();
        if(dadosRecebidos.hasExtra("leilao")){
            Leilao leilao = (Leilao) dadosRecebidos.getSerializableExtra("leilao");

            TextView descricao = findViewById(R.id.lances_leilao_descricao);
            descricao.setText(leilao.getDescricao());

            TextView maiorLance = findViewById(R.id.lances_leilao_maior_lance);
            String maiorLanceFormatado = formataParaBrasileiro(new BigDecimal(leilao.getMaiorLance()));
            maiorLance.setText(maiorLanceFormatado);

            TextView menorLance = findViewById(R.id.lances_leilao_menor_lance);
            String menorLanceFormatado = formataParaBrasileiro(new BigDecimal(leilao.getMenorLance()));
            menorLance.setText(menorLanceFormatado);

            TextView maioresLances = findViewById(R.id.lances_leilao_maiores_lances);

            StringBuilder sb = new StringBuilder();
            for (Lance lance : leilao.tresMaioresLances()){
                Usuario usuario = lance.getUsuario();
                String valorFormatado = formataParaBrasileiro(new BigDecimal(lance.getValor()));
                sb.append(valorFormatado + " - " + usuario.getNome() + "\n");
            }
            String maioresLancesEmTexto = sb.toString();
            maioresLances.setText(maioresLancesEmTexto);
        }
    }
}

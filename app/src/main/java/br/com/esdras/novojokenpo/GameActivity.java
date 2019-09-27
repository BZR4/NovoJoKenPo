package br.com.esdras.novojokenpo;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    ImageView papel, tesoura, pedra, jogador, computador;
    static int jogadaDoComputador = 0;
    static String nome = "";
    static int ganhou = 0, empatou = 0, perdeu = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Obter referencias dos WidGets
        papel = (ImageView) findViewById(R.id.imageViewPapel);
        tesoura = (ImageView) findViewById(R.id.imageViewTesoura);
        pedra = (ImageView) findViewById(R.id.imageViewPedra);
        jogador = (ImageView) findViewById(R.id.imageViewJogador);
        computador = (ImageView) findViewById(R.id.imageViewComputador);

        //Receber dados da Intent que invocou esta atividade
        nome = getIntent().getStringExtra("nome");

    }

    public void escolhaDoComputador() {
        Random random = new Random();
        jogadaDoComputador = random.nextInt(3) + 1;

        switch (jogadaDoComputador) {
            case 1:
                computador.setImageResource(R.drawable.papel);
                break;
            case 2:
                computador.setImageResource(R.drawable.tesoura);
                break;
            case 3:
                computador.setImageResource(R.drawable.pedra);
                break;
            default:
        }
    }


    public void escolhaDoJogador(View view) {
        int opcao = view.getId();
        escolhaDoComputador();
        switch (opcao) {
            case R.id.imageViewPapel:
                jogador.setImageResource(R.drawable.papel);
                if (jogadaDoComputador == 3) {
                    ganhou++;
                    alertaVencedor("Papel embrulha a pedra!");
                } else if (jogadaDoComputador == 2) {
                    perdeu++;
                    mensagem("Perdeu");
                } else {
                    empatou++;
                    mensagem("Empatou");
                }
                break;
            case R.id.imageViewTesoura:
                jogador.setImageResource(R.drawable.tesoura);
                if (jogadaDoComputador == 1) {
                    ganhou++;
                    alertaVencedor("Tesoura corta o papel!");
                } else if (jogadaDoComputador == 3) {
                    ganhou++;
                    mensagem("Perdeu");
                } else {
                    empatou++;
                    mensagem("Empatou");
                }
                break;
            case R.id.imageViewPedra:
                jogador.setImageResource(R.drawable.pedra);
                if (jogadaDoComputador == 2) {
                    ganhou++;
                    alertaVencedor("Pedra quebra a tesoura!");
                } else if (jogadaDoComputador == 1) {
                    perdeu++;
                    mensagem("Perdeu");
                } else {
                    empatou++;
                    mensagem("Empatou");
                }
                break;
            default:
                break;
        }
    }

    private void mensagem(String mensagem) {
        Toast.makeText(getApplicationContext(),mensagem,Toast.LENGTH_SHORT).show();
    }


    public void alertaVencedor(String frase) {
        if (nome.isEmpty()) {
            nome = "Jogador anônimo";
        }

        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Você ganhou!");
        alert.setMessage("Parabéns " + nome + "!\n" + frase);
        alert.setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                computador.setImageResource(R.drawable.question);
                jogador.setImageResource(R.drawable.escolha);
            }
        });
        alert.setNegativeButton("Parar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                jogoEncerrado();
                Log.d("Jogo:", "Encerrado");
            }
        });
        alert.show();
    }

    public void jogoEncerrado() {

        int total = empatou + ganhou + perdeu;
        float aproveitamento = ganhou * 100 / total;

        AlertDialog.Builder alert = new AlertDialog.Builder(this)
                .setTitle("Desempenho de " + nome)
                .setMessage("Ganhou " + ganhou + " vezes!\nEmpatou " + empatou
                        + " vezes!\nPerdeu  " + perdeu + " vezes!\n" + aproveitamento + "% de aproveitamento!")
                .setPositiveButton("Fim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        empatou = 0;
                        perdeu = 0;
                        ganhou = 0;
                        finish();
                    }
                });
        alert.create();
        alert.show();
    }


}

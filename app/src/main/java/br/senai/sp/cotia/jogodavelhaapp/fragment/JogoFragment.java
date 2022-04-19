package br.senai.sp.cotia.jogodavelhaapp.fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Random;

import br.senai.sp.cotia.jogodavelhaapp.R;
import br.senai.sp.cotia.jogodavelhaapp.databinding.FragmentJogoBinding;
import br.senai.sp.cotia.jogodavelhaapp.util.PrefsUtil;


public class JogoFragment extends Fragment {

    private FragmentJogoBinding binding;
    //vetor de botao para referenciar os botoes
    private Button[] botoes;
    private String[][] tabuleiro;
    // Variáveis para os símbolos
    private String simbJog1, simbJog2, simbolo, nomeJog1, nomeJog2;
    //Variável Random para sortea os símbolos
    private Random random;
    // Variável para controlar o número de jogadas
    private int numJogadas = 0;
    //Variaveis aa p placar
    private int placarJog1 = 0, placarJog2 = 0;
    private int numVelhas = 0;
    public AlertDialog alertDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //habilitar o menu
        setHasOptionsMenu(true);
        // instancia o binding
        binding = FragmentJogoBinding.inflate(inflater, container, false);

        // instanciar o vetor
        botoes = new Button[9];

        //associar o vetor aos botoes
        botoes[0] = binding.bt00;
        botoes[1] = binding.bt01;
        botoes[2] = binding.bt02;
        botoes[3] = binding.bt10;
        botoes[4] = binding.bt11;
        botoes[5] = binding.bt12;
        botoes[6] = binding.bt20;
        botoes[7] = binding.bt21;
        botoes[8] = binding.bt22;

        //Associar o listener aos botões
        for (Button bt : botoes) {
            bt.setOnClickListener(listenerBotoes);
        }

        // Instancia o tabuleiro
        tabuleiro = new String[3][3];

        // Preenche a Matriz com ""
        for (String[] vetor : tabuleiro) {
            Arrays.fill(vetor, "");
        }

        // Define os símbolos do jogador1 e do jogador2
        simbJog1 = PrefsUtil.getSimboloJog1(getContext());
        simbJog2 = PrefsUtil.getSimboloJog2(getContext());
        // Define os nomes do jogador1 e do jogador2
        nomeJog1 = PrefsUtil.getNomeJog1(getContext());
        nomeJog2 = PrefsUtil.getNomeJog2(getContext());


        //atualizar o placar com os simbolos
        binding.jogador1.setText(getResources().getString(R.string.nome1, simbJog1, nomeJog1));
        binding.jogador2.setText(getResources().getString(R.string.nome2, simbJog2, nomeJog2));




        //Instancia o random
        random = new Random();

        // Sorteia quem inicia o jogo
        sorteia();

        atualizaVez();

        // fazendo um alertDialog

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Deseja resetar ?");
        builder.setTitle("Aviso !");
        builder.setCancelable(false);
        builder.setNegativeButton("Não", null);
        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                placarJog1 = 0;
                placarJog2 = 0;
                numVelhas =0;
                contaVelha();
                atualizarPlacr();
                resetar();
            }
        });
        alertDialog = builder.create();

        //retorna a view root do binding
        return binding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        //pega referencia para Activy
        AppCompatActivity minhaActivity = (AppCompatActivity) getActivity();
        minhaActivity.getSupportActionBar().show();

        minhaActivity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
    }

    private void sorteia() {
        // Se gerar um valor verdadeiro jogador 1 começa,
        // Caso contrário jogador 2 começa
        if (random.nextBoolean()) {
            simbolo = simbJog1;
        } else {
            simbolo = simbJog2;
        }
    }

    private void atualizarPlacr() {
        binding.placar1.setText(placarJog1 + "");
        binding.placar2.setText(placarJog2 + "");

    }

    private void contaVelha(){
        binding.numeroVelhas.setText(numVelhas + "");
    }


    private void atualizaVez() {
        if (simbolo.equals(simbJog1)) {
            binding.linear1.setBackgroundColor(getResources().getColor(R.color.purple_200));
            binding.linear2.setBackgroundColor(getResources().getColor(R.color.rosa));
        } else {
            binding.linear2.setBackgroundColor(getResources().getColor(R.color.purple_200));
            binding.linear1.setBackgroundColor(getResources().getColor(R.color.rosa));
        }
    }

    private boolean venceu() {

        // Verificar se venceu nas linhas
        for (int li = 0; li < 3; li++) {
            if (tabuleiro[li][0].equals(simbolo) && tabuleiro[li][1].equals(simbolo) && tabuleiro[li][2].equals(simbolo)) {
                return true;
            }
        }
        for (int col = 0; col < 3; col++) {
            if (tabuleiro[0][col].equals(simbolo) && tabuleiro[1][col].equals(simbolo) && tabuleiro[2][col].equals(simbolo)) {
                return true;
            }
        }
        // Verifica se venceu nas diagonais
        if (tabuleiro[0][0].equals(simbolo) && tabuleiro[1][1].equals(simbolo) && tabuleiro[2][2].equals(simbolo)) {
            return true;
        }
        if (tabuleiro[0][2].equals(simbolo) && tabuleiro[1][1].equals(simbolo) && tabuleiro[2][0].equals(simbolo)) {
            return true;
        }

        return false;

    }

    public void resetar() {
        //Percorrer os botões do vetor, voltando o background inicial,
        // Tornando-os clicáveis novamente e limpando os textos
        for (Button botao : botoes) {
            botao.setClickable(true);
            botao.setText("");
            botao.setBackgroundColor(getResources().getColor(R.color.rosa));
        }
        // Limpar a matriz
        for (String[] vetor : tabuleiro) {
            Arrays.fill(vetor, "");
        }
        // Zerar o número de jogada
        numJogadas = 0;

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //verificar qual item do menu foi seleionado
        switch (item.getItemId()) {
            //caso seja a opção de resetar
            case R.id.menu_resetar:
               alertDialog.show();

                break;
        //caso seja a opção de preferências
            case R.id.menu_prefs:
                NavHostFragment.findNavController(JogoFragment.this).navigate(R.id.action_jogoFragment_to_prefFragment);
                break;
            case R.id.menu_inicio:
                NavHostFragment.findNavController(JogoFragment.this).navigate(R.id.action_jogoFragment_to_inicioFragment);
                break;
        }
        return true;
    }

    //listener para os botoes
    private View.OnClickListener listenerBotoes = btPress -> {
        numJogadas++;
        Log.w("BOTAO", getContext().getResources().getResourceName(btPress.getId()));
        //obtem o nome do botao
        String nomeBotao = getContext().getResources().getResourceName(btPress.getId());
        //extrai a posição atraves do nome do botao
        String posicao = nomeBotao.substring(nomeBotao.length() - 2);
        //extrai linha e coluna da string posição
        int linha = Character.getNumericValue(posicao.charAt(0));
        int coluna = Character.getNumericValue(posicao.charAt(1));

        // Preencher a posição da matriz com o símbolo "da vez"
        tabuleiro[linha][coluna] = simbolo;
        Button botao = (Button) btPress;
        // "Seta" o símbolo no botão pressionado
        botao.setText(simbolo);
        // Trocar o background do botão pra magenta
        botao.setBackgroundColor(Color.LTGRAY);
        // Desabilitar o botão que foi pressionado
        botao.setClickable(false);
        // Verifica se venceu
        if (numJogadas >= 5 && venceu()) {

            //Informa que houve um vencedor
            Toast.makeText(getContext(), R.string.venceu, Toast.LENGTH_LONG).show();

            //verifica quem venceu
            if (simbolo.equals(simbJog1)) {
                placarJog1++;

            } else {
                placarJog2++;
            }

            //atualiza o placar
            atualizarPlacr();

            // Reseta
            resetar();
        } else if (numJogadas == 9) {

            //informa que deu velha
            Toast.makeText(getContext(), R.string.deuvelha, Toast.LENGTH_LONG).show();
                numVelhas++;
                contaVelha();

            //Reseta
            resetar();
        } else {
            // Inverte o símbolo
            simbolo = simbolo.equals(simbJog1) ? simbJog2 : simbJog1;
            // Inverte o símbolo
    /*if(simbolo.equals(simbJog1)){
        simbolo = simbJog2;
    }else{
        simbolo = simbJog1;
    }*/
            atualizaVez();
        }
    };
};

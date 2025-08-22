package org.example;

import Controller.PokemonController;
import Controller.TreinadorController;
import Model.Pokemon;
import Services.JsonReader;
import View.Pokemon.CadastrarPoke;
import View.Pokemon.ListarPokesPanel;
import View.Treinador.CadastrarTreinador;
import View.Treinador.ListarTreinadoresPanel;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

public class Main extends JFrame {
    private JDesktopPane desktopPane;
    private PokemonController pokemonController;
    private TreinadorController treinadorController;

    public Main() {
        super("Centro Pokémon");
        this.pokemonController = new PokemonController();
        this.treinadorController = new TreinadorController();

        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        desktopPane = new JDesktopPane();
        setContentPane(desktopPane);

        createMenuBar();
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Menu de Pokémons
        JMenu menuPokemons = new JMenu("Pokémons");
        JMenuItem itemCadastrarPokemon = new JMenuItem("Cadastrar Pokémon");
        JMenuItem itemListarPokemons = new JMenuItem("Listar Pokémons");
        JMenuItem itemInserirListaPokemons = new JMenuItem("Inserir Lista de Pokémons");

        itemCadastrarPokemon.addActionListener(e -> {
            try {
                openPokemonForm(null);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        itemListarPokemons.addActionListener(e -> openListaPokemonsPanel());
        itemInserirListaPokemons.addActionListener(e-> {
            try {
                cadastrarLoteDePokes();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        menuPokemons.add(itemCadastrarPokemon);
        menuPokemons.add(itemListarPokemons);
        menuPokemons.add(itemInserirListaPokemons);

        menuBar.add(menuPokemons);

        // Menu de Treinadores
        JMenu menuTreinadores = new JMenu("Treinadores");
        JMenuItem itemCadastrarTreinador = new JMenuItem("Cadastrar Treinador");
        JMenuItem itemListarTreinadores = new JMenuItem("Listar Treinador");

        itemCadastrarTreinador.addActionListener(e->{
            try {
                openTreinadorForm(null);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        itemListarTreinadores.addActionListener(e -> openListaTreinadoresPanel());

        menuTreinadores.add(itemCadastrarTreinador);
        menuTreinadores.add(itemListarTreinadores);

        menuBar.add(menuTreinadores);

        // Menu Sair
        JMenu menuSair = new JMenu("Sair");
        JMenuItem itemSair = new JMenuItem("Sair do Sistema");
        itemSair.addActionListener(e -> System.exit(0));

        menuSair.add(itemSair);
        menuBar.add(menuSair);

        setJMenuBar(menuBar);
    }

    private void openPokemonForm(Integer idPokemon) throws Exception {
        CadastrarPoke cadastroPoke = new CadastrarPoke(pokemonController, idPokemon);
        desktopPane.add(cadastroPoke);
        cadastroPoke.setVisible(true);
        cadastroPoke.toFront();
    }

    private void openListaPokemonsPanel() {
        ListarPokesPanel listaPokemons = new ListarPokesPanel(pokemonController);
        desktopPane.add(listaPokemons);
        listaPokemons.setVisible(true);
        listaPokemons.toFront();
    }

    private void cadastrarLoteDePokes() throws IOException {
        JsonReader reader = new JsonReader();
        try{
            List<Pokemon> pokemons = reader.lerPokemonsDoJson();
            pokemonController.cadastrarEmLote(pokemons);
        } catch (Exception e){
            System.out.println("Erro ao ler o Json: "+e.getMessage());
        }
    }

    private void openTreinadorForm(Integer idTreinador) throws Exception {
        CadastrarTreinador cadastroTreinador = new CadastrarTreinador(treinadorController, idTreinador);
        desktopPane.add(cadastroTreinador);
        cadastroTreinador.setVisible(true);
        cadastroTreinador.toFront();
    }

    private void openListaTreinadoresPanel() {
        ListarTreinadoresPanel listaTreinadores = new ListarTreinadoresPanel(treinadorController);
        desktopPane.add(listaTreinadores);
        listaTreinadores.setVisible(true);
        listaTreinadores.toFront();
    }

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(() -> {
            new Main().setVisible(true);
        });
    }

}
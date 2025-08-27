import Controller.PokemonController;
import Controller.TreinadorController;
import Model.DAO.TreinadorDAO;
import View.CadastroForm.PokemonForm;
import View.CadastroForm.TreinadorForm;
import View.CadastroPanel.PokemonPanel;
import View.CadastroPanel.TreinadorPanel;

import javax.swing.*;

public class MainFrame extends JFrame {

    private JDesktopPane desktopPane;
    private PokemonController pokemonController;
    private TreinadorController treinadorController;

    public MainFrame() {
        super("Pokecenter");
        this.pokemonController = new PokemonController();
        this.treinadorController = new TreinadorController(new TreinadorDAO());

        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        desktopPane = new JDesktopPane();
        setContentPane(desktopPane);

        createMenuBar();
    }

    private void createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // Menu Pokémons
        JMenu menuPokemons = new JMenu("Pokémons");
        JMenuItem itemCadastrarPokemon = new JMenuItem("Cadastrar Pokémon");
        JMenuItem itemListarPokemons = new JMenuItem("Listar Pokémons");
        JMenuItem cadastrarListaDePokemons = new JMenuItem("Cadastar Lista de Pokémons");

        JMenu menuTreinador = new JMenu("Treinadores");
        JMenuItem itemCadastrarTreinador = new JMenuItem("Cadastrar Treinador");
        JMenuItem itemListarTreinadores = new JMenuItem("Listar Treinadores");

        itemCadastrarPokemon.addActionListener(e -> openPokemonForm(null));
        itemListarPokemons.addActionListener(e -> openListaPokemonsPanel());
        cadastrarListaDePokemons.addActionListener(e-> {
            try {
                openCadastrarCargaMassiva();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        menuPokemons.add(itemCadastrarPokemon);
        menuPokemons.add(itemListarPokemons);
        menuPokemons.add(cadastrarListaDePokemons);

        itemCadastrarTreinador.addActionListener(e-> openTreinadorForm(null));
        itemListarTreinadores.addActionListener(e-> openListaTreinadoresPanel());

        menuTreinador.add(itemCadastrarTreinador);
        menuTreinador.add(itemListarTreinadores);

        menuBar.add(menuPokemons);
        menuBar.add(menuTreinador);

        // Menu Sair
        JMenu menuSair = new JMenu("Sair");
        JMenuItem itemSair = new JMenuItem("Sair do Sistema");
        itemSair.addActionListener(e -> System.exit(0));

        menuSair.add(itemSair);
        menuBar.add(menuSair);

        setJMenuBar(menuBar);
    }

    private void openPokemonForm(Integer idPokemon) {
        PokemonForm pokemonForm = new PokemonForm(pokemonController,treinadorController, idPokemon);
        desktopPane.add(pokemonForm);
        pokemonForm.setVisible(true);
        pokemonForm.toFront();
    }

    private void openListaPokemonsPanel() {
        PokemonPanel listaPokemons = new PokemonPanel(pokemonController, treinadorController);
        desktopPane.add(listaPokemons);
        listaPokemons.setVisible(true);
        listaPokemons.toFront();
    }

    public void openCadastrarCargaMassiva() throws Exception {
        PokemonController pokemonController = new PokemonController();
        pokemonController.cadastrarCargaMassiva();
        PokemonPanel listaPokemons = new PokemonPanel(pokemonController, treinadorController);
        desktopPane.add(listaPokemons);
        listaPokemons.setVisible(true);
        listaPokemons.toFront();
    }

    private void openTreinadorForm(Integer idTreinador) {
        TreinadorForm treinadorForm = new TreinadorForm(treinadorController, idTreinador);
        desktopPane.add(treinadorForm);
        treinadorForm.setVisible(true);
        treinadorForm.toFront();
    }

    private void openListaTreinadoresPanel() {
        TreinadorPanel listaTreinadores = new TreinadorPanel(treinadorController);
        desktopPane.add(listaTreinadores);
        listaTreinadores.setVisible(true);
        listaTreinadores.toFront();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}
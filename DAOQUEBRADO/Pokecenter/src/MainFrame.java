import Controller.PokemonController;
import Controller.TreinadorController;
import View.CadastroForm.PokemonForm;
import View.CadastroPanel.PokemonPanel;

import javax.swing.*;

public class MainFrame extends JFrame {

    private JDesktopPane desktopPane;
    private PokemonController pokemonController;
    private TreinadorController treinadorController;

    public MainFrame() {
        super("Pokecenter");
        this.pokemonController = new PokemonController();
        this.treinadorController = new TreinadorController();

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
        JMenuItem itemCadastrarTreinador = new JMenuItem("Cadastrar Treinador");
        JMenuItem itemListarPokemons = new JMenuItem("Listar Pokémons");
        JMenuItem itemListarTreinadores = new JMenuItem("Listar Treinadores");

        itemCadastrarPokemon.addActionListener(e -> openPokemonForm(null));
        itemListarPokemons.addActionListener(e -> openListaPokemonsPanel());

        menuPokemons.add(itemCadastrarPokemon);
        menuPokemons.add(itemListarPokemons);

        menuBar.add(menuPokemons);

        // Menu Sair
        JMenu menuSair = new JMenu("Sair");
        JMenuItem itemSair = new JMenuItem("Sair do Sistema");
        itemSair.addActionListener(e -> System.exit(0));

        menuSair.add(itemSair);
        menuBar.add(menuSair);

        setJMenuBar(menuBar);
    }

    private void openPokemonForm(String nomePokemon) {
        PokemonForm pokemonForm = new PokemonForm(pokemonController,treinadorController, nomePokemon);
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


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}
package View.Treinador;

import Controller.TreinadorController;
import Model.Treinador;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ListarTreinadoresPanel extends JInternalFrame{
    private TreinadorController controller;
    private JTable tabelaTreinadores;
    private DefaultTableModel tableModel;
    private JButton btnAtualizar, btnRemover, btnBuscar, btnEditar;
    private JTextField txtBuscaNome;

    public ListarTreinadoresPanel(TreinadorController controller) {
        super("Lista de Treinadores", true, true, true, true);
        this.controller = controller;

        setSize(900, 500);
        setLayout(new BorderLayout());

        String[] colunas = {"ID", "Nome", "Cidade"};
        tableModel = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tabelaTreinadores = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tabelaTreinadores);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panelAcoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        txtBuscaNome = new JTextField(20);
        btnBuscar = new JButton("Buscar por Nome");
        btnAtualizar = new JButton("Atualizar Tabela");
        btnRemover = new JButton("Remover Selecionado");
        btnEditar = new JButton("Editar Selecionado");

        panelAcoes.add(new JLabel("Nome:"));
        panelAcoes.add(txtBuscaNome);
        panelAcoes.add(btnBuscar);
        panelAcoes.add(btnAtualizar);
        panelAcoes.add(btnRemover);
        panelAcoes.add(btnEditar);
        add(panelAcoes, BorderLayout.NORTH);

        btnAtualizar.addActionListener(e -> carregarTreinadoresNaTabela());
        btnRemover.addActionListener(e -> removerTreinadorSelecionado());
        btnBuscar.addActionListener(e -> buscarTreinadoresPorNome());
        btnEditar.addActionListener(e -> editarTreinadorSelecionado());

        carregarTreinadoresNaTabela();
    }

    private void carregarTreinadoresNaTabela() {
        tableModel.setRowCount(0);
        java.util.List<Treinador> treinadores = controller.listarTreinadores();
        for (Treinador treinador : treinadores) {
            tableModel.addRow(new Object[]{
                    treinador.getId(),
                    treinador.getNome(),
                    treinador.getCidade(),
            });
        }
    }

    private void removerTreinadorSelecionado() {
        int selectedRow = tabelaTreinadores.getSelectedRow();
        if (selectedRow >= 0) {
            int idTreinador = (int) tableModel.getValueAt(selectedRow, 0);

            int confirm = JOptionPane.showConfirmDialog(this,
                    "Tem certeza que deseja remover o Treinador ID: " + idTreinador + "?",
                    "Confirmar Remoção", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    controller.removerTreinador(idTreinador);
                    JOptionPane.showMessageDialog(this, "Treinador removido com sucesso!");
                    carregarTreinadoresNaTabela();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Erro ao remover Treinador: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um Treinador para remover.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void buscarTreinadoresPorNome() {
        String nomeBusca = txtBuscaNome.getText().trim();
        tableModel.setRowCount(0);

        Treinador treinador = controller.getTreinadorByName(nomeBusca);

        if (treinador == null && !nomeBusca.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum Treinador encontrado com o nome: '" + nomeBusca + "'", "Busca", JOptionPane.INFORMATION_MESSAGE);
        }

        assert treinador != null;
        tableModel.addRow(new Object[]{
                    treinador.getId(),
                    treinador.getNome(),
                    treinador.getCidade(),
            });
    }

    private void editarTreinadorSelecionado() {
        int selectedRow = tabelaTreinadores.getSelectedRow();
        if (selectedRow >= 0) {
            int idTreinador = (int) tableModel.getValueAt(selectedRow, 0);

            CadastrarTreinador cadastroTreinador = new CadastrarTreinador(controller, idTreinador);
            this.getDesktopPane().add(cadastroTreinador);
            cadastroTreinador.setVisible(true);
            cadastroTreinador.toFront();

            cadastroTreinador.addInternalFrameListener(new javax.swing.event.InternalFrameAdapter() {
                @Override
                public void internalFrameClosed(javax.swing.event.InternalFrameEvent e) {
                    carregarTreinadoresNaTabela();
                }
            });

        } else {
            JOptionPane.showMessageDialog(this, "Selecione um Treinador para editar.", "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }
}

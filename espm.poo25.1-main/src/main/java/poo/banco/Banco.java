package poo.banco;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class Banco {
    private List<Cliente> clientes;
    private List<Conta> contas;

    public Banco() {
        this.clientes = new ArrayList<>();
        this.contas = new ArrayList<>();
    }

    public void cadastrarCliente() {
        String[] opcoes = {"Pessoa Física", "Pessoa Jurídica"};
        String opcao = (String) JOptionPane.showInputDialog(null, "Escolha o tipo de cliente:", "Cadastro de Cliente",
                JOptionPane.PLAIN_MESSAGE, null, opcoes, opcoes[0]);

        if (opcao == null) return;

        String nome = JOptionPane.showInputDialog("Nome:");

        if ("Pessoa Física".equals(opcao)) {
            String cpf = JOptionPane.showInputDialog("CPF:");
            clientes.add(new PessoaFisica(nome, cpf));
        } else {
            String cnpj = JOptionPane.showInputDialog("CNPJ:");
            clientes.add(new PessoaJuridica(nome, cnpj));
        }
        JOptionPane.showMessageDialog(null, "Cliente cadastrado com sucesso!");
    }

    public void cadastrarConta() {
        if (clientes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não há clientes cadastrados!");
            return;
        }

        String[] tiposConta = {"Conta Corrente", "Conta Poupança", "Conta Investimento"};
        String tipoConta = (String) JOptionPane.showInputDialog(null, "Escolha o tipo de conta:", "Cadastro de Conta",
                JOptionPane.PLAIN_MESSAGE, null, tiposConta, tiposConta[0]);

        if (tipoConta == null) return;

        String[] clientesNomes = clientes.stream().map(Cliente::getNome).toArray(String[]::new);
        String clienteEscolhido = (String) JOptionPane.showInputDialog(null, "Escolha o cliente:", "Cadastro de Conta",
                JOptionPane.PLAIN_MESSAGE, null, clientesNomes, clientesNomes[0]);

        if (clienteEscolhido == null) return;

        int clienteIndex = -1;
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getNome().equals(clienteEscolhido)) {
                clienteIndex = i;
                break;
            }
        }

        String numero = JOptionPane.showInputDialog("Número da conta:");
        Conta conta = null;

        if ("Conta Corrente".equals(tipoConta)) {
            String limiteStr = JOptionPane.showInputDialog("Limite (0 para sem limite):");
            double limite = Double.parseDouble(limiteStr);
            conta = limite > 0 ?
                    new ContaCorrente(numero, clientes.get(clienteIndex), limite) :
                    new ContaCorrente(numero, clientes.get(clienteIndex));
        } else if ("Conta Poupança".equals(tipoConta)) {
            conta = new ContaPoupanca(numero, clientes.get(clienteIndex));
        } else if ("Conta Investimento".equals(tipoConta)) {
            conta = new ContaInvestimento(numero, clientes.get(clienteIndex));
        }

        contas.add(conta);
        JOptionPane.showMessageDialog(null, "Conta cadastrada com sucesso!");
    }

    public void realizarOperacao() {
        if (contas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não há contas cadastradas!");
            return;
        }

        String[] operacoes = {"Depósito", "Saque"};
        String operacao = (String) JOptionPane.showInputDialog(null, "Escolha a operação:", "Operações Bancárias",
                JOptionPane.PLAIN_MESSAGE, null, operacoes, operacoes[0]);

        if (operacao == null) return;

        String[] contasDisponiveis = contas.stream().map(Conta::toString).toArray(String[]::new);
        String contaEscolhida = (String) JOptionPane.showInputDialog(null, "Escolha a conta:", "Operações Bancárias",
                JOptionPane.PLAIN_MESSAGE, null, contasDisponiveis, contasDisponiveis[0]);

        if (contaEscolhida == null) return;

        int contaIndex = -1;
        for (int i = 0; i < contas.size(); i++) {
            if (contas.get(i).toString().equals(contaEscolhida)) {
                contaIndex = i;
                break;
            }
        }

        String valorStr = JOptionPane.showInputDialog("Valor:");
        double valor = Double.parseDouble(valorStr);

        if ("Depósito".equals(operacao)) {
            contas.get(contaIndex).depositar(valor);
        } else if ("Saque".equals(operacao)) {
            contas.get(contaIndex).sacar(valor);
        }
    }

    public void listarClientesEContas() {
        StringBuilder lista = new StringBuilder("=== Clientes e Contas ===\n");
        clientes.forEach(cliente -> {
            lista.append("\nCliente: ").append(cliente.getNome())
                    .append("\nIdentificação: ").append(cliente.getIdentificacao())
                    .append("\nContas:\n");
            contas.stream()
                    .filter(conta -> conta.getTitular().equals(cliente))
                    .forEach(conta -> lista.append("  ").append(conta).append("\n"));
        });
        JOptionPane.showMessageDialog(null, lista.toString());
    }

    public void removerCliente() {
        if (clientes.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não há clientes cadastrados!");
            return;
        }

        String[] clientesNomes = clientes.stream().map(Cliente::getNome).toArray(String[]::new);
        String clienteEscolhido = (String) JOptionPane.showInputDialog(null, "Escolha o cliente para remover:", "Remoção de Cliente",
                JOptionPane.PLAIN_MESSAGE, null, clientesNomes, clientesNomes[0]);

        if (clienteEscolhido == null) return;

        int clienteIndex = -1;
        for (int i = 0; i < clientes.size(); i++) {
            if (clientes.get(i).getNome().equals(clienteEscolhido)) {
                clienteIndex = i;
                break;
            }
        }

        Cliente cliente = clientes.get(clienteIndex);
        contas.removeIf(conta -> conta.getTitular().equals(cliente));
        clientes.remove(clienteIndex);
        JOptionPane.showMessageDialog(null, "Cliente e suas contas removidos com sucesso!");
    }

    public void aplicarRendimento() {
        if (contas.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Não há contas cadastradas!");
            return;
        }

        int count = 0;
        StringBuilder detalhes = new StringBuilder("Rendimento aplicado às seguintes contas de investimento:\n");

        for (Conta conta : contas) {
            if (conta instanceof ContaInvestimento) {
                ((ContaInvestimento) conta).aplicarRendimento();
                detalhes.append(conta.toString()).append("\n");
                count++;
            }
        }

        JOptionPane.showMessageDialog(null, 
            count > 0 ? detalhes.toString() : "Não há contas de investimento para aplicar rendimento.");
    }

    public void menu() {
        while (true) {
            String[] opcoes = {
                "Cadastrar Cliente", 
                "Cadastrar Conta", 
                "Realizar Operação", 
                "Aplicar Rendimento",
                "Listar Clientes e Contas", 
                "Remover Cliente", 
                "Sair"
            };
            String opcao = (String) JOptionPane.showInputDialog(null, "Escolha uma opção:", "Sistema Bancário",
                    JOptionPane.PLAIN_MESSAGE, null, opcoes, opcoes[0]);

            if (opcao == null || "Sair".equals(opcao)) {
                JOptionPane.showMessageDialog(null, "Saindo do sistema...");
                return;
            }

            switch (opcao) {
                case "Cadastrar Cliente":
                    cadastrarCliente();
                    break;
                case "Cadastrar Conta":
                    cadastrarConta();
                    break;
                case "Realizar Operação":
                    realizarOperacao();
                    break;
                case "Aplicar Rendimento":
                    aplicarRendimento();
                    break;
                case "Listar Clientes e Contas":
                    listarClientesEContas();
                    break;
                case "Remover Cliente":
                    removerCliente();
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opção inválida!");
            }
        }
    }

    public static void main(String[] args) {
        Banco banco = new Banco();
        banco.menu();
    }
}

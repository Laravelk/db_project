package GUI.mainwindow;

import Data.ClientData;
import GUI.instruments.CategoryFilter;
import Server.DataBaseServer;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Vector;

public class MainWindow extends JFrame {
    private final String ADMIN_LOG = "admin";
    private final String ADMIN_PASSWORD = "admin";

    private final JButton addPeople = new JButton("add people");
    private final JButton addDeclaration = new JButton("add declaration");
    private JButton editTrip = new JButton("Edit Trips");
    private JButton requestSecond = new JButton("Hotel request");
    private JButton requestThree = new JButton("Count request");
    private JButton requestFive = new JButton("Booking request");
    private boolean adminMode = false;
    private boolean isTableUpdating = false;

    private boolean isTableSelection = false;
    private int selectionID = -1;
    private DataBaseServer server;
    private final JTextField loginField = new JTextField("login", 10);
    private JTextField searchField = new JTextField(100);
    private final JPasswordField passwordField = new JPasswordField();
    private JTable table;
    private CategoryFilter categoryFilter = new CategoryFilter();

    private MainController mainController;


    /**
     * @param server
     * */
    public void setDataBaseServer(DataBaseServer server) {
        this.server = server;
    }

    public MainWindow(DataBaseServer server, MainController controller) {
        this.server = server;
        this.mainController = controller;

        addPeople.setMinimumSize(new Dimension(100,20));
        addDeclaration.setMinimumSize(new Dimension(100,20));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(1370, 480));
        setLayout(new GridBagLayout());


        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.gridwidth = 4;
        constraints.gridheight = 1;
        add(searchField, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 5;
        constraints.gridy = 0;
        add(addPeople, constraints);

        constraints = new GridBagConstraints();
        table = createClientsTable();
        JScrollPane scrollTable = new JScrollPane(table);
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 4;
        constraints.gridheight = 7;
        add(scrollTable, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 5;
        constraints.gridy = 1;
        add(addDeclaration, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 5;
        constraints.gridy = 2;
        add(categoryFilter, constraints);

        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.NORTH;
        constraints.gridx = 5;
        constraints.gridy = 3;
        add(editTrip, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 5;
        constraints.gridy = 4;
        add(requestSecond, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 5;
        constraints.gridy = 5;
        add(requestThree, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 5;
        constraints.gridy = 6;
        add(requestFive, constraints);

        //JDialog loginPage = createLoginDialog();
        //loginPage.setVisible(true);
        setVisible(true);
    }

    public void filterTableValue(String name, String lastName, String lastLastName, JTable table) {
        isTableUpdating = true;
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        final int NAME_INDEX = 1;
        final int LAST_NAME_INDEX = 2;
        final int LAST_LAST_NAME_INDEX = 3;

        int numberOfRows = server.getClientsCount(); // count id from CLIENTS
        Vector<Vector<String>> data = getData();

        System.out.println(name);

        if (null == name) {
            updateTable(table);
            return;
        }

        if (null == lastName) {
            for (Vector<String> row : data) {
                boolean isNameCoincides = compareField(NAME_INDEX, name, row);
                if (isNameCoincides) {
                    ((DefaultTableModel) table.getModel()).addRow(row);
                }
            }
            return;
        }

        if (null == lastLastName) {
            for (Vector<String> row : data) {
                boolean isNameCoincides = compareField(NAME_INDEX, name, row);
                boolean isLastNameCoincides = compareField(LAST_NAME_INDEX, lastName, row);
                if (isNameCoincides & isLastNameCoincides) {
                    ((DefaultTableModel) table.getModel()).addRow(row);
                }
            }
            return;
        }

        for (Vector<String> row : data) {
            boolean isNameCoincides = compareField(NAME_INDEX, name, row);
            boolean isLastNameCoincides = compareField(LAST_NAME_INDEX, lastName, row);
            boolean isLLNameCoincides = compareField(LAST_LAST_NAME_INDEX, lastLastName, row);

            if (isNameCoincides & isLastNameCoincides & isLLNameCoincides) {
                ((DefaultTableModel) table.getModel()).addRow(row);
            }
        }
    }

    private boolean compareField(int field_index, String compares, Vector<String> row) {
        int i = 0;
        char [] comparesChar = compares.toCharArray();
        char [] rowElementChar = row.elementAt(field_index).toCharArray();
        for (char c : comparesChar) {
            if (rowElementChar[i] != c) {
                return false;
            }
            i++;
        }
        return true;
    }

    /**
     * @return log_dialog
     * */
    private JDialog createLoginDialog() {
        final JLabel LOGIN = new JLabel("login:");
        final JLabel PASSWORD = new JLabel("password:");
        JButton okButton = new JButton("Ok");

        JDialog loginDialog = new JDialog(this, "Title", true);
        loginDialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        loginDialog.setSize(200,105);
        passwordField.setEchoChar('*');

        Container container = loginDialog.getContentPane();
        JPanel info = new JPanel(new GridLayout(2, 2, 1, 0));

        info.add(LOGIN);
        info.add(loginField);
        info.add(PASSWORD);
        info.add(passwordField);

        // warning window
        JDialog invalidDataWarning = new JDialog();
        invalidDataWarning.setMinimumSize(new Dimension(200,70));
        invalidDataWarning.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        JLabel warning = new JLabel("Invalid login or password");
        Container containerWarning = invalidDataWarning.getContentPane();
        containerWarning.add(warning);
        // end warning window

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if (ADMIN_LOG.equals(loginField.getText()) &&
                        ADMIN_PASSWORD.equals(passwordField.getText())) {
                    adminMode = true;
                    loginDialog.dispose();
                    setVisible(true);
                } else {
                    invalidDataWarning.setVisible(true);
                }
            }
        });

        container.add(info, BorderLayout.NORTH);
        container.add(okButton, BorderLayout.SOUTH);

        return loginDialog;
    }

    /**
     * @return clientTable
     * */
    private JTable createClientsTable() {
        int numberOfRows = server.getClientsCount();
         assert numberOfRows >= 0;
        // Headers from clients
        Vector<String> header = new Vector<>();
        header.add("ID");
        header.add("NAME");
        header.add("LAST_NAME");
        header.add("LAST_LAST_NAME");
        header.add("DOCUMENT_NUMBER");
        header.add("AGE");
        header.add("CHILDREN_ID");
        header.add("TRAVEL_TARGET");

        Vector<Vector<String>> data = getData();
        JTable clientsTable = new JTable();
        TableModel tableModel = new DefaultTableModel(data, header) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return 0 != column;
            }
        };

        tableModel.addTableModelListener(tableModelEvent -> {
            if (!isTableUpdating) {
                int id = Integer.parseInt(String.valueOf(clientsTable.getModel().getValueAt(clientsTable.getEditingRow(), 0)));
                updateClientsData(id, clientsTable.getEditingRow(), clientsTable.getEditingColumn(), clientsTable);
            }
        });

        clientsTable.setModel(tableModel);

        ListSelectionModel listSelectionModel = clientsTable.getSelectionModel();
        listSelectionModel.addListSelectionListener(listSelectionEvent -> {
            isTableSelection = true;
            int selectionRow = listSelectionEvent.getLastIndex(); // потому что он совпадает с выбранной строкой
            selectionID = Integer.parseInt(tableModel.getValueAt(selectionRow, 0).toString());
        });

        // Setting table
        clientsTable.setRowHeight(30);
        clientsTable.setRowHeight(1, 20);
        clientsTable.setIntercellSpacing(new Dimension(10,10));
        clientsTable.setGridColor(Color.blue);
        clientsTable.setShowVerticalLines(true);

        JPanel panel = new JPanel();
        panel.add(new JScrollPane(clientsTable));

        return clientsTable;
    }

    public void updateTable(JTable table) {
        isTableUpdating = true;
        DefaultTableModel model = (DefaultTableModel)table.getModel();
        model.getDataVector().removeAllElements();
        model.fireTableDataChanged();

        int numberOfRows = server.getClientsCount(); // count id from CLIENTS
        Vector<Vector<String>> data = getData();
        for (int i = 0; i < data.size(); i++) {
                model.addRow(data.elementAt(i));
        }
        isTableUpdating = false;
    }

    private Vector<Vector<String>> getData() {
        LinkedList<ClientData> clients;
        boolean isOnlyTravel = getCategoryFilter().getTravelCheck().isSelected();
        boolean isOnlyWork = getCategoryFilter().getWorkCheck().isSelected();
        if (isOnlyTravel) {
            clients = mainController.getModel().getClientDataByCategory("TRAVEL");
        } else if (isOnlyWork) {
            clients = mainController.getModel().getClientDataByCategory("WORK");
        } else {
            clients = mainController.getModel().getClientsData();
        }

        Vector<Vector<String>> data = new Vector<Vector<String>>();
        int countOfClients = clients.size();
        for (int i = 0; i < countOfClients; i++) {
            Vector<String> row = new Vector<String>();
            ClientData clientData = clients.pop();
            row.add(clientData.getId());
            row.add(clientData.getName());
            row.add(clientData.getLastName());
            row.add(clientData.getLastLastName());
            row.add(clientData.getDocumentID());
            row.add(clientData.getAge());
            row.add(clientData.getChildID());
            row.add(clientData.getTravelTarget());
            data.add(row);
        }
        return data;
    }

    private void updateClientsData(int ID, int rowInTable, int column, JTable table) {
        String changeString = String.valueOf(table.getModel().getValueAt(rowInTable, column));
        String columnName = String.valueOf(table.getModel().getColumnName(column));
        server.editClientInDataBase(ID, columnName, changeString);
    }

    public JButton getAddDeclaration() {
        return addDeclaration;
    }

    public JButton getEditTrip() {
        return editTrip;
    }

    public JButton getAddPeople() {
        return addPeople;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public boolean isTableSelection() {
        return isTableSelection;
    }

    public void setTableSelection(boolean t) {
        isTableSelection = t;
    }

    public int getSelectionID() {
        return selectionID;
    }

    public JTable getTable() {
        return table;
    }

    public CategoryFilter getCategoryFilter() {
        return categoryFilter;
    }

    public JButton getRequestSecond() {
        return requestSecond;
    }

    public JButton getRequestThree() { return requestThree; }

    public JButton getRequestFive() {
        return requestFive;
    }
}
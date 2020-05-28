package GUI.addwindow;

import Server.DataBaseServer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddWindow extends JFrame {
    private DataBaseServer server = null;


    /**
     * @param
     *              childrenWindow if we add a child then use true else use false
     *        title is title
     * server is server
     *
     * */
    public AddWindow(DataBaseServer server, String title, boolean childrenWindow) {
        this.server = server;
        setDefaultCloseOperation(HIDE_ON_CLOSE);
        setMinimumSize(new Dimension(400, 500));

        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        int count = 0;
        if (!childrenWindow) {
            count = server.getClientsCount();
        } else {
            count = server.getChildCount();
        }
        JLabel newIdLabel = new JLabel("ID is: " + String.valueOf(count));
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(newIdLabel, constraints);

        JLabel name = new JLabel("Name: ");
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(name, constraints);

        JTextField nameField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 1;
        add(nameField, constraints);

        JLabel lastName = new JLabel("last name: ");
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(lastName, constraints);

        JTextField lastNameField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 2;
        add(lastNameField, constraints);

        JLabel lastLastName = new JLabel("last last name: ");
        constraints.gridx = 0;
        constraints.gridy = 3;
        add(lastLastName, constraints);

        JTextField lastLastNameField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 3;
        add(lastLastNameField, constraints);

        JLabel documentNumber = new JLabel("Document number: ");
        constraints.gridx = 0;
        constraints.gridy = 4;
        add(documentNumber, constraints);

        JTextField documentNumberField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 4;
        add(documentNumberField, constraints);

        JLabel age = new JLabel("Age");
        constraints.gridx = 0;
        constraints.gridy = 5;
        add(age, constraints);

        JTextField ageField = new JTextField(20);
        constraints.gridx = 1;
        constraints.gridy = 5;
        add(ageField, constraints);

        JLabel travelTarget = new JLabel("Travel Target is Work?");
        constraints.gridx = 0;
        constraints.gridy = 6;
        add(travelTarget, constraints);

        JCheckBox travelTargetBox = new JCheckBox("Yes");
        constraints.gridx = 1;
        constraints.gridy = 6;
        add(travelTargetBox, constraints);

        JButton okButton = new JButton("Ok");
        JButton cancelButton = new JButton("Cancel");

        if (!childrenWindow) {
            JButton addChildButton = new JButton("Add child");
            constraints.fill = GridBagConstraints.HORIZONTAL;
            constraints.gridx = 0;
            constraints.gridy = 7;
            add(addChildButton, constraints);

            constraints.fill = GridBagConstraints.NONE;
            constraints.gridx = 0;
            constraints.gridy = 8;
            add(okButton, constraints);

            constraints.gridx = 1;
            constraints.gridy = 8;
            add(cancelButton, constraints);

            addChildButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    AddWindow childAdd = new AddWindow(server, "Child Add", true);
                    childAdd.setVisible(true);
                }
            });
        } else {
            constraints.gridx = 0;
            constraints.gridy = 7;
            add(okButton, constraints);

            constraints.gridx = 1;
            constraints.gridy = 7;
            add(cancelButton, constraints);
        }

        int finalCount = count;
        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String name = nameField.getText();
                String lastName = lastNameField.getText();
                String lastLastName = lastLastNameField.getText();
                String documentNumber = documentNumberField.getText();
                String age = ageField.getText();
                String travelTarget;
                System.out.println(name + " " + lastName + " " + lastLastName + " " + age);
                if (!name.isEmpty() && !lastLastName.isEmpty() && !lastName.isEmpty() &&
                !age.isEmpty()) {
                    if (travelTargetBox.isSelected()) {
                        travelTarget = "WORK";
                    } else {
                        travelTarget = "TRAVEL";
                    }
                    server.addClientInDataBase(name, lastName,
                            lastLastName, documentNumber, age, travelTarget);
                    dispose();
                }
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                dispose();
            }
        });

    }
}

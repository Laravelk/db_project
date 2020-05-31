package GUI.requests.paymentinfo;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.text.ParseException;

public class PaymentView extends JFrame {
    private PaymentController controller;
    private JFormattedTextField dateIn;
    private JFormattedTextField dateOut;

    private final String ar = "Расходы/Доход на самолеты: ";
    private final String ho = "Расходы/Доход на гостиницы: ";
    private final String ex = "Расходы/Доход на экскурсии";
    private final String vi = "Доходы с виз: ";
    private final String pa = "Расходы/Доходы представительство: ";

    private JLabel airplane = new JLabel(ar);
    private JLabel host = new JLabel(ho);
    private JLabel excursion = new JLabel(ex);
    private JLabel visa = new JLabel(vi);
    private JLabel paymentPred = new JLabel(pa);


    PaymentView(PaymentController controller) {
        this.controller = controller;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        setSize(400,200);

        MaskFormatter maskFormatter = null;
        try {
            maskFormatter = new MaskFormatter("##.##.####");
            maskFormatter.setPlaceholderCharacter('_');
        } catch (ParseException e) {
            e.printStackTrace();
        }
        GridBagConstraints constraints = new GridBagConstraints();

        JLabel dateInStr = new JLabel("Date In:");
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(dateInStr, constraints);


        constraints = new GridBagConstraints();
        dateIn = new JFormattedTextField(maskFormatter);
        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.gridwidth = 2;
        add(dateIn, constraints);

        constraints = new GridBagConstraints();
        JLabel dateOutStr = new JLabel("Date Out: ");
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(dateOutStr, constraints);

        constraints = new GridBagConstraints();
        dateOut = new JFormattedTextField(maskFormatter);
        dateOut.setEnabled(false);
        constraints.gridx = 0;
        constraints.gridy = 3;
        constraints.gridwidth = 2;
        add(dateOut, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 4;
        add(airplane, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 5;
        add(host, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 6;
        add(excursion, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 7;
        add(visa, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 8;
        add(paymentPred, constraints);
    }

    public JFormattedTextField getDateIn() {
        return dateIn;
    }

    public JFormattedTextField getDateOut() {
        return dateOut;
    }

    public JLabel getAirplane() {
        return airplane;
    }

    public void setAirplane(JLabel airplane) {
        this.airplane = airplane;
    }

    public JLabel getHost() {
        return host;
    }

    public void setHost(JLabel host) {
        this.host = host;
    }

    public JLabel getExcursion() {
        return excursion;
    }

    public void setExcursion(JLabel excursion) {
        this.excursion = excursion;
    }

    public JLabel getVisa() {
        return visa;
    }

    public void setVisa(JLabel visa) {
        this.visa = visa;
    }

    public JLabel getPaymentPred() {
        return paymentPred;
    }

    public void setPaymentPred(JLabel paymentPred) {
        this.paymentPred = paymentPred;
    }

    public void payEx(int m, int p) {
        excursion.setText(ex + m + "/" + p);
    }

    public void payAirplane(int m, int p) {
        airplane.setText(ar + m + "/" + p);
    }

    public void payHotel(int m, int p) {
        host.setText(ho + m + "/" + p);
    }

    public void payVi(int p) {
        visa.setText(vi + p);
    }

    public void payPred(int m, int p) {
        paymentPred.setText(pa + m + "/" + p);
    }
}

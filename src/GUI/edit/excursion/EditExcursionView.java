package GUI.edit.excursion;

import javax.swing.*;
import java.awt.*;

public class EditExcursionView extends JFrame {
    private EditExcursionController controller;
    EditExcursionView(EditExcursionController controller) {
        this.controller = controller;
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());


    }
}

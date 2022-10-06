import Converters.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Objects;

public class CalcUI extends JFrame implements ActionListener {
    public JPanel rootPanel;
    private JButton calculateButton;
    private JComboBox<String> comboBoxFrom;
    private JComboBox<String> comboBoxTo;
    private JTextField xFieldFrom;
    private JTextField yFieldFrom;
    private JTextField zFieldFrom;
    private JLabel resultLabelZ;
    private JLabel resultLabelX;
    private JLabel resultLabelY;
    private JComboBox<String> comboBoxCoordFrom;
    private JLabel XorBLabelFrom;
    private JLabel YorLLabelFrom;
    private JLabel ZorHLabelFrom;
    private JComboBox<String> comboBoxCoordTo;
    private JLabel XorBLabelTo;
    private JLabel YorLLabelTo;
    private JLabel ZorHLabelTo;
    private double[] coordinates = new double[3];

    public CalcUI() {
        init();

        comboBoxCoordFrom.addActionListener(e -> {
            if (comboBoxCoordFrom.getSelectedItem().toString().equals("X,Y,Z")) {
                XorBLabelFrom.setText("X");
                YorLLabelFrom.setText("Y");
                ZorHLabelFrom.setText("Z");
                return;
            }
            if (comboBoxCoordFrom.getSelectedItem().toString().equals("B,L,H")) {
                XorBLabelFrom.setText("B");
                YorLLabelFrom.setText("L");
                ZorHLabelFrom.setText("H");
            }
        });
        comboBoxCoordTo.addActionListener(e -> {
            if (comboBoxCoordTo.getSelectedItem().toString().equals("X,Y,Z")) {
                XorBLabelTo.setText("X");
                YorLLabelTo.setText("Y");
                ZorHLabelTo.setText("Z");
                return;
            }
            if (comboBoxCoordTo.getSelectedItem().toString().equals("B,L,H")) {
                XorBLabelTo.setText("B");
                YorLLabelTo.setText("L");
                ZorHLabelTo.setText("H");
            }
        });
        ///////////////////////////////////////////////////////////////////////////////
        calculateButton.addActionListener(e -> {
            try {
                double x = Double.parseDouble(xFieldFrom.getText());
                double y = Double.parseDouble(yFieldFrom.getText());
                double z = Double.parseDouble(zFieldFrom.getText());
                // считываем выбранные системы для перевода
                String from = Objects.requireNonNull(comboBoxFrom.getSelectedItem()).toString();
                String to = Objects.requireNonNull(comboBoxTo.getSelectedItem()).toString();

                String fromSC = Objects.requireNonNull(comboBoxCoordFrom.getSelectedItem()).toString();
                String toSC = Objects.requireNonNull(comboBoxCoordTo.getSelectedItem()).toString();

                System.out.println(to);

                if (fromSC.equals("XYZ") && toSC.equals("XYZ")) {
                    coordinates = Converter.convertPSKtoPSK(getTypeConverter(from, to), x, y, z);
                } else if (fromSC.equals("XYZ") && toSC.equals("BLH")) {
                    System.out.println(111111111);
                    TypeConverter typeConverter = getTypeConverter(from, to);
                    System.out.println(typeConverter);

                    double[] coordinate = Converter.convertPSKtoGSK(x, y, z);

                    if(from.equals(to)){
                        coordinates = coordinate;
                    } else {
                        coordinates = Converter.convertGSKtoGSK(typeConverter, coordinate[0], coordinate[1], coordinate[2]);
                    }

                } else if (fromSC.equals("BLH") && toSC.equals("BLH")) {
                    coordinates = Converter.convertGSKtoGSK(getTypeConverter(from, to), x, y, z);
                }

                // обновляем Labels
                if (toSC.equals("BLH")) {
                    // переводим в градусы
                    coordinates[0] = coordinates[0] * 180 / Math.PI;
                    coordinates[1] = coordinates[1] * 180 / Math.PI;
                }
                updateCoordinateLabels(coordinates);

            } catch (Exception exception) {
            }
        });
    }

    private void init() {
        comboBoxFrom.addItem("WGS-84");
        comboBoxFrom.addItem("ПЗ-90.11");
        comboBoxFrom.addItem("СК-42");
        comboBoxFrom.addItem("СК-95");
        comboBoxTo.addItem("ПЗ-90.11");
        comboBoxTo.addItem("WGS-84");
        comboBoxTo.addItem("СК-95");
        comboBoxTo.addItem("СК-42");

        comboBoxCoordFrom.addItem("XYZ");
        comboBoxCoordFrom.addItem("BLH");

        comboBoxCoordTo.addItem("XYZ");
        comboBoxCoordTo.addItem("BLH");
    }

    private static TypeConverter getTypeConverter(String systemA, String systemB) {
        TypeConverter typeConverterSysA = Main.getTranslateTableCoordinateSystem().get(systemA);

        if (systemB.equals("ПЗ-90.11")) {
            return typeConverterSysA;
        } else {
            TypeConverter typeConverterSysB = Main.getTranslateTableCoordinateSystem().get(systemB);
            System.out.println(typeConverterSysB);
            return new TypeConverter(
                    typeConverterSysA.getDeltaX() - typeConverterSysB.getDeltaX(),
                    typeConverterSysA.getDeltaY() - typeConverterSysB.getDeltaY(),
                    typeConverterSysA.getDeltaZ() - typeConverterSysB.getDeltaZ(),
                    typeConverterSysA.getOmegaX() - typeConverterSysB.getOmegaX(),
                    typeConverterSysA.getOmegaY() - typeConverterSysB.getOmegaY(),
                    typeConverterSysA.getOmegaZ() - typeConverterSysB.getOmegaZ(),
                    typeConverterSysA.getM() - typeConverterSysB.getM());
        }
    }


    private void updateCoordinateLabels(double[] coord) {
        resultLabelX.setText(String.valueOf(coord[0]));
        resultLabelY.setText(String.valueOf(coord[1]));
        resultLabelZ.setText(String.valueOf(coord[2]));
        Arrays.fill(coordinates, 0);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // имплементация интерфейса требует реализации метода
    }
}

import Converters.ConverterPSK;
import Converters.TypeConverter;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class CalcUI extends JFrame implements ActionListener{
    private static JPanel rootPanel;
    private static JButton calculateButton;
    private static JComboBox<String> comboBoxFrom;
    private static JComboBox<String> comboBoxTo;
    private static JTextField xFieldFrom;
    private static JTextField yFieldFrom;
    private static JTextField zFieldFrom;
    private static JLabel resultLabelZ;
    private static JLabel resultLabelX;
    private static JLabel resultLabelY;
    private static JComboBox<String> comboBoxCoordFrom;
    private static JLabel XorBLabelFrom;
    private static JLabel YorLLabelFrom;
    private static JLabel ZorHLabelFrom;
    private static JComboBox<String> comboBoxCoordTo;
    private static JLabel XorBLabelTo;
    private static JLabel YorLLabelTo;
    private static JLabel ZorHLabelTo;
    private CoordinateSystemManager coordinateSystemManager;

    public CalcUI() {
        init();

        calculateButton.addActionListener(e -> {
            try {
                double x = Double.parseDouble(xFieldFrom.getText());
                double y = Double.parseDouble(yFieldFrom.getText());
                double z = Double.parseDouble(zFieldFrom.getText());

                // считываем выбранные системы для перевода
                String from = Objects.requireNonNull(comboBoxFrom.getSelectedItem()).toString();
                String to = Objects.requireNonNull(comboBoxTo.getSelectedItem()).toString();
                TypeConverter typeConverter = Main.getTranslateTableCoordinateSystem().get(from);

                String fromSC = Objects.requireNonNull(comboBoxCoordFrom.getSelectedItem()).toString();
                String toSC = Objects.requireNonNull(comboBoxCoordTo.getSelectedItem()).toString();

                if(fromSC.equals("XYZ") && toSC.equals("XYZ")) {
                    if(to.equals("ПЗ-90.11")) {
                        updateCoordinateLabels(ConverterPSK.convertAtoB(typeConverter, x, y, z));
                    } else {
                        TypeConverter typeConverter1 = Main.getTranslateTableCoordinateSystem().get(to);
                        updateCoordinateLabels(ConverterPSK.convertAtoB(new TypeConverter(
                                typeConverter.getDeltaX() - typeConverter1.getDeltaX(),
                                typeConverter.getDeltaY() - typeConverter1.getDeltaY(),
                                typeConverter.getDeltaZ() - typeConverter1.getDeltaZ(),
                                typeConverter.getOmegaX() - typeConverter1.getOmegaX(),
                                typeConverter.getOmegaY() - typeConverter1.getOmegaY(),
                                typeConverter.getOmegaZ() - typeConverter1.getOmegaZ(),
                                typeConverter.getM() - typeConverter1.getM()), x, y, z));
                    }
                } else if (fromSC.equals("XYZ") && toSC.equals("BLH")) {
// todo Никита Сергеевич

                }

                if(fromSC.equals("B,L,H")){
                    // перевести градусы в радианы
                    x = x * Math.PI / 180;
                    y = y * Math.PI / 180;
                }
                double[] coordinates = new double[3];

                // если из одной системы в нее же
                if(from.equals(to)){
                    if(fromSC.equals("X,Y,Z")){
                        if(toSC.equals("X,Y,Z")){
                            coordinates = new double[]{x,y,z};
                        }
                        else if(toSC.equals("B,L,H")){
                      //      coordinates = coordinateSystemManager.convertXYZtoBLH(from, x,y,z);
                        }
                    } else if(fromSC.equals("B,L,H")){
                        if(toSC.equals("X,Y,Z")){
                        //    coordinates = coordinateSystemManager.convertBLHtoXYZ(from, x,y,z);
                        }
                        else if(toSC.equals("B,L,H")){
                            coordinates = new double[]{x,y,z};
                        }
                    }
                } else {    // если из одной в другую
                    if(fromSC.equals("X,Y,Z")){
                        if(toSC.equals("X,Y,Z")){
                        //    coordinates = coordinateSystemManager.convertXYZ(from,to, x,y,z);
                        }
                        else if(toSC.equals("B,L,H")){
                     //       coordinates = coordinateSystemManager.convertXYZ(from,to, x,y,z);
                      //      coordinates = coordinateSystemManager.convertXYZtoBLH(to, coordinates[0],coordinates[1],coordinates[2]);
                        }
                    } else if(fromSC.equals("B,L,H")){
                        if(toSC.equals("X,Y,Z")){
                       //     coordinates = coordinateSystemManager.convertBLHtoXYZ(from, x,y,z);
                       //     coordinates = coordinateSystemManager.convertXYZ(from,to, coordinates[0],coordinates[1],coordinates[2]);
                        }
                        else if(toSC.equals("B,L,H")){
                        //    coordinates = coordinateSystemManager.convertBLHtoXYZ(from, x,y,z);
                        //    coordinates = coordinateSystemManager.convertXYZ(from,to, coordinates[0],coordinates[1],coordinates[2]);
                        //    coordinates = coordinateSystemManager.convertXYZtoBLH(to, coordinates[0],coordinates[1],coordinates[2]);
                        }
                    }
                }
                // обновляем Labels
                if(toSC.equals("B,L,H")){
                    // переводим в градусы
                    coordinates[0] = coordinates[0] * 180 / Math.PI;
                    coordinates[1] = coordinates[1] * 180 / Math.PI;
                }
                updateCoordinateLabels(coordinates);
            }catch(Exception exception){}

        });
        /////////////////////////////////////////////////////////////////////////////
        comboBoxCoordFrom.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(comboBoxCoordFrom.getSelectedItem().toString().equals("X,Y,Z")){
                    XorBLabelFrom.setText("X");
                    YorLLabelFrom.setText("Y");
                    ZorHLabelFrom.setText("Z");
                    return;
                }
                if(comboBoxCoordFrom.getSelectedItem().toString().equals("B,L,H")){
                    XorBLabelFrom.setText("B");
                    YorLLabelFrom.setText("L");
                    ZorHLabelFrom.setText("H");
                }
            }
        });
        comboBoxCoordTo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(comboBoxCoordTo.getSelectedItem().toString().equals("X,Y,Z")){
                    XorBLabelTo.setText("X");
                    YorLLabelTo.setText("Y");
                    ZorHLabelTo.setText("Z");
                    return;
                }
                if(comboBoxCoordTo.getSelectedItem().toString().equals("B,L,H")){
                    XorBLabelTo.setText("B");
                    YorLLabelTo.setText("L");
                    ZorHLabelTo.setText("H");
                }
            }
        });
        ///////////////////////////////////////////////////////////////////////////////
    }

    public static void init() {
        comboBoxFrom.addItem("WGS84");
        comboBoxFrom.addItem("PZ9011");
        comboBoxFrom.addItem("SK42");
        comboBoxFrom.addItem("SK95");
        comboBoxTo.addItem("PZ9011");
        comboBoxTo.addItem("WGS84");
        comboBoxTo.addItem("SK95");
        comboBoxTo.addItem("SK42");

        comboBoxCoordFrom.addItem("XYZ");
        comboBoxCoordFrom.addItem("BLH");

        comboBoxCoordTo.addItem("XYZ");
        comboBoxCoordTo.addItem("BLH");
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("CalcUI");
        frame.setContentPane(new CalcUI().rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void updateCoordinateLabels(double[] coord){
        resultLabelX.setText(String.valueOf(coord[0]));
        resultLabelY.setText(String.valueOf(coord[1]));
        resultLabelZ.setText(String.valueOf(coord[2]));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // имплементация интерфейса требует реализации метода
    }
}

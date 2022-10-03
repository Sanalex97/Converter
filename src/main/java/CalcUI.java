import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalcUI extends JFrame implements ActionListener{
    private JPanel rootPanel;
    private JButton calculateButton;
    private JComboBox comboBoxFrom;
    private JComboBox comboBoxTo;
    private JTextField xFieldFrom;
    private JTextField yFieldFrom;
    private JTextField zFieldFrom;
    private JLabel resultLabelZ;
    private JLabel resultLabelX;
    private JLabel resultLabelY;
    private JComboBox comboBoxCoordFrom;
    private JLabel XorBLabelFrom;
    private JLabel YorLLabelFrom;
    private JLabel ZorHLabelFrom;
    private JComboBox comboBoxCoordTo;
    private JLabel XorBLabelTo;
    private JLabel YorLLabelTo;
    private JLabel ZorHLabelTo;
    private CoordinateSystemManager coordinateSystemManager;

    public CalcUI() {
        coordinateSystemManager = new CoordinateSystemManager();
        // добавить системы координат в UI
        comboBoxFrom.addItem("WGS84");
        comboBoxFrom.addItem("PZ9011");
        comboBoxFrom.addItem("SK42");
        comboBoxFrom.addItem("SK95");
        comboBoxTo.addItem("PZ9011");
        comboBoxTo.addItem("WGS84");
        comboBoxTo.addItem("SK95");
        comboBoxTo.addItem("SK42");
        //////////////////////////////////
        comboBoxCoordFrom.addItem("X,Y,Z");
        comboBoxCoordFrom.addItem("B,L,H");
//        comboBoxCoordFrom.addItem("G,k");
        comboBoxCoordTo.addItem("X,Y,Z");
        comboBoxCoordTo.addItem("B,L,H");
//        comboBoxCoordTo.addItem("G,k");

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    double x = Double.parseDouble(xFieldFrom.getText());
                    double y = Double.parseDouble(yFieldFrom.getText());
                    double z = Double.parseDouble(zFieldFrom.getText());
                    // считываем выбранные системы для перевода
                    String from = comboBoxFrom.getSelectedItem().toString();
                    String to = comboBoxTo.getSelectedItem().toString();
                    String fromCoord = comboBoxCoordFrom.getSelectedItem().toString();
                    String toCoord = comboBoxCoordTo.getSelectedItem().toString();

                    if(fromCoord.equals("B,L,H")){
                        // перевести градусы в радианы
                        x = x * Math.PI / 180;
                        y = y * Math.PI / 180;
                    }
                    double[] coordinates = new double[3];

                    // если из одной системы в нее же
                    if(from.equals(to)){
                        if(fromCoord.equals("X,Y,Z")){
                            if(toCoord.equals("X,Y,Z")){
                                coordinates = new double[]{x,y,z};
                            }
                            else if(toCoord.equals("B,L,H")){
                          //      coordinates = coordinateSystemManager.convertXYZtoBLH(from, x,y,z);
                            }
                        } else if(fromCoord.equals("B,L,H")){
                            if(toCoord.equals("X,Y,Z")){
                            //    coordinates = coordinateSystemManager.convertBLHtoXYZ(from, x,y,z);
                            }
                            else if(toCoord.equals("B,L,H")){
                                coordinates = new double[]{x,y,z};
                            }
                        }
                    } else {    // если из одной в другую
                        if(fromCoord.equals("X,Y,Z")){
                            if(toCoord.equals("X,Y,Z")){
                            //    coordinates = coordinateSystemManager.convertXYZ(from,to, x,y,z);
                            }
                            else if(toCoord.equals("B,L,H")){
                         //       coordinates = coordinateSystemManager.convertXYZ(from,to, x,y,z);
                          //      coordinates = coordinateSystemManager.convertXYZtoBLH(to, coordinates[0],coordinates[1],coordinates[2]);
                            }
                        } else if(fromCoord.equals("B,L,H")){
                            if(toCoord.equals("X,Y,Z")){
                           //     coordinates = coordinateSystemManager.convertBLHtoXYZ(from, x,y,z);
                           //     coordinates = coordinateSystemManager.convertXYZ(from,to, coordinates[0],coordinates[1],coordinates[2]);
                            }
                            else if(toCoord.equals("B,L,H")){
                            //    coordinates = coordinateSystemManager.convertBLHtoXYZ(from, x,y,z);
                            //    coordinates = coordinateSystemManager.convertXYZ(from,to, coordinates[0],coordinates[1],coordinates[2]);
                            //    coordinates = coordinateSystemManager.convertXYZtoBLH(to, coordinates[0],coordinates[1],coordinates[2]);
                            }
                        }
                    }
                    // обновляем Labels
                    if(toCoord.equals("B,L,H")){
                        // переводим в градусы
                        coordinates[0] = coordinates[0] * 180 / Math.PI;
                        coordinates[1] = coordinates[1] * 180 / Math.PI;
                    }
                    updateCoordinateLabels(coordinates);
                }catch(Exception exception){}

            }
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

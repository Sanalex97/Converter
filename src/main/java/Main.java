import Converters.TypeConverter;
import com.opencsv.CSVReader;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class Main {
    private static CoordinateSystemManager coordinateSystemManager;
    private static HashMap<String, TypeConverter> translateTableCoordinateSystem = new HashMap<>();

    public static void main(String[] args) {
        if (init()) {

        }
    }

    private static boolean init() {
        try (CSVReader csvReader = new CSVReader(new FileReader("src/main/resources/DB/ConverterPSK.csv"))) {
            ColumnPositionMappingStrategy<TypeConverter> strategy = new ColumnPositionMappingStrategy<>();
            strategy.setType(TypeConverter.class);
            strategy.setColumnMapping("id", "systemA", "deltaX", "deltaY",
                    "deltaZ", "omegaX", "omegaY", "omegaZ", "m");

            CsvToBean<TypeConverter> csv = new CsvToBeanBuilder<TypeConverter>(csvReader)
                    .withMappingStrategy(strategy)
                    .build();

            List<TypeConverter> list = csv.parse();
            for (TypeConverter typeConverter : list) {
                translateTableCoordinateSystem.put(typeConverter.getSystemA(), typeConverter);
            }

            return true;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static HashMap<String, TypeConverter> getTranslateTableCoordinateSystem() {
        return translateTableCoordinateSystem;
    }
}
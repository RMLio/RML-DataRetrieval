package be.ugent.mmlab.rml.output;

import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.sql.ResultSet;

/**
 * Created by andimou on 9/28/16.
 */
public class CsvWriter {

    public void writeHashMapToCsv(ResultSet rs, String outputFile) throws Exception {

        CSVWriter wr = new CSVWriter(new FileWriter(outputFile), ',');
        wr.writeAll(rs, true);
        wr.close();

    }
}

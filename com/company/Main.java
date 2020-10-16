package com.company;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        File file = new File(args[0]);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        List<Company> companies = new ArrayList<>();
        while ((line = br.readLine()) != null) {
            Company company = new Company(line);
            companies.add(company);
            company.Print(args[1]);
        }
        br.close();
        fr.close();
    }

    static class Company {
        private String name;
        private String shortName;
        private String actualDate;
        private String adress;
        private String foundationDate;
        private int employee;
        private String auditor;
        private String number;
        private String email;
        private String sphere;
        private String type;
        private String website;

        Company(String line) {
            StringTokenizer tokens = new StringTokenizer(line, ";");
            name = tokens.nextToken();
            shortName = tokens.nextToken();
            actualDate = tokens.nextToken();
            adress = tokens.nextToken();
            foundationDate = tokens.nextToken();
            employee = Integer.parseInt(tokens.nextToken());
            auditor = tokens.nextToken();
            number = tokens.nextToken();
            email = tokens.nextToken();
            sphere = tokens.nextToken();
            type = tokens.nextToken();
            website = tokens.nextToken();
        }

        void Print(String filename) throws IOException {
            FileWriter writer = new FileWriter(filename);
            writer.write(name + ";" +
                    shortName + ";" +
                    actualDate + ";" +
                    adress + ";" +
                    foundationDate + ";" +
                    employee + ";" +
                    auditor + ";" +
                    number + ";" +
                    email + ";" +
                    sphere + ";" +
                    type + ";" +
                    website);
        }
    }
}

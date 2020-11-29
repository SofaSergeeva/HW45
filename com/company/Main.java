package com.company;

import java.io.*;
import java.security.InvalidKeyException;
import java.util.*;


public class Main {

    public static void main(String[] args) throws IOException {
        File file = new File(args[0]);
        FileReader fr = new FileReader(file);
        BufferedReader br = new BufferedReader(fr);
        String line;
        List<Company> companies = new ArrayList<>();
        try (FileWriter writer = new FileWriter(args[1])) {
            while ((line = br.readLine()) != null) {
                Company company = new Company(line);
                companies.add(company);
                String s = company.toString();
                writer.write(s);
            }

        }
        br.close();
        fr.close();
        String request;
        Scanner scanner = new Scanner(System.in);
        try (FileWriter fw = new FileWriter(args[1], true)) {
            Date date = new Date();
            fw.write(date.toString());
            while ((!(request = scanner.next()).equals("0"))) {
                switch (request) {
                    case "ShortName": {
                        String name = scanner.next();
                        try {
                            fw.write("Found by short name " + name + ": " + FindByShortName(companies, name).toString());
                        } catch (InvalidKeyException ex) {
                            System.out.println(ex);
                        }
                        break;
                    }
                    case "Sphere": {
                        String sphere = scanner.next();
                        try {
                            fw.write("Found by sphere " + sphere + ": " + FindBySphere(companies, sphere).size());
                        } catch (InvalidKeyException ex) {
                            System.out.println(ex);
                        }
                        break;
                    }
                    case "Type": {
                        String type = scanner.next();
                        try {
                            fw.write("Found by type " + type + ": " + FindByType(companies, type).size());
                        } catch (InvalidKeyException ex) {
                            System.out.println(ex);
                        }
                        break;
                    }
                    case "Date": {
                        String start = scanner.next();
                        StringTokenizer tokens = new StringTokenizer(start, "-");
                        int year = Integer.parseInt(tokens.nextToken());
                        int month = Integer.parseInt(tokens.nextToken());
                        int day = Integer.parseInt(tokens.nextToken());
                        Calendar date_start = new GregorianCalendar(year, month, day);

                        String end = scanner.next();
                        tokens = new StringTokenizer(end, "-");
                        year = Integer.parseInt(tokens.nextToken());
                        month = Integer.parseInt(tokens.nextToken());
                        day = Integer.parseInt(tokens.nextToken());
                        Calendar date_end = new GregorianCalendar(year, month, day);
                        try {
                            fw.write("Found in range " + date_start.toString() + " - " + date_end.toString() + FindByDate(companies, date_start, date_end).size());
                        } catch (InvalidKeyException ex) {
                            System.out.println(ex);
                        }
                        break;
                    }
                    case "Employee": {
                        Integer employee_start = Integer.parseInt(scanner.next());
                        Integer employee_end = Integer.parseInt(scanner.next());
                        try {
                            fw.write("Found in range of number of employees:  " + employee_start + " - " + employee_end + ": " + FindByEmployee(companies, employee_start, employee_end).size());
                        } catch (InvalidKeyException ex) {
                            System.out.println(ex);
                        }
                        break;
                    }
                    case "": {
                        Date date_end = new Date();
                        fw.write(date.toString());
                        break;
                    }

                }

            }
        }
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


        public String toString() {
            return new String(name + ";" +
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
                    website + "\n");
        }
    }

    static Company FindByShortName(List<Company> companies, String shortName) throws InvalidKeyException {
        for (Company company : companies) {
            if (company.shortName.equals(shortName)) {
                return company;
            }
        }
        throw new InvalidKeyException("There is no company with short name " + shortName);
    }

    static List<Company> FindBySphere(List<Company> companies, String sphere) throws InvalidKeyException {
        List<Company> companiesBySphere = new ArrayList<>();
        for (Company company : companies) {
            if (company.sphere.equals(sphere)) {
                companiesBySphere.add(company);
            }
        }
        if (companiesBySphere.isEmpty()) {
            throw new InvalidKeyException("There is no company with sphere " + sphere);
        } else {
            return companiesBySphere;
        }
    }

    static List<Company> FindByType(List<Company> companies, String type) throws InvalidKeyException {
        List<Company> companiesByType = new ArrayList<>();
        for (Company company : companies) {
            if (company.type.equals(type)) {
                companiesByType.add(company);
            }
        }
        if (companiesByType.isEmpty()) {
            throw new InvalidKeyException("There is no company with type " + type);
        } else {
            return companiesByType;
        }
    }

    static List<Company> FindByDate(List<Company> companies, Calendar start, Calendar end) throws InvalidKeyException {
        List<Company> companiesByDate = new ArrayList<>();

        for (Company company : companies) {
            StringTokenizer tokens_company = new StringTokenizer(company.foundationDate, "-");
            int day_company = Integer.parseInt(tokens_company.nextToken());
            int month_company = Integer.parseInt(tokens_company.nextToken());
            int year_company = Integer.parseInt(tokens_company.nextToken());
            Calendar calendar = new GregorianCalendar(year_company, month_company, day_company);
            if (calendar.after(start) && calendar.before(end)) {
                companiesByDate.add(company);
            }
        }
        if (companiesByDate.isEmpty()) {
            throw new InvalidKeyException("There is no company was founded in range " + start.getTime() + " - " + end.getTime());
        } else {
            return companiesByDate;
        }
    }

    static List<Company> FindByEmployee(List<Company> companies, int start, int end) throws InvalidKeyException {
        List<Company> companiesByEmployee = new ArrayList<>();

        for (Company company : companies) {
            if (start <= company.employee && company.employee <= end) {
                companiesByEmployee.add(company);
            }
        }
        if (companiesByEmployee.isEmpty()) {
            throw new InvalidKeyException("There is no company with number of employee in range:" + start + " - " + end);
        } else {
            return companiesByEmployee;
        }

    }
}
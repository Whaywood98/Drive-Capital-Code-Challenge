package org.example.database;

import org.example.model.Company;
import org.example.model.Contact;
import org.example.model.Employee;
import org.example.model.Partner;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class DbBuilder {

    public static void build(Path path) {
        createPartners(path);
        createCompanies(path);
        createEmployees(path);
        createContacts(path);
    }

    private static void createPartners(Path path) {
        try(BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while((line = br.readLine()) != null) {
                String[] arr = line.split(" ");
                String command = arr[0];
                if(command.equals("Partner")) {
                    Database.partners.add(
                        new Partner(
                            arr[1]
                        )
                    );
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createCompanies(Path path) {
        try(BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while((line = br.readLine()) != null) {
                String[] arr = line.split(" ");
                String command = arr[0];
                if(command.equals("Company")) {
                    Database.companies.add(
                        new Company(
                            arr[1]
                        )
                    );
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createEmployees(Path path) {
        try(BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while((line = br.readLine()) != null) {
                String[] arr = line.split(" ");
                String command = arr[0];
                if(command.equals("Employee")) {
                    Database.employees.add(
                        new Employee(
                            arr[1],
                            getCompanyIdByName(arr[2])
                        )
                    );
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createContacts(Path path) {
        try(BufferedReader br = Files.newBufferedReader(path)) {
            String line;
            while((line = br.readLine()) != null) {
                String[] arr = line.split(" ");
                String command = arr[0];
                if(command.equals("Contact")) {
                    Database.contacts.add(
                        new Contact(
                            getEmployeeIdByName(arr[1]),
                            getPartnerIdByName(arr[2]),
                            arr[3]
                        )
                    );
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static int getCompanyIdByName(String name) {
        for(Company company : Database.companies) {
            if(company.getName().equals(name)) {
                return Database.companies.indexOf(company);
            }
        }
        return -1;
    }

    private static int getPartnerIdByName(String name) {
        for(Partner partner : Database.partners) {
            if(partner.getName().equals(name)) {
                return Database.partners.indexOf(partner);
            }
        }
        return -1;
    }

    private static int getEmployeeIdByName(String name) {
        for(Employee employee : Database.employees) {
            if(employee.getName().equals(name)) {
                return Database.employees.indexOf(employee);
            }
        }
        return -1;
    }

}

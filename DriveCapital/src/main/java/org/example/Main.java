package org.example;

import org.example.database.Database;
import org.example.database.DbBuilder;
import org.example.model.Company;
import org.example.model.Contact;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    private static Path path;

    public static void main(String[] args) {
        if(args.length == 0) {
            System.out.println("Please enter a valid input file");
            System.exit(1);
        }
        path = Paths.get(args[0]);
        Main app = new Main();
        app.run();
    }

    public void run() {
        DbBuilder.build(path);
        TreeMap<String, List<Integer>> contactsPerCompany = getContactsPerCompany();
        List<String> closestContactPerCompany = getClosestContactPerCompany(contactsPerCompany);
        closestContactPerCompany.forEach(System.out::println);
    }

    private List<String> getClosestContactPerCompany(TreeMap<String, List<Integer>> contactsPerCompany) {
        List<String> closestContactPerCompany = new ArrayList<>();
        for(String companyName : contactsPerCompany.keySet()) {
            List<Integer> partnerContacts = contactsPerCompany.get(companyName);
            if(partnerContacts.isEmpty()) {
                closestContactPerCompany.add(companyName + ": No current relationship");
                continue;
            }
            int[] mostCommonId = getMostCommonPartnerId(partnerContacts);
            try{
                closestContactPerCompany.add(
                        companyName
                                + ": " + Database.partners.get(mostCommonId[0]).getName()
                                + " (" + mostCommonId[1] + ")"
                );
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
            }
        }
        return closestContactPerCompany;
    }

    private TreeMap<String, List<Integer>> getContactsPerCompany() {
        TreeMap<String, List<Integer>> contactsPerCompany = new TreeMap<>();
        for(Company company : Database.companies) {
            contactsPerCompany.put(company.getName(), new ArrayList<>());
        }
        for(Contact contact : Database.contacts) {
            try {
                contactsPerCompany.get(
                    Database.companies.get(
                        Database.employees.get(
                            contact.getEmployee()
                        ).getCompany()
                    ).getName()
                ).add(contact.getPartner());
            } catch(NullPointerException e) {
                System.out.println("Company not found. Skipping entry.");;
            }
        }
        return contactsPerCompany;
    }

    private int[] getMostCommonPartnerId(List<Integer> partnerIds) {
        HashMap<Integer, Integer> numberOfContactsPerPartner = new HashMap<>();
        int mostCommonPartner = -1;
        int max = -1;
        for(Integer i : partnerIds) {
            if(!numberOfContactsPerPartner.containsKey(i)) {
                numberOfContactsPerPartner.put(i, 1);
            } else {
                numberOfContactsPerPartner.put(i, numberOfContactsPerPartner.get(i) + 1);
            }
        }
        for(Integer i : numberOfContactsPerPartner.keySet()) {
            int contactCount = numberOfContactsPerPartner.get(i);
            if(contactCount > max) {
                max = contactCount;
                mostCommonPartner = i;
            }
        }
        return new int[] {mostCommonPartner, max};
    }

}
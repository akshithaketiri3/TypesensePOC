package com.example.typesense.script;


import com.example.typesense.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
        import java.util.List;
import java.util.Random;
import java.util.UUID;

public class GenerateJson {

    public static void main(String[] args) {
        int numberOfRecords = 80000;
        List<InvoiceDTO> invoices = generateInvoices(numberOfRecords);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        try {
            File jsonFile = new File("/Users/ketiriakshitha/Desktop/invoices.json");
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(jsonFile, invoices);
            System.out.println("JSON file created successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static List<InvoiceDTO> generateInvoices(int numberOfRecords) {
        List<InvoiceDTO> invoices = new ArrayList<>();
        for (int i = 0; i < numberOfRecords; i++) {
            invoices.add(generateRandomInvoiceDTO());
        }
        return invoices;
    }

    private static InvoiceDTO generateRandomInvoiceDTO() {
    InvoiceDTO invoice = new InvoiceDTO();
        Random random = new Random();
        invoice.setInvoiceId("INV-" + UUID.randomUUID().toString().substring(0, 8));
        invoice.setDocumentDetailsCreatedBy(generateCreatedBy());
        invoice.setInvoiceDataInvoiceDocumentDetailsSupplierDocumentNumber(generateSupplierDocumentNumber());
        invoice.setInvoiceDataInvoiceDocumentDetailsTotalInvoiceAmount(generateTotalInvoiceAmount());
        invoice.setInvoiceDataInvoiceDocumentDetailsDocumentDate(generateDocumentDate());
        invoice.setInvoiceDataInvoiceDocumentDetailsDocumentType(generateDocumentType());
        invoice.setInvoiceDataInvoiceDocumentDetailsGoodsServiceType(generateGoodsServiceType());
                invoice.setInvoiceDataInvoiceDocumentDetailsFiDocumentNumber(generateFiDocumentNumber());
                invoice.setInvoiceDataInvoiceDocumentDetailsShipmentNumber(generateShipmentNumber());
                invoice.setInvoiceDataBillToDetailsPan(generateRandomPan());
                invoice.setInvoiceDataBillToDetailsGstin(generateRandomGstin());
                invoice.setInvoiceDataBarcodeDetails(generateBarcode());
                invoice.setInvoiceDataGrnDetailsGrnNumber(generateGrnDetails());
                invoice.setInvoiceDataPoDetailsPoNumber(generatePoDetails());
                invoice.setInvoiceDataVendorDetailsCode(generateVendorCode());
                invoice.setInvoiceDataVendorDetailsName(generateName());
                invoice.setInvoiceDataVendorDetailsMsmeClassification(generateMsmeClassification());
                invoice.setInvoiceDataBuyerDetailsGstin(generateRandomGstin());
                invoice.setInvoiceDataBuyerDetailsPan(generateRandomPan());
                invoice.setInvoiceDataBuyerDetailsBranchName(generateBranch());
        invoice.setInvoiceLabel("LABEL-" + UUID.randomUUID().toString().substring(0, 4));
        InvoiceStatus[] statusValues = InvoiceStatus.values();
        invoice.setStatus(statusValues[random.nextInt(statusValues.length)]);
        invoice.setCreatedAt((LocalDateTime.now().minusDays((long) (Math.random() * 365)).toString()));
        return invoice;
    }





    private static String generateBarcode() {
        Random random = new Random();
        BarcodeDetails barcodeDetails = new BarcodeDetails();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            int digit = random.nextInt(10);
            stringBuilder.append(digit);
        }
        return "BAR" + stringBuilder.toString();

    }


    private static String generateBranch() {
        Random random = new Random();
        String[] places = {"New York", "London", "Tokyo", "Paris", "Sydney", "Rome", "Cairo", "Rio de Janeiro", "Moscow", "Cape Town", "Dubai", "Toronto", "Singapore", "Berlin", "Beijing", "Los Angeles", "Mumbai", "Stockholm", "Amsterdam", "Seoul", "Barcelona", "Istanbul", "Mexico City", "Bangkok", "Dublin", "Athens", "Vienna", "Prague", "Buenos Aires", "Helsinki"};
        return places[random.nextInt(places.length)];
    }

    private static String generateRandomPan() {
        StringBuilder panBuilder = new StringBuilder();

        // Generate 5 uppercase letters
        for (int i = 0; i < 5; i++) {
            char randomLetter = (char) ('A' + new Random().nextInt(26));
            panBuilder.append(randomLetter);
        }

        // Generate 4 random digits
        for (int i = 0; i < 4; i++) {
            int randomDigit = new Random().nextInt(10);
            panBuilder.append(randomDigit);
        }

        // Generate 1 uppercase letter (usually 'A', 'B', 'C', or 'P')
        char randomLastLetter = "ABCP".charAt(new Random().nextInt(4));
        panBuilder.append(randomLastLetter);

        return panBuilder.toString();
    }
    private static String generateRandomGstin() {
        StringBuilder gstinBuilder = new StringBuilder("27"); // Assuming GSTIN starts with state code 27 for testing

        Random random = new Random();

        // Generate 10 random digits for the GSTIN
        for (int i = 0; i < 10; i++) {
            int randomDigit = random.nextInt(10);
            gstinBuilder.append(randomDigit);
        }

        // Calculate and append the checksum digit
        String gstinWithoutChecksum = gstinBuilder.toString();
        char checksumDigit = calculateGstinChecksum(gstinWithoutChecksum);
        gstinBuilder.append(checksumDigit);

        return gstinBuilder.toString();
    }

    private static char calculateGstinChecksum(String gstinWithoutChecksum) {
        int factor = 2;
        int sum = 0;

        // Calculate weighted sum of digits
        for (int i = gstinWithoutChecksum.length() - 2; i >= 0; i--) {
            int digit = Integer.parseInt(String.valueOf(gstinWithoutChecksum.charAt(i)));
            sum += digit * factor;

            factor = (factor == 1) ? 2 : 1;
        }

        // Calculate and return the checksum digit
        int checksumDigit = (int) ((sum + 10) % 11);
        return (checksumDigit == 0) ? '0' : (char) (11 - checksumDigit + '0');
    }

    private static String generateVendorCode() {
        Random random = new Random();
        StringBuilder vendorCode = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int digit = random.nextInt(10);
            vendorCode.append(digit);
        }

        return vendorCode.toString();

    }

    private static String generateName() {
        Random random = new Random();
        String[] people = {"Sophia", "Jackson", "Olivia", "Liam", "Emma", "Noah", "Ava", "Lucas", "Isabella", "Oliver",
                "Amelia", "Ethan", "Mia", "Aiden", "Harper", "Caden", "Ella", "Grayson", "Aria", "Muhammad",
                "Scarlett", "Carter", "Chloe", "Matthew", "Abigail", "Mason", "Emily", "Sebastian", "Sofia", "Logan"};
        return people[random.nextInt(people.length)];
    }

    private static MMSE generateMsmeClassification() {
        Random random = new Random();
        MMSE[] mmse = MMSE.values();
        return mmse[random.nextInt(mmse.length)];
    }

    private static String generateGrnDetails() {
        String prefix = "GRN";
        int randomSerialNumber = new Random().nextInt(9000) + 1000;
       return prefix + randomSerialNumber;

    }

    private static String generatePoDetails() {
        int randomNumber = new Random().nextInt(1000000);
        String randomPO = "PO" + randomNumber;
        return randomPO;
    }


    private static String generateShipmentNumber() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            int digit = random.nextInt(10);
            stringBuilder.append(digit);
        }

        return "SHIP" + stringBuilder.toString();
    }

    private static String generateFiDocumentNumber() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            int digit = random.nextInt(10);
            stringBuilder.append(digit);
        }

        return "FID" + stringBuilder.toString();
    }

    private static String generateSupplierDocumentNumber() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder stringBuilder = new StringBuilder();

        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(characters.length());
            stringBuilder.append(characters.charAt(index));
        }

        return "SUP" + stringBuilder.toString();
    }

    private static String generateTotalInvoiceAmount() {
        Random random = new Random();
        int firstDigit = random.nextInt(9) + 1;
        int remainingDigits = random.nextInt(9000000) + 1000000;
        int ans  = firstDigit * 10000000 + remainingDigits;
        return String.valueOf(ans);
    }

    private static DocumentType generateDocumentType() {
        Random random = new Random();
        DocumentType[] documentTypes = DocumentType.values();
        return documentTypes[random.nextInt(documentTypes.length)];
    }

    private static GoodsServiceType generateGoodsServiceType() {
        Random random = new Random();
        GoodsServiceType[] goodsServiceType = GoodsServiceType.values();
         return   goodsServiceType[random.nextInt(goodsServiceType.length)];
    }

    private static String generateDocumentDate() {

            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            String formattedDate = currentDate.format(formatter);

            return formattedDate;
    }

    private static String generateCreatedBy() {

        Random random = new Random();
        String[] people = {"Sophia", "Jackson", "Olivia", "Liam", "Emma", "Noah", "Ava", "Lucas", "Isabella", "Oliver",
                "Amelia", "Ethan", "Mia", "Aiden", "Harper", "Caden", "Ella", "Grayson", "Aria", "Muhammad",
                "Scarlett", "Carter", "Chloe", "Matthew", "Abigail", "Mason", "Emily", "Sebastian", "Sofia", "Logan"};
       return people[random.nextInt(people.length)];

    }
}

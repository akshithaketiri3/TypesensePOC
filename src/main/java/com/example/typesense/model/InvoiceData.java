package com.example.typesense.model;

import lombok.Data;

import java.util.List;

@Data
public class InvoiceData {

    private InvoiceDocumentDetails invoiceDocumentDetails;
    private BuyerDetails billToDetails;
    private VendorDetails vendorDetails;
    private PODetails poDetails;
    private GRNDetails grnDetails;
    private BarcodeDetails barcodeDetails;

}

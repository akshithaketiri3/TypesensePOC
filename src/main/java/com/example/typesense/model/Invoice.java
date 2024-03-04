package com.example.typesense.model;

import lombok.Data;
import java.time.LocalDateTime;



@Data
public class Invoice {

    private String invoiceId;
    private DocumentDetails documentDetails;
    private InvoiceData invoiceData;
    private InvoiceStatus status;
    private String createdAt;
    private String invoiceLabel;

}

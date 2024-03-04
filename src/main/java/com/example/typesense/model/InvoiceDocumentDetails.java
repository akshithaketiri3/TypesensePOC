package com.example.typesense.model;





import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Data
public class InvoiceDocumentDetails {
    private String supplierDocumentNumber;
    private int documentDate;
    private DocumentType documentType;
    private String totalInvoiceAmount;
    private GoodsServiceType goodsServiceType;
    private String fiDocumentNumber;
    private String shipmentNumber;
}

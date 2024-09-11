package com.example.demo_java_spring.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo_java_spring.dto.InvoiceDetailDto;
import com.example.demo_java_spring.dto.ResInvoiceDetailDto;
import com.example.demo_java_spring.dto.ResInvoiceDto;
import com.example.demo_java_spring.dto.ResSaleDto;
import com.example.demo_java_spring.dto.SaleDto;
import com.example.demo_java_spring.helper.Helper;
import com.example.demo_java_spring.models.Invoice;
import com.example.demo_java_spring.models.InvoiceDetail;
import com.example.demo_java_spring.models.Item;
import com.example.demo_java_spring.models.User;
import com.example.demo_java_spring.repository.InvoiceDetailRepository;
import com.example.demo_java_spring.repository.InvoiceRepository;
import com.example.demo_java_spring.repository.ItemRepository;
import com.example.demo_java_spring.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class SaleService {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private InvoiceRepository invoiceRepository;

    @Autowired
    private InvoiceDetailRepository invoiceDetailRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public ResSaleDto sale(SaleDto saleDto) {
        String invId = UUID.randomUUID().toString();
        double totalAmount = 0.0;
        Invoice invoice = new Invoice(invId, saleDto.getInvoice().getInvoiceNo(), new Date(),
                0.0, Helper.ACTIVE, new Date(), saleDto.getInvoice().getUserId());
        List<InvoiceDetail> details = new ArrayList<>();

        for (InvoiceDetailDto detail : saleDto.getDetails()) {
            Item item = itemRepository.findById(detail.getItemId()).get();
            totalAmount += (detail.getQty() * item.getPrice());
            details.add(new InvoiceDetail(UUID.randomUUID().toString(), invId, detail.getItemId(), detail.getQty()));
        }
        invoice.setTotalAmount(totalAmount);
        Invoice resultInvoice = invoiceRepository.save(invoice);
        List<InvoiceDetail> resultInvoiceDetails = invoiceDetailRepository.saveAll(details);

        ResSaleDto resSaleDto = new ResSaleDto();
        User user = userRepository.findById(resultInvoice.getUserId()).get();
        resSaleDto.setInvoice(new ResInvoiceDto(resultInvoice.getId(), resultInvoice.getInvNumber(),
                resultInvoice.getInvDate(), resultInvoice.getTotalAmount(),
                resultInvoice.getStatus(), resultInvoice.getCreatedDate(), resultInvoice.getUserId(),
                user.getFullName()));

        List<ResInvoiceDetailDto> resInvoiceDetailDtos = new ArrayList<>();
        for (InvoiceDetail resultInvoiceDetail : resultInvoiceDetails) {
            Item item = itemRepository.findById(resultInvoiceDetail.getItemId()).get();
            resInvoiceDetailDtos.add(new ResInvoiceDetailDto(resultInvoiceDetail.getId(),
                    resultInvoiceDetail.getInvoiceId(), resultInvoiceDetail.getItemId(),
                    item.getFullName(), item.getPrice(), resultInvoiceDetail.getQty()));
        }
        resSaleDto.setDetails(resInvoiceDetailDtos);
        return resSaleDto;
    }

}

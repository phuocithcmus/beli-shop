package org.beli.services;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.beli.dtos.req.CreateRevenueRequestDto;
import org.beli.dtos.req.UpdateProductRequestDto;
import org.beli.dtos.req.UpdateRevenueRequestDto;
import org.beli.dtos.res.RevenueFeeResponseDto;
import org.beli.dtos.res.RevenueResponseDto;
import org.beli.entities.Product;
import org.beli.entities.Revenues;
import org.beli.repositories.RevenueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class RevenueService extends BaseService<Revenues, String> {
    @Autowired
    private RevenueRepository revenueRepository;

    @Autowired
    private FeeService feeService;

    @Autowired
    private ProductService productService;


    public RevenueService(RevenueRepository revenueRepository) {
        super(revenueRepository);
    }

    public Revenues createNewRevenue(CreateRevenueRequestDto dto) {

        var product = productService.findById(dto.productId());
        if (product == null) {
            throw new RuntimeException("Product not found");
        }
        var revenues = mappingToCreateEntity(dto);
        var newRevenues = revenueRepository.save(revenues);

        if (newRevenues == null) {
            throw new RuntimeException("Failed to create revenue");
        }
        var remainingAmount = product.getRemainingAmount() - dto.amount();
        product.setRemainingAmount(remainingAmount);
        productService.update(product);

        System.out.println("Product remaining amount: " + product.getRemainingAmount());

        return newRevenues;
    }

    public Revenues mappingToCreateEntity(CreateRevenueRequestDto dto) {

        var feesOpt = feeService.findByFeePlatform(dto.channel());
        var product = productService.findById(dto.productId());


        if (feesOpt.isPresent()) {

            var feeAmount = dto.packageFee(); // + additional fees

            var revenue = dto.receivedAmount() - (product.getPrice() + product.getTransferFee()) * dto.amount() - feeAmount;

            Revenues revenues = new Revenues();
            revenues.setChannel(dto.channel());
            revenues.setPrice(dto.price());
            revenues.setSellPrice(dto.sellPrice());
            revenues.setProductId(dto.productId());
            revenues.setAmount(dto.amount());
            revenues.setPackageFee(dto.packageFee());
            revenues.setCreatedAt(System.currentTimeMillis());
            revenues.setUpdatedAt(System.currentTimeMillis());
            revenues.setRevenue(revenue);
            revenues.setReceivedAmount(dto.receivedAmount());

            return revenues;
        } else {
            throw new RuntimeException("Phase not found");
        }
    }

    public RevenueResponseDto mappingToRevenueResponse(Revenues dto) {

        var product = productService.findById(dto.getProductId());

        if (product == null) {
            return null;
        }

        // Empty array
        var fees = new RevenueFeeResponseDto[]{};

        return new RevenueResponseDto(
                dto.getId(),
                dto.getChannel(),
                dto.getPrice(),
                dto.getSellPrice(),
                dto.getRevenue(),
                dto.getAmount(),
                dto.getReceivedAmount(),
                fees,
                product.getId(),
                product.getCode(),
                dto.getPackageFee()
        );
    }

    public Revenues mappingToUpdateEntity(UpdateRevenueRequestDto dto) {
        var revenuesOpt = revenueRepository.findById(dto.id());
        if (revenuesOpt.isEmpty()) {
            throw new RuntimeException("Revenue not found");
        }
        var revenues = revenuesOpt.get();
        revenues.setChannel(dto.channel());
        revenues.setPrice(dto.price());
        revenues.setSellPrice(dto.sellPrice());
        revenues.setProductId(dto.productId());
        revenues.setAmount(dto.amount());
        revenues.setPackageFee(dto.packageFee());
        revenues.setUpdatedAt(System.currentTimeMillis());

        return revenues;
    }

    public Revenues updateRevenue(UpdateRevenueRequestDto dto) {
        var revenues = mappingToUpdateEntity(dto);
        var updatedRevenues = revenueRepository.save(revenues);

        var revenuesOpt = revenueRepository.findById(dto.id());

        if (revenuesOpt.isEmpty()) {
            throw new RuntimeException("Revenue not found");
        }
        var revenuesEntity = revenuesOpt.get();

        var product = productService.findById(dto.productId());
        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        if (updatedRevenues == null) {
            throw new RuntimeException("Failed to update revenue");
        }

        var currentAmount = revenuesEntity.getAmount(); // 3

        var remainingAmount = product.getRemainingAmount() + (currentAmount - dto.amount());
        product.setRemainingAmount(remainingAmount);
        productService.update(product);

        return updatedRevenues;
    }

    public ByteArrayResource export() throws IOException {
        StringBuilder filename = new StringBuilder("Product_Export").append(" - ");
        filename.append(System.currentTimeMillis()).append(".xlsx");

        return export(filename.toString());
    }

    private ByteArrayResource export(String filename) throws IOException {
        byte[] bytes = new byte[1024];
        try (Workbook workbook = generateExcel()) {
            FileOutputStream fos = write(workbook, filename);
            fos.write(bytes);
            fos.flush();
            fos.close();
        }

        return new ByteArrayResource(bytes);
    }

    private Workbook generateExcel() {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        //create columns and rows
        Row row = sheet.createRow(0);

        Cell cell = row.createCell(0);
        cell.setCellValue("ID");
        return workbook;
    }

    private FileOutputStream write(final Workbook workbook, final String filename) throws IOException {
        FileOutputStream fos = new FileOutputStream(filename);
        workbook.write(fos);
        fos.close();
        return fos;
    }

    public ByteArrayOutputStream exportToExcel(String sheetName) throws Exception {
        // Create workbook and sheet
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);

        // Create header style
        CellStyle headerStyle = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        headerStyle.setFont(font);
        headerStyle.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        // Create headers
        List<String> headers = Arrays.asList("Channel", "Code", "Amount", "Price", "Received Amount", "Package Fee", "Revenue");
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers.get(i));
            cell.setCellStyle(headerStyle);
        }

        // Sample data
        List<Revenues> revenues = revenueRepository.findAll();

        List<Product> products = productService.findAll();
        Map<String, Product> productMap = products.stream()
                .collect(Collectors.toMap(Product::getId, Function.identity()));

        List<List<String>> data = revenues.stream().map(revenue -> {
            return Arrays.asList(
                    revenue.getChannel(),
                    productMap.get(revenue.getProductId()).getCode(),
                    String.valueOf(revenue.getAmount()),
                    String.valueOf(revenue.getPrice()),
                    String.valueOf(revenue.getReceivedAmount()),
                    String.valueOf(revenue.getPackageFee()),
                    String.valueOf(revenue.getRevenue())
            );
        }).toList();

        // Fill data
        for (int i = 0; i < data.size(); i++) {
            Row row = sheet.createRow(i + 1);
            List<String> rowData = data.get(i);
            for (int j = 0; j < rowData.size(); j++) {
                row.createCell(j).setCellValue(rowData.get(j));
            }
        }

        // Auto-size columns
        for (int i = 0; i < headers.size(); i++) {
            sheet.autoSizeColumn(i);
        }

        // Write to output stream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();

        return outputStream;
    }
}

package org.beli.services;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.beli.Utils;
import org.beli.dtos.req.ProductRequestDto;
import org.beli.dtos.req.UpdateProductRequestDto;
import org.beli.dtos.res.ProductResponseDto;
import org.beli.dtos.res.RemainningProductResponseDto;
import org.beli.entities.Product;
import org.beli.repositories.FeeRepository;
import org.beli.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductService extends BaseService<Product, String> {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PhaseService phaseService;

    public ProductService(ProductRepository productRepository) {
        super(productRepository);
    }

    public Product mappingToCreateEntity(ProductRequestDto dto) {
        var product = new Product();
        product.setCode(dto.code());
        product.setProductType(dto.productType());
        product.setFormType(dto.formType());
        product.setPhaseCode(dto.phaseCode());
//        product.setEntryDate(dto.entryDate());
        product.setAmount(dto.amount());
        product.setTransferFee(dto.transferFee());
        product.setRemainingAmount(dto.amount());
        product.setCreatedAt(System.currentTimeMillis());
        product.setUpdatedAt(System.currentTimeMillis());
        product.setSize(dto.size());
        product.setColor(dto.color());
        product.setPrice(dto.price());
        return product;
    }


    public Product mappingToUpdateEntity(UpdateProductRequestDto dto) {
        var productOpt = productRepository.findById(dto.id());
        if (productOpt.isEmpty()) {
            throw new RuntimeException("Product not found");
        }
        var product = productOpt.get();
        product.setCode(dto.code());
        product.setProductType(dto.productType());
        product.setFormType(dto.formType());
        product.setPhaseCode(dto.phaseCode());
        product.setAmount(dto.amount());
        product.setTransferFee(dto.transferFee());
        product.setCreatedAt(System.currentTimeMillis());
        product.setUpdatedAt(System.currentTimeMillis());
        product.setSize(dto.size());
        product.setColor(dto.color());
        product.setPrice(dto.price());
        return product;
    }


    public ProductResponseDto mappingToProductResponse(Product dto) {
        var phaseCode = dto.getPhaseCode();

        var phaseCodeOpt = phaseService.findByPhaseCode(phaseCode);
        if (phaseCodeOpt.isPresent()) {
            var phase = phaseCodeOpt.get();
            return new ProductResponseDto(
                    dto.getId(),
                    dto.getCode(),
                    phase.getPhaseCode(),
                    phase.getPhaseName(),
                    dto.getProductType(),
                    dto.getFormType(),
                    dto.getSize(),
                    dto.getColor(),
                    dto.getAmount(),
                    dto.getRemainingAmount(),
                    dto.getPrice(),
                    dto.getTransferFee()

            );
        } else {
            throw new RuntimeException("Phase not found");
        }
    }

    public List<ProductResponseDto> findAllProductByPhaseCode(String phaseCode) {
        var productOpt = productRepository.findByPhaseCode(phaseCode);

        if (productOpt.isPresent()) {
            return productOpt.get().stream()
                    .map(this::mappingToProductResponse)
                    .toList();
        } else {
            throw new RuntimeException("Product not found");
        }
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
        List<String> headers = Arrays.asList("Code", "Phase", "Amount", "Price", "Transfer Fee", "Remaining Amount");
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.size(); i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers.get(i));
            cell.setCellStyle(headerStyle);
        }

        // Sample data

        List<Product> products = productRepository.findAll();

        List<List<String>> data = products.stream().map(product -> {
            return Arrays.asList(
                    product.getCode(),
                    product.getPhaseCode(),
                    String.valueOf(product.getAmount()),
                    String.valueOf(product.getPrice()),
                    String.valueOf(product.getTransferFee()),
                    String.valueOf(product.getRemainingAmount())
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

    public List<RemainningProductResponseDto> getAllProductsOfAllPhases() {
        var products = productRepository.findAll();
        if (products.isEmpty()) {
            throw new RuntimeException("No products found");
        }
        products.forEach(product -> {
            System.out.println(product.getCode());
        });

        Map<String, Long> productListRemaninningAmount = products.stream()
                .collect(Collectors.groupingBy(Product::getCode,
                        Collectors.summingLong(Product::getRemainingAmount)));

        // products list unique by code
        List<Product> productsUnique = products.stream()
                .filter(Utils.distinctByKey(Product::getCode)).toList();

        List<RemainningProductResponseDto> remainningProductResponseDtos = productsUnique.stream()
                .map(product -> {
                    return new RemainningProductResponseDto(
                            product.getCode(),
                            productListRemaninningAmount.get(product.getCode())
                    );
                }).toList();

        return remainningProductResponseDtos;
    }
}

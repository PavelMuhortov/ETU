package com.example.market.tools;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import static java.util.concurrent.CompletableFuture.supplyAsync;

public class JasperExportHelper<M> {

    public static final String REPORT_PATH = "report.jrxml";

    public CompletableFuture<Void> export(String file, Supplier<Collection<M>> dataSupplier) {
        HashMap<String, Object> params = new HashMap<>();
        params.put("DATE", LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));
        var loadData = supplyAsync(() -> loadData(dataSupplier));
        var loadReport = supplyAsync(() -> loadReport(REPORT_PATH));
        var fillData = loadReport.thenCombineAsync(loadData, (report, data) -> fillData(report, params, data));
        return fillData.thenAcceptAsync(print -> writeFile(print, file));
    }

    private JRBeanCollectionDataSource loadData(Supplier<Collection<M>> dataSupplier) {
        return new JRBeanCollectionDataSource(dataSupplier.get());
    }

    private JasperReport loadReport(String reportPath) {
        return $(() -> JasperCompileManager.compileReport(JRLoader.getLocationInputStream(reportPath)));
    }

    private JasperPrint fillData(JasperReport jasperReport, Map<String, Object> params, JRDataSource dataSource) {
        return $(() -> JasperFillManager.fillReport(jasperReport, params, dataSource));
    }

    private void writeFile(JasperPrint print, String file) {
        if (file.toLowerCase().endsWith("pdf")) {
            $(() -> JasperExportManager.exportReportToPdfFile(print, file));
        } else if (file.toLowerCase().endsWith("html")) {
            $(() -> JasperExportManager.exportReportToHtmlFile(print, file));
        } else {
            throw new IllegalArgumentException("Unsupported report type");
        }
    }

    private void $(Cr cr) {
        try {
            cr.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private <T> T $(Cs<T> cs) {
        try {
            return cs.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    interface Cs<T> {
        T get() throws Exception;
    }

    interface Cr {
        void run() throws Exception;
    }
}

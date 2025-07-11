package com.amay.scu;

import javafx.fxml.FXMLLoader;
import views.Path;

import java.io.InputStream;
import java.util.Objects;

public class ViewFactory {

    public static FXMLLoader getStationMapView() {
        FXMLLoader fxmlLoader= new FXMLLoader(ViewFactory.class.getResource
                ("station_map/station-map-view.fxml"));
        return fxmlLoader;

    }

    public static FXMLLoader getTOMView() {
        FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource
                (Path.TOM_VIEW));
        return fxmlLoader;
    }


    public static FXMLLoader getEFOView() {
        FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource
                (Path.EFO_VIEW));
        return fxmlLoader;

    }

    public static FXMLLoader getTVMView() {
        FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource
                (Path.TVM_VIEW));
        return fxmlLoader;
    }

    public static FXMLLoader getAGView() {
        FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource
                (Path.AG_VIEW));
        return fxmlLoader;
    }
    public static FXMLLoader getTomCommand() {
        FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource
                (Path.TOM_COMMAND));
        return fxmlLoader;
    }

    public static String loadStylesheet() {
        return ViewFactory.class.getResource(Path.POPUP_CSS).toExternalForm();
    }

    public static FXMLLoader getReport() {
        FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource
                (Path.REPORT));
        return fxmlLoader;

    }

    public static FXMLLoader getMonitorRightView() {
        FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource
                (Path.MONITOR_RIGHT_VIEW));
        return fxmlLoader;
    }

    public static FXMLLoader getCenter() {
        FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource
                (Path.CENTER));
        return fxmlLoader;
    }

    public static FXMLLoader getAlert() {
        FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource
                (Path.ALERT));
        return fxmlLoader;
    }

    public static FXMLLoader getTomWidgets() {
        FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource
                (Path.TOM_WIDGETS));
        return fxmlLoader;
    }


    public static FXMLLoader getAGWidgets() {
        FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource
                (Path.AG_WIDGETS));
        return fxmlLoader;
    }

    public static FXMLLoader getAGCommand() {
        FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource
                (Path.AG_COMMAND));
        return fxmlLoader;
    }

    public static FXMLLoader getLogin() {
        return new FXMLLoader(ViewFactory.class.getResource(Path.LOGIN));
    }

    public static FXMLLoader getLogout() {
        return new FXMLLoader(ViewFactory.class.getResource(Path.LOGOUT));
    }

    public static FXMLLoader getPermission() {
        return new FXMLLoader(ViewFactory.class.getResource(Path.PERMISSION));
    }

    public static FXMLLoader getTRView() {
        FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource
                (Path.TR_VIEW));
        return fxmlLoader;
    }

    public static FXMLLoader  getReportNavigationView() {
        FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource
                (Path.REPORT_LEFT_VIEW));
        return fxmlLoader;
    }

    public static FXMLLoader  getRevenueReport() {
        FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource
                (Path.REVENUE_REPORT));
        return fxmlLoader;
    }

    public static FXMLLoader getTRWidgets() {
        return new FXMLLoader((ViewFactory.class.getResource(Path.TR_WIDGETS)));
    }
    public static FXMLLoader getTVMWidgets() {
        return new FXMLLoader((ViewFactory.class.getResource(Path.TVM_WIDGETS)));
    }

    public static FXMLLoader getFilterView() {
        FXMLLoader fxmlLoader = new FXMLLoader(ViewFactory.class.getResource
                (Path.FILTER_VIEW));
        return fxmlLoader;
    }

    public static InputStream loadRevenueReport() {
        // Usage in JasperHelper
        InputStream reportStream = Objects.requireNonNull(
                ViewFactory.class.getResourceAsStream(Path.REVENUE_REPORT_JRXML),
                "JRXML file not found: " + Path.REVENUE_REPORT_JRXML
        );
        return reportStream;
    }

    public static InputStream loadShiftReport() {
        // Usage in JasperHelper
        return Objects.requireNonNull(
                ViewFactory.class.getResourceAsStream(Path.SHIFT_REPORT_JRXML),
                "JRXML file not found: " + Path.SHIFT_REPORT_JRXML
        );
    }

    public static InputStream loadRidershipDayReport() {
        // Usage in JasperHelper
        return Objects.requireNonNull(
                ViewFactory.class.getResourceAsStream(Path.RIDERSHIP_DAY_REPORT_JRXML),
                "JRXML file not found: " + Path.RIDERSHIP_DAY_REPORT_JRXML
        );
    }
    public static InputStream loadRidershipHOURReport() {
        // Usage in JasperHelper
        return Objects.requireNonNull(
                ViewFactory.class.getResourceAsStream(Path.RIDERSHIP_HOUR_REPORT_JRXML),
                "JRXML file not found: " + Path.RIDERSHIP_HOUR_REPORT_JRXML
        );
    }
}

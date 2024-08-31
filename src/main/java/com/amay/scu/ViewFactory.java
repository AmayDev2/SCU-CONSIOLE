package com.amay.scu;

import javafx.fxml.FXMLLoader;
import views.Path;

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
}

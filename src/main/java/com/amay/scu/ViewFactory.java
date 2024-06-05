package com.amay.scu;

import javafx.fxml.FXMLLoader;
import views2.Path;

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

}

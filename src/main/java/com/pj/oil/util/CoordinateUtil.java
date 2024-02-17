package com.pj.oil.util;

import org.locationtech.proj4j.*;
import org.springframework.stereotype.Component;

@Component
public class CoordinateUtil {
    public ProjCoordinate convertKATECToWGS84(double x, double y) {
        CRSFactory crsFactory = new CRSFactory();
        CoordinateReferenceSystem KATEC = crsFactory.createFromParameters(
                "KATEC",
                "+proj=tmerc +lat_0=38 +lon_0=128 +k=0.9999 +x_0=400000 +y_0=600000 +ellps=bessel +units=m +no_defs +towgs84=-115.80,474.99,674.11,1.16,-2.31,-1.63,6.43"
        );
        CoordinateReferenceSystem WGS84 = crsFactory.createFromName("epsg:4326");

        CoordinateTransformFactory ctFactory = new CoordinateTransformFactory();
        CoordinateTransform katecToWgs = ctFactory.createTransform(KATEC, WGS84);

        ProjCoordinate result = new ProjCoordinate();
        katecToWgs.transform(new ProjCoordinate(x, y), result);
        System.out.println(result);
        return result;
    }
}

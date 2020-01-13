package com.crossent.microservice.plvback.configuration;

import com.crossent.microservice.plvback.dto.*;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class TestConfiguration {
    public static final String ELLIPSOID_NAME = "wgs84";
    public static final Double LAT1 = 37.541165;
    public static final Double LNG1 = 126.891248;
    public static final Double LAT2 = 37.539310;
    public static final Double LNG2 = 126.893093;
    public static final List<BuildingPnu> BUILDING_PNU_LISTS = new ArrayList<>(Arrays.asList(
            new BuildingPnu("한신","105","1156012900100760000",2019)
    )) ;
    public static final String PNU = "1156012900100760000";

    public static final List<BuildingValue> BUILDING_VALUE_LISTS = new ArrayList<>(Arrays.asList(
            new BuildingValue("한신","105",2019,
            37.54012189780912, 126.89287699425235,
            Long.parseLong("459400000"), Long.parseLong("36752000000"), Long.parseLong("5412985"))
    ));

    public static final List<UnitValue> BUILDING_UNIT_VALUE_LISTS = new ArrayList<>(Arrays.asList(
            new UnitValue("한신","105",2019,Long.parseLong("5412985"))
    ));
    public static final String OPEN_API_RESPONSE = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>\n" +
            "<wfs:FeatureCollection xmlns:xs=\"http://www.w3.org/2001/XMLSchema\" xmlns:wfs=\"http://www.opengis.net/wfs\" xmlns:gml=\"http://www.opengis.net/gml\" xmlns:NSDI=\"http://10.1.17.66:6080/arcgis/services/opendb/EiosSpceServiceWFS2/MapServer/WFSServer\" xmlns:xlink=\"http://www.w3.org/1999/xlink\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" timeStamp=\"2019-11-28T08:14:03Z\" numberOfFeatures=\"unknown\" xsi:schemaLocation=\"http://www.opengis.net/wfs http://schemas.opengis.net/wfs/1.1.0/wfs.xsd http://www.opengis.net/gml http://schemas.opengis.net/gml/3.1.1/base/gml.xsd http://10.1.17.66:6080/arcgis/services/opendb/EiosSpceServiceWFS2/MapServer/WFSServer http://10.1.17.66:6080/arcgis/services/opendb/EiosSpceServiceWFS2/MapServer/WFSServer?service=wfs%26version=1.1.0%26request=DescribeFeatureType\">\n" +
            "<gml:boundedBy>\n" +
            "  <gml:Envelope srsName=\"urn:ogc:def:crs:EPSG::5174\">\n" +
            "    <gml:lowerCorner>190307.0050999997 448582.0379000008</gml:lowerCorner>\n" +
            "    <gml:upperCorner>190488.3548999997 448769.7243000008</gml:upperCorner>\n" +
            "  </gml:Envelope>\n" +
            "</gml:boundedBy>\n" +
            "  <gml:featureMember>\n" +
            "    <NSDI:F163 gml:id=\"F163.3736865\">\n" +
            "      <NSDI:SHAPE><gml:Point><gml:pos>190363.2044000002 448675.5591000002</gml:pos></gml:Point></NSDI:SHAPE>\n" +
            "      <NSDI:X_CRDNT>190363.2044</NSDI:X_CRDNT>\n" +
            "      <NSDI:Y_CRDNT>448675.5591</NSDI:Y_CRDNT>\n" +
            "      <NSDI:PNU>1156012900100760000</NSDI:PNU>\n" +
            "      <NSDI:LD_CPSG_CODE>11560</NSDI:LD_CPSG_CODE>\n" +
            "      <NSDI:LD_EMD_LI_CODE>12900</NSDI:LD_EMD_LI_CODE>\n" +
            "      <NSDI:REGSTR_SE_CODE>1</NSDI:REGSTR_SE_CODE>\n" +
            "      <NSDI:MNNM>0076</NSDI:MNNM>\n" +
            "      <NSDI:SLNO>0000</NSDI:SLNO>\n" +
            "      <NSDI:STDR_YEAR>2019</NSDI:STDR_YEAR>\n" +
            "      <NSDI:STDR_MT>01</NSDI:STDR_MT>\n" +
            "      <NSDI:APHUS_CODE>1044</NSDI:APHUS_CODE>\n" +
            "      <NSDI:APHUS_SE_CODE>1</NSDI:APHUS_SE_CODE>\n" +
            "      <NSDI:APHUS_NM>한신</NSDI:APHUS_NM>\n" +
            "      <NSDI:DONG_NM>105</NSDI:DONG_NM>\n" +
            "      <NSDI:AVRG_PBLNTF_PC>459179487</NSDI:AVRG_PBLNTF_PC>\n" +
            "      <NSDI:ALL_PBLNTF_PC>35816000000</NSDI:ALL_PBLNTF_PC>\n" +
            "      <NSDI:UNIT_AR_PC>5410386</NSDI:UNIT_AR_PC>\n" +
            "      <NSDI:CALC_APHUS_HO_CO>78</NSDI:CALC_APHUS_HO_CO>\n" +
            "      <NSDI:PSTYR_1_AVRG_PBLNTF_PC>376410256</NSDI:PSTYR_1_AVRG_PBLNTF_PC>\n" +
            "      <NSDI:PSTYR_2_AVRG_PBLNTF_PC>341333333</NSDI:PSTYR_2_AVRG_PBLNTF_PC>\n" +
            "      <NSDI:PSTYR_3_AVRG_PBLNTF_PC>316128205</NSDI:PSTYR_3_AVRG_PBLNTF_PC>\n" +
            "      <NSDI:PSTYR_4_AVRG_PBLNTF_PC>295948718</NSDI:PSTYR_4_AVRG_PBLNTF_PC>\n" +
            "      <NSDI:FRST_REGIST_DT>2019-10-13T03:00:06</NSDI:FRST_REGIST_DT>\n" +
            "    </NSDI:F163>\n" +
            "  </gml:featureMember>\n" +
            "</wfs:FeatureCollection>";

    public static final ArrayList<OpenApiResponseData> OPEN_API_REPONSE_DATA_LIST = new ArrayList<OpenApiResponseData>(Arrays.asList(
            new OpenApiResponseData("한신", "105", 2019, "1156012900100760000",
                    190363.2044, 448675.5591, Long.parseLong("459179487"), Long.parseLong("35816000000"),
                    Long.parseLong("5410386"))));


    public static final String BBOX = "190481.9082653816,448567.23642489954,190317.14459999872,448773.3124403328,EPSG:5174";
    public static final String EPSG_NAME = "epsg5174";
    public static final Double X = 190363.2044;
    public static final Double Y = 448675.5591;

    public static final ThreeDimCrs THREEDIM_LOCATION = new ThreeDimCrs(ELLIPSOID_NAME, 37.53983091323218, 126.89316520921383);
    public static final TwoDimCrs TWODIM_LOCATION = new TwoDimCrs(EPSG_NAME, 190363.2044,448675.5591);

}

import java.util.HashMap;
import java.util.Map;

/**
 * This class provides all code necessary to take a query box and produce
 * a query result. The getMapRaster method must return a Map containing all
 * seven of the required fields, otherwise the front end code will probably
 * not draw the output correctly.
 */
public class Rasterer {

    private static final double lonBase = 0.087890625 / 256;
    private static final double latBase = 0.069393113716796 / 256;
    private static final double UL_LON = -122.2998046875, UL_LAT = 37.892195547244356,
            LR_LAT = 37.82280243352756, LR_LON = -122.2119140625;

    public Rasterer() {
        // YOUR CODE HERE
    }

    /**
     * Takes a user query and finds the grid of images that best matches the query. These
     * images will be combined into one big image (rastered) by the front end. <br>
     * <p>
     * The grid of images must obey the following properties, where image in the
     * is referred to as a "tile".
     * <ul>
     *     <li>The tiles collected must cover the most longitudinal distance per pixel
     *     (LonDPP) possible, while still covering less than or equal to the amount of
     *     longitudinal distance per pixel in the query box for the user viewport size. </li>
     *     <li>Contains all tiles that intersect the query bounding box that fulfill the
     *     above condition.</li>
     *     <li>The tiles must be arranged in-order to reconstruct the full image.</li>
     * </ul>
     *
     * @param params Map of the HTTP GET request's query parameters - the query box and
     *               the user viewport width and height.
     * @return A map of results for the front end as specified: <br>
     * "render_grid"   : String[][], the files to display. <br>
     * "raster_ul_lon" : Number, the bounding upper left longitude of the rastered image. <br>
     * "raster_ul_lat" : Number, the bounding upper left latitude of the rastered image. <br>
     * "raster_lr_lon" : Number, the bounding lower right longitude of the rastered image. <br>
     * "raster_lr_lat" : Number, the bounding lower right latitude of the rastered image. <br>
     * "depth"         : Number, the depth of the nodes of the rastered image <br>
     * "query_success" : Boolean, whether the query was able to successfully complete; don't
     * forget to set this to true on success! <br>
     */
    public Map<String, Object> getMapRaster(Map<String, Double> params) {
        System.out.println(params);
        double ullon = params.get("ullon");
        double ullat = params.get("ullat");
        double lrlon = params.get("lrlon");
        double lrlat = params.get("lrlat");
        double width = params.get("w");
        boolean flag = true;
        if (lrlon < UL_LON || lrlat > UL_LAT || ullon > LR_LON || ullat < LR_LAT || ullon > lrlon || lrlat > ullat)
            flag = false;
        double lonDPP = getDPP(lrlon, ullon, width);
        int depth = getDepth(lonDPP);
        int[][] range = getRange(depth, ullon, ullat, lrlon, lrlat);
        ullon = UL_LON + range[0][0] * 256 * getDefaultLonDPP(depth);
        ullat = UL_LAT - range[1][0] * 256 * getDefaultLatDPP(depth);
        lrlon = UL_LON + (range[0][1] + 1) * 256 * getDefaultLonDPP(depth);
        lrlat = UL_LAT - (range[1][1] + 1) * 256 * getDefaultLatDPP(depth);

        String[][] renderGrid = new String[range[1][1] - range[1][0] + 1][range[0][1] - range[0][0] + 1];
        for (int i = 0; i < renderGrid.length; i += 1) {
            for (int j = 0; j < renderGrid[0].length; j += 1) {
                renderGrid[i][j] = "d" + depth + "_x" + (range[0][0] + j) + "_y" + (range[1][0] + i) + ".png";
            }
        }
        Map<String, Object> results = new HashMap<>();
        results.put("raster_ul_lon", ullon);
        results.put("raster_ul_lat", ullat);
        results.put("raster_lr_lon", lrlon);
        results.put("raster_lr_lat", lrlat);
        results.put("query_success", flag);
        results.put("depth", depth);
        results.put("render_grid", renderGrid);
        return results;
    }

    private double getDPP(double lr, double ul, double range) {
        return Math.abs((lr - ul) / range);
    }

    private double getDefaultLonDPP(int depth) {
        return lonBase / Math.pow(2, depth);
    }

    private double getDefaultLatDPP(int depth) {
        return latBase / Math.pow(2, depth);
    }

    private int getDepth(double lonDPP) {
        double temp = lonBase;
        int count = 0;
        while (temp > lonDPP) {
            temp /= 2;
            count++;
        }
        if (count > 7) {
            return 7;
        }
        return count;
    }

    /**
     private boolean isOverLap(int depth, int x, int y, double xpos, double ypos) {
     double lonDPP = getDefaultLonDPP(depth);
     double latDPP = getDefaultLatDPP(depth);
     double ulLon = UL_LON + x * 256 * lonDPP;
     double ulLat = UL_LAT - y * 256 * latDPP;
     double lrLon = ulLon + 256 * lonDPP;
     double lrLat = ulLat - 256 * latDPP;
     return (xpos >= ulLon && xpos <= lrLon) && (ypos >= lrLat && ypos < ulLat);
     }*/

    /**
     * Get range in a 2D array below:
     * <p>
     * Xul,Xlr
     * Yul,Ylr
     */
    private int[][] getRange(int depth, double ulLon, double ulLat, double lrLon, double lrLat) {
        int[][] range = new int[2][2];
        int xul, xlr, yul, ylr;
        double lonDPP = getDefaultLonDPP(depth);
        double latDPP = getDefaultLatDPP(depth);
        int maxValue=(int)(Math.pow(2,depth))-1;
        xul = (int) ((ulLon - UL_LON) / (lonDPP * 256));
        xlr = (int) ((lrLon - UL_LON) / (lonDPP * 256));
        yul = (int) ((UL_LAT - ulLat) / (latDPP * 256));
        ylr = (int) ((UL_LAT - lrLat) / (latDPP * 256));
        range[0][0] = xul;
        range[0][1] = xlr;
        range[1][0] = yul;
        range[1][1] = ylr;
        for (int i=0;i<2;i+=1){
            for (int j=0;j<2;j+=1){
                if (range[i][j]<0){
                    range[i][j]=0;
                }
                else if (range[i][j]>maxValue){
                    range[i][j]=maxValue;
                }
            }
        }
        return range;
    }
}

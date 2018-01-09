package yalantis.com.sidemenu.sample.com.VO;

/**
 * Created by togla on 2018-01-09.
 */

public class Al_infoVO {

    private String product_no;
    private String barcode;
    private String product_name;
    private String alcohol;
    private String price;
    private String volume;
    private String img_url;

    public Al_infoVO(String product_no, String barcode, String product_name, String alcohol, String price,
                     String volume, String img_url) {
        super();
        this.product_no = product_no;
        this.barcode = barcode;
        this.product_name = product_name;
        this.alcohol = alcohol;
        this.price = price;
        this.volume = volume;
        this.img_url = img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public void setProduct_no(String product_no) {
        this.product_no = product_no;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public void setAlcohol(String alcohol) {
        this.alcohol = alcohol;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }


    public String getImg_url(String img_url) {
        return img_url;
    }


    public String getProduct_no() {
        return product_no;
    }

    public String getBarcode() {
        return barcode;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getAlcohol() {
        return alcohol;
    }

    public String getPrice() {
        return price;
    }

    public String getVolume() {
        return volume;
    }

}

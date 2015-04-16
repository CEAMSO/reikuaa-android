package py.gov.contrataciones.reikuaa.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pagination {

    @Expose
    private String order;
    @SerializedName("total_items")
    @Expose
    private Integer totalItems;
    @SerializedName("total_pages")
    @Expose
    private Integer totalPages;
    @SerializedName("items_per_page")
    @Expose
    private Integer itemsPerPage;
    @SerializedName("current_page")
    @Expose
    private Integer currentPage;

    /**
     *
     * @return
     * The order
     */
    public String getOrder() {
        return order;
    }

    /**
     *
     * @param order
     * The order
     */
    public void setOrder(String order) {
        this.order = order;
    }

    /**
     *
     * @return
     * The totalItems
     */
    public Integer getTotalItems() {
        return totalItems;
    }

    /**
     *
     * @param totalItems
     * The total_items
     */
    public void setTotalItems(Integer totalItems) {
        this.totalItems = totalItems;
    }

    /**
     *
     * @return
     * The totalPages
     */
    public Integer getTotalPages() {
        return totalPages;
    }

    /**
     *
     * @param totalPages
     * The total_pages
     */
    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    /**
     *
     * @return
     * The itemsPerPage
     */
    public Integer getItemsPerPage() {
        return itemsPerPage;
    }

    /**
     *
     * @param itemsPerPage
     * The items_per_page
     */
    public void setItemsPerPage(Integer itemsPerPage) {
        this.itemsPerPage = itemsPerPage;
    }

    /**
     *
     * @return
     * The currentPage
     */
    public Integer getCurrentPage() {
        return currentPage;
    }

    /**
     *
     * @param currentPage
     * The current_page
     */
    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

}
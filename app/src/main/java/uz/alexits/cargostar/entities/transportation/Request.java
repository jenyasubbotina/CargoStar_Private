package uz.alexits.cargostar.entities.transportation;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "request")
public class Request {
    @Expose
    @SerializedName("id")
    @PrimaryKey
    @ColumnInfo(name = "id")
    private long id;

    @Expose
    @SerializedName("country_id")
    @ColumnInfo(name = "sender_country_id")
    private long senderCountryId;

    @Expose
    @SerializedName("city_name")
    @ColumnInfo(name = "sender_city_name")
    private String senderCityName;

    @Expose
    @SerializedName("user_id")
    @ColumnInfo(name = "user_id")
    private long userId;

    @Expose
    @SerializedName("client_id")
    @ColumnInfo(name = "client_id")
    private long clientId;

    @Expose
    @SerializedName("firstname")
    @ColumnInfo(name = "first_name")
    private String senderFirstName;

    @Expose
    @SerializedName("middlename")
    @ColumnInfo(name = "middle_name")
    private String senderMiddleName;

    @Expose
    @SerializedName("lastname")
    @ColumnInfo(name = "last_name")
    private String senderLastName;

    @Expose
    @SerializedName("email")
    @ColumnInfo(name = "email")
    private String senderEmail;

    @Expose
    @SerializedName("telephone")
    @ColumnInfo(name = "phone")
    private String senderPhone;

    @Expose
    @SerializedName("adres")
    @ColumnInfo(name = "address")
    private String senderAddress;

    @Expose
    @SerializedName("country_to")
    @ColumnInfo(name = "recipient_country_id")
    private long recipientCountryId;

    @Expose
    @SerializedName("city_to_name")
    @ColumnInfo(name = "recipient_city_name")
    private String recipientCityName;

    @Expose
    @SerializedName("employee_id")
    @ColumnInfo(name = "courier_id")
    private long courierId;

    @Expose
    @SerializedName("provider_id")
    @ColumnInfo(name = "provider_id")
    private long providerId;

    @Expose
    @SerializedName("invoice_id")
    @ColumnInfo(name = "invoice_id")
    private long invoiceId;

    @Expose
    @SerializedName("delivery_type")
    @ColumnInfo(name = "delivery_type")
    private int deliveryType;

    @Expose
    @SerializedName("comment")
    @ColumnInfo(name = "comment")
    private String comment;

    @ColumnInfo(name = "is_new")
    private boolean isNew;

    @Expose
    @SerializedName("senderCity")
    @ColumnInfo(name = "sender_city")
    private String senderCity;

    @Expose
    @SerializedName("recipientCity")
    @ColumnInfo(name = "recipient_city")
    private String recipientCity;

    @Expose
    @SerializedName("count")
    @ColumnInfo(name = "consignment_quantity")
    private int consignmentQuantity;

    @Expose
    @SerializedName("payment_status")
    @ColumnInfo(name = "payment_status")
    private String paymentStatus;

//    @Ignore
//    public Request() {
//        this.isNew = true;
//    }

    public Request(final long id,
                   final String senderFirstName,
                   final String senderMiddleName,
                   final String senderLastName,
                   final String senderEmail,
                   final String senderPhone,
                   final String senderAddress,
                   final long senderCountryId,
                   final String senderCityName,
                   final long recipientCountryId,
                   final String recipientCityName,
                   final String comment,
                   final long userId,
                   final long clientId,
                   final long courierId,
                   final long providerId,
                   final long invoiceId,
                   final String senderCity,
                   final String recipientCity,
                   final int consignmentQuantity,
                   final String paymentStatus,
                   final int deliveryType) {
        this.id = id;
        this.senderFirstName = senderFirstName;
        this.senderMiddleName = senderMiddleName;
        this.senderLastName = senderLastName;
        this.senderEmail = senderEmail;
        this.senderPhone = senderPhone;
        this.senderAddress = senderAddress;
        this.senderCountryId = senderCountryId;
        this.senderCityName = senderCityName;
        this.recipientCountryId = recipientCountryId;
        this.recipientCityName = recipientCityName;
        this.comment = comment;
        this.userId = userId;
        this.clientId = clientId;
        this.courierId = courierId;
        this.providerId = providerId;
        this.invoiceId = invoiceId;
        this.isNew = true;
        this.senderCity = senderCity;
        this.recipientCity = recipientCity;
        this.consignmentQuantity = consignmentQuantity;
        this.paymentStatus = paymentStatus;
        this.deliveryType = deliveryType;
    }

    public int getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(int deliveryType) {
        this.deliveryType = deliveryType;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSenderFirstName() {
        return senderFirstName;
    }

    public void setSenderFirstName(String senderFirstName) {
        this.senderFirstName = senderFirstName;
    }

    public String getSenderMiddleName() {
        return senderMiddleName;
    }

    public void setSenderMiddleName(String senderMiddleName) {
        this.senderMiddleName = senderMiddleName;
    }

    public String getSenderLastName() {
        return senderLastName;
    }

    public void setSenderLastName(String senderLastName) {
        this.senderLastName = senderLastName;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public void setSenderEmail(String senderEmail) {
        this.senderEmail = senderEmail;
    }

    public String getSenderPhone() {
        return senderPhone;
    }

    public void setSenderPhone(String senderPhone) {
        this.senderPhone = senderPhone;
    }

    public String getSenderAddress() {
        return senderAddress;
    }

    public void setSenderAddress(String senderAddress) {
        this.senderAddress = senderAddress;
    }

    public long getSenderCountryId() {
        return senderCountryId;
    }

    public void setSenderCountryId(long senderCountryId) {
        this.senderCountryId = senderCountryId;
    }

    public long getRecipientCountryId() {
        return recipientCountryId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getClientId() {
        return clientId;
    }

    public void setClientId(long clientId) {
        this.clientId = clientId;
    }

    public long getCourierId() {
        return courierId;
    }

    public void setCourierId(long courierId) {
        this.courierId = courierId;
    }

    public long getProviderId() {
        return providerId;
    }

    public void setProviderId(final long providerId) {
        this.providerId = providerId;
    }

    public long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public void setRecipientCountryId(final long recipientCountryId) {
        this.recipientCountryId = recipientCountryId;
    }

    public String getSenderCity() {
        return senderCity;
    }

    public void setSenderCity(String senderCity) {
        this.senderCity = senderCity;
    }

    public String getRecipientCity() {
        return recipientCity;
    }

    public void setRecipientCity(String recipientCity) {
        this.recipientCity = recipientCity;
    }

    public int getConsignmentQuantity() {
        return consignmentQuantity;
    }

    public void setConsignmentQuantity(int consignmentQuantity) {
        this.consignmentQuantity = consignmentQuantity;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getRecipientCityName() {
        return recipientCityName;
    }

    public String getSenderCityName() {
        return senderCityName;
    }

    @NonNull
    @Override
    public String toString() {
        return "Request{" +
                "id=" + id +
                ", senderCountryId=" + senderCountryId +
                ", senderCityName=" + senderCityName +
                ", userId=" + userId +
                ", clientId=" + clientId +
                ", courierId=" + courierId +
                ", providerId=" + providerId +
                ", invoiceId=" + invoiceId +
                ", senderFirstName='" + senderFirstName + '\'' +
                ", senderMiddleName='" + senderMiddleName + '\'' +
                ", senderLastName='" + senderLastName + '\'' +
                ", senderEmail='" + senderEmail + '\'' +
                ", senderPhone='" + senderPhone + '\'' +
                ", senderAddress='" + senderAddress + '\'' +
                ", recipientCountryId=" + recipientCountryId +
                ", recipientCityName=" + recipientCityName +
                ", deliveryType=" + deliveryType +
                ", comment='" + comment + '\'' +
                ", isNew=" + isNew +
                ", senderCity='" + senderCity + '\'' +
                ", recipientCity='" + recipientCity + '\'' +
                ", consignmentQuantity=" + consignmentQuantity +
                ", paymentStatus='" + paymentStatus + '\'' +
                '}';
    }

    public void setSenderCityName(String senderCityName) {
        this.senderCityName = senderCityName;
    }

    public void setRecipientCityName(String recipientCityName) {
        this.recipientCityName = recipientCityName;
    }
}

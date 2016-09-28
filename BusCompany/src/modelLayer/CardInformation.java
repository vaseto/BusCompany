package modelLayer;

import java.util.Date;
import java.util.List;

public class CardInformation {

    // instance variables
    private String bankName,cardNumber;
    private int ccv;
    private Date expDate; 
    private List<Payment> payments;

    // a parameterized costructor

    public CardInformation(String bankName, String cardNumber, int ccv, Date expDate) {
        this.bankName = bankName;
        this.cardNumber = cardNumber;
        this.ccv = ccv;
        this.expDate = expDate;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public void setCcv(int ccv) {
        this.ccv = ccv;
    }
   
    // get methods
    public String getBankName() {
        return bankName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public int getCcv() {
        return ccv;
    }

    public Date getExpDate() {
        return expDate;
    }

    // set methods
    public void setBankName(String bankName) {
        this.bankName = bankName;
    }


    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    @Override
    public String toString() {
        return "CardInformation{" + "bankName=" + bankName + ", cardNumber=" + cardNumber + ", ccv=" + ccv + ", expDate=" + expDate + '}';
    }
    
}

package ctrlLayer;

import java.sql.SQLException;
import java.util.List;
import dbLayer.DbCardInfo;
import java.util.Date;
import modelLayer.CardInformation;

public class CardInfoCtrl {

    private DbCardInfo dbCardInfo;

    public CardInfoCtrl() {
        dbCardInfo = new DbCardInfo();
    }

    /**
     * Deletes a card
     * @param card to be deleted
     * @return 1 if successful
     * @throws SQLException 
     */
    public int deleteCard(CardInformation card) throws SQLException {
        return dbCardInfo.delete(card);
    }

    /**
     * Updates a card
     * @param cardNumberBeforeUpdate
     * @param card to be updated
     * @return 1 if successful
     * @throws SQLException 
     */
    public int updateCard(String cardNumberBeforeUpdate,CardInformation card) throws SQLException {
        return dbCardInfo.update(card,cardNumberBeforeUpdate);
    }

    /**
     * Creates and inserts a new card
     * @param cardno
     * @param bankName
     * @param ccv
     * @param expDate
     * @return
     * @throws SQLException 
     */
    public int createCard(String cardno, String bankName, int ccv, java.util.Date expDate) throws SQLException {
        return dbCardInfo.insert(new CardInformation(bankName, cardno, ccv, expDate));
    }

    /**
     * Returns a list of all cards
     * @return
     * @throws SQLException 
     */
    public List<CardInformation> getAllCards() throws SQLException {
        return dbCardInfo.getAllCards();
    }

    /**
     * Returns a card with the given number
     * @param number
     * @return
     * @throws SQLException 
     */
    public CardInformation getCardByNumber(String  number) throws SQLException {
        return dbCardInfo.getCardByNumber(number);
    }
    
    public String getCardNumber(CardInformation card){
        return card.getCardNumber();
    }
    
    public int getCardCCV(CardInformation card){
        return card.getCcv();
    }
    
    public String getBankName(CardInformation card){
        return card.getBankName();
    }
    
    public Date getCardExpDate(CardInformation card){
        return card.getExpDate();
    }

}

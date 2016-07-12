package de.commercetools.javacodingtask.models;

import io.sphere.sdk.models.Base;

import java.util.Currency;

/**
 * The order of a customer in an online shop.
 */
public class Order extends Base {
    private String customerId;
    private String id;
    private String pick;
    private Currency currency;
    private long centAmount;

    public Order() {
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(final String personId) {
        this.customerId = personId;
    }

    public long getCentAmount() {
        return centAmount;
    }

    public void setCentAmount(final long centAmount) {
        this.centAmount = centAmount;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(final Currency currency) {
        this.currency = currency;
    }

    public String getPick() {
        return pick;
    }

    public void setPick(final String pick) {
        this.pick = pick;
    }
}

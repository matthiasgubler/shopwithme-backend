package ch.zhaw.swm.wall.model;

import org.springframework.data.annotation.Id;

import java.math.BigInteger;

public class AbstractDocument {

    @Id
    private BigInteger id;

    public AbstractDocument() {
    }

    public AbstractDocument(BigInteger id) {
        this.id = id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    /**
     * Returns the identifier of the document.
     *
     * @return the id
     */
    public BigInteger getId() {
        return id;
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (this.id == null || obj == null || !(this.getClass().equals(obj.getClass()))) {
            return false;
        }

        AbstractDocument that = (AbstractDocument) obj;

        return this.id.equals(that.getId());
    }

    /*
     * (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        return id == null ? 0 : id.hashCode();
    }

    @Override
    public String toString() {
        return "AbstractDocument{"
            + "id=" + id
            + "}";
    }
}

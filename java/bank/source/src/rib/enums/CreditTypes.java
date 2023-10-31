package rib.enums;

public enum CreditTypes {
	CL("Consumer loans"), MI("Mortgage loan");
	public final String code;

    private CreditTypes(String code) {
        this.code = code;
    }
    public String getCode() {
    	return code;
    }
}
